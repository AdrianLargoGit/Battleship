package es.upm.etsisi.fis.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "puntuaciones")
public class Puntuacion implements IPuntuacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long puntos;

    @Column(name = "partida_id")
    private Long partidaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Puntuacion() {}

    public Puntuacion(long puntos, Long partidaId) {
        this.puntos = puntos;
        this.partidaId = partidaId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public long getPuntos() {
        return puntos;
    }

    @Override
    public IPuntuacion clonePuntuacion() {
        return new Puntuacion(puntos,partidaId);
    }

    @Override
    public void setPuntuacion(long l) {
        this.puntos = l;
    }

    @Override
    public void setPartidaId(Long aLong) {
        this.partidaId = aLong;
    }

    public Long getPartidaId() { return this.partidaId; }

    public User getUser() { return this.user; }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Puntuacion that = (Puntuacion) object;
        return puntos == that.puntos && Objects.equals(partidaId, that.partidaId) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(puntos, partidaId, user);
    }

    @Override
    public String toString() {
        return "Puntuacion{" +
                "puntos=" + puntos +
                ", partidaId=" + partidaId +
                ", user=" + user +
                '}';
    }
}
