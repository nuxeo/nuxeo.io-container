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

import org.apache.commons.lang.StringUtils;
import org.nuxeo.connect.data.DownloadablePackage;
import org.nuxeo.connect.packages.PackageManager;
import org.nuxeo.connect.update.PackageState;
import org.nuxeo.connect.update.PackageType;
import org.nuxeo.ecm.admin.runtime.PlatformVersionHelper;
import org.nuxeo.io.container.EnvConstants;
import org.nuxeo.io.etcd.EtcdService;
import org.nuxeo.runtime.api.Framework;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 5.9.4
 */
public class PusherServiceImpl implements PusherService {

    @Override
    public void pushPackages() {
        EtcdService etcdService = Framework.getLocalService(EtcdService.class);
        PackageManager pm = Framework.getLocalService(PackageManager.class);
        String key = String.format(EnvConstants.ENV_INSTALLED_PKGS,
                System.getenv(EnvConstants.ENV_TECH_ID_VAR));
        String targetPlatform = PlatformVersionHelper.getPlatformFilter();

        // Fetch all marketplace packages "running"
        List<DownloadablePackage> installedPackages = pm.listUpdatePackages
                (PackageType.getByValue("addon"), targetPlatform);
        List<String> installedPkgIds = new ArrayList<>();
        for (DownloadablePackage installPackage : installedPackages) {
            installedPkgIds.add(installPackage.getId());
        }

        // Fetch studio bundles "running"
        List<DownloadablePackage> installedStudios = pm
                .listAllStudioRemoteOrLocalPackages();
        for (DownloadablePackage studioPackage : installedStudios) {
            if (!PackageState.getByValue(studioPackage.getState())
                    .isInstalled()) {
                continue;
            }
            installedPkgIds.add(studioPackage.getId());
        }
        etcdService.set(key, StringUtils.join(installedPkgIds, " "));
    }

    @Override
    public void pushCurrentStatus() {
        EtcdService etcdService = Framework.getLocalService(EtcdService.class);
        String key = String.format(EnvConstants.ENVS_CURRENT_KEY_PATTERN,
                System.getenv(EnvConstants.ENV_TECH_ID_VAR));
        etcdService.set(key, "started");
    }

    @Override
    public void pushAliveStatus() {
        EtcdService etcdService = Framework.getLocalService(EtcdService.class);
        String key = String.format(EnvConstants.ENVS_ALIVE_KEY_PATTERN,
                System.getenv(EnvConstants.ENV_TECH_ID_VAR));
        etcdService.set(key, "1", EnvConstants.TTL);
    }
}
