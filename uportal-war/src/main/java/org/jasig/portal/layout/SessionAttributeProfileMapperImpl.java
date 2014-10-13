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
package org.jasig.portal.layout;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasig.portal.security.IPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 
 * @author Jen Bourey, jennifer.bourey@gmail.com
 * @version $Revision$
 */
public class SessionAttributeProfileMapperImpl implements IProfileMapper {
    public static final String DEFAULT_SESSION_ATTRIBUTE_NAME = "profileKey";

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String,String> mappings = Collections.<String,String>emptyMap();
    private String defaultProfileName = null;
    private String attributeName = DEFAULT_SESSION_ATTRIBUTE_NAME;
    
    /**
     * Session profile key to database profile fname mappings.
     */
    public void setMappings(Map<String,String> mappings) {
        this.mappings = mappings;
    }
    
    /**
     * Default profile name to return if no match is found, defaults to <code>null</code>.
     */
    public void setDefaultProfileName(String defaultProfileName) {
        this.defaultProfileName = defaultProfileName;
    }

    /**
     * 
     * @param attributeName
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    
    @Override
    public String getProfileFname(IPerson person, HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            logger.debug("Cannot get a session-stored profile fname from a null session, so returning null.");
            return null;
        }
        
        final String requestedProfileKey = (String) session.getAttribute(attributeName);
        if (requestedProfileKey != null) {
            final String profileName = mappings.get(requestedProfileKey);
            if (profileName != null) {
                logger.debug("The stored requested profile key {} mapped to profile fname {}.",
                        requestedProfileKey, profileName);
                return profileName;
            } else {
                logger.warn("The stored requested profile key {} does not map to any profile fname.",
                        requestedProfileKey);
            }
        } else {
            logger.trace("There is no requested profile key stored at session attribute {}.",
                    attributeName);
        }

        logger.trace("Falling back on default profile name {} .", defaultProfileName);
        return defaultProfileName;
    }

    /**
     * Store the requested profile key into the user Session so that this SessionAttributeProfileMapperImpl
     * can subsequently find it and use it to determine a profile mapping.
     * @param profileKey key to desired profile, or null indicating no desired profile.
     * @param session non-null Session
     */
    public void storeRequestedProfileKeyIntoSession(final String profileKey, final HttpSession session) {

        Assert.notNull(session, "Cannot store requested profile key into a null session.");

        session.setAttribute(this.attributeName, profileKey);

        logger.trace("Stored desired profile key [{}] into session (at attribute [{}]).", profileKey, attributeName);

        String fnameTheKeyMapsTo = this.mappings.get(profileKey);

        if (null == fnameTheKeyMapsTo) {
            logger.warn("The desired profile key {} has no mapping so will have no effect.",
                    profileKey);
        }

    }

    @Override
    public String toString() {
        return "SessionAttributeProfileMapper which considers session attribute [" + this.attributeName +
                "] as key to mappings [" + this.mappings +
                "], falling back on default [" + this.defaultProfileName +"] .";
    }

}
