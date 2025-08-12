/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser.header

import net.minecraftforge.licenser.util.CaseInsensitiveMap

class HeaderFormatRegistry extends CaseInsensitiveMap<HeaderFormat> {
    HeaderFormatRegistry() {
        for (HeaderStyle style : HeaderStyle.values()) {
            style.register(this)
        }
    }

    HeaderFormat put(String key, HeaderStyle value) {
        return put(key, value.getFormat())
    }

    HeaderFormat put(String key, String value) {
        return put(key, HeaderStyle.valueOf(value))
    }

    HeaderFormat putAt(String key, HeaderStyle value) {
        return put(key, value)
    }

    HeaderFormat putAt(String key, String value) {
        return put(key, value)
    }

    @Override
    Object getProperty(String key) {
        return get(key)
    }

    @Override
    void setProperty(String key, Object value) {
        put(key, String.valueOf(value))
    }

}
