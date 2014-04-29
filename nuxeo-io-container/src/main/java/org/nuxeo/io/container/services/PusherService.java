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

/**
 * @since 5.9.4
 */
public interface PusherService {

    /**
     * Push installed packages listing in etcd
     */
    void pushPackages();

    /**
     * Push "started" environment status in etcd
     */
    void pushCurrentStatus();

    /**
     * Push "alive" environment status in etcd
     */
    void pushAliveStatus();
}
