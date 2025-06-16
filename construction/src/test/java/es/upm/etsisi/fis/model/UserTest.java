package es.upm.etsisi.fis.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;
    private Movimiento movimiento;
    private Puntuacion puntuacion;

    @BeforeEach
    void setUp() {
        user = new User("upm123", "Alice", false);
        movimiento = new Movimiento(1, 1, 1L, 1000L);
        puntuacion = new Puntuacion(100L, 1L);
    }

    @Test
    void testConstructorProtegido() throws Exception {
        Constructor<User> constructor = User.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        User u = constructor.newInstance();
        assertNotNull(u);
    }

    @Test
    void testConstructorConParametros() {
        assertEquals("upm123", user.getId());
        assertEquals("Alice", user.getNombre());
        assertFalse(user.isAdmin());
    }

    @Test
    void testGetters() {
        assertEquals("upm123", user.getId());
        assertEquals("Alice", user.getNombre());
        assertFalse(user.isAdmin());
    }

    @Test
    void testSetScanner() {
        Scanner scanner = new Scanner(System.in);
        user.setScanner(scanner);

    }

    @Test
    void testAddMovimientoValido() {
        user.addMovimiento(movimiento);
        assertEquals(1, user.getMovimientos().size());
        assertEquals(movimiento, user.getMovimientos().get(0));
    }

    @Test
    void testAddMovimientoInvalido() {
        user.addMovimiento(null);
        assertEquals(0, user.getMovimientos().size());
    }

    @Test
    void testAddPuntuacionValida() {
        user.addPuntuacion(puntuacion);
        assertEquals(1, user.getPuntuaciones().size());
        assertEquals(puntuacion, user.getPuntuaciones().get(0));
    }

    @Test
    void testEquals() {
        User mismoId = new User("upm123", "Bob", true);
        User diferenteId = new User("upm456", "Alice", false);

        assertEquals(user, mismoId);
        assertNotEquals(user, diferenteId);
        assertNotEquals(user, null);
        assertNotEquals(user, new Object());
    }

    @Test
    void testHashCode() {
        User mismoId = new User("upm123", "Carol", true);
        assertEquals(user.hashCode(), mismoId.hashCode());
    }

    @Test
    void testToString() {
        String str = user.toString();
        assertTrue(str.contains("User{"));
        assertTrue(str.contains("id='upm123'"));
        assertTrue(str.contains("name='Alice'"));
    }

    @Test
    void testAceptarAccionComplementaria() {
        assertTrue(user.aceptarAccionComplementaria(null, 1));  // Valor positivo
        assertFalse(user.aceptarAccionComplementaria(null, 0)); // Límite inferior
        assertFalse(user.aceptarAccionComplementaria(null, -1)); // Valor negativo
    }

}