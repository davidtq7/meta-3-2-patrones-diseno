import java.io.PrintStream;
import java.util.function.IntUnaryOperator;

class MotorCombate {
    private final PrintStream salida;
    private final Bitacora bitacora;

    MotorCombate(PrintStream salida, Bitacora bitacora) {
        this.salida = salida;
        this.bitacora = bitacora;
    }

    void iniciarCombate(Personaje heroe, Personaje enemigo, IntUnaryOperator elegirAccion) {
        salida.println("\n=== COMBATE INICIADO ===");
        int turnos = 0, pociones = 2, venenoEnemigo = 0;
        while (heroe.hp() > 0 && enemigo.hp() > 0) {
            salida.println("\n--- TURNO " + ++turnos + " ---");
            if (venenoEnemigo > 0) {
                venenoEnemigo--;
                int antes = enemigo.hp();
                enemigo.cambiarHp(-3);
                salida.printf("Veneno: %s HP: %d -> %d%n", enemigo.nombre(), antes, enemigo.hp());
                if (enemigo.hp() == 0) break;
            }
            if (heroe.regeneracion() > 0 && heroe.hp() < heroe.hpMax()) {
                int antes = heroe.hp();
                heroe.cambiarHp(heroe.regeneracion());
                salida.println("Amuleto: +" + (heroe.hp() - antes) + " HP.");
            }
            int accion = elegirAccion.applyAsInt(pociones);
            if (accion == 2) {
                salida.println(heroe.nombre() + " se defiende. El proximo golpe hace la mitad de dano.");
            } else if (accion == 3 && pociones > 0) {
                pociones--;
                int antes = heroe.hp();
                heroe.cambiarHp(22);
                salida.printf("Pocion: %s HP: %d -> %d (%d restantes).%n", heroe.nombre(), antes, heroe.hp(), pociones);
            } else {
                ResultadoAtaque golpe = golpear(heroe, enemigo, false);
                venenoEnemigo = Math.max(venenoEnemigo, golpe.turnosVeneno());
            }
            if (enemigo.hp() > 0) golpear(enemigo, heroe, accion == 2);
            salida.printf("Estado: %s %d/%d HP | %s %d/%d HP%n",
                    heroe.nombre(), heroe.hp(), heroe.hpMax(), enemigo.nombre(), enemigo.hp(), enemigo.hpMax());
        }
        salida.println("\n=== COMBATE FINALIZADO ===");
        bitacora.cerrar(heroe, enemigo, turnos);
    }

    private ResultadoAtaque golpear(Personaje atacante, Personaje objetivo, boolean cubierto) {
        ResultadoAtaque golpe = atacante.atacar(objetivo);
        if (cubierto) {
            int reducido = Math.max(1, golpe.dano() / 2);
            golpe = new ResultadoAtaque(golpe.forma(), reducido,
                    golpe.bloqueo() + golpe.dano() - reducido, golpe.critico(), golpe.turnosVeneno());
        }
        int antes = objetivo.hp();
        objetivo.cambiarHp(-golpe.dano());
        bitacora.ataque(atacante, objetivo, golpe, antes);
        return golpe;
    }
}
