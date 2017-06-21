package com.mb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringJoiner;

import static java.nio.file.Files.readAllLines;
import static org.assertj.core.api.Assertions.assertThat;

public class JsLibParserTest {

    private JsLibParser jsLibParser;

    @BeforeEach
    public void setUp() {
        jsLibParser = new JsLibParser();
    }

    @Test
    public void should_parse_a_single_javascript_library() {
        // Given
        String input = "<script type=\"text/javascript\" src=\"https://s.yimg.com/lq/ult/ylc_1.9.js\" >";

        // When
        List<String> parsedLibraries = jsLibParser.parse(input);

        // Then
        assertThat(parsedLibraries).containsExactly("ylc_1.9");
    }

    @Test
    public void should_not_fail_upon_empty_string() {
        // When
        List<String> parsedLibraries = jsLibParser.parse("");

        // Then
        assertThat(parsedLibraries).isEmpty();
    }

    @Test
    public void should_be_permissive_with_regards_to_extra_spaces() {
        // Given
        String input = "< script   type=   \"text/javascript\"  src =   \"https://s.yimg.com/lq/ult/ylc_1.9.js\" >";

        // When
        List<String> parsedLibraries = jsLibParser.parse(input);

        // Then
        assertThat(parsedLibraries).containsExactly("ylc_1.9");
    }

    @Test
    public void should_parse_javascript_file_names() throws IOException, URISyntaxException {
        // Given
        String htmlFileContent = TestFiles.read("afile.html");

        // When
        List<String> parsedLibraries = jsLibParser.parse(htmlFileContent);

        // Then
        assertThat(parsedLibraries).containsExactlyInAnyOrder(
                "ylc_1.9",
                "beacon-1.3.6.4",
                "recs-1.3.2.2"
        );
    }

}
