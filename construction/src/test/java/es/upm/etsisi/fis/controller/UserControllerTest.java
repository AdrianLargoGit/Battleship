package es.upm.etsisi.fis.controller;

import es.upm.etsisi.fis.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private UserController controller;

    @BeforeEach
    void setUp() throws Exception {
        //Quitar comentario para ejecutar UserControllerTest
        //DataController.initForTest();

        Field instanceField = UserController.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        controller = UserController.getInstance(new Scanner(System.in));

        Field usersField = UserController.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersField.set(controller, new HashMap<>());
    }

    @Test
    void testAddAndGetUser() {
        User user = new User("id1", "Mario", false);
        controller.addUser(user);
        assertEquals(user, controller.getUser("id1"));
    }

    @Test
    void testDeleteUser() {
        User user = new User("id2", "Luigi", false);
        controller.addUser(user);
        controller.deleteUser(user);
        assertNull(controller.getUser("id2"));
    }

    @Test
    void testGetUsersAndIds() {
        User user1 = new User("id3", "Peach", false);
        User user2 = new User("id4", "Daisy", false);
        controller.addUser(user1);
        controller.addUser(user2);

        List<User> users = controller.getUsers();
        List<String> ids = controller.getUsersIds();

        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(ids.contains("id3"));
        assertTrue(ids.contains("id4"));
    }

    @Test
    void testGetUserNotFound() {
        assertNull(controller.getUser("no-id"));
    }
}
