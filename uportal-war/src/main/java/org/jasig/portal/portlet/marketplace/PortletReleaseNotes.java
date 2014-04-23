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

package org.jasig.portal.portlet.marketplace;

import java.util.List;

import org.joda.time.DateTime;

public class PortletReleaseNotes {

	private DateTime releaseDate;
	private List<String> releaseNotes;
	
	public PortletReleaseNotes(){
		
	}
	
	/**
	 * 
	 * @return releaseDate as String. Can be null if not set via portlet preference.
	 */
	public DateTime getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(DateTime releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	/**
	 * 
	 * @return List of releaseNotes as strings. Can be null if not set via portlet preference.
	 */
	public List<String> getReleaseNotes() {
		return releaseNotes;
	}
	
	public void setReleaseNotes(List<String> releaseNotes) {
		this.releaseNotes = releaseNotes;
	}
}
