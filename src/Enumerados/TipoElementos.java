package Enumerados;

public enum TipoElementos {
    AIRE("00"),
    PLATAFORMA_ESTATICA("01"),
    PLATAFORMA_QUEBRADIZA("02"),
    PLATAFORMA_MOVIL("03"),
    SUELO_RESBALADIZO("04"),
    ESCALERA("05"),
    TRAMPA("06"),
    PARED("07"),
    PARED_DESTRUCTIBLE("08"),
    DEMONIO_ROJO("10"),
    TROLL_AMARILLO("11"),
    RANA_DE_FUEGO("12"),
    JUGADOR("20"),
    BOLA_DE_NIEVE("21"),
    POWER_UP_AZUL("22"),
    POWER_UP_VIDAEXTRA("23"),
    POWER_UP_ROJA("24"),
    POWER_UP_VERDE("25"),
    POWER_UP_FRUTA("26"),
    BOLA_DE_FUEGO("30"),
    CALABAZA("13"),
    FANTASMA("14"),
    POWER_UP_DOBLE_PUNTAJE("27"),
    ENEMIGO_ALEATORIO("15"),
    MOGHERA("16"),
    FUEGO("17"),
    KAMAKICHI("18"),
    BOMBA("19");

    protected final String codigo;

    TipoElementos(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static TipoElementos fromCodigo(String codigo) {
        for (TipoElementos tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código desconocido: " + codigo);
    }

}
