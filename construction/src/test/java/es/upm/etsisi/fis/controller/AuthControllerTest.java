package es.upm.etsisi.fis.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest {

    private AuthController controller;

    @BeforeEach
    void setUp() throws Exception {
        Field instanceField = AuthController.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        controller = AuthController.getInstance();

        // Simular blacklist vacía o con admin
        Field blacklistField = controller.getClass().getDeclaredField("blacklistedNames");
        blacklistField.setAccessible(true);
        blacklistField.set(controller, new HashSet<>(Collections.singletonList("admin")));
    }

    @Test
    void testSingletonReturnsSameInstance() {
        AuthController instance1 = AuthController.getInstance();
        AuthController instance2 = AuthController.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void testGetCurrentUserInitiallyNull() {
        assertNull(controller.getCurrentUser());
    }

    @Test
    void testIsValidName() throws Exception {
        Method method = AuthController.class.getDeclaredMethod("isValidName", String.class);
        method.setAccessible(true);

        assertFalse((boolean) method.invoke(controller, (Object)null));
        assertFalse((boolean) method.invoke(controller, "ab"));
        assertTrue((boolean) method.invoke(controller, "abc"));
        assertFalse((boolean) method.invoke(controller, "abcdefghijk"));
        assertFalse((boolean) method.invoke(controller, "inv@lid"));
        assertFalse((boolean) method.invoke(controller, "admin"));
        assertTrue((boolean) method.invoke(controller, "Player1"));
    }

    @Test
    void testLoadBlacklistedNamesReturnsNonNullSet() throws Exception {
        Method method = AuthController.class.getDeclaredMethod("loadBlacklistedNames");
        method.setAccessible(true);
        Object result = method.invoke(controller);
        assertNotNull(result);
        assertTrue(result instanceof Set);
    }
}
