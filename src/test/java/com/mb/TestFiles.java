package com.mb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.StringJoiner;

import static java.nio.file.Files.readAllLines;

public class TestFiles {

    // We are okay letting exceptions bubble up for tests
    public static String read(String relativeFileName) throws URISyntaxException, IOException {
        final StringJoiner joiner = new StringJoiner("");
        readAllLines(
                Paths.get(
                        TestFiles.class
                                .getClassLoader()
                                .getResource(relativeFileName)
                                .toURI()
                )
        ).forEach(line -> joiner.add(line));

        return joiner.toString();
    }

}
