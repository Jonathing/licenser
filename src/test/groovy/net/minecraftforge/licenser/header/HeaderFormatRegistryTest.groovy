/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser.header

import spock.lang.Specification

class HeaderFormatRegistryTest extends Specification {
    def "registry contains default properties"() {
        when:
        def registry = new HeaderFormatRegistry()

        then:
        registry.keySet().containsAll(["java", "js", "kt", "groovy", "yml", "xml", "html"])
    }
}
