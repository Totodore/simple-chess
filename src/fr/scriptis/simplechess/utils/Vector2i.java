package fr.scriptis.simplechess.utils;

import java.awt.Point;
import java.util.Objects;

public class Vector2i extends Vector2<Integer> {
    public Vector2i(Integer x, Integer y) {
        super(x, y);
    }

    public Vector2i(Vector2i v) {
        super(v.x, v.y);
    }

    public Vector2i(Point p) {
        this(p.x, p.y);
    }

    public Vector2i translate(Vector2i v) {
        Vector2i v2 = new Vector2i(this);
        v2.x += v.x;
        v2.y += v.y;
        return v2;
    }
    public Vector2i translate(int x, int y) {
        return this.translate(new Vector2i(x, y));
    }

    public Vector2i reverse() {
        Vector2i v = new Vector2i(this);
        v.x = -this.x;
        v.y = -this.y;
        return v;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2i v) {
            return Objects.equals(this.x, v.x) && Objects.equals(this.y, v.y);
        } else return super.equals(obj);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
