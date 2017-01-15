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


import java.nio.file.Path;

public final class JcdkInstallationFactory {

    private JcdkInstallationFactory() {
    }

    public static JcdkInstallation getInstance(String jcdkVersion, Path jcdkHome) throws InitializationException {
        JcdkInstallation jcdkInstallation;

        if ("3.0.x".equals(jcdkVersion)) {
            jcdkInstallation = new Jcdk3Installation(jcdkHome);
        } else if ("2.2.x".equals(jcdkVersion)) {
            jcdkInstallation = new Jcdk2Installation(jcdkHome);
        } else {
            throw new InitializationException("Invalid JCDK version. Supported parameters: 3.0.x, 2.2.x");
        }

        if (!jcdkInstallation.isJcdkAvailable()) {
            throw new InitializationException("Provided JCDK path is invalid or does not match provided JCDK version.");
        }

        return jcdkInstallation;
    }
}
