/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.resources.TextResource
import org.gradle.api.resources.TextResourceFactory
import org.gradle.api.tasks.util.PatternFilterable
import org.gradle.api.tasks.util.PatternSet

import javax.inject.Inject

/**
 * Specifies how to format the license header of a subset of the files, defined by the filter.
 *
 * @see #filter
 */
class LicenseProperties implements PatternFilterable {

    /**
     * The filter to apply to the source files.
     * <p>By default this only includes a few excludes for binary files or files without standardized comment
     * formats.</p>
     */
    @Delegate
    PatternFilterable filter

    /**
     * The path to the file containing the license header.
     * <p>By default this is the {@code LICENSE} file in the project directory.</p>
     */
    final Property<TextResource> header

    /**
     * Whether to insert an empty line after the license header.
     * <p>By default this is {@code true}.</p>
     */
    final Property<Boolean> newLine

    protected final Property<String> charset
    private final TextResourceFactory resources

    @Inject
    LicenseProperties(PatternSet filter, ObjectFactory objects, TextResourceFactory resources) {
        this.filter = filter
        this.charset = objects.property(String)
        this.header = objects.property(TextResource)
        this.newLine = objects.property(Boolean)
        this.resources = resources
    }

    @Inject
    LicenseProperties(PatternSet filter, ObjectFactory objects, TextResourceFactory resources, Property<String> charset) {
        this(filter, objects, resources)
        this.charset.set(charset)
    }

    /**
     * @see #header
     * @param header the new header
     */
    void header(final TextResource header) {
        this.header.set(header)
    }

    // kotlin + from other plugins
    /**
     * Set the header to the contents of a file.
     *
     * @param header the new header
     * @see #header
     * @see <a href="https://docs.gradle.org/current/kotlin-dsl/gradle/org.gradle.api/-project/index.html#-1922377900%2FFunctions%2F-1793262594">Project.file(Object)</a>
     */
    void header(final Object header) {
        this.header.set(this.charset.map { this.resources.fromFile(header, it) })
    }

    // groovy
    void setHeader(final File header) {
        this.header.set(this.charset.map { this.resources.fromFile(header, it) })
    }

    void newLine(final Boolean newLine) {
        this.newLine.set(newLine)
    }

}
