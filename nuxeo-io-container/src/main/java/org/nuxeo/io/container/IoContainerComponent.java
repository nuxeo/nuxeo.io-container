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

import org.apache.commons.lang.StringUtils;
import org.nuxeo.connect.data.DownloadablePackage;
import org.nuxeo.connect.packages.PackageManager;
import org.nuxeo.connect.update.PackageType;
import org.nuxeo.ecm.admin.runtime.PlatformVersionHelper;
import org.nuxeo.io.container.services.PusherService;
import org.nuxeo.io.etcd.EtcdService;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.model.ComponentContext;
import org.nuxeo.runtime.model.DefaultComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 5.9.3
 */
public class IoContainerComponent extends DefaultComponent {

    @Override
    public void applicationStarted(ComponentContext context) throws Exception {
        PusherService pusherService = Framework.getLocalService(PusherService.class);
        pusherService.pushCurrentStatus();
        pusherService.pushPackages();
    }

}
