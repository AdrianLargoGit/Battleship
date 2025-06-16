package es.upm.etsisi.fis.controller;

import es.upm.etsisi.fis.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DataControllerTest {

    private DataController controller;

    @BeforeEach
    void setUp() {
        //Quitar comentario para ejecutar DataControllerTest
        //DataController.initForTest();
        controller = DataController.getInstance();
    }

    @Test
    void testSingletonReturnsSameInstance() {
        DataController second = DataController.getInstance();
        assertSame(controller, second);
    }

    @Test
    void testLoadUsersReturnsEmptyMap() {
        Map<String, User> users = controller.loadUsers();
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    void testSaveOrUpdateAndDeleteDoesNotThrow() {
        User user = new User("id1", "testuser", false);
        assertDoesNotThrow(() -> controller.saveOrUpdateUser(user));
        assertDoesNotThrow(() -> controller.deleteUser(user));
        assertDoesNotThrow(() -> controller.initializeUserRelations(user));
    }
}