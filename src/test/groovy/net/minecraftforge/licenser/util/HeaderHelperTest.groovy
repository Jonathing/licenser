/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser.util


import net.minecraftforge.licenser.header.HeaderStyle
import spock.lang.Specification

class HeaderHelperTest extends Specification {
    def "contentStartsWithValidHeaderFormat returns false with empty input"() {
        given:
        def inputString = ""
        def stringReader = new StringReader(inputString)
        def reader = new BufferedReader(stringReader)

        when:
        def result = HeaderHelper.contentStartsWithValidHeaderFormat(reader, HeaderStyle.BLOCK_COMMENT.format)

        then:
        !result
    }

    def "contentStartsWithValidHeaderFormat returns false with non-matching input"() {
        given:
        def inputString = "Not a copyright header"
        def stringReader = new StringReader(inputString)
        def reader = new BufferedReader(stringReader)

        when:
        def result = HeaderHelper.contentStartsWithValidHeaderFormat(reader, HeaderStyle.BLOCK_COMMENT.format)

        then:
        !result
    }

    def "contentStartsWithValidHeaderFormat returns true with valid non-empty header"() {
        given:
        def inputString = """\
            /*
             * Some copyright header
             */
            My Content
        """.stripIndent()
        def stringReader = new StringReader(inputString)
        def reader = new BufferedReader(stringReader)

        when:
        def result = HeaderHelper.contentStartsWithValidHeaderFormat(reader, HeaderStyle.BLOCK_COMMENT.format)

        then:
        result
    }

    def "contentStartsWithValidHeaderFormat returns false with invalid non-empty header"() {
        given:
        def inputString = """\
            /**
             * Some copyright header
             */
            My Content
        """.stripIndent()
        def stringReader = new StringReader(inputString)
        def reader = new BufferedReader(stringReader)

        when:
        def result = HeaderHelper.contentStartsWithValidHeaderFormat(reader, HeaderStyle.BLOCK_COMMENT.format)

        then:
        !result
    }

    def "contentStartsWithValidHeaderFormat returns true with valid empty header"() {
        given:
        def inputString = """\
            /*
             */
            My Content
        """.stripIndent()
        def stringReader = new StringReader(inputString)
        def reader = new BufferedReader(stringReader)

        when:
        def result = HeaderHelper.contentStartsWithValidHeaderFormat(reader, HeaderStyle.BLOCK_COMMENT.format)

        then:
        result
    }

    def "contentStartsWithValidHeaderFormat returns false with incomplete header"() {
        given:
        def inputString = """\
            /*
             * Incomplete copyright header
            My Content
        """.stripIndent()
        def stringReader = new StringReader(inputString)
        def reader = new BufferedReader(stringReader)

        when:
        def result = HeaderHelper.contentStartsWithValidHeaderFormat(reader, HeaderStyle.BLOCK_COMMENT.format)

        then:
        !result
    }

    def "contentStartsWithValidHeaderFormat returns true with valid header with ignored lines"() {
        given:
        def inputString = """\
            #!/bin/bash
            # Some header
            My Content
        """.stripIndent()
        def stringReader = new StringReader(inputString)
        def reader = new BufferedReader(stringReader)

        when:
        def result = HeaderHelper.contentStartsWithValidHeaderFormat(reader, HeaderStyle.HASH.format)

        then:
        result
    }

    def "contentStartsWithValidHeaderFormat returns true with valid header with XML"() {
        given:
        def inputString = """\
            <?xml version="1.0" encoding="UTF-8" standalone="yes" ?>
            <!--
               Some copyright header
            -->
            <document>
            </document>
        """.stripIndent()
        def stringReader = new StringReader(inputString)
        def reader = new BufferedReader(stringReader)

        when:
        def result = HeaderHelper.contentStartsWithValidHeaderFormat(reader, HeaderStyle.XML.format)

        then:
        result
    }
}
