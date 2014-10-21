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

import org.apache.commons.lang3.Validate;
import org.jasig.portal.security.IPerson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by apetro on 10/21/14.
 */
public class StickyProfileMapperImpl
    implements IProfileMapper, IProfileSelectionRequestHandler {

    private IProfileSelectionRegistry profileSelectionRegistry;

    @Override
    public void handleProfileSelectionRequest(final String profileKey, IPerson person, final HttpServletRequest request) {




    }

    @Override
    public String getProfileFname(IPerson person, HttpServletRequest request) {

        Validate.notNull(person, "Cannot get profile fname for a null person.");
        Validate.notNull(request, "Cannot get profile fname for a null request.");

        final String userName = person.getUserName();
        Validate.notNull(userName, "Cannot get profile fname for a null username.");

        return this.profileSelectionRegistry.profileSelectionForUser(userName);

    }


    @Autowired
    public void setProfileSelectionRegistry(final IProfileSelectionRegistry profileSelectionRegistry) {
        this.profileSelectionRegistry = profileSelectionRegistry;
    }

}
