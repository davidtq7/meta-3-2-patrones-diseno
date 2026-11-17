abstract class CreadorPersonaje {
    public abstract Personaje crearPersonaje(String nombre);

    protected String validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        return nombre.strip();
    }
}

class CreadorGuerrero extends CreadorPersonaje {
    public Personaje crearPersonaje(String nombre) {
        return new Guerrero(validarNombre(nombre));
    }
}

class CreadorMago extends CreadorPersonaje {
    public Personaje crearPersonaje(String nombre) {
        return new Mago(validarNombre(nombre));
    }
}

class CreadorArquero extends CreadorPersonaje {
    public Personaje crearPersonaje(String nombre) {
        return new Arquero(validarNombre(nombre));
    }
}

class CreadorPicaro extends CreadorPersonaje {
    public Personaje crearPersonaje(String nombre) {
        return new Picaro(validarNombre(nombre));
    }
}

class Guerrero extends PersonajeBase {
    Guerrero(String nombre) { super(nombre, "Guerrero", 105, 17, 12, 8, new AtaqueEspada()); }
}

class Mago extends PersonajeBase {
    Mago(String nombre) { super(nombre, "Mago", 80, 20, 5, 10, new AtaqueHechizo()); }
}

class Arquero extends PersonajeBase {
    Arquero(String nombre) { super(nombre, "Arquero", 88, 18, 7, 13, new AtaqueFlecha()); }
}

class Picaro extends PersonajeBase {
    Picaro(String nombre) { super(nombre, "Picaro", 85, 16, 6, 16, new AtaquePunalada()); }
}
