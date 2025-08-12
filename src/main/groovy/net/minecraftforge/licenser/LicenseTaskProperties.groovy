/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser

import groovy.transform.PackageScope
import org.gradle.api.Incubating
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.resources.TextResourceFactory
import org.gradle.api.tasks.util.PatternSet

/**
 * Represents a custom license task that operates on a number of files.
 *
 * @see #files
 */
@Incubating
class LicenseTaskProperties extends LicenseProperties {

    /**
     * The name of this custom task. This is set automatically in the container.
     */
    final String name

    /**
     * The set of files to operate on.
     */
    final ConfigurableFileCollection files

    @PackageScope
    LicenseTaskProperties(PatternSet filter, String name, ObjectFactory objects, TextResourceFactory text, Property<String> charset) {
        super(filter, objects, text, charset)
        this.name = name
        this.files = objects.fileCollection()
    }

}
