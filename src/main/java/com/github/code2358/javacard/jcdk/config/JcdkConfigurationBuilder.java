package com.github.code2358.javacard.jcdk.config;

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

import com.github.code2358.javacard.jcdk.ConfigurationException;
import com.github.code2358.javacard.jcdk.JcdkInstallation;
import com.github.code2358.javacard.jcdk.utils.StringJoiner;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class JcdkConfigurationBuilder {

    private JcdkInstallation jcdkInstallation;
    private Path classesDirectory;
    private Path outputDirectory;
    private List<Path> exportPaths = new ArrayList<>();

    private AppletId appletId;
    private AppletClass appletClass;
    private AppletVersion appletVersion;

    boolean supportInt32 = false;

    public JcdkConfigurationBuilder jcdkInstallation(JcdkInstallation jcdkInstallation) {
        this.jcdkInstallation = Objects.requireNonNull(jcdkInstallation);
        return this;
    }

    public JcdkConfigurationBuilder classesDirectory(Path classesDirectory) {
        this.classesDirectory = Objects.requireNonNull(classesDirectory);
        return this;
    }

    public JcdkConfigurationBuilder outputDirectory(Path outputDirectory) {
        this.outputDirectory = Objects.requireNonNull(outputDirectory);
        return this;
    }

    public JcdkConfigurationBuilder exportPath(List<Path> paths) {
        Objects.requireNonNull(paths);
        exportPaths.addAll(paths);
        return this;
    }

    public JcdkConfigurationBuilder exportPath(Path path) {
        Objects.requireNonNull(path);
        exportPaths.add(path);
        return this;
    }

    public JcdkConfigurationBuilder appletId(String appletId) {
        Objects.requireNonNull(appletId);
        this.appletId = new AppletId(appletId);
        return this;
    }

    public JcdkConfigurationBuilder appletClass(String appletClass) {
        Objects.requireNonNull(appletClass);
        this.appletClass = new AppletClass(appletClass);
        return this;
    }

    public JcdkConfigurationBuilder appletVersion(String appletVersion) {
        Objects.requireNonNull(appletVersion);
        this.appletVersion = new AppletVersion(appletVersion);
        return this;
    }

    public JcdkConfigurationBuilder supportInt32(boolean supportInt32) {
        this.supportInt32 = supportInt32;
        return this;
    }

    public String build() throws ConfigurationException {
        ensureThatRequiredParametersAvailable();

        StringJoiner commandString = new StringJoiner(" ");
        commandString.add("-classdir");
        commandString.add(classesDirectory.toString());
        if (supportInt32) {
            commandString.add("-i");
        }
        commandString.add("-exportpath");
        commandString.add(exportPathList());
        commandString.add("-applet");
        commandString.add(formatAsAid(appletId.toString()));
        commandString.add(appletClass.toString());
        commandString.add("-d");
        commandString.add(outputDirectory.toString());
        commandString.add("-out");
        commandString.add("CAP");
        commandString.add("EXP");
        commandString.add("JCA");
        commandString.add("-v");
        commandString.add(appletClass.getPackage());
        commandString.add(formatAsAid(appletId.getPackageId()));
        commandString.add(appletVersion.toString());

        return commandString.toString();
    }

    private String exportPathList() {
        StringJoiner exportPathList = new StringJoiner(":");

        exportPathList.add(jcdkInstallation.getApiExportPath().toString());

        for (Path exportPath : exportPaths) {
            exportPathList.add(exportPath.toString());
        }

        return exportPathList.toString();
    }

    private static String formatAsAid(String id) {
        return "0x" + id.replaceAll(":", ":0x");
    }

    private void ensureThatRequiredParametersAvailable() throws ConfigurationException {
        if (jcdkInstallation == null) {
            throw new ConfigurationException("Missing parameter jcdkInstallation");
        }

        if (classesDirectory == null) {
            throw new ConfigurationException("Missing parameter classesDirectory");
        }

        if (outputDirectory == null) {
            throw new ConfigurationException("Missing parameter outputDirectory");
        }

        if (appletId == null) {
            throw new ConfigurationException("Missing parameter appletId");
        }

        if (appletClass == null) {
            throw new ConfigurationException("Missing parameter appletClass");
        }

        if (appletVersion == null) {
            throw new ConfigurationException("Missing parameter appletVersion");
        }
    }
}
