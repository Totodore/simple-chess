package fr.scriptis.simplechess.utils;

import java.util.Random;

public class MathUtils {
    public static int generateId() {
        Random r = new Random();
        return r.nextInt(Integer.MAX_VALUE);
    }
}
