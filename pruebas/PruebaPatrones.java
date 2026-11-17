import java.util.LinkedHashSet;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class PruebaPatrones {
    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }

    public static void main(String[] args) {
        Personaje aria = new CreadorMago().crearPersonaje("Aria");
        Personaje goblin = new CreadorGoblin().crearEnemigo();
        comprobar(aria instanceof Mago && goblin instanceof Goblin, "Creación de mago y goblin");
        comprobar(new CreadorGuerrero().crearPersonaje("Teo") instanceof Guerrero
                && new CreadorEsqueleto().crearEnemigo() instanceof Esqueleto, "Creadores alternativos");
        comprobar(new CreadorArquero().crearPersonaje("Iris").estrategia() instanceof AtaqueFlecha,
                "Estrategia de arquero");
        int danoBase = aria.atacar(goblin).dano();
        Personaje equipo = Main.equipar(aria, new LinkedHashSet<>(List.of(1, 3, 5)));
        ResultadoAtaque golpe = equipo.atacar(goblin);
        comprobar(danoBase == 21 && equipo.ataque() == 40 && golpe.dano() == 43,
                "El cálculo usa el ataque con equipo y suma el daño directo del veneno");
        comprobar(golpe.turnosVeneno() == 2 && golpe.forma().startsWith("Hechizo"),
                "El veneno no reemplaza la estrategia");
        Personaje cambioOrden = Main.equipar(aria, new LinkedHashSet<>(List.of(5, 1, 3)));
        comprobar(cambioOrden.ataque() == 38, "El orden de las capas importa");
        equipo.cambiarEstrategia(new AtaqueEspada());
        comprobar(equipo.atacar(goblin).forma().startsWith("Espada") && equipo.ataque() == 40,
                "Se puede cambiar la estrategia sin perder el equipo");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream salida = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        new MotorCombate(salida, new BitacoraDetallada(new BitacoraBase(salida), salida))
                .iniciarCombate(equipo, goblin, pociones -> 1);
        String texto = buffer.toString(StandardCharsets.UTF_8);
        comprobar(goblin.hp() == 0 && texto.contains("Veneno:") && texto.contains("VICTORIA"),
                "Combate completo, daño por turno y resumen");
        System.out.println("Pruebas de patrones: OK");
    }
}
