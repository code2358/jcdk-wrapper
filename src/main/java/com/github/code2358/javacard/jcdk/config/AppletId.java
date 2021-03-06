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

public final class AppletId {

    private final String appletId;

    public AppletId(String appletId) {
        Objects.requireNonNull(appletId);

        Pattern pattern = Pattern.compile("[0-9A-Fa-f][0-9A-Fa-f](:[0-9A-Fa-f][0-9A-Fa-f]){5,11}");
        if (!pattern.matcher(appletId).matches()) {
            throw new IllegalArgumentException(
                    "Invalid format of applet ID. Supported formats: 01:02:03:04:05:06 - 01:02:03:04:05:06:07:08:09:10:11:12");
        }

        this.appletId = appletId;
    }

    public String getPackageId() {
        return appletId.substring(0, 14);
    }

    @Override
    public String toString() {
        return appletId;
    }
}
