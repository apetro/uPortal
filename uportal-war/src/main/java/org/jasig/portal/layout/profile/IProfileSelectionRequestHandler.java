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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Handles a user having requested a particular profile selection.
 *
 * Profile selections are user desires for a particular profile.  Controller tier components that become aware of a
 * user desire (in practice as of this writing, the PortalPreAuthenticatedProcessingFilter that is in the /Login
 * handling chain) present that desire ("request") to an IProfileSelectionRequestHandler to be remembered in whatever
 * way is appropriate to the local uPortal implementation.
 *
 * @since uPortal 4.2
 */
public interface IProfileSelectionRequestHandler {
    void handleProfileSelectionRequest(String profileKey, HttpServletRequest request);
}
