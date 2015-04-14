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

package org.apache.log4j.net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Based on the default SocketAppender, the only difference is that it sends an event to the socket an event containing
 * only Strings in his MDC. It allows the event to correctly been deserialized by Logstash with a simple context without
 * any Unknown classes.
 *
 * @author <a href="mailto:ak@nuxeo.com">Arnaud Kervern</a>
 */
public class StringifiedSocketAppender extends SocketAppender {

    private static final int RESET_FREQUENCY = 1;

    @Override
    public void append(LoggingEvent event) {
        if (event == null)
            return;

        if (address == null) {
            errorHandler.error("No remote host is set for SocketAppender named \"" + this.name + "\".");
            return;
        }

        if (oos != null) {
            try {
                Set<Map.Entry> set = event.getProperties().entrySet();
                for (Map.Entry entry : set) {
                    event.setProperty((String) entry.getKey(), entry.getValue().toString());
                }
                if (getApplication() != null) {
                    event.setProperty("application", getApplication());
                }

                oos.writeObject(event);
                oos.flush();
                if (++counter >= RESET_FREQUENCY) {
                    counter = 0;
                    oos.reset();
                }
            } catch (IOException e) {
                if (e instanceof InterruptedIOException) {
                    Thread.currentThread().interrupt();
                }
                oos = null;
                LogLog.warn("Detected problem with connection: " + e);
                if (reconnectionDelay > 0) {
                    fireConnector();
                } else {
                    errorHandler.error("Detected problem with connection, not reconnecting.", e,
                            ErrorCode.GENERIC_FAILURE);
                }
            }
        }
    }

}
