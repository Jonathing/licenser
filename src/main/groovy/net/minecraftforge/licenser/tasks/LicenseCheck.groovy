/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser.tasks

import net.minecraftforge.licenser.LicenseViolationException
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.VerificationTask

abstract class LicenseCheck extends LicenseTask {

    @Input
    abstract Property<Boolean> getIgnoreFailures()

    void ignoreFailures(final boolean ignoreFailures) {
        this.ignoreFailures.set(ignoreFailures)
    }

    @TaskAction
    void checkFiles() {
        didWork = false

        def headers = this.headers.get()
        if (headers.size() == 1 && headers.first().text.empty) {
            return
        }

        Set<File> violations = []
        def charset = charset.get()
        def skipExistingHeaders = skipExistingHeaders.get()
        matchingFiles.visit { FileVisitDetails details ->
            if (!details.directory) {
                didWork = true
                def file = details.file

                try {
                    def prepared = prepareMatchingHeader(details, file)
                    if (prepared == null) {
                        return
                    }

                    if (!prepared.check(file, charset, skipExistingHeaders)) {
                        violations.add(file)
                    }
                } catch (Exception e) {
                    violations.add(file)
                    logger.error("Failed to check license header of ${getSimplifiedPath(file)}", e)
                }
            }
        }

        if (!violations.isEmpty()) {
            String violators = violations.collect { getSimplifiedPath(it) }.join(', ')

            final def message = "License violations were found: $violators"
            if (this.ignoreFailures.get()) {
                logger.warn(message)
            } else {
                throw new LicenseViolationException(message)
            }
        }
    }

}
