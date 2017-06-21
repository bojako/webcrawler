package com.mb;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsLibRankerTest {

    @Test
    public void should_learn_library_name_by_pattern() {
        // Given
        JsLibRanker jsLibRanker = new JsLibRanker();
        jsLibRanker.usePattern("angularjs.*", "Angular JS");

        // When
        jsLibRanker.consume("angularjs_1.2.3.js");

        // Then
        assertThat(jsLibRanker.getRanking())
                .extracting(libraryRanking -> libraryRanking.libraryName)
                .containsExactly("Angular JS");
    }

    @Test
    public void should_return_ranking_based_on_number_of_occurrences() {
        // Given
        JsLibRanker jsLibRanker = new JsLibRanker();
        jsLibRanker.usePattern("angularjs.*", "Angular JS");
        jsLibRanker.usePattern("jquery.*", "JQuery");

        // When
        jsLibRanker.consume("angularjs_1.2.3.js");
        jsLibRanker.consume("jquery_3.4.js");
        jsLibRanker.consume("jquery_3.2.js");
        jsLibRanker.consume("jquery_3.1.js");
        jsLibRanker.consume("angularjs_1.2.3.js");

        // Then
        assertThat(jsLibRanker.getRanking())
                .extracting(libraryRanking -> libraryRanking.libraryName)
                .containsExactly("JQuery", "Angular JS");
    }

    @Test
    public void should_take_into_account_unknown_libraries() {
        // Given
        JsLibRanker jsLibRanker = new JsLibRanker();
        jsLibRanker.usePattern("angularjs.*", "Angular JS");
        jsLibRanker.usePattern("jquery.*", "JQuery");

        // When
        jsLibRanker.consume("angularjs_1.2.3.js");
        jsLibRanker.consume("jquery_3.4.js");
        jsLibRanker.consume("jquery_3.4.js");
        jsLibRanker.consume("unknown.js");
        jsLibRanker.consume("unknown.js");
        jsLibRanker.consume("unknown.js");

        // Then
        assertThat(jsLibRanker.getRanking())
                .extracting(libraryRanking -> libraryRanking.libraryName)
                .containsExactly("unknown.js", "JQuery", "Angular JS");
    }

}
