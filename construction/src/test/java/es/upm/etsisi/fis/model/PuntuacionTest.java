package es.upm.etsisi.fis.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import static org.junit.jupiter.api.Assertions.*;

class PuntuacionTest {
    private Puntuacion puntuacion;
    private User user;

    @BeforeEach
    void setUp() {
        puntuacion = new Puntuacion(100L, 1L);
        user = new User("upm123", "Jugador1", false);
    }

    @Test
    void testConstructorProtegido() throws Exception {
        Constructor<Puntuacion> constructor = Puntuacion.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Puntuacion p = constructor.newInstance();

        assertNotNull(p);
        assertEquals(0L, p.getPuntos()); // Valores por defecto
    }

    @Test
    void testConstructorConParametros() {
        assertEquals(100L, puntuacion.getPuntos());
        assertEquals(1L, puntuacion.getPartidaId());
    }

    @Test
    void testSetYGetPuntos() {
        puntuacion.setPuntuacion(200L);
        assertEquals(200L, puntuacion.getPuntos());
    }

    @Test
    void testSetYGetPartidaId() {
        puntuacion.setPartidaId(2L);
        assertEquals(2L, puntuacion.getPartidaId());

        puntuacion.setPartidaId(null);
        assertNull(puntuacion.getPartidaId());
    }

    @Test
    void testSetYGetUser() {
        puntuacion.setUser(user);
        assertEquals(user, puntuacion.getUser());

        puntuacion.setUser(null);
        assertNull(puntuacion.getUser());
    }

    @Test
    void testClonePuntuacion() {
        Puntuacion clone = (Puntuacion) puntuacion.clonePuntuacion();

        assertNotSame(puntuacion, clone);
        assertEquals(puntuacion.getPuntos(), clone.getPuntos());
        assertEquals(puntuacion.getPartidaId(), clone.getPartidaId());
    }

    @Test
    void testEquals() {
        Puntuacion misma = new Puntuacion(100L, 1L);
        Puntuacion diferente = new Puntuacion(50L, 2L);

        assertEquals(puntuacion, misma);
        assertNotEquals(puntuacion, diferente);
        assertNotEquals(puntuacion, null);
        assertNotEquals(puntuacion, new Object());
    }

    @Test
    void testHashCode() {
        Puntuacion misma = new Puntuacion(100L, 1L);
        assertEquals(puntuacion.hashCode(), misma.hashCode());

        puntuacion.setUser(null);
        assertDoesNotThrow(() -> puntuacion.hashCode());
    }

    @Test
    void testToString() {
        String str = puntuacion.toString();
        assertTrue(str.contains("Puntuacion{"));
        assertTrue(str.contains("partidaId=" + puntuacion.getPartidaId()));
    }

}
