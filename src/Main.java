import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.UnaryOperator;

public class Main {
    private static final List<CreadorPersonaje> CLASES = List.of(
            new CreadorGuerrero(), new CreadorMago(), new CreadorArquero(), new CreadorPicaro());
    private static final List<CreadorEnemigo> ENEMIGOS = List.of(
            new CreadorGoblin(), new CreadorEsqueleto(), new CreadorBrujo());
    private static final List<UnaryOperator<Personaje>> EQUIPO = List.of(
            EspadaDecorator::new, ArmaduraDecorator::new, VenenoDecorator::new,
            AmuletoRegeneracionDecorator::new, BendicionDecorator::new);

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.println("=== RPG POR TURNOS ===");
        String nombre = "";
        while (nombre.isBlank()) {
            System.out.println("Nombre del personaje:");
            if (!entrada.hasNextLine()) return;
            nombre = entrada.nextLine().strip();
        }
        int clase = opcion(entrada, "Clase: 1 Guerrero | 2 Mago | 3 Arquero | 4 Pícaro", 1, 4, 1);
        Personaje heroe = CLASES.get(clase - 1).crearPersonaje(nombre);
        heroe = equipar(heroe, elegirEquipo(entrada));
        int rival = opcion(entrada, "Enemigo: 1 Goblin | 2 Esqueleto | 3 Brujo", 1, 3, 1);
        Personaje enemigo = ENEMIGOS.get(rival - 1).crearEnemigo();
        int nivel = opcion(entrada, "Bitácora: 1 Básica | 2 Detallada", 1, 2, 1);

        System.out.printf("Héroe: %s | HP %d | ATQ %d | DEF %d | VEL %d%n",
                heroe.descripcion(), heroe.hp(), heroe.ataque(), heroe.defensa(), heroe.velocidad());
        System.out.printf("Enemigo: %s | HP %d%n", enemigo.descripcion(), enemigo.hp());
        Bitacora bitacora = new BitacoraBase(System.out);
        if (nivel == 2) bitacora = new BitacoraDetallada(bitacora, System.out);
        new MotorCombate(System.out, bitacora).iniciarCombate(heroe, enemigo,
                pociones -> opcion(entrada, "Acción: 1 Atacar | 2 Defender"
                        + (pociones > 0 ? " | 3 Poción (" + pociones + ")" : ""), 1, pociones > 0 ? 3 : 2, 1));
    }

    static Personaje equipar(Personaje heroe, Set<Integer> opciones) {
        for (int numero : opciones) heroe = EQUIPO.get(numero - 1).apply(heroe);
        return heroe;
    }

    private static Set<Integer> elegirEquipo(Scanner entrada) {
        while (true) {
            System.out.println("Equipo: 1 Espada | 2 Armadura | 3 Veneno | 4 Amuleto | 5 Bendición");
            System.out.println("Escribe los números separados por comas, o 0 para no equipar:");
            if (!entrada.hasNextLine()) return Set.of();
            String linea = entrada.nextLine().strip();
            if (linea.equals("0") || linea.isEmpty()) return Set.of();
            Set<Integer> elegidos = new LinkedHashSet<>();
            try {
                for (String parte : linea.split(",", -1)) {
                    int id = Integer.parseInt(parte.strip());
                    if (id < 1 || id > EQUIPO.size()) throw new NumberFormatException();
                    elegidos.add(id);
                }
                return elegidos;
            } catch (NumberFormatException error) {
                System.out.println("Equipo no válido. Ejemplo: 1,3,5");
            }
        }
    }

    private static int opcion(Scanner entrada, String mensaje, int min, int max, int sinEntrada) {
        while (true) {
            System.out.println(mensaje);
            if (!entrada.hasNextLine()) return sinEntrada;
            try {
                int numero = Integer.parseInt(entrada.nextLine().strip());
                if (numero >= min && numero <= max) return numero;
            } catch (NumberFormatException error) {
                // Se vuelve a pedir la opción.
            }
            System.out.println("Elige una opción válida.");
        }
    }
}
