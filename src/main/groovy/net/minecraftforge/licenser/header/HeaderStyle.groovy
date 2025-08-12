/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser.header

import javax.annotation.Nullable
import java.util.regex.Pattern

enum HeaderStyle {
    BLOCK_COMMENT(~/^\s*\/\*(?:[^*].*)?$/, ~/\*\/\s*(.*?)$/, null, '/*', ' *', ' */',
        'java', 'groovy', 'scala', 'kt', 'kts', 'gradle', 'css', 'js'),
    JAVADOC(~/^\s*\/\*\*(?:[^*].*)?$/, ~/\*\/\s*(.*?)$/, null, '/**', ' *', ' */'),
    HASH(~/^\s*#/, null, ~/^\s*#!/, '#', '#', '#', 'properties', 'yml', 'yaml', 'sh'),
    XML(~/^\s*<!--/, ~/-->\s*(.*?)$/, ~/^\s*<(?:\?xml .*\?|!DOCTYPE .*)>\s*$/, '<!--', '   ', '-->',
            'xml', 'xsd', 'xsl', 'fxml', 'dtd', 'html', 'xhtml'),
    DOUBLE_SLASH(~/^\s*\/\//, null, null, '//', '//', '//')

    final CommentHeaderFormat format
    private final String[] extensions

    HeaderStyle(Pattern start, @Nullable Pattern end, @Nullable Pattern skipLine,
                String firstLine, String prefix, String lastLine, String... extensions) {
        this.format = new CommentHeaderFormat(this.name(), start, end, skipLine, firstLine, prefix, lastLine)
        this.extensions = extensions
    }

    void register(HeaderFormatRegistry registry) {
        extensions.each { registry[it] = this }
    }

}
