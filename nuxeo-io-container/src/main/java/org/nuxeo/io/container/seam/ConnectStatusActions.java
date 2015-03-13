/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Nuxeo
 */

package org.nuxeo.io.container.seam;

import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage;
import org.nuxeo.connect.client.jsf.ConnectStatusActionBean;
import org.nuxeo.ecm.platform.ui.web.util.ComponentUtils;

/**
 * @author <a href="mailto:ak@nuxeo.com">Arnaud Kervern</a>
 */
@Name("connectStatus")
@Scope(ScopeType.CONVERSATION)
@Install(precedence = Install.DEPLOYMENT)
public class ConnectStatusActions extends ConnectStatusActionBean {
    @Override
    public String unregister() {
        facesMessages.add(StatusMessage.Severity.WARN, translate("label.admin.center.io.unregister"));
        return null;
    }

    protected static String translate(String label, Object... params) {
        return ComponentUtils.translate(FacesContext.getCurrentInstance(), label, params);
    }
}
