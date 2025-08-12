/*
 * Copyright (c) 2015, Minecrell <https://github.com/Minecrell>
 * Forked by Forge Development LLC and contributors
 * SPDX-License-Identifier: MIT
 */
package net.minecraftforge.licenser.util

class CaseInsensitiveMap<V> {
    @Delegate(includes = ["size", "isEmpty", "containsValue", "clear", "keySet", "values", "entrySet", "equals", "hashCode"])
    private final Map<String, V> map = new HashMap<>();

    private static String normalizeKey(Object key) {
        return key.toString().toLowerCase(Locale.ROOT)
    }

    boolean containsKey(Object key) {
        return map.containsKey(normalizeKey(key))
    }

    V get(Object key) {
        return map.get(normalizeKey(key))
    }

    V put(String key, V value) {
        return map.put(normalizeKey(key), value)
    }

    V remove(Object key) {
        return map.remove(normalizeKey(key))
    }

    void putAll(Map<? extends String, ? extends V> m) {
        m.each this.&put
    }

}
