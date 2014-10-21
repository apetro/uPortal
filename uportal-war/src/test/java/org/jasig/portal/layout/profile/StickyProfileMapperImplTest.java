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

import org.jasig.portal.security.IPerson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class StickyProfileMapperImplTest {

    StickyProfileMapperImpl stickyMapper;

    @Mock IPerson person;

    @Mock HttpServletRequest request;

    @Mock IProfileSelectionRegistry registry;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        when(registry.profileSelectionForUser("bobby")).thenReturn("profileFNameFromRegistry");

        when(person.getUserName()).thenReturn("bobby");

        stickyMapper = new StickyProfileMapperImpl();
        stickyMapper.setProfileSelectionRegistry(registry);

    }

    /**
     * Test that when the underlying service has a stored selection for a user, reflects that selection.
     */
    @Test
    public void testReflectsStoredSelection() {

        final String mappedFName = stickyMapper.getProfileFname(person, request);

        assertEquals("profileFNameFromRegistry", mappedFName);

    }

    /**
     * Test that when asked about the profile mapping for a null IPerson,
     * throws NullPointerException (which is the Validate.notNull() behavior).
     */
    @Test( expected = NullPointerException.class )
    public void testThrowsNullPointerExceptionOnNullPerson() {

        stickyMapper.getProfileFname(null, request);

    }

    /**
     * Test that when asked about the profile mapping for a null HttpServletRequest,
     * throws NullPointerException (which is the Validate.notNull() behavior).
     */
    @Test( expected = NullPointerException.class )
    public void testThrowsNullPointerExceptionOnServletRequest() {

        stickyMapper.getProfileFname(person, null);

    }

    /**
     * Test that when asked about the profile mapping for a broken IPerson with a null userName,
     * throws NullPointerException (which is the Validate.notNull() behavior).
     */
    @Test( expected = NullPointerException.class )
    public void testThrowsNullPointerExceptionOnNullUsernamedIPerson() {

        // overrides the mock behavior specified in setUp().
        when(person.getUserName()).thenReturn(null);

        stickyMapper.getProfileFname(person, request);

    }

}
