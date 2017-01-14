package com.github.code2358.javacard.jcdk;

/*-
 * #%L
 * jcdk-wrapper
 * %%
 * Copyright (C) 2017 code2358
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public final class Jcdk2Installation implements JcdkInstallation {
    private static final String API_EXPORTS = "/api_export_files";
    private static final String CONVERTER_JAR = "/lib/converter.jar";
    private static final String LIBS = "/lib/*";

    private final Path jcdk2Home;

    public Jcdk2Installation(Path jcdkPath) throws InitializationException {
        Objects.requireNonNull(jcdkPath);
        jcdk2Home = jcdkPath;
    }

    @Override
    public boolean isJcdkAvailable() {
        return Files.isReadable(getApiExportPath()) && Files.isReadable(getPath(CONVERTER_JAR));
    }

    @Override
    public Path getBasePath() {
        return jcdk2Home;
    }

    @Override
    public Path getApiExportPath() {
        return getPath(API_EXPORTS);
    }

    @Override
    public Path getConverterClasspath() {
        return getPath(LIBS);
    }

    @Override
    public String getConverterClass() {
        return "com.sun.javacard.converter.Converter";
    }

    private Path getPath(String subPath) {
        return Paths.get(jcdk2Home.toString(), subPath);
    }
}
