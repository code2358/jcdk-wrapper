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

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class Jcdk3InstallationTest {

    public static final Path JCDK3_PATH = Paths.get(Jcdk3InstallationTest.class.getResource("/jcdk3").getPath());

    private Jcdk3Installation jcdk3Installation;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() throws InitializationException {
        jcdk3Installation = new Jcdk3Installation(JCDK3_PATH);
    }

    @Test
    public void detectInvalidJcdk3Success() throws Exception {
        Jcdk3Installation jcdk3Installation = new Jcdk3Installation(Jcdk2InstallationTest.JCDK2_PATH);
        assertThat(jcdk3Installation.isJcdkAvailable(), equalTo(false));
    }

    @Test
    public void detectValidJcdk3Success() throws Exception {
        assertThat(jcdk3Installation.isJcdkAvailable(), equalTo(true));
    }

    @Test
    public void getBasePathSuccess() throws Exception {
        assertThat(jcdk3Installation.getBasePath(), equalTo(JCDK3_PATH));
    }

    @Test
    public void getApiExportsPathSuccess() throws Exception {
        assertThat(jcdk3Installation.getApiExportPath(),
                equalTo(Paths.get(JCDK3_PATH.toString(), "/api_export_files")));
    }

    @Test
    public void getConverterClasspathSuccess() throws Exception {
        assertThat(jcdk3Installation.getConverterClasspath(),
                equalTo(Paths.get(JCDK3_PATH.toString(), "/lib/tools.jar")));
    }

    @Test
    public void getConverterClassSuccess() throws Exception {
        assertThat(jcdk3Installation.getConverterClass(), equalTo("com.sun.javacard.converter.Main"));
    }
}
