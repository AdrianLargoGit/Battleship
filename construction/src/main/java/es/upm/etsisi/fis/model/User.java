package es.upm.etsisi.fis.model;

import es.upm.etsisi.fis.controller.UserController;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements IJugador {
    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Movimiento> movimientos = new LinkedList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Puntuacion> puntuaciones = new ArrayList<>();

    private transient Scanner sc;

    protected User() {}

    public User(String id, String name, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public String getId() {
        return this.id;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setScanner(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public boolean aceptarAccionComplementaria(TBarcoAccionComplementaria tBarcoAccionComplementaria, int i) {
        return i > 0;
    }

    public int[] realizaTurno(char[][] tableroEnemigo) {
        return UserController.getInstance().realizarTurnoUser(tableroEnemigo);
    }


    @Override
    public void addMovimiento(IMovimiento IMovimiento) {
        if (IMovimiento instanceof Movimiento) {
            Movimiento movimiento = (Movimiento) IMovimiento;
            movimiento.setUser(this);
            this.movimientos.add(movimiento);
        }
    }

    @Override
    public String getNombre() {
        return this.name;
    }

    @Override
    public void addPuntuacion(IPuntuacion IPuntuacion) {
        if (IPuntuacion instanceof Puntuacion) {
            Puntuacion puntuacion = (Puntuacion) IPuntuacion;
            puntuacion.setUser(this);
            this.puntuaciones.add((Puntuacion) IPuntuacion);
        }
    }

    @Override
    public List<IMovimiento> getMovimientos() {
        return new ArrayList<>(movimientos);
    }

    @Override
    public List<IPuntuacion> getPuntuaciones() {
        return new ArrayList<>(puntuaciones);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
