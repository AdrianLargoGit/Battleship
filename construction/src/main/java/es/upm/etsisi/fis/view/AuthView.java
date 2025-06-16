package es.upm.etsisi.fis.view;

import javax.swing.*;

public class AuthView implements GView {

    public AuthView() {
    }

    public void showWelcomeMessage() {
        System.out.println("\n=====================================");
        System.out.println(" Bienvenido al Sistema de Autenticación");
        System.out.println("=====================================\n");
    }

    public int showAuthMenu() {
        String[] options = {"Iniciar sesión / Registrarse", "Salir"};
        return JOptionPane.showOptionDialog(
                null,
                "Selecciona una opción:",
                "Menú de Autenticación",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
    }

    public String getName() {
        return JOptionPane.showInputDialog(
                null,
                "Introduzca su nombre de usuario:",
                "Registro de Usuario",
                JOptionPane.QUESTION_MESSAGE
        );
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, "Éxito: " + message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, "Error: " + message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}

