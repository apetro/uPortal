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

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.Validate;
import org.jasig.portal.security.IPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by apetro on 10/21/14.
 */
public class StickyProfileMapperImpl
    implements IProfileMapper, IProfileSelectionRequestHandler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // autowired
    private IProfileSelectionRegistry profileSelectionRegistry;

    // dependency injected as "mappings", required.
    private Map<String, String> immutableMappings;

    @Override
    public void handleProfileSelectionRequest(final String profileKey, IPerson person, final HttpServletRequest request) {

        Validate.notNull(profileKey, "Cannot handle selection of null profile.");
        Validate.notNull(person, "Cannot handle selection by a null person.");
        Validate.notNull(person.getUserName(), "Cannot handle selection by a person with a null username.");
        Validate.notNull(request, "Cannot handle selection of null profile in context of null request.");

        String userName = person.getUserName();

        if (!immutableMappings.containsKey(profileKey)) {
            logger.warn("User desired a profile by a key {} that does not map to any profile fname.  Ignoring.",
                    profileKey);
            return;
        }


        final String profileFName = immutableMappings.get(profileKey);

        profileSelectionRegistry.registerUserProfileSelection(userName, profileFName);
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

    @Required
    public void setMappings(Map<String, String> mappings) {

        Validate.notNull(mappings);

        this.immutableMappings = ImmutableMap.copyOf(mappings);
    }
}
