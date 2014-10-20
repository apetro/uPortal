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

/**
 * API for user selection of profile.
 *
 * Disturbingly identical to IProfileSelectionRegistry.
 */
public interface IProfileSelectionService {

    /**
     * Returns the fname of the profile selected by the user identified by the given username,
     * or null indicating user apathy (no registered profile selection.)
     *
     * @param userName non-null String username of user who may have made a selection
     * @return String fname of selected profile, or null if no selection
     * @throws java.lang.IllegalArgumentException if userName is null
     */
    public String profileSelectionForUser(String userName);

    /**
     * Register user selection of a profile fname.
     * Selecting null registers the lack of a selection.
     *
     * @param userName non-null username of user who my have selected a profile fname
     * @param profileFName fname of profile the user is selecting, or null to have no selection.
     * @throws java.lang.IllegalArgumentException if userName is null
     */
    public void registerUserProfileSelection(String userName, String profileFName);
}
