package com.saam.testetecnicoswing.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author guimathis
 */
class PasswordUtilsTest {

    @Test
    void shouldGenerateValidSha256HexHash() {
        String hash = PasswordUtils.hashSha256("123456");
        assertNotNull(hash);
        assertEquals(64, hash.length());
        assertEquals("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", hash);
    }

    @Test
    void shouldRejectBlankPassword() {
        assertThrows(IllegalArgumentException.class, () -> PasswordUtils.hashSha256(""));
        assertThrows(IllegalArgumentException.class, () -> PasswordUtils.hashSha256("   "));
        assertThrows(IllegalArgumentException.class, () -> PasswordUtils.hashSha256(null));
    }
}
