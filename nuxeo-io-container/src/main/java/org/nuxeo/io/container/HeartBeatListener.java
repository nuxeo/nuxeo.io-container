/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     nuxeo.io Team
 */

package org.nuxeo.io.container;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.runtime.api.Framework;

import org.nuxeo.io.etcd.EtcdService;

/**
 * @since 5.9.3
 */
public class HeartBeatListener implements EventListener {

    public static final String ENVS_ALIVE_KEY_PATTERN = "/envs/%s/status/alive";

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
