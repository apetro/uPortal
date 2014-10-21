/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portal.layout.profile;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

import org.jasig.portal.security.IPerson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for ChainingProfileMapperImpl.
 *
 * @author Jen Bourey, jennifer.bourey@gmail.com
 */
public class ChainingProfileMapperImplTest {
    
    ChainingProfileMapperImpl mapper = new ChainingProfileMapperImpl();
    
    @Mock IPerson person;
    @Mock HttpServletRequest request;

    @Mock IProfileMapper subMapper1;

    @Mock(extraInterfaces = IProfileSelectionRequestHandler.class)
    IProfileMapper subMapper2;

    @Mock(extraInterfaces = IProfileSelectionRequestHandler.class)
    IProfileMapper subMapper3;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mapper.setDefaultProfileName("profile");
        
        List<IProfileMapper> subMappers = new ArrayList<IProfileMapper>();
        subMappers.add(subMapper1);
        subMappers.add(subMapper2);
        subMappers.add(subMapper3);
        mapper.setSubMappers(subMappers);
    }
    
    @Test
    public void testDefaultProfile() {
        String fname = mapper.getProfileFname(person, request);
        assertEquals("profile", fname);
    }
    
    @Test
    public void testFirstProfile() {
        when(subMapper1.getProfileFname(person, request)).thenReturn("profile1");
        when(subMapper2.getProfileFname(person, request)).thenReturn("profile2");
        String fname = mapper.getProfileFname(person, request);
        assertEquals("profile1", fname);
    }

    @Test
    public void testSecondProfile() {
        when(subMapper2.getProfileFname(person, request)).thenReturn("profile2");
        String fname = mapper.getProfileFname(person, request);
        assertEquals("profile2", fname);
    }

    @Test
    public void testBroadcastsProfileSelectionRequestToSubMappers() {

        mapper.handleProfileSelectionRequest("key", person, request);

        // subMapper1 does not implement handler interface, so should be ignored
        verifyZeroInteractions(subMapper1);

        // subMapper2 and 3 each implement the interface,
        // so should each have had the opportunity to handle the selection.
        verify((IProfileSelectionRequestHandler) subMapper2).handleProfileSelectionRequest("key", person, request);
        verify((IProfileSelectionRequestHandler) subMapper3).handleProfileSelectionRequest("key", person, request);
    }

}
