/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser.util;

import net.minecraftforge.licenser.header.CommentHeaderFormat;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Pattern;

public final class HeaderHelper {

    private HeaderHelper() {
    }

    public static String getExtension(String s) {
        final int pos = s.lastIndexOf('.');
        return pos >= 0 ? s.substring(pos + 1) : null;
    }

    public static String stripIndent(String s) {
        if (s.isEmpty()) {
            return s;
        }

        final int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return i == 0 ? s : s.substring(i);
            }
        }

        return "";
    }

    public static String stripTrailingIndent(String s) {
        if (s.isEmpty()) {
            return s;
        }

        final int last = s.length() - 1;
        for (int i = last; i >= 0; i--) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return i == last ? s : s.substring(0, i + 1);
            }
        }

        return "";
    }

    public static boolean contentStartsWith(BufferedReader reader, Iterator<String> itr, Pattern ignored) throws IOException {
        String line;
        while (itr.hasNext() && (line = reader.readLine()) != null) {
            if (ignored != null && ignored.matcher(line).find()) {
                continue;
            }

            if (!line.equals(itr.next())) {
                return false;
            }
        }

        return !itr.hasNext();
    }

    public static boolean contentStartsWithValidHeaderFormat(BufferedReader reader, CommentHeaderFormat format) throws IOException {
        String firstLine;
        while ((firstLine = skipEmptyLines(reader)) != null && findPattern(firstLine, format.getSkipLine())) {
            // skip ignored lines
        }
        if (firstLine == null) {
            return false;
        }
        final boolean firstLineMatches = format.getStart().matcher(firstLine).find();
        boolean lastLineMatches = format.getEnd() == null;
        boolean contentLinesMatch = true;

        String line;
        while ((line = reader.readLine()) != null) {
            // skip ignored lines
            if (findPattern(line, format.getSkipLine())) {
                continue;
            }

            if (findPattern(line, format.getEnd())) {
                lastLineMatches = true;
                break;
            } else if (format.getEnd() == null) {
                break;
            }
            // If the current line doesn't match and there's no end marker, assume the header is complete
            contentLinesMatch = contentLinesMatch && (line.startsWith(format.getPrefix()) || format.getEnd() == null);
        }

        return firstLineMatches && contentLinesMatch && lastLineMatches;
    }

    private static boolean findPattern(CharSequence line, @Nullable Pattern pattern) {
        if (pattern == null) {
            return false;
        } else {
            return pattern.matcher(line).find();
        }
    }

    public static boolean isBlank(String s) {
        return stripIndent(s).isEmpty();
    }

    public static String skipEmptyLines(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!isBlank(line)) {
                return line;
            }
        }

        return null;
    }

}
