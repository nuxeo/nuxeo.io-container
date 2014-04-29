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

/**
 * @since 5.9.4
 */
public class EnvConstants {

    public static final String ENVS_CURRENT_KEY_PATTERN = "/envs/%s/status/current";

    public static final String ENV_INSTALLED_PKGS = "/envs/%s/config/packages";

    public static final String ENVS_ALIVE_KEY_PATTERN = "/envs/%s/status/alive";

    public static final String ENV_TECH_ID_VAR = "ENV_TECH_ID";

    public static final int TTL = 10;
}
