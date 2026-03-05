import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.List;

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
        System.out.print("Introduce email: ");
        String email = sc.nextLine();
        System.out.print("Introduce contraseña: ");
        String password = sc.nextLine();

        if (email.isEmpty() || password.isEmpty() || !email.contains("@")) {
            System.out.println("Error: Datos invalidos o vacios.");
            return;
        }

        try {
            Files.createDirectories(Paths.get("data"));
            Path users_file = Paths.get("data", "users.txt");
            
            if (Files.exists(users_file)) {
                List<String> usuarios = Files.readAllLines(users_file);
                for (String u : usuarios) {
                    if (u.split(";")[0].equals(email)) {
                        System.out.println("Error: El usuario ya existe.");
                        return;
                    }
                }
            }

            // Guardamos la contraseña directamente (sin cifrar)
            String datos = email + ";" + password + System.lineSeparator();
            Files.writeString(users_file, datos, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            
            String email_sanitizado = email.replace("@", "_").replace(".", "_");
            Files.createDirectories(Paths.get("data", "usuarios", email_sanitizado));

            System.out.println("Usuario registrado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero.");
        }
    }

    public static void iniciar_sesion(Scanner sc) {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Contraseña: ");
        String password = sc.nextLine();

        Path users_file = Paths.get("data", "users.txt");

        if (!Files.exists(users_file)) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        try {
            List<String> lineas = Files.readAllLines(users_file);
            boolean login_ok = false;

            for (String linea : lineas) {
                String[] datos = linea.split(";");
                // Comparamos la contraseña directamente con el texto del archivo
                if (datos.length >= 2 && datos[0].equals(email) && datos[1].equals(password)) {
                    login_ok = true;
                    break;
                }
            }

            if (login_ok) {
                System.out.println("Ha iniciado sesión");
                menu_notas(sc, email);
            } else {
                System.out.println("Email y/o contraseña incorrecto.");
            }
        } catch (IOException e) {
            System.out.println("Hubo un error al leer los datos.");
        }
    }

    public static void menu_notas(Scanner sc, String email) {
        String email_sanitizado = email.replace("@", "_").replace(".", "_");
        Path archivo_notas = Paths.get("data", "usuarios", email_sanitizado, "notas.txt");

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("--- GESTION DE NOTAS ---");
            System.out.println("1. Crear nota");
            System.out.println("2. Listar notas");
            System.out.println("3. Ver nota por numero");
            System.out.println("4. Eliminar nota");
            System.out.println("5. Buscar nota por palabra");
            System.out.println("0. Cerrar sesion");
            System.out.print("Selecciona una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }

            switch (opcion) {
                case 1 -> crear_nota(sc, archivo_notas);
                case 2 -> listar_notas(archivo_notas);
                case 3 -> ver_nota(sc, archivo_notas);
                case 4 -> eliminar_nota(sc, archivo_notas);
                case 5 -> buscar_nota(sc, archivo_notas);
                case 0 -> System.out.println("Sesion cerrada");
            }
        }
    }

    public static void crear_nota(Scanner sc, Path archivo_notas) {
        System.out.print("Titulo: ");
        String titulo = sc.nextLine();
        System.out.print("Contenido: ");
        String contenido = sc.nextLine();
        
        if (titulo.contains(";") || contenido.contains(";")) {
            System.out.println("Error: No uses el caracter ';' en tus notas.");
            return;
        }

        String linea = titulo + ";" + contenido + System.lineSeparator();

        try {
            Files.writeString(archivo_notas, linea, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Nota guardada.");
        } catch (IOException e) {
            System.out.println("Error al guardar la nota.");
        }
    }

    public static void listar_notas(Path archivo_notas) {
        if (!Files.exists(archivo_notas)) {
            System.out.println("No hay notas guardadas.");
            return;
        }
        try {
            List<String> lineas = Files.readAllLines(archivo_notas);
            for (int i = 0; i < lineas.size(); i++) {
                String[] partes = lineas.get(i).split(";");
                System.out.println((i + 1) + ". " + partes[0]);
            }
        } catch (IOException e) {
            System.out.println("Error al leer las notas.");
        }
    }

    public static void ver_nota(Scanner sc, Path archivo_notas) {
        if (!Files.exists(archivo_notas)) return;
        try {
            List<String> lineas = Files.readAllLines(archivo_notas);
            System.out.print("Numero de nota: ");
            int num = Integer.parseInt(sc.nextLine());
            if (num > 0 && num <= lineas.size()) {
                String[] partes = lineas.get(num - 1).split(";");
                System.out.println("Contenido: " + partes[1]);
            }
        } catch (Exception e) {
            System.out.println("Error al ver la nota.");
        }
    }

    public static void eliminar_nota(Scanner sc, Path archivo_notas) {
        if (!Files.exists(archivo_notas)) return;
        try {
            List<String> lineas = Files.readAllLines(archivo_notas);
            System.out.print("Numero de nota a borrar: ");
            int num = Integer.parseInt(sc.nextLine());
            if (num > 0 && num <= lineas.size()) {
                lineas.remove(num - 1);
                Files.write(archivo_notas, lineas);
                System.out.println("Nota eliminada.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar la nota.");
        }
    }

    public static void buscar_nota(Scanner sc, Path archivo_notas) {
        if (!Files.exists(archivo_notas)) return;
        System.out.print("Palabra clave: ");
        String palabra = sc.nextLine().toLowerCase();
        try {
            List<String> lineas = Files.readAllLines(archivo_notas);
            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).toLowerCase().contains(palabra)) {
                    String[] partes = lineas.get(i).split(";");
                    System.out.println("Encontrada en nota " + (i + 1) + ": " + partes[0]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar.");
        }
    }
}