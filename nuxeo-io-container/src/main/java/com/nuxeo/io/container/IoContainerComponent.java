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
import org.nuxeo.runtime.model.ComponentContext;
import org.nuxeo.runtime.model.DefaultComponent;

/**
 * @since 5.9.3
 */
public class IoContainerComponent extends DefaultComponent {

    public static final String ENVS_ALIVE_KEY_PATTERN = "/envs/%s/status/current";

    public static final String ENV_TECH_ID_VAR = "ENV_TECH_ID";

    @Override
    public void applicationStarted(ComponentContext context) throws Exception {
        EtcdService etcdService = Framework.getLocalService(EtcdService.class);
        String key = String.format(ENVS_ALIVE_KEY_PATTERN,
                System.getenv(ENV_TECH_ID_VAR));
        etcdService.set(key, "started");


    }

}
