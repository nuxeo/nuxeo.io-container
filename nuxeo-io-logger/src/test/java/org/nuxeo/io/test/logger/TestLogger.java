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

package org.nuxeo.io.test.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.Test;
import org.nuxeo.io.log4j.IoLog4jFilter;

/**
 * @author <a href="mailto:ak@nuxeo.com">Arnaud Kervern</a>
 * @since 7.1
 */
public class TestLogger {

    @Test
    public void testRegex() {
        assertTrue(IoLog4jFilter.isNuxeoUrlLine("nuxeo.url=http://casad"));
        assertFalse(IoLog4jFilter.isNuxeoUrlLine("#nuxeo.url=http://casad"));
    }

    @Test
    public void testFile() throws IOException {
        URL resource = this.getClass().getClassLoader().getResource("nuxeo.conf");
        assertNotNull(resource);

        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath(resource.getFile());
        String nuxeoUrl = IoLog4jFilter.findNuxeoUrl(path);
        assertEquals("http://last:8080/nuxeo", nuxeoUrl);
    }
}
