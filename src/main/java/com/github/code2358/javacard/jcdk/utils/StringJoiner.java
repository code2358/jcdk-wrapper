package com.github.code2358.javacard.jcdk.utils;

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

public final class StringJoiner {
    private String separator;
    private final StringBuffer buffer = new StringBuffer();

    public StringJoiner(String separator) {
        this.separator = separator;
    }

    public void add(String stringElement) {
        if (buffer.length() > 0) {
            buffer.append(separator);
        }
        buffer.append(stringElement);
    }

    @Override
    public String toString() {
        return buffer.toString();
    }
}
