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

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public final class StreamPipe extends Thread {
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean pipeInProgress = true;

    StreamPipe(InputStream inputStream, OutputStream outputStream) {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(outputStream);

        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int length;

        try {
            while (pipeInProgress) {
                if (inputStream.available() > 0) {
                    length = inputStream.read(buffer);
                    outputStream.write(buffer, 0, length);
                }
            }

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPipe() {
        pipeInProgress = false;
    }
}
