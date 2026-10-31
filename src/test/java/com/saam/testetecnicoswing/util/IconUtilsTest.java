package com.saam.testetecnicoswing.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author guimathis
 */
class IconUtilsTest {

    @Test
    void shouldLoadAllSystemIconsFromClasspath() {
        assertNotNull(IconUtils.load(IconUtils.ADICIONA));
        assertNotNull(IconUtils.load(IconUtils.CONFIRMA));
        assertNotNull(IconUtils.load(IconUtils.DELETA));
        assertNotNull(IconUtils.load(IconUtils.DELETA_48));
        assertNotNull(IconUtils.load(IconUtils.EDIT));
        assertNotNull(IconUtils.load(IconUtils.FUNCIONARIO));
        assertNotNull(IconUtils.load(IconUtils.LIXEIRA));
        assertNotNull(IconUtils.load(IconUtils.SAIR));
        assertNotNull(IconUtils.load(IconUtils.SAVE));
        assertNotNull(IconUtils.load(IconUtils.UNDO));
        assertNotNull(IconUtils.load(IconUtils.USUARIO));
    }
}
