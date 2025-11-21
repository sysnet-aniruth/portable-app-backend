package com.example.portable.Util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileLoader
{
    public static Optional<String> readStringExternal(String relativePath) {
        try {
            Path p = Path.of(relativePath);
            if (Files.exists(p)) return Optional.of(Files.readString(p).trim());
        } catch (Exception ignored) {}
        return Optional.empty();
    }
}