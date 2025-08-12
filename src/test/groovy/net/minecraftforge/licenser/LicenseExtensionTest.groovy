/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser

import net.minecraftforge.licenser.header.HeaderStyle
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class LicenseExtensionTest extends Specification {
    def "style closure registers header style"() {
        given:
        def project = ProjectBuilder.builder().build()
        def licenseExtension = new LicenseExtension(project.objects, project)

        when:
        licenseExtension.style {
            ext = 'BLOCK_COMMENT'
        }

        then:
        licenseExtension.style.getProperty("ext") == HeaderStyle.BLOCK_COMMENT.format
    }
}
