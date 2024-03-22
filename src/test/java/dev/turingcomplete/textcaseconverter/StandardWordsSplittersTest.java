package dev.turingcomplete.textcaseconverter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StandardWordsSplittersTest {
    // -- Class Fields ---------------------------------------------------------------------------------------------- //
    // -- Instance Fields ------------------------------------------------------------------------------------------- //
    // -- Initialization -------------------------------------------------------------------------------------------- //
    // -- Exposed Methods ------------------------------------------------------------------------------------------- //

    @ParameterizedTest
    @CsvSource(value = {
            ",",
            "foo,foo",
            "  foo ,foo",
            "foo bar,foo|bar",
            " foo   bar  ,foo|bar"
    })
    void testSpaceWordSeparator(String input, String expectedWordsEncoded) {
        input = input == null ? "" : input;
        String[] expectedWords = expectedWordsEncoded == null ? new String[0] : expectedWordsEncoded.split("\\|");

        List<String> actualWords = StandardWordsSplitters.SPACES.split(input);
        assertThat(actualWords).containsExactly(expectedWords);
    }

    @ParameterizedTest
    @CsvSource(value = {
            ",",
            "f,f",
            "F,F",
            "Fo,Fo",
            "Foo,Foo",
            "FooBar,Foo|Bar",
            "fooBar,foo|Bar",
            "fooSQLbar,foo|S|Q|Lbar",
            "foo,foo",
            "FB,F|B",
            "FBB,F|B|B",
            "FoBB,Fo|B|B",
            "f o,f| o",
            "f o B,f| o| |B",
            "  foo Bar,foo| |Bar"
    })
    void testStrictUpperCaseCharacterWordSeparator(String input, String expectedWordsEncoded) {
        input = input == null ? "" : input;
        String[] expectedWords = expectedWordsEncoded == null ? new String[0] : expectedWordsEncoded.split("\\|");

        List<String> actualWords = StandardWordsSplitters.STRICT_UPPER_CASE.split(input);
        assertThat(actualWords).containsExactly(expectedWords);
    }

    @ParameterizedTest
    @CsvSource(value = {
            ",",
            "f,f",
            "F,F",
            "Fo,Fo",
            "Foo,Foo",
            "FooBar,Foo|Bar",
            "fooBar,foo|Bar",
            "fooSQLbar,foo|SQLbar",
            "foo,foo",
            "FB,FB",
            "FBB,FBB",
            "FoBB,Fo|BB",
            "f o,f| o",
            "f o B,f| o| B",
            "  foo Bar,foo| Bar"
    })
    void testSoftUpperCaseCharacterWordSeparator(String input, String expectedWordsEncoded) {
        input = input == null ? "" : input;
        String[] expectedWords = expectedWordsEncoded == null ? new String[0] : expectedWordsEncoded.split("\\|");

        List<String> actualWords = StandardWordsSplitters.SOFT_UPPER_CASE.split(input);
        assertThat(actualWords).containsExactly(expectedWords);
    }

    @ParameterizedTest
    @CsvSource({
            ",",
            "f,f",
            "f-o,f|o",
            "-,",
            "-o,o",
            "f-o-o,f|o|o",
            "f-o----o,f|o|o"
    })
    void testDashWordSeparator(String input, String expectedWordsEncoded) {
        input = input == null ? "" : input;
        String[] expectedWords = expectedWordsEncoded == null ? new String[0] : expectedWordsEncoded.split("\\|");

        List<String> actualWords = StandardWordsSplitters.DASH.split(input);
        assertThat(actualWords).containsExactly(expectedWords);
    }

    @ParameterizedTest
    @CsvSource(value = {
            ",",
            "f,f",
            "f_o,f|o",
            "_,",
            "_o,o",
            "_o_,o",
            "f_o_o,f|o|o",
            "f_o____o,f|o|o"
    })
    void testUnderscoreWordSeparator(String input, String expectedWordsEncoded) {
        input = input == null ? "" : input;
        String[] expectedWords = expectedWordsEncoded == null ? new String[0] : expectedWordsEncoded.split("\\|");

        List<String> actualWords = StandardWordsSplitters.UNDERSCORE.split(input);
        assertThat(actualWords).containsExactly(expectedWords);
    }

    @ParameterizedTest
    @CsvSource(value = {
            ",",
            "f,f",
            "f.o,f|o",
            ".,",
            ".o,o",
            ".o.,o",
            "f.o.o,f|o|o",
            "f.o....o,f|o|o"
    })
    void testDotWordSeparator(String input, String expectedWordsEncoded) {
        input = input == null ? "" : input;
        String[] expectedWords = expectedWordsEncoded == null ? new String[0] : expectedWordsEncoded.split("\\|");

        List<String> actualWords = StandardWordsSplitters.DOT.split(input);
        assertThat(actualWords).containsExactly(expectedWords);
    }

    // -- Private Methods ------------------------------------------------------------------------------------------- //
    // -- Inner Type ------------------------------------------------------------------------------------------------ //
}
