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

public class AppletClassTest {

    private static final String ERROR_MSG = "Applet class must be fully qualified. For example: com.company.MyApplet";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void vaildClassSuccess() throws Exception {
        AppletClass appletClass = new AppletClass("com.MyApplet");

        assertThat(appletClass.getPackage(), equalTo("com"));
        assertThat(appletClass.toString(), equalTo("com.MyApplet"));
    }

    @Test
    public void withoutPackageFailure() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(ERROR_MSG);

        new AppletClass("MyApplet");
    }

    @Test
    public void withWildcardFailure() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(ERROR_MSG);

        new AppletClass("*.MyApplet");
    }

    @Test
    public void dotOnFirstPositionFailure() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(ERROR_MSG);

        new AppletClass(".MyApplet");
    }

    @Test
    public void dotOnLastPositionFailure() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(ERROR_MSG);

        new AppletClass("MyApplet.");
    }
}
