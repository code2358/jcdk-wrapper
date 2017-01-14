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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public final class CommandLine {

    public static int execute(String command, OutputStream commandOutput) throws IOException, InterruptedException {
        Objects.requireNonNull(command);
        Objects.requireNonNull(commandOutput);

        Process process = Runtime.getRuntime().exec(command);
        StreamPipe errorPipe = new StreamPipe(process.getErrorStream(), commandOutput);
        StreamPipe outputPipe = new StreamPipe(process.getInputStream(), commandOutput);
        errorPipe.start();
        outputPipe.start();

        process.waitFor();

        errorPipe.stopPipe();
        outputPipe.stopPipe();

        return process.exitValue();
    }
}
