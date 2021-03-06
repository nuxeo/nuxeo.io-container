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
package org.nuxeo.io.container.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.connect.data.DownloadablePackage;
import org.nuxeo.connect.packages.PackageManager;
import org.nuxeo.connect.update.PackageState;
import org.nuxeo.connect.update.PackageType;
import org.nuxeo.etcd.EtcdService;
import org.nuxeo.io.container.EnvConstants;
import org.nuxeo.runtime.api.Framework;

/**
 * @since 5.9.4
 */
public class PusherServiceImpl implements PusherService {

    private static final Log log = LogFactory.getLog(PusherServiceImpl.class);

    public static final String SERVICE_KEY_PATTERN = "/services/%s";

    public static final String SERVICE_STATUS_KEY_PATTERN = SERVICE_KEY_PATTERN
            + "/%d/status";

    public static final String SERVICE_CURRENT_STATUS_KEY_PATTERN = SERVICE_STATUS_KEY_PATTERN
            + "/current";

    public static final String SERVICE_CONFIG_KEY_PATTERN = SERVICE_KEY_PATTERN
            + "/%d/config";

    public static final String SERVICE_CONFIG_PACKAGES = SERVICE_CONFIG_KEY_PATTERN
            + "/packages";

    public static final String SERVICE_ALIVE_STATUS_KEY_PATTERN = SERVICE_STATUS_KEY_PATTERN
            + "/alive";

    protected static int _1 = 1;

    @Override
    public void pushPackages() {
        EtcdService etcdService = Framework.getLocalService(EtcdService.class);
        PackageManager pm = Framework.getLocalService(PackageManager.class);
        String key = String.format(SERVICE_CONFIG_PACKAGES,
                System.getenv(EnvConstants.ENV_TECH_ID_VAR), _1);

        // Fetch all local packages "running"
        List<DownloadablePackage> installedPackages = pm.listLocalPackages(PackageType.getByValue("addon"));
        List<String> installedPkgIds = new ArrayList<>();
        for (DownloadablePackage installPackage : installedPackages) {
            if (pm.isInstalled(installPackage)) {
                installedPkgIds.add(installPackage.getName());
            }
        }

        // Fetch studio bundles "running"
        List<DownloadablePackage> installedStudios = pm.listAllStudioRemoteOrLocalPackages();
        for (DownloadablePackage studioPackage : installedStudios) {
            if (!PackageState.getByValue(studioPackage.getState()).isInstalled()) {
                continue;
            }
            installedPkgIds.add(studioPackage.getId());
        }

        String packages = StringUtils.join(installedPkgIds, " ");
        log.info("Update installed package list: " + packages);
        etcdService.set(key, packages);
    }

    @Override
    public void pushCurrentStatus() {
        EtcdService etcdService = Framework.getLocalService(EtcdService.class);
        String key = String.format(SERVICE_CURRENT_STATUS_KEY_PATTERN,
                System.getenv(EnvConstants.ENV_TECH_ID_VAR), _1);
        etcdService.set(key, "started");
    }

    @Override
    public void pushAliveStatus() {
        EtcdService etcdService = Framework.getLocalService(EtcdService.class);
        String key = String.format(SERVICE_ALIVE_STATUS_KEY_PATTERN,
                System.getenv(EnvConstants.ENV_TECH_ID_VAR), _1);
        etcdService.set(key, "1", EnvConstants.TTL);
    }
}
