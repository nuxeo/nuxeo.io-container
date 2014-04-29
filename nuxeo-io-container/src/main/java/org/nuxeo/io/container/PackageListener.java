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
import org.nuxeo.io.container.services.PusherService;
import org.nuxeo.runtime.api.Framework;

/**
 * Update installed packages listing in etcd
 *
 * @since 5.9.4
 */
public class PackageListener implements EventListener {

    @Override
    public void handleEvent(Event event) throws ClientException {
        PusherService pusherService = Framework.getLocalService(PusherService.class);
        pusherService.pushPackages();
    }

}
