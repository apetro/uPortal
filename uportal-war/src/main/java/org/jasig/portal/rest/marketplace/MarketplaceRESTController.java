/*
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


package org.jasig.portal.rest.marketplace;

import org.jasig.portal.portlet.om.PortletCategory;
import org.jasig.portal.portlets.marketplace.MarketplacePortletDefinition;
import org.jasig.portal.portlets.marketplace.MarketplaceService;
import org.jasig.portal.security.IPerson;
import org.jasig.portal.security.IPersonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
public class MarketplaceRESTController {

    private MarketplaceService marketplaceService;

    private IPersonManager personManager;

    @RequestMapping(value="/marketplace/registry.json", method = RequestMethod.GET)
    ModelAndView getMarketplaceEntries(HttpServletRequest request, HttpServletRequest response) {

        IPerson person = this.personManager.getPerson(request);

        Set<MarketplacePortletDefinition> marketplacePortletDefinitions =
                this.marketplaceService.browseableMarketplaceEntriesFor(person);

        Set<PortletCategory> visibleNonEmptyPortletCategories =
                this.marketplaceService.browseableNonEmptyPortletCategoriesFor(person);

        Map portletsAndCategories = new HashMap();
        portletsAndCategories.put("portlets", marketplacePortletDefinitions);
        portletsAndCategories.put("categories", visibleNonEmptyPortletCategories);

        return new ModelAndView("json", "marketplace", portletsAndCategories);
    }

    public IPersonManager getPersonManager() {
        return personManager;
    }

    @Autowired
    public void setPersonManager(IPersonManager personManager) {
        this.personManager = personManager;
    }

    public MarketplaceService getMarketplaceService() {
        return marketplaceService;
    }

    @Autowired
    public void setMarketplaceService(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

}
