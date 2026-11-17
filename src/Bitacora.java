import java.io.PrintStream;

interface Bitacora {
    void ataque(Personaje atacante, Personaje objetivo, ResultadoAtaque resultado, int hpPrevio);
    void cerrar(Personaje heroe, Personaje enemigo, int turnos);
}

class BitacoraBase implements Bitacora {
    private final PrintStream salida;

    BitacoraBase(PrintStream salida) { this.salida = salida; }

    public void ataque(Personaje atacante, Personaje objetivo, ResultadoAtaque resultado, int hpPrevio) {
        salida.printf("%s usa %s: %d de dano | %s HP: %d -> %d%n",
                atacante.nombre(), resultado.forma(), resultado.dano(), objetivo.nombre(), hpPrevio, objetivo.hp());
    }

    public void cerrar(Personaje heroe, Personaje enemigo, int turnos) {
        salida.println(heroe.hp() > 0 ? "Ganador: " + heroe.nombre() : "Ganador: " + enemigo.nombre());
    }
}

class BitacoraDetallada implements Bitacora {
    private final Bitacora base;
    private final PrintStream salida;
    private int golpes;

    BitacoraDetallada(Bitacora base, PrintStream salida) {
        this.base = base;
        this.salida = salida;
    }

    public void ataque(Personaje atacante, Personaje objetivo, ResultadoAtaque resultado, int hpPrevio) {
        base.ataque(atacante, objetivo, resultado, hpPrevio);
        golpes++;
        salida.printf("  Bloqueado: %d%s%n", resultado.bloqueo(), resultado.critico() ? " | CRITICO" : "");
    }

    public void cerrar(Personaje heroe, Personaje enemigo, int turnos) {
        base.cerrar(heroe, enemigo, turnos);
        salida.printf("  %s | Turnos: %d | Golpes: %d | HP final: %d vs. %d%n",
                heroe.hp() > 0 ? "VICTORIA" : "DERROTA", turnos, golpes, heroe.hp(), enemigo.hp());
    }
}
