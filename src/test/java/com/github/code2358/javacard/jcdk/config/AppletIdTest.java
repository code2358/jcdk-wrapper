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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class AppletIdTest {

    private static final String ERROR_MSG = "Invalid format of applet ID. Supported formats: 01:02:03:04:05:06 - 01:02:03:04:05:06:07:08:09:10:11:12";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void validMinimumSuccess() throws Exception {
        AppletId appletId = new AppletId("AB:CD:EF:01:23:89");

        assertThat(appletId.getPackageId(), equalTo("AB:CD:EF:01:23"));
        assertThat(appletId.toString(), equalTo("AB:CD:EF:01:23:89"));
    }

    @Test
    public void validMaximumSuccess() throws Exception {
        AppletId appletId = new AppletId("01:02:03:04:05:06:07:08:09:10:11:12");

        assertThat(appletId.getPackageId(), equalTo("01:02:03:04:05"));
        assertThat(appletId.toString(), equalTo("01:02:03:04:05:06:07:08:09:10:11:12"));
    }

    @Test
    public void tooShortFailure() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(ERROR_MSG);

        new AppletId("AB:CD:EF:01:23");
    }

    @Test
    public void tooLongFailure() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(ERROR_MSG);

        new AppletId("AB:CD:EF:01:AB:CD:EF:01:AB:CD:EF:01:23");
    }

    @Test
    public void wrongFormatFailure() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(ERROR_MSG);

        new AppletId("ABCDEF012389");
    }
}
