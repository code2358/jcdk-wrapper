package com.github.code2358.javacard.jcdk;

/*-
 * #%L
 * Java Card SDK Wrapper
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

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JcdkInstallationFactoryTest {
    private final static Path JCDK2 = Paths.get(JcdkInstallationFactoryTest.class.getResource("/jcdk2").getFile());
    private final static Path JCDK3 = Paths.get(JcdkInstallationFactoryTest.class.getResource("/jcdk3").getFile());

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void jcdk2InstallationSuccess() throws InitializationException {
        JcdkInstallation jcdkInstallation = JcdkInstallationFactory.getInstance("2.2.x", JCDK2);

        Assert.assertNotNull(jcdkInstallation);
    }

    @Test
    public void jcdk3InstallationSuccess() throws InitializationException {
        JcdkInstallation jcdkInstallation = JcdkInstallationFactory.getInstance("3.0.x", JCDK3);

        Assert.assertNotNull(jcdkInstallation);
    }

    @Test
    public void jcdkInstallationVersionMismatchFailure() throws InitializationException {
        exception.expect(InitializationException.class);
        exception.expectMessage("Provided JCDK path is invalid or does not match provided JCDK version.");

        JcdkInstallationFactory.getInstance("2.2.x", JCDK3);
    }

    @Test
    public void jcdkInstallationInvalidVersionFailure() throws InitializationException {
        exception.expect(InitializationException.class);
        exception.expectMessage("Invalid JCDK version. Supported parameters: 3.0.x, 2.2.x");

        JcdkInstallationFactory.getInstance("1.x", JCDK3);
    }
}
