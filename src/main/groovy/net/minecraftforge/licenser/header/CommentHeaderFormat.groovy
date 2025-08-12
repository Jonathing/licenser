/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser.header

import groovy.transform.EqualsAndHashCode
import groovy.transform.PackageScope
import net.minecraftforge.licenser.util.HeaderHelper

import javax.annotation.Nullable
import java.util.regex.Pattern

@EqualsAndHashCode
class CommentHeaderFormat implements HeaderFormat {

    final String name

    final Pattern start
    @Nullable
    final Pattern end
    @Nullable
    final Pattern skipLine

    final String firstLine
    final String prefix
    final String lastLine

    @PackageScope
    CommentHeaderFormat(String name, Pattern start, @Nullable Pattern end, @Nullable Pattern skipLine,
                        String firstLine, String prefix, String lastLine) {
        this.name = name
        this.start = start
        this.end = end
        this.skipLine = skipLine
        this.firstLine = firstLine
        this.prefix = prefix
        this.lastLine = lastLine
    }

    protected List<String> format(String text) {
        ensureAbsent(text, firstLine)
        ensureAbsent(text, lastLine)

        List<String> result = [firstLine]

        text.eachLine {
            result << HeaderHelper.stripTrailingIndent("$prefix $it")
        }

        result << lastLine
        return result
    }

    private static void ensureAbsent(String s, String search) {
        if (s.contains(search)) {
            throw new IllegalArgumentException("Header contains unsupported characters $search")
        }
    }

    @Override
    PreparedHeader prepare(Header header, String text) {
        return new PreparedCommentHeader(header, this, format(text))
    }

    @Override
    String toString() {
        return name
    }

}
