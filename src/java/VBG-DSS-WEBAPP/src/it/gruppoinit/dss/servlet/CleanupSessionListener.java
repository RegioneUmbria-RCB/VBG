/**
 * verifica-firma - a simple web application for verifying CMS/PKCS7 signed files Copyright (c) 2009 Roberto Resoli -
 * Comune di Trento;
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * 
 */
package it.gruppoinit.dss.servlet;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import org.apache.commons.io.FileUtils;

public class CleanupSessionListener implements HttpSessionListener {

    private Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public void sessionCreated(HttpSessionEvent hse) {

	String s = hse.getSession().getId();
	log.info("Session " + s + " created.");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {

	String dirToRemove = hse.getSession().getId();
	log.info(dirToRemove + " destroyed!");
	String parentPath = hse.getSession().getServletContext().getRealPath("/WEB-INF/files");
	File d = new File(parentPath, dirToRemove);
	if (d.exists() && d.isDirectory()) {
	    try {
		FileUtils.deleteDirectory(d);
		log.info(dirToRemove + " removed");
	    } catch (IOException e) {
		log.severe(e.getMessage());
		log.warning(dirToRemove + " NOT removed!");
	    }
	} else
	    log.info(dirToRemove + " not exists or is not a directory!");
    }
}
