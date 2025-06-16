package es.upm.etsisi.fis.controller;

import es.upm.etsisi.fis.model.User;
import es.upm.etsisi.fis.view.AuthView;
import servidor.ExternalLDAP;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class AuthController{
    private static AuthController instance;
    private final AuthView view;
    private User currentUser;
    private final Set<String> blacklistedNames;

    private AuthController() {
        this.currentUser = null;
        this.view = new AuthView();
        this.blacklistedNames = loadBlacklistedNames();
    }

    public static AuthController getInstance() {
        if (instance == null) {
            instance = new AuthController();
        }
        return instance;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void startAuthProcess() {
        view.showWelcomeMessage();

        boolean exit = false;
        while (!exit) {
            int selected = view.showAuthMenu();

            switch (selected) {
                case 0:
                    if (loginOrRegister()) exit = true;
                    break;
                case 1:
                case -1:
                    exit = true;
                    break;
                default:
                    view.showErrorMessage("Opción no válida");
            }
        }
    }

    private Set<String> loadBlacklistedNames() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("list_raw.txt")) {
            if (inputStream == null) {
                throw new RuntimeException("Archivo list_raw.txt no encontrado en resources");
            }
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return Arrays.stream(content.split("\n"))
                    .filter(line -> !line.trim().isEmpty() && !line.startsWith("#"))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException("Error al leer la lista negra", e);
        }
    }

    private boolean isValidName(String name) {
        if (name == null || name.length() < 3 || name.length() > 10) {
            return false;
        }
        if (!name.matches("^[a-zA-Z0-9]+$")) {
            return false;
        }
        return !blacklistedNames.contains(name.toLowerCase());
    }


    private boolean loginOrRegister() {
        String id = ExternalLDAP.LoginLDAP();

        if (id != null) {
            for (User user : UserController.getInstance().getUsers()) {
                String userId = user.getId();
                if (userId.equals(id)) {
                    currentUser = user;
                    view.showSuccessMessage("Inicio de sesión exitoso, bienvenido " + user.getNombre());
                    break;
                }
            }

            if (currentUser == null) {
                String name;
                boolean nameExists;

                do {
                    name = view.getName();

                    if (name == null) {
                        view.showErrorMessage("Registro cancelado por el usuario.");
                        return false;
                    }

                    nameExists = false;

                    for (User user : UserController.getInstance().getUsers()) {
                        if (user.getNombre().equalsIgnoreCase(name)) {
                            nameExists = true;
                            break;
                        }
                    }

                    if (nameExists) {
                        view.showErrorMessage("Ese nombre ya está en uso. Por favor, elige otro.");
                    } else if (!isValidName(name)) {
                        view.showErrorMessage("Nombre no válido. Debe tener entre 3 y 10 caracteres alfanuméricos y no estar en la lista negra.");
                    }
                } while (nameExists || !isValidName(name));

                User user = new User(id, name, false);
                currentUser = user;
                UserController.getInstance().addUser(currentUser);
                view.showSuccessMessage("Registro exitoso");
            }
            return true;
        } else {
            view.showErrorMessage("Error en el inicio de sesión / registro");
            return false;
        }
    }
}
