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
package org.nuxeo.io.log4j;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author <a href="mailto:ak@nuxeo.com">Arnaud Kervern</a>
 * @since 7.1
 */
public class IoLog4jFilter extends Filter {

    protected static String nuxeoUrl;

    protected static final Pattern REGEX = Pattern.compile("nuxeo\\.url=.+");

    @Override
    public int decide(LoggingEvent event) {
        event.setProperty("nuxeo_url", getNuxeoUrl());
        return Filter.NEUTRAL;
    }

    protected String getNuxeoUrl() {
        if (nuxeoUrl == null) {
            try {
                FileSystem fs = FileSystems.getDefault();
                Path path = fs.getPath("./bin/nuxeo.conf");
                nuxeoUrl = findNuxeoUrl(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return nuxeoUrl;
    }

    public static String findNuxeoUrl(Path path) throws IOException {
        // XXX Should be better to get the first match using a reverse reader.
        String lastMatch = "";
        for (String line : Files.readAllLines(path)) {
            if (isNuxeoUrlLine(line)) {
                lastMatch = line.split("=")[1].trim();
            }
        }
        return lastMatch;
    }

    public static boolean isNuxeoUrlLine(String line) {
        return REGEX.matcher(line).matches();
    }
}
