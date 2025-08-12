/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser.header;

import java.io.File;
import java.io.IOException;

public interface PreparedHeader {

    boolean check(File file, String charset, boolean skipExistingHeaders) throws IOException;

    boolean update(File file, String charset, String lineSeparator, boolean skipExistingHeaders, Runnable callback) throws IOException;

}
