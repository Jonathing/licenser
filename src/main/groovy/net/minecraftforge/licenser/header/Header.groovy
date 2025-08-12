/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser.header

import net.minecraftforge.licenser.util.HeaderHelper
import org.gradle.api.file.FileTreeElement
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Provider
import org.gradle.api.specs.Spec
import org.gradle.api.specs.Specs
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.util.PatternSet

class Header {

    @Internal
    final HeaderFormatRegistry registry

    @Input
    final Provider<List<String>> keywords

    private final Provider<String> loader

    @Input
    final Spec<FileTreeElement> filter

    @Input
    final Provider<Boolean> newLine

    private String text

    private final Map<HeaderFormat, PreparedHeader> formatted = new HashMap<>()

    Header(HeaderFormatRegistry registry, ListProperty<String> keywords, Provider<String> loader, PatternSet filter, Provider<Boolean> newLine) {
        this.registry = registry
        this.keywords = keywords.map { it*.toLowerCase() }
        this.loader = loader
        this.filter = filter?.asSpec ?: Specs.satisfyAll()
        this.newLine = newLine
    }

    @Input
    String getText() {
        if (this.text == null) {
            this.text = this.loader.get()
            if (!containsKeyword(this.text)) {
                throw new IllegalArgumentException("Header does not contain any of the required keywords: $keywords")
            }
        }

        return this.text
    }

    boolean containsKeyword(String s) {
        s = s.toLowerCase()
        return keywords.get().any(s.&contains)
    }

    PreparedHeader prepare(HeaderFormat format) {
        if (format == null) {
            return null
        }

        PreparedHeader result = formatted[format]
        if (result == null) {
            result = format.prepare(this, getText())
            formatted[format] = result
        }
        return result
    }

    PreparedHeader prepare(String path) {
        return prepare(registry[HeaderHelper.getExtension(path)])
    }

    PreparedHeader prepare(File file) {
        return prepare(file.path)
    }

}
