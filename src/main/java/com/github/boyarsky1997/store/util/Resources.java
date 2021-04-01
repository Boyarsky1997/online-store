package com.github.boyarsky1997.store.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Resources {
    public static String asString(String path) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Resources.class.getResourceAsStream(path)));
        return bufferedReader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static String load(String path) {
        return new BufferedReader(new InputStreamReader(Resources.class.getResourceAsStream(path)))
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}