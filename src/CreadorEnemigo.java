abstract class CreadorEnemigo {
    public abstract Personaje crearEnemigo();
}

class CreadorGoblin extends CreadorEnemigo {
    public Personaje crearEnemigo() { return new Goblin(); }
}

class CreadorEsqueleto extends CreadorEnemigo {
    public Personaje crearEnemigo() { return new Esqueleto(); }
}

class CreadorBrujo extends CreadorEnemigo {
    public Personaje crearEnemigo() { return new Brujo(); }
}

class Goblin extends PersonajeBase {
    Goblin() { super("Goblin", "salvaje", 60, 12, 5, 12, new AtaquePunalada()); }
}

class Esqueleto extends PersonajeBase {
    Esqueleto() { super("Esqueleto", "arquero", 82, 17, 9, 9, new AtaqueFlecha()); }
}

class Brujo extends PersonajeBase {
    Brujo() { super("Brujo", "de la torre", 94, 22, 8, 10, new AtaqueHechizo()); }
}
