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

import java.util.Objects;
import java.util.regex.Pattern;

public final class AppletClass {
    private final String appletClass;

    public AppletClass(String appletClass) {
        Objects.requireNonNull(appletClass);

        Pattern pattern = Pattern.compile("[0-9a-z]+(\\.[0-9a-z]+)*\\.[0-9a-zA-Z]+");
        if (!pattern.matcher(appletClass).matches()) {
            throw new IllegalArgumentException("Applet class must be fully qualified. For example: com.company.MyApplet");
        }

        this.appletClass = appletClass;
    }

    public String getPackage() {
        return appletClass.substring(0, appletClass.lastIndexOf("."));
    }

    @Override
    public String toString() {
        return appletClass;
    }
}
