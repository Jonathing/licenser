/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser;

import org.gradle.api.GradleException;

/**
 * Thrown if a license violation was found.
 */
public class LicenseViolationException extends GradleException {

    /**
     * Constructs a new exception with the specified message.
     *
     * @param message The exception message
     */
    public LicenseViolationException(String message) {
        super(message);
    }

}
