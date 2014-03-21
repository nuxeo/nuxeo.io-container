/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/).
 * This is unpublished proprietary source code of Nuxeo SA. All rights reserved.
 * Notice of copyright on this source code does not indicate publication.
 *
 * Contributors:
 *     Thomas Roger
 */

package com.nuxeo.io.container;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.runtime.api.Framework;

import com.nuxeo.io.etcd.EtcdService;

/**
 * @since 1.0
 */
public class HeartBeatListener implements EventListener {

    public static final String ENVS_ALIVE_KEY_PATTERN = "/envs/%s/alive";

    public static final String ENV_TECH_ID_VAR = "ENV_TECH_ID";

    public static final int TTL = 10;

    @Override
    public void handleEvent(Event event) throws ClientException {
        EtcdService etcdService = Framework.getLocalService(EtcdService.class);
        String key = String.format(ENVS_ALIVE_KEY_PATTERN,
                System.getenv(ENV_TECH_ID_VAR));
        etcdService.set(key, "1", TTL);
    }

}
