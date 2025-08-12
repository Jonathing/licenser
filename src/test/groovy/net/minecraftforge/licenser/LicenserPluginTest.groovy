/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class LicenserPluginTest extends Specification {
    def "plugin registers tasks"() {
        given:
        def project = ProjectBuilder.builder().build()

        when:
        project.plugins.apply("net.minecraftforge.licenser")

        then:
        ["licenseCheck", "licenseFormat", "checkLicenses", "updateLicenses"].each {
            assert project.tasks.findByName(it) != null
        }
    }
}
