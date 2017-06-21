package com.mb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleLinksParserTest {

    private GoogleLinksParser googleLinksParser;

    @BeforeEach
    public void setUp() {
        googleLinksParser = new GoogleLinksParser();
    }

    @Test
    public void should_not_fail_on_empty_string() {
        // When
        List<String> parsedLinks = googleLinksParser.parse("");

        // Then
        assertThat(parsedLinks).isEmpty();
    }

    @Test
    public void should_parse_google_results_links() throws IOException, URISyntaxException {
        // Given
        String googleResults = TestFiles.read("sketchpad_google_results.html");

        // When
        List<String> parsedLinks = googleLinksParser.parse(googleResults);

        // Then
        assertThat(parsedLinks).containsExactlyInAnyOrder(
                "https://www.youtube.com/watch?v=USyoT_Ha_bA",
                "https://fr.wikipedia.org/wiki/Sketchpad",
                "https://en.wikipedia.org/wiki/Sketchpad",
                "https://www.cl.cam.ac.uk/techreports/UCAM-CL-TR-574.pdf",
                "http://history-computer.com/ModernComputer/Software/Sketchpad.html",
                "http://www.cgsociety.org/news/article/579/ivan-sutherland-sketchpad-demo",
                "http://www.inventinginteractive.com/2010/01/18/1963-sketchpad/",
                "http://www.cadazz.com/cad-software-Sketchpad.htm",
                "https://www.britannica.com/technology/Sketchpad",
                "http://techland.time.com/2013/04/12/a-talk-with-computer-graphics-pioneer-ivan-sutherland/"
        );
    }

    @Test
    public void should_handle_link_to_pdf() {
        // When
        List<String> parsedLinks = googleLinksParser.parse("<h3 class=\"r\"><span class=\"_ogd b w xsm\">[PDF]</span><a\n" +
                "                    href=\"https://www.cl.cam.ac.uk/techreports/UCAM-CL-TR-574.pdf\"");

        // Then
        assertThat(parsedLinks).containsExactly("https://www.cl.cam.ac.uk/techreports/UCAM-CL-TR-574.pdf");
    }

}
