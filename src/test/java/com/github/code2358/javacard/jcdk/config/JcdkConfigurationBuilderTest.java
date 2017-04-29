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
import com.github.code2358.javacard.jcdk.Jcdk3Installation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Paths;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class JcdkConfigurationBuilderTest {

    private JcdkConfigurationBuilder builder;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        builder = new JcdkConfigurationBuilder();
    }

    @Test
    public void validMinimalConfigSuccess() throws Exception {
        String config = builder.jcdkInstallation(new Jcdk3Installation(Paths.get("/some/jcdk")))
                .classesDirectory(Paths.get("/some/classes"))
                .outputDirectory(Paths.get("/some/output"))
                .appletId("01:02:03:04:05:06")
                .appletClass("some.Class")
                .appletVersion("1.2")
                .build();

        assertThat(config, equalTo("-classdir /some/classes "
                + "-exportpath /some/jcdk/api_export_files "
                + "-applet 0x01:0x02:0x03:0x04:0x05:0x06 some.Class "
                + "-d /some/output "
                + "-out CAP EXP JCA "
                + "-v "
                + "some 0x01:0x02:0x03:0x04:0x05 1.2"));
    }

    @Test
    public void validFullConfigSuccess() throws Exception {
        String config = builder.jcdkInstallation(new Jcdk3Installation(Paths.get("/some/jcdk")))
                .classesDirectory(Paths.get("/some/classes"))
                .outputDirectory(Paths.get("/some/output"))
                .supportInt32(true)
                .appletId("01:02:03:04:05:06")
                .appletClass("some.Class")
                .appletVersion("1.2")
                .build();

        assertThat(config, equalTo("-classdir /some/classes "
                + "-i "
                + "-exportpath /some/jcdk/api_export_files "
                + "-applet 0x01:0x02:0x03:0x04:0x05:0x06 some.Class "
                + "-d /some/output "
                + "-out CAP EXP JCA "
                + "-v "
                + "some 0x01:0x02:0x03:0x04:0x05 1.2"));
    }

    @Test
    public void validConfigWithMultiplePathsSuccess() throws Exception {
        String config = builder.jcdkInstallation(new Jcdk3Installation(Paths.get("/some/jcdk")))
                .classesDirectory(Paths.get("/some/classes"))
                .outputDirectory(Paths.get("/some/output"))
                .appletId("01:02:03:04:05:06")
                .appletClass("some.Class")
                .appletVersion("1.2")
                .exportPath(Paths.get("/individual/export"))
                .exportPath(Arrays.asList(Paths.get("/individual/export1"), Paths.get("/individual/export2")))
                .build();

        assertThat(config, equalTo("-classdir /some/classes "
                + "-exportpath /some/jcdk/api_export_files:/individual/export:/individual/export1:/individual/export2 "
                + "-applet 0x01:0x02:0x03:0x04:0x05:0x06 some.Class "
                + "-d /some/output "
                + "-out CAP EXP JCA "
                + "-v "
                + "some 0x01:0x02:0x03:0x04:0x05 1.2"));
    }

    @Test
    public void missingJcdkInstallationFailure() throws Exception {
        exception.expect(ConfigurationException.class);
        exception.expectMessage("Missing parameter jcdkInstallation");

        builder.build();
    }

    @Test
    public void missingclassesDirectoryFailure() throws Exception {
        exception.expect(ConfigurationException.class);
        exception.expectMessage("Missing parameter classesDirectory");

        builder.jcdkInstallation(new Jcdk3Installation(Paths.get("/some/jcdk"))).build();
    }

    @Test
    public void missingOutputDirectoryFailure() throws Exception {
        exception.expect(ConfigurationException.class);
        exception.expectMessage("Missing parameter outputDirectory");

        builder.jcdkInstallation(new Jcdk3Installation(Paths.get("/some/jcdk")))
                .classesDirectory(Paths.get("/some/classes"))
                .build();
    }

    @Test
    public void missingAppletIdFailure() throws Exception {
        exception.expect(ConfigurationException.class);
        exception.expectMessage("Missing parameter appletId");

        builder.jcdkInstallation(new Jcdk3Installation(Paths.get("/some/jcdk")))
                .classesDirectory(Paths.get("/some/classes"))
                .outputDirectory(Paths.get("/some/output"))
                .build();
    }

    @Test
    public void missingAppletClassFailure() throws Exception {
        exception.expect(ConfigurationException.class);
        exception.expectMessage("Missing parameter appletClass");

        builder.jcdkInstallation(new Jcdk3Installation(Paths.get("/some/jcdk")))
                .classesDirectory(Paths.get("/some/classes"))
                .outputDirectory(Paths.get("/some/output"))
                .appletId("01:02:03:04:05:06")
                .build();
    }

    @Test
    public void missingAppletVersionFailure() throws Exception {
        exception.expect(ConfigurationException.class);
        exception.expectMessage("Missing parameter appletVersion");

        builder.jcdkInstallation(new Jcdk3Installation(Paths.get("/some/jcdk")))
                .classesDirectory(Paths.get("/some/classes"))
                .outputDirectory(Paths.get("/some/output"))
                .appletId("01:02:03:04:05:06")
                .appletClass("some.Class")
                .build();
    }
}
