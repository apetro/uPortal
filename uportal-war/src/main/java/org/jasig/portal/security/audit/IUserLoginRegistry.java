/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.portal.security.audit;

import org.joda.time.ReadableInstant;

/**
 * Registry of user logins.
 * Registry tier in a Service-Registry-Dao-JPA architecture.
 * @since uPortal 4.2
 */
public interface IUserLoginRegistry {

    /**
     * Store a login into the registry.
     *
     * @param username the non-null username of the user who logged in
     * @param momentOfLogin the non-null moment of login
     */
    public void storeUserLogin(String username, ReadableInstant momentOfLogin);

    /**
     * Retrieve from the registry the most recent login by a given username,
     * or null if none.
     *
     * @param username non-null username of user who might have previously logged in
     * @return the most recent login by the given user, or null if none.
     */
    public IUserLogin mostRecentLoginBy(String username);

}