abstract class PersonajeDecorator implements Personaje {
    protected final Personaje personaje;

    PersonajeDecorator(Personaje personaje) { this.personaje = personaje; }

    public String nombre() { return personaje.nombre(); }
    public String descripcion() { return personaje.descripcion(); }
    public int hp() { return personaje.hp(); }
    public int hpMax() { return personaje.hpMax(); }
    public int ataque() { return personaje.ataque(); }
    public int defensa() { return personaje.defensa(); }
    public int velocidad() { return personaje.velocidad(); }
    public int regeneracion() { return personaje.regeneracion(); }
    public void cambiarHp(int puntos) { personaje.cambiarHp(puntos); }
    public EstrategiaAtaque estrategia() { return personaje.estrategia(); }
    public void cambiarEstrategia(EstrategiaAtaque estrategia) {
        personaje.cambiarEstrategia(estrategia);
    }

    public ResultadoAtaque atacarComo(Personaje atacante, Personaje objetivo) {
        // Conserva al atacante equipado aunque la llamada pase por varias capas.
        return personaje.atacarComo(atacante, objetivo);
    }
}

class EspadaDecorator extends PersonajeDecorator {
    EspadaDecorator(Personaje personaje) { super(personaje); }
    public int ataque() { return super.ataque() + 10; }
    public String descripcion() { return super.descripcion() + " + Espada"; }
}

class ArmaduraDecorator extends PersonajeDecorator {
    ArmaduraDecorator(Personaje personaje) { super(personaje); }
    public int defensa() { return super.defensa() + 8; }
    public String descripcion() { return super.descripcion() + " + Armadura"; }
}

class VenenoDecorator extends PersonajeDecorator {
    VenenoDecorator(Personaje personaje) { super(personaje); }
    public int ataque() { return super.ataque() + 5; }
    public String descripcion() { return super.descripcion() + " + Veneno"; }
    public ResultadoAtaque atacarComo(Personaje atacante, Personaje objetivo) {
        return super.atacarComo(atacante, objetivo).conVeneno();
    }
}

class AmuletoRegeneracionDecorator extends PersonajeDecorator {
    AmuletoRegeneracionDecorator(Personaje personaje) { super(personaje); }
    public int defensa() { return super.defensa() + 3; }
    public int regeneracion() { return super.regeneracion() + 2; }
    public String descripcion() { return super.descripcion() + " + Amuleto"; }
}

class BendicionDecorator extends PersonajeDecorator {
    BendicionDecorator(Personaje personaje) { super(personaje); }
    public int ataque() { return (int) Math.round(super.ataque() * 1.15); }
    public int defensa() { return (int) Math.round(super.defensa() * 1.15); }
    public int velocidad() { return (int) Math.round(super.velocidad() * 1.15); }
    public String descripcion() { return super.descripcion() + " + Bendicion"; }
}
