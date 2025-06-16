package es.upm.etsisi.fis.controller;

import es.upm.etsisi.fis.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataController{
    private static DataController instance;
    private final Session session;

    private DataController(Session session) {
        this.session = session;
    }

    public static DataController getInstance(Session session) {
        if (instance == null) {
            instance = new DataController(session);
        }
        return instance;
    }

    public static DataController getInstance() {
        if (instance == null) {
            throw new RuntimeException("Debe iniciarse la primera vez con una sesión válido \"getInstance(Session session)\"");
        }
        return instance;
    }

    public Map<String, User> loadUsers() {
        Transaction tx = session.beginTransaction();
        List<User> userList = session.createQuery("FROM User", User.class).list();
        tx.commit();
        Map<String, User> users = new HashMap<>();
        for (User user : userList) {
            users.put(user.getId(), user);
        }
        return users;
    }

    public void saveOrUpdateUser(User user) {
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(user);
        tx.commit();
    }

    public void deleteUser(User user) {
        Transaction tx = session.beginTransaction();
        session.delete(user);
        tx.commit();
    }


    public void initializeUserRelations(User user) {
        Transaction tx = session.beginTransaction();
        session.refresh(user);
        Hibernate.initialize(user.getMovimientos());
        Hibernate.initialize(user.getPuntuaciones());
        tx.commit();
    }

    /* Solo para pruebas. Quitar comentario para ejecutar pruebas
    public static void initForTest() {
        instance = new DataController(null) {
            @Override
            public Map<String, User> loadUsers() {
                return new HashMap<>();
            }

            @Override
            public void saveOrUpdateUser(User user) {
                // nada
            }
            @Override
            public void deleteUser(User user) {
                // No hacer nada
            }
            @Override
            public void initializeUserRelations(User user) {
                // nada
            }
        };
    }*/
}