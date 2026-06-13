package Utils;

public class Vector2 {
    protected float coordX;
    protected float coordY;
    
    public Vector2(float x, float y) {
        coordX = x;
        coordY = y;
    }
    
    public float getX() {
        return coordX;
    }
    
    public float getY() {
        return coordY;
    }
    
    public void setX(float x) {
        this.coordX = x;
    }
    
    public void setY(float y) {
        this.coordY = y;
    }

    public void sumar(float x, float y) {
        coordX += x;
        coordY += y;
    }

    public void sumar(Vector2 v) {
        if (v != null) {
            coordX += v.coordX;
            coordY += v.coordY;
        }
    }

    public void multiplicar(float e) {
        coordX *= e;
        coordY *= e;
    }

    public void normalizar() {
        float modulo = (float) Math.sqrt(Math.pow(coordX, 2.0) + Math.pow(coordY, 2.0));
        if (modulo > 1){
            coordX /= modulo;
            coordY /= modulo;
        }
    }

    public float modulo() {
        return (float) Math.sqrt(Math.pow(coordX, 2) + Math.pow(coordY, 2));
    }

    public Vector2 direccionHacia(Vector2 destino) {
        Vector2 res = new Vector2(0, 0);
        res.setX(destino.coordX - coordX);
        res.setY(destino.coordY - coordY);
        return res;
    }

    public Vector2 clone() {
        return new Vector2(coordX,coordY);
    }

    public void copy(Vector2 v) {
        coordX = v.coordX;
        coordY = v.coordY;
    }

    public String toString() {
        return "(" + String.valueOf(coordX) + "," + String.valueOf(coordY) + ")";
    }
    
}
