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

public class Jcdk2InstallationTest {

    public static final Path JCDK2_PATH = Paths.get(Jcdk2InstallationTest.class.getResource("/jcdk2").getPath());

    private Jcdk2Installation Jcdk2Installation;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        Jcdk2Installation = new Jcdk2Installation(JCDK2_PATH);
    }

    @Test
    public void detectInvalidJcdk2Success() throws Exception {
        Jcdk2Installation Jcdk2Installation = new Jcdk2Installation(Jcdk3InstallationTest.JCDK3_PATH);
        assertThat(Jcdk2Installation.isJcdkAvailable(), equalTo(false));
    }

    @Test
    public void detectValidJcdk2Success() throws Exception {
        assertThat(Jcdk2Installation.isJcdkAvailable(), equalTo(true));
    }

    @Test
    public void getBasePathSuccess() throws Exception {
        assertThat(Jcdk2Installation.getBasePath(), equalTo(JCDK2_PATH));
    }

    @Test
    public void getApiExportsPathSuccess() throws Exception {
        assertThat(Jcdk2Installation.getApiExportPath(),
                equalTo(Paths.get(JCDK2_PATH.toString(), "/api_export_files")));
    }

    @Test
    public void getConverterClasspathSuccess() throws Exception {
        assertThat(Jcdk2Installation.getConverterClasspath(), equalTo(Paths.get(JCDK2_PATH.toString(), "/lib/*")));
    }

    @Test
    public void getConverterClassSuccess() throws Exception {
        assertThat(Jcdk2Installation.getConverterClass(), equalTo("com.sun.javacard.converter.Converter"));
    }
}
