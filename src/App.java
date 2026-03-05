import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("0. Salir");
            System.out.print("Porfavor, elija una opción: ");
            
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, elija una de las opciones");
                continue;
            }

            switch (opcion) {
                case 1 -> registrar_usuario(sc);
                case 2 -> iniciar_sesion(sc);
                case 0 -> System.out.println("Saliendo");
                default -> System.out.println("Por favor, elija una de las opciones");
            }
        }
    }

    public static void registrar_usuario(Scanner sc) {
        System.out.println("Registrarse");
    }

    public static void iniciar_sesion(Scanner sc) {
        System.out.println("Inicio de Sesión");
    }
}