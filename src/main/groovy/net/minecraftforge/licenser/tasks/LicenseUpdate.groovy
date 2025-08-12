/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser.tasks

import org.gradle.api.GradleException
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import javax.annotation.Nullable

abstract class LicenseUpdate extends LicenseTask {

    /**
     * Get the line separator to use when writing license files.
     *
     * @return the file line separator
     */
    @Input
    abstract Property<String> getLineEnding()

    void lineEnding(final @Nullable String lineEnding) {
        this.lineEnding.set(lineEnding)
    }

    @TaskAction
    void formatFiles() {
        didWork = false

        this.headers.finalizeValue()
        def headers = this.headers.get()
        if (headers.size() == 1 && headers.first().text.empty) {
            return
        }

        // Backup files before modifying them
        def original = new File(temporaryDir, 'original')
        def updated = 0
        def failed = false

        matchingFiles.visit { FileVisitDetails details ->
            if (!details.directory) {
                def file = details.file

                try {
                    def prepared = prepareMatchingHeader(details, file)
                    if (prepared == null) {
                        return
                    }

                    if (prepared.update(file, charset.get(), lineEnding.get(), skipExistingHeaders.get(), {
                        def backup = details.relativePath.getFile(original)
                        if (backup.exists()) {
                            assert backup.delete(), "Failed to delete backup file: $backup"
                        } else {
                            backup.parentFile.mkdirs()
                        }

                        assert file.renameTo(backup), "Failed to backup file $file to $backup"
                        assert file.createNewFile(), "Failed to recreate source file: $file"
                    })) {
                        updated++
                        logger.lifecycle('Updating license header in {}', getSimplifiedPath(file))
                        didWork = true
                    }
                } catch (Exception e) {
                    logger.error("Failed to update license header in ${getSimplifiedPath(file)}", e)
                    failed = true
                }
            }
        }

        if (updated > 0) {
            logger.lifecycle('{} license header(s) updated. A backup of the original file(s) was created in {}', updated, getSimplifiedPath(original))
        }

        if (failed) {
            throw new GradleException('One or more license headers could not be successfully updated')
        }
    }

}
