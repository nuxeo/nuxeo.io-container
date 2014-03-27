/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/).
 * This is unpublished proprietary source code of Nuxeo SA. All rights reserved.
 * Notice of copyright on this source code does not indicate publication.
 *
 * Contributors:
 *     Thomas Roger
 */

package com.nuxeo.io.container;

import com.nuxeo.io.etcd.EtcdService;
import org.nuxeo.runtime.api.Framework;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class IoContainerServletContextListener implements ServletContextListener {

    public static final String ENVS_ALIVE_KEY_PATTERN = "/envs/%s/status/current";

    public static final String ENV_TECH_ID_VAR = "ENV_TECH_ID";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        EtcdService etcdService = Framework.getLocalService(EtcdService.class);
        String key = String.format(ENVS_ALIVE_KEY_PATTERN,
                System.getenv(ENV_TECH_ID_VAR));
        etcdService.set(key, "started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // TODO mark it stopped?
    }

}
