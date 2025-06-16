package es.upm.etsisi.fis.controller;
import es.upm.etsisi.fis.model.*;
import es.upm.etsisi.fis.view.UsersView;

import java.util.Map;

import java.util.*;

public class UserController{
    private static UserController instance;
    private final Map<String, User> users;
    private final UsersView view;

    private UserController(Scanner scanner) {
        this.view = new UsersView(scanner);
        this.users = getUsersFromData();
    }

    public static UserController getInstance(Scanner scanner) {
        if (instance == null) {
            instance = new UserController(scanner);
        }
        return instance;
    }

    public static UserController getInstance() {
        if (instance == null) {
            throw new IllegalStateException("UserController no inicializado aún con Scanner.");
        }
        return instance;
    }

    public void startUserProcess(){
        view.showWelcome();
        boolean exit = false;
        while (!exit) {
            int selected = view.showUserMenu();

            switch (selected) {
                case 0:
                    IMovimiento movimiento = new Movimiento(0, 0, null, 0);
                    IPuntuacion puntuacion = new Puntuacion(0, null);
                    ControladorPartida.getInstance().crearPartida(getUser(AuthController.getInstance().getCurrentUser().getId()), FactoriaMaquina.creaMaquina(askMachine()), puntuacion, movimiento);
                    exit = true;
                    break;
                case 1:
                    startScoresUserProcess();
                    break;
                case 2:
                    if (view.sureForDelete().equals("s")) {
                        DataController.getInstance().deleteUser(getUser(AuthController.getInstance().getCurrentUser().getId()));
                        users.remove(AuthController.getInstance().getCurrentUser().getId());
                        view.showSuccessMessage("Tu cuenta ha sido eliminada exitosamente.");
                        exit = true;
                    } else {
                        view.showErrorMessage("Operación cancelada.");
                    }
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    view.showErrorMessage("Opción no válida");
            }
        }
    }

    private String askMachine(){
        List<String> machines = new ArrayList<>();
        machines.add("FACIL");
        machines.add("NORMAL");
        machines.add("DIFICIL");
        boolean correcto = false;
        String machine = "";
        while(!correcto){
            machine = view.askMachine().toUpperCase();
            if (machines.contains(machine)){
                correcto = true;
            } else view.showErrorMessage("Valor no encontrado. Introduce un valor válido.");
        }
        return machine;
    }

    private void startScoresUserProcess(){
        boolean exit = false;
        while (!exit) {
            int selected = view.showScoresSubmenu();
            switch (selected) {
                case 0:
                    view.showUserTopScores(getRankedScores());
                    break;
                case 1:
                    if (getUser(AuthController.getInstance().getCurrentUser().getId()).isAdmin()) {
                        view.showAllUsersScores(getRankedAllScores());
                    } else {
                        view.showErrorMessage("Acceso denegado. Solo para administradores.");
                    }
                    break;
                case 2:
                    exit = true;
                    break;
                default:
                    view.showErrorMessage("Opción no válida");
            }
        }
    }

    private List<Map.Entry<String, Long>> getRankedAllScores() {
        List<Map.Entry<String, Long>> allScores = users.values().stream()
                .flatMap(user -> user.getPuntuaciones().stream()
                        .map(score -> Map.entry(user.getNombre(), score.getPuntos())))
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .toList();
        return allScores;
    }

    public List<IPuntuacion> getRankedScores() {
        return getUser(AuthController.getInstance().getCurrentUser().getId()).getPuntuaciones().stream()
                .sorted(Comparator.comparingLong(IPuntuacion::getPuntos).reversed())
                .limit(10)
                .toList();
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public List<String> getUsersIds() {
        return new ArrayList<>(users.keySet());
    }

    private Map<String, User> getUsersFromData(){
        return DataController.getInstance().loadUsers();
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
        DataController.getInstance().saveOrUpdateUser(user);
        DataController.getInstance().initializeUserRelations(user);
    }

    public void deleteUser(User user) {
        users.remove(user.getId());
    }


    public int[] realizarTurnoUser(char[][] tableroEnemigo){
        if (tableroEnemigo == null || tableroEnemigo.length == 0) {
            throw new IllegalArgumentException("Tablero enemigo no válido");
        }
        int fila = -1, columna = -1;
        boolean casillaValida = false;
        do {
            int[] resutl = view.askTurn(tableroEnemigo.length, tableroEnemigo[0].length);
            fila = resutl[0]; columna = resutl[1];
            if (fila < 0 || fila >= tableroEnemigo.length ||
                    columna < 0 || columna >= tableroEnemigo[0].length) {
                view.showErrorMessage("¡Coordenadas fuera del tablero!");
                continue;
            }
            if (tableroEnemigo[fila][columna] == '?') {
                casillaValida = true;
            } else {
                view.showErrorMessage("¡Esa casilla ya fue atacada! Elige otra.");
            }
        } while (!casillaValida);
        return new int[]{fila, columna};
    }
}