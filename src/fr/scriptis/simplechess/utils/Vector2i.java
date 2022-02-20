package fr.scriptis.simplechess.utils;

public class Vector2i extends Vector2<Integer> {
    public Vector2i(Integer x, Integer y) {
        super(x, y);
    }

    public Vector2i(Vector2i v) {
        super(v.x, v.y);
    }

    public void translate(Vector2i v) {
        this.x += v.x;
        this.y += v.y;
    }
}
