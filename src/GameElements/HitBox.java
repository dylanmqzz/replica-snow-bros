package GameElements;

import Utils.Vector2;

public class HitBox {
    protected float base;
    protected float altura;
    protected Vector2 posicion;
    protected Vector2 offset;

    public HitBox(float b, float a, Vector2 pos) {
        posicion = pos;
        base = b;
        altura = a;
        offset = new Vector2(0, 0);
    }

    public HitBox(float b, float a, Vector2 pos, Vector2 offset) {
        this.offset = offset;
        posicion = pos;
        posicion.sumar(offset);
        base = b;
        altura = a;
    }

    public float getAltura() {
        return altura;
    }

    public float getBase() {
        return base;
    }

    public Vector2 getPosition() {
        return posicion;
    }

    public void setPosition(Vector2 v) {
        posicion = v;
        posicion.sumar(offset);
    }

    public boolean chequearColision(HitBox h) {
        boolean colision = false;
        if (h != null) {
            float ax = posicion.getX();
            float ay = posicion.getY();
            float aw = base;
            float ah = altura;
            float bx = h.posicion.getX();
            float by = h.posicion.getY();
            float bw = h.base;
            float bh = h.altura;

            colision = ax <= bx + bw &&
                    ax + aw >= bx &&
                    ay <= by + bh &&
                    ay + ah >= by;
        }
        return colision;
    }

    public Vector2 calcularCorreccion(HitBox h) {
        Vector2 correccion = new Vector2(0, 0);
        float overlapX = Math.min(posicion.getX() + base, h.posicion.getX() + h.base) - Math.max(posicion.getX(), h.posicion.getX());
        float overlapY = Math.min(posicion.getY() + altura, h.posicion.getY() + h.altura) - Math.max(posicion.getY(), h.posicion.getY());

        if (overlapX < overlapY)
            correccion = new Vector2(posicion.getX() < h.posicion.getX() ? -overlapX : overlapX, 0);
        else
            correccion = new Vector2(0, posicion.getY() < h.posicion.getY() ? -overlapY : overlapY);
        
        return correccion;
    }

    public int diferenciaAltura(HitBox h) {
        float diff = posicion.getY() - h.posicion.getY();
        if (diff > 0 && Math.abs(diff) >= h.altura)
            return 1;
        if (diff < 0 && Math.abs(diff) >= altura)
            return -1;
        return 0;
    }

}
