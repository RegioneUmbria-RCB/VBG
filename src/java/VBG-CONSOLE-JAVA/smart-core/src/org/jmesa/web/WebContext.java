/*
 * Copyright 2004 original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.jmesa.web;

import java.io.Writer;
import java.util.Locale;
import java.util.Map;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public interface WebContext {

    public static final String LOCALE_SESSION_ATTRIBUTE_NAME = "org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE";

    public Object getApplicationInitParameter(String name);

    public Object getApplicationAttribute(String name);

    public void setApplicationAttribute(String name, Object value);

    public void removeApplicationAttribute(String name);

    public Object getPageAttribute(String name);

    public void setPageAttribute(String name, Object value);

    public void removePageAttribute(String name);

    public String getParameter(String name);

    public Map<?, ?> getParameterMap();

    public void setParameterMap(Map<?, ?> parameterMap);

    public Object getRequestAttribute(String name);

    public void setRequestAttribute(String name, Object value);

    public void removeRequestAttribute(String name);

    public Object getSessionAttribute(String name);

    public void setSessionAttribute(String name, Object value);

    public void removeSessionAttribute(String name);

    public Writer getWriter();

    public Locale getLocale();

    public void setLocale(Locale locale);

    public String getContextPath();

    public String getRealPath(String path);

    public Object getBackingObject();
}
