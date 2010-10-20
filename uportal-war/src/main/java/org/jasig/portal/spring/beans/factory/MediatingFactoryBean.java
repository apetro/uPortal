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

package org.jasig.portal.spring.beans.factory;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * Takes a list of bean names and attempts to load each in order. If the loading of a bean
 * throws an Exception it is logged and the next bean name in the list is tried. If no beans
 * can be loaded a {@link BeanCreationException} is thrown.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public class MediatingFactoryBean extends AbstractFactoryBean implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private List<String> delegateBeanNames;
    private Class<?> type = null;
    
    /**
     * @return the delegateBeanNames
     */
    public List<String> getDelegateBeanNames() {
        return this.delegateBeanNames;
    }
    /**
     * @param delegateBeanNames the delegateBeanNames to set
     */
    public void setDelegateBeanNames(List<String> delegateBeanNames) {
        this.delegateBeanNames = delegateBeanNames;
    }
    
    /**
     * @return the type
     */
    public Class<?> getType() {
        return this.type;
    }
    /**
     * @param type the type to set
     */
    public void setType(Class<?> type) {
        this.type = type;
    }
    
    
    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.AbstractFactoryBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notEmpty(this.delegateBeanNames, "delegateBeanNames list cannot be empty");
        
        super.afterPropertiesSet();
    }
    
    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.AbstractFactoryBean#createInstance()
     */
    @Override
    protected Object createInstance() throws Exception {
        for (final String beanName : this.delegateBeanNames) {
            try {
                final Object bean;
                if (this.type == null) {
                    bean = this.applicationContext.getBean(beanName);
                }
                else {
                    bean = this.applicationContext.getBean(beanName, this.type);
                }
                
                if (this.logger.isInfoEnabled()) {
                    this.logger.info("Loaded bean for name " + beanName);
                }
                
                return bean;
            }
            catch (Exception e) {
                final String msg = "Failed to load bean '" + beanName + "' from ApplicationContext" + (this.type != null ? " expecting type: " + this.type : "") + 
                    ". Will try to load the next bean in the list instead. Error message from the attempt to load this bean ('" + beanName + "'): ";
                
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug(msg, e);
                }
                else if (this.logger.isInfoEnabled()) {
                    this.logger.info(msg + " " + e.getMessage() + " (enable debug for stack trace)");
                }
            }
        }
        
        throw new BeanCreationException("None of the configured bean names could be loaded. BeanNames: " + this.delegateBeanNames);
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.AbstractFactoryBean#getObjectType()
     */
    @Override
    public Class<?> getObjectType() {
        return this.type;
    }
}