package es.upm.etsisi.fis;

import es.upm.etsisi.fis.controller.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (
                SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
                Session session = sessionFactory.openSession()
        ) {
            DataController dataController = DataController.getInstance(session);
            UserController userController = UserController.getInstance(sc);
            AuthController authController = AuthController.getInstance();
            ControladorPartida.getInstance(sc);

            authController.startAuthProcess();

            if (authController.getCurrentUser() != null) {
                authController.getCurrentUser().setScanner(sc);

                userController.startUserProcess();

                if (userController.getUsersIds().contains(authController.getCurrentUser().getId())) {
                    dataController.saveOrUpdateUser(authController.getCurrentUser());
                }


            } else {
                System.out.println("Sesión no iniciada. Saliendo...\n");
            }
        } finally {
            sc.close();
        }
    }
}