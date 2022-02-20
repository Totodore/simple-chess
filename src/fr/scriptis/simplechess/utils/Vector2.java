package fr.scriptis.simplechess.utils;

public class Vector2<T> {

    public T x;
    public T y;

    public Vector2(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2<T> v) {
        this.x = v.x;
        this.y = v.y;
    }
}
