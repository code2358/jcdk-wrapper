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

import com.github.code2358.javacard.jcdk.utils.CommandLine;
import com.github.code2358.javacard.jcdk.utils.StringJoiner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class JcdkWrapperImpl implements JcdkWrapper {

    private final JcdkInstallation jcdkInstallation;
    private final Path configurationFile;

    private ByteArrayOutputStream jcdkOutput;

    public JcdkWrapperImpl(JcdkInstallation jcdkInstallation, Path configurationFile) {
        Objects.requireNonNull(jcdkInstallation);
        Objects.requireNonNull(configurationFile);

        this.jcdkInstallation = jcdkInstallation;
        this.configurationFile = configurationFile;
    }

    @Override
    public final void convert() throws ConvertionException {
        try {
            jcdkOutput = new ByteArrayOutputStream();

            int result = CommandLine.execute(generateCommand(jcdkInstallation, configurationFile), jcdkOutput);
            if (result != 0) {
                throw new ConvertionException("Error during execution of JCDK converter (see output for details).");
            }

        } catch (IOException | InterruptedException e) {
            throw new ConvertionException(e.getMessage(), e);
        }
    }

    public String getJcdkOutput() throws UnsupportedEncodingException {
        if (jcdkOutput != null) {
            return jcdkOutput.toString("UTF-8");
        }
        return null;
    }

    protected static String generateCommand(JcdkInstallation jcdkInstallation, Path configurationFile) {
        if (!jcdkInstallation.isJcdkAvailable()) {
            throw new RuntimeException(
                    "Provided JCDK path is not valid: " + jcdkInstallation.getBasePath().toString());
        }
        if (!Files.isReadable(configurationFile)) {
            throw new RuntimeException(
                    "Provided configuration path is not valid: " + configurationFile.toString());
        }

        StringJoiner commandString = new StringJoiner(" ");
        commandString.add("java");
        commandString.add("-cp");
        commandString.add(jcdkInstallation.getConverterClasspath().toString());

        commandString.add("-Djc.home=" + jcdkInstallation.getBasePath().toString());
        commandString.add(jcdkInstallation.getConverterClass());
        commandString.add("-config");
        commandString.add(configurationFile.toString());

        return commandString.toString();
    }
}
