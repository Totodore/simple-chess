package fr.scriptis.simplechess.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AssetsManager {

    private static final String DIRECTORY = "./assets";

    public static String getSvgAsset(String name) throws IOException {
        String data = "";
        Files.writeString(Path.of(DIRECTORY, name), data);
        return data;
    }
}
