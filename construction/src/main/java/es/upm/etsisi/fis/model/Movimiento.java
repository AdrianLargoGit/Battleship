package es.upm.etsisi.fis.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movimientos")
public class Movimiento implements IMovimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int fila;

    @Column(nullable = false)
    private int columna;

    @Column(name = "partida_id")
    private Long partidaId;

    @Column(nullable = false)
    private long time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Movimiento () {}

    public Movimiento(int fila, int columna, Long partidaId, long time) {
        this.fila = fila;
        this.columna = columna;
        this.partidaId = partidaId;
        this.time = time;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public IMovimiento cloneMovimiento() {
        return new Movimiento(fila,columna,partidaId,time);
    }

    @Override
    public void setPartidaId(Long aLong) {
        this.partidaId = aLong;
    }

    @Override
    public void setFila(int i) {
        this.fila = i;
    }

    @Override
    public void setColumna(int i) {
        this.columna = i;
    }

    @Override
    public void setTime(long l) {
        this.time = l;
    }

    public int getFila() { return this.fila; }
    public int getColumna() { return this.columna; }
    public Long getPartidaId() { return this.partidaId; }
    public long getTime() { return this.time; }
    public User getUser() { return this.user; }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Movimiento that = (Movimiento) object;
        return fila == that.fila && columna == that.columna && Objects.equals(partidaId, that.partidaId) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila, columna, partidaId, user);
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "fila=" + fila +
                ", columna=" + columna +
                ", partidaId=" + partidaId +
                ", time=" + time +
                '}';
    }
}

