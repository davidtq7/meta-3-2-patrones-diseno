interface EstrategiaAtaque {
    ResultadoAtaque atacar(Personaje atacante, Personaje objetivo);
}

record ResultadoAtaque(String forma, int dano, int bloqueo, boolean critico, int turnosVeneno) {
    static ResultadoAtaque calcular(String forma, int potencia, int bloqueo, boolean critico) {
        int total = Math.max(1, potencia - bloqueo);
        return new ResultadoAtaque(forma, total, Math.min(bloqueo, potencia - total), critico, 0);
    }

    ResultadoAtaque conVeneno() {
        return new ResultadoAtaque(forma + " con veneno", dano + 2, bloqueo, critico, 2);
    }
}

class AtaqueEspada implements EstrategiaAtaque {
    public ResultadoAtaque atacar(Personaje atacante, Personaje objetivo) {
        return ResultadoAtaque.calcular("Espada", atacante.ataque() + 4, objetivo.defensa(), false);
    }
}

class AtaqueHechizo implements EstrategiaAtaque {
    public ResultadoAtaque atacar(Personaje atacante, Personaje objetivo) {
        return ResultadoAtaque.calcular("Hechizo", atacante.ataque() + 3, objetivo.defensa() / 2, false);
    }
}

class AtaqueFlecha implements EstrategiaAtaque {
    public ResultadoAtaque atacar(Personaje atacante, Personaje objetivo) {
        return ResultadoAtaque.calcular("Flecha", atacante.ataque() + 2,
                Math.max(0, objetivo.defensa() - 3), false);
    }
}

class AtaquePunalada implements EstrategiaAtaque {
    public ResultadoAtaque atacar(Personaje atacante, Personaje objetivo) {
        boolean critico = atacante.velocidad() - objetivo.velocidad() >= 4;
        return ResultadoAtaque.calcular("Puñalada", atacante.ataque() + (critico ? 8 : 0),
                objetivo.defensa(), critico);
    }
}
