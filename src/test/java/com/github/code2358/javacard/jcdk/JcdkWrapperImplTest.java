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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class JcdkWrapperImplTest {
    public static final Path JCDK2_PATH = Paths.get(JcdkWrapperImplTest.class.getResource("/jcdk2").getPath());
    public static final Path JCDK3_PATH = Paths.get(JcdkWrapperImplTest.class.getResource("/jcdk3").getPath());
    private Path configurationFile;

    // for jcdk version specific tests
    private Jcdk2Installation jcdk2Installation;
    private Jcdk3Installation jcdk3Installation;

    // for jcdk version independent tests
    private JcdkInstallation jcdkInstallation;
    private JcdkInstallation jcdkInstallationWrongPath;
    private Path jcdkWrongPath;

    // runtime tests
    private JcdkWrapperImpl jcdkWrapper;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() throws InitializationException, IOException {
        configurationFile = Files.createTempFile("", "");

        // for jcdk version specific tests
        jcdk2Installation = new Jcdk2Installation(JCDK2_PATH);
        jcdk3Installation = new Jcdk3Installation(JCDK3_PATH);

        // for jcdk version independent tests
        jcdkInstallation = jcdk3Installation;
        jcdkWrongPath = Files.createTempDirectory("");
        jcdkInstallationWrongPath = new Jcdk3Installation(jcdkWrongPath);

        // runtime tests
        jcdkWrapper = new JcdkWrapperImpl(jcdkInstallation, configurationFile);
    }

    @Test
    public void generateCommandJcdk2Success() throws Exception {
        String command = JcdkWrapperImpl.generateCommand(jcdk2Installation, configurationFile);

        assertThat(command,
                equalTo(String.format("java -cp %s " + "-Djc.home=%s %s -config %s",
                        jcdk2Installation.getConverterClasspath(), jcdk2Installation.getBasePath(),
                        jcdk2Installation.getConverterClass(), configurationFile)));
    }

    @Test
    public void generateCommandJcdk3Success() throws Exception {
        String command = JcdkWrapperImpl.generateCommand(jcdk3Installation, configurationFile);

        assertThat(command,
                equalTo(String.format("java -cp %s " + "-Djc.home=%s %s -config %s",
                        jcdk3Installation.getConverterClasspath(), jcdk3Installation.getBasePath(),
                        jcdk3Installation.getConverterClass(), configurationFile)));
    }

    @Test
    public void generateCommandJcdkWithoutConfigurationFailure() throws Exception {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Provided configuration path is not valid: " + configurationFile);

        Files.delete(configurationFile);
        JcdkWrapperImpl.generateCommand(jcdkInstallation, configurationFile);
    }

    @Test
    public void generateCommandJcdkWithWrongJcdkPathFailure() throws Exception {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Provided JCDK path is not valid: " + jcdkWrongPath);

        JcdkWrapperImpl.generateCommand(jcdkInstallationWrongPath, configurationFile);
    }

    @Test
    public void convertCannotFindClassFailure() throws Exception {
        exception.expect(ConvertionException.class);
        exception.expectMessage("Error during execution of JCDK converter (see output for details).");

        jcdkWrapper.convert();
    }

    @Test
    public void checkoutAfterConvertFailureSuccess() throws Exception {
        try {
            jcdkWrapper.convert();
        } catch (ConvertionException e) {
        }

        assertThat(jcdkWrapper.getJcdkOutput(), startsWith("Error: Could not find or load main class "));
    }

    @Test
    public void emptyOutputSuccess() throws Exception {
        assertThat(jcdkWrapper.getJcdkOutput(), nullValue());
    }
}
