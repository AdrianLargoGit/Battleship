package es.upm.etsisi.fis.view;
import es.upm.etsisi.fis.model.*;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UsersView implements GView{
    private final Scanner sc;

    public UsersView(Scanner sc) {
        this.sc = sc;
    }

    public void showWelcome(){
        System.out.println("\n--- Bienvenido al sistema de juegos ---\n");
    }

    public int showUserMenu() {
        String[] options = {"Jugar partida", "Ver puntuaciones", "Darse de baja", "Salir"};
        return JOptionPane.showOptionDialog(
                null,
                "Selecciona una opción:",
                "Menú de Usuario",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
    }

    public String sureForDelete() {
        System.out.print("¿Estás seguro de que deseas darte de baja? (s/n): ");
        return sc.nextLine().toLowerCase();
    }

    public int showScoresSubmenu() {
        String[] options = {"Ver mi top 10 puntuaciones", "Ver todas las puntuaciones (Admin)", "Volver al menú principal"};
        return JOptionPane.showOptionDialog(
                null,
                "Selecciona una opción:",
                "Menú de Usuario",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

    }

    public void showUserTopScores(List<IPuntuacion> topScores) {
        System.out.println("\n========= Mis 10 Mejores Puntuaciones =========");

        if (topScores.isEmpty()) {
            System.out.println("No hay puntuaciones para mostrar\n");
            return;
        }

        System.out.printf("%-10s | %-10s\n", "Posición", "Puntos");
        System.out.println("-----------|-----------");

        for (int i = 0; i < topScores.size(); i++) {
            System.out.printf("%-10d | %-10d\n", i + 1, topScores.get(i).getPuntos());
        }

        System.out.println();
    }

    public void showAllUsersScores(List<Map.Entry<String, Long>> allScores) {

        System.out.println("\n========= Todas las Puntuaciones =========");

        if (allScores.isEmpty()) {
            System.out.println("No hay puntuaciones para mostrar\n");
            return;
        }

        System.out.printf("%-10s | %-10s | %-20s\n", "Posición", "Puntos", "Usuario");
        System.out.println("-----------|------------|----------------------");

        for (int i = 0; i < allScores.size(); i++) {
            Map.Entry<String, Long> entry = allScores.get(i);
            System.out.printf("%-10d | %-10d | %-20s\n", i + 1, entry.getValue(), entry.getKey());
        }

        System.out.println();
    }

    public int[] askTurn(int largo, int ancho){
        int fila = -1, columna = -1;
        System.out.println("\n--- Tu turno ---");
        while (true) {
            System.out.print("Introduce fila (0-" + (largo - 1) + "): ");
            String inputFila = sc.nextLine().trim();
            try {
                fila = Integer.parseInt(inputFila);
                break;
            } catch (Exception e) {
                System.out.println("Entrada no válida. Introduce un número.");
            }
        }
        while (true) {
            System.out.print("Introduce columna (0-" + (ancho - 1) + "): ");
            String inputCol = sc.nextLine().trim();
            try {
                columna = Integer.parseInt(inputCol);
                break;
            } catch (Exception e) {
                System.out.println("Entrada no válida. Introduce un número.");
            }
        }
        return new int[]{fila, columna};
    }

    public String askMachine(){
        System.out.println("Selecciona el nivel de máquina (FACIL, NORMAL, DIFICIL)");
        return sc.nextLine();
    }

    public void showSuccessMessage(String message) {
        System.out.println("Éxito: " + message);
    }

    public void showErrorMessage(String message) {
        System.err.println("Error: " + message + "\n");
    }
}
