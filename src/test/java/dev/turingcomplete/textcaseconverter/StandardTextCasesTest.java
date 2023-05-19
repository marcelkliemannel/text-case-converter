package dev.turingcomplete.textcaseconverter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static dev.turingcomplete.textcaseconverter.StandardTextCases.ALTERNATING_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.CAMEL_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.CAMEL_SNAKE_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.COBOL_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.INVERTED_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.KEBAB_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.LOWER_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.PASCAL_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.PASCAL_SNAKE_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.SCREAMING_SNAKE_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.SNAKE_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.TRAIN_CASE;
import static dev.turingcomplete.textcaseconverter.StandardTextCases.UPPER_CASE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StandardTextCasesTest {
    // -- Class Fields ---------------------------------------------------------------------------------------------- //
    // -- Instance Fields ------------------------------------------------------------------------------------------- //
    // -- Initialization -------------------------------------------------------------------------------------------- //
    // -- Exposed Methods ------------------------------------------------------------------------------------------- //

    @ParameterizedTest
    @CsvFileSource(
            resources = "/dev/turingcomplete/textcaseconverter/textCasesTestVectors.csv",
            useHeadersInDisplayName = true,
            nullValues = {"null"}
    )
    void testConvertWords(
            String combinedWords,
            String camelCase,
            String kebabCase,
            String snakeCase,
            String screamingSnakeCase,
            String trainCase,
            String cobolCase,
            String pascalCase,
            String pascalSnakeCase,
            String camelSnakeCase,
            String lowerCase,
            String upperCase,
            String invertedCase,
            String alternatingCase
    ) {
        List<String> words = Arrays.stream(combinedWords.split(";")).toList();
        assertThat(CAMEL_CASE.convert(words)).isEqualTo(camelCase);
        assertThat(KEBAB_CASE.convert(words)).isEqualTo(kebabCase);
        assertThat(SNAKE_CASE.convert(words)).isEqualTo(snakeCase);
        assertThat(SCREAMING_SNAKE_CASE.convert(words)).isEqualTo(screamingSnakeCase);
        assertThat(TRAIN_CASE.convert(words)).isEqualTo(trainCase);
        assertThat(COBOL_CASE.convert(words)).isEqualTo(cobolCase);
        assertThat(PASCAL_CASE.convert(words)).isEqualTo(pascalCase);
        assertThat(PASCAL_SNAKE_CASE.convert(words)).isEqualTo(pascalSnakeCase);
        assertThat(CAMEL_SNAKE_CASE.convert(words)).isEqualTo(camelSnakeCase);
        assertThat(LOWER_CASE.convert(words)).isEqualTo(lowerCase);
        assertThat(UPPER_CASE.convert(words)).isEqualTo(upperCase);
        assertThat(INVERTED_CASE.convert(words)).isEqualTo(invertedCase);
        assertThat(ALTERNATING_CASE.convert(words)).isEqualTo(alternatingCase);
    }

    /**
     * Tests {@link TextCase#convert(String...)}.
     *
     * <p>We only test one test case here since internally everything gets
     * formatted by the same code as tested in
     * {@link StandardTextCasesTest#testConvertWords)}.
     */
    @ParameterizedTest
    @CsvSource(
            value = {
                    "\\\"\\\",\\\"\\\"",
                    "foo,foo",
                    "foo|bar,fooBar"
            },
            nullValues = "null"
    )
    void testConvertOfWordsArray(String encodedWords, String expectedResult) {
        String[] words = encodedWords.split("\\|");
        assertThat(CAMEL_CASE.convert(words)).isEqualTo(expectedResult);
        assertThat(CAMEL_CASE.convert(words)).isEqualTo(expectedResult);
        assertThat(CAMEL_CASE.convert(words)).isEqualTo(expectedResult);
    }

    /**
     * Tests {@link TextCase#convert(String, WordsSplitter)}.
     *
     * <p>We only test one test case here since internally everything gets
     * formatted by the same code as tested in
     * {@link StandardTextCasesTest#testConvertWords)}.
     */
    @Test
    void testConvertTextWithWordsSplitter() {
        assertThat(KEBAB_CASE.convert("foo bar", StandardWordsSplitters.SPACES)).isEqualTo("foo-bar");
    }

    /**
     * Tests {@link TextCase#convert(String, WordsSplitter, String)}.
     *
     * <p>We only test one test case here since internally everything gets
     * formatted by the same code as tested in
     * {@link StandardTextCasesTest#testConvertWords)}.
     */
    @Test
    void testConvertTextWithWordsSplitterAndDelimiter() {
        assertThat(KEBAB_CASE.convert("foo bar", StandardWordsSplitters.SPACES, "//")).isEqualTo("foo//bar");
    }

    /**
     * Tests {@link TextCase#convert(List, String)}.
     *
     * <p>We only test one test case here since internally everything gets
     * formatted by the same code as tested in
     * {@link StandardTextCasesTest#testConvertWords)}.
     */
    @Test
    void testConvertWordsWithCustomDelimiter() {
        assertThat(KEBAB_CASE.convert(List.of("foo", "bar"), "//")).isEqualTo("foo//bar");
    }

    /**
     * Tests {@link TextCase#convertFrom(TextCase, String)}.
     *
     * <p>We only test one test case here since internally everything gets
     * formatted by the same code as tested in
     * {@link StandardTextCasesTest#testConvertWords)}.
     */
    @Test
    void testConvertFrom() {
        assertThat(SNAKE_CASE.convertFrom(KEBAB_CASE, "foo-bar")).isEqualTo("foo_bar");
    }

    /**
     * Tests {@link TextCase#convertFrom(TextCase, String, String)}.
     *
     * <p>We only test one test case here since internally everything gets
     * formatted by the same code as tested in
     * {@link StandardTextCasesTest#testConvertWords)}.
     */
    @Test
    void testConvertFromWithCustomDelimiter() {
        assertThat(SNAKE_CASE.convertFrom(COBOL_CASE, "FOO-BAR", "//")).isEqualTo("foo//bar");
    }

    /**
     * Tests {@link TextCase#convertTo(TextCase, String)}.
     *
     * <p>We only test one test case here since internally everything gets
     * formatted by the same code as tested in
     * {@link StandardTextCasesTest#testConvertWords)}.
     */
    @Test
    void testConvertTo() {
        assertThat(SNAKE_CASE.convertTo(KEBAB_CASE, "foo_bar")).isEqualTo("foo-bar");
    }

    /**
     * Tests {@link TextCase#convertTo(TextCase, String)}.
     *
     * <p>We only test one test case here since internally everything gets
     * formatted by the same code as tested in
     * {@link StandardTextCasesTest#testConvertWords} )}.
     */
    @Test
    void testConvertToWithCustomDelimiter() {
        assertThat(SNAKE_CASE.convertTo(COBOL_CASE, "foo_bar", "//")).isEqualTo("FOO//BAR");
    }

    @ParameterizedTest
    @MethodSource("createTestExampleTestVectors")
    void testExample(TextCase textCase) {
        List<String> titleWords = StandardWordsSplitters.SPACES.split(textCase.title());
        String expectedExample = textCase.convert(titleWords);
        assertThat(expectedExample).isEqualTo(textCase.example());
    }

    static Stream<Arguments> createTestExampleTestVectors() {
        return Stream.of(
                arguments(CAMEL_CASE),
                arguments(KEBAB_CASE),
                arguments(SNAKE_CASE),
                arguments(SCREAMING_SNAKE_CASE),
                arguments(TRAIN_CASE),
                arguments(COBOL_CASE),
                arguments(PASCAL_CASE),
                arguments(PASCAL_SNAKE_CASE),
                arguments(CAMEL_SNAKE_CASE),
                arguments(LOWER_CASE),
                arguments(UPPER_CASE),
                arguments(INVERTED_CASE),
                arguments(ALTERNATING_CASE)
        );
    }

    // -- Private Methods ------------------------------------------------------------------------------------------- //
    // -- Inner Type ------------------------------------------------------------------------------------------------ //
}
