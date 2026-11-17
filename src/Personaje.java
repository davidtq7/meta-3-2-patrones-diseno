interface Personaje {
    String nombre();
    String descripcion();
    int hp();
    int hpMax();
    int ataque();
    int defensa();
    int velocidad();
    void cambiarHp(int puntos);
    EstrategiaAtaque estrategia();
    void cambiarEstrategia(EstrategiaAtaque estrategia);

    default int regeneracion() { return 0; }
    default ResultadoAtaque atacar(Personaje objetivo) {
        return atacarComo(this, objetivo);
    }
    ResultadoAtaque atacarComo(Personaje atacante, Personaje objetivo);
}

class PersonajeBase implements Personaje {
    private final String nombre, clase;
    private final int hpMax, ataque, defensa, velocidad;
    private int hp;
    private EstrategiaAtaque estrategia;

    PersonajeBase(String nombre, String clase, int hpMax, int ataque,
                  int defensa, int velocidad, EstrategiaAtaque estrategia) {
        this.nombre = nombre;
        this.clase = clase;
        this.hpMax = hpMax;
        this.hp = hpMax;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.estrategia = estrategia;
    }

    public String nombre() { return nombre; }
    public String descripcion() { return nombre + " (" + clase + ")"; }
    public int hp() { return hp; }
    public int hpMax() { return hpMax; }
    public int ataque() { return ataque; }
    public int defensa() { return defensa; }
    public int velocidad() { return velocidad; }
    public void cambiarHp(int puntos) { hp = Math.max(0, Math.min(hpMax, hp + puntos)); }
    public EstrategiaAtaque estrategia() { return estrategia; }
    public void cambiarEstrategia(EstrategiaAtaque estrategia) { this.estrategia = estrategia; }

    public ResultadoAtaque atacarComo(Personaje atacante, Personaje objetivo) {
        return estrategia.atacar(atacante, objetivo);
    }
}
