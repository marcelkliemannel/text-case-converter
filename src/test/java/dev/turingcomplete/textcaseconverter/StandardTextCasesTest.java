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

import static dev.turingcomplete.textcaseconverter.StandardTextCases.*;
import static java.util.Objects.requireNonNullElse;
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
            String inputWordsEncoded,
            String strictCamelCase,
            String softCamelCase,
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
        List<String> words = inputWordsEncoded == null ? List.of() : Arrays.stream(inputWordsEncoded.split(";")).toList();
        assertThat(STRICT_CAMEL_CASE.convert(words)).isEqualTo(requireNonNullElse(strictCamelCase, ""));
        assertThat(SOFT_CAMEL_CASE.convert(words)).isEqualTo(requireNonNullElse(softCamelCase, ""));
        assertThat(KEBAB_CASE.convert(words)).isEqualTo(requireNonNullElse(kebabCase, ""));
        assertThat(SNAKE_CASE.convert(words)).isEqualTo(requireNonNullElse(snakeCase, ""));
        assertThat(SCREAMING_SNAKE_CASE.convert(words)).isEqualTo(requireNonNullElse(screamingSnakeCase, ""));
        assertThat(TRAIN_CASE.convert(words)).isEqualTo(requireNonNullElse(trainCase, ""));
        assertThat(COBOL_CASE.convert(words)).isEqualTo(requireNonNullElse(cobolCase, ""));
        assertThat(PASCAL_CASE.convert(words)).isEqualTo(requireNonNullElse(pascalCase, ""));
        assertThat(PASCAL_SNAKE_CASE.convert(words)).isEqualTo(requireNonNullElse(pascalSnakeCase, ""));
        assertThat(CAMEL_SNAKE_CASE.convert(words)).isEqualTo(requireNonNullElse(camelSnakeCase, ""));
        assertThat(LOWER_CASE.convert(words)).isEqualTo(requireNonNullElse(lowerCase, ""));
        assertThat(UPPER_CASE.convert(words)).isEqualTo(requireNonNullElse(upperCase, ""));
        assertThat(INVERTED_CASE.convert(words)).isEqualTo(requireNonNullElse(invertedCase, ""));
        assertThat(ALTERNATING_CASE.convert(words)).isEqualTo(requireNonNullElse(alternatingCase, ""));
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
        assertThat(STRICT_CAMEL_CASE.convert(words)).isEqualTo(requireNonNullElse(expectedResult, ""));
        assertThat(STRICT_CAMEL_CASE.convert(words)).isEqualTo(requireNonNullElse(expectedResult, ""));
        assertThat(STRICT_CAMEL_CASE.convert(words)).isEqualTo(requireNonNullElse(expectedResult, ""));
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
        assertThat(KEBAB_CASE.convert("foo bar", StandardWordsSplitters.SPACES)).isEqualTo(requireNonNullElse("foo-bar", ""));
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
        assertThat(KEBAB_CASE.convert("foo bar", StandardWordsSplitters.SPACES, "//")).isEqualTo(requireNonNullElse("foo//bar", ""));
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
        assertThat(KEBAB_CASE.convert(List.of("foo", "bar"), "//")).isEqualTo(requireNonNullElse("foo//bar", ""));
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
        assertThat(SNAKE_CASE.convertFrom(KEBAB_CASE, "foo-bar")).isEqualTo(requireNonNullElse("foo_bar", ""));
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
        assertThat(SNAKE_CASE.convertFrom(COBOL_CASE, "FOO-BAR", "//")).isEqualTo(requireNonNullElse("foo//bar", ""));
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
        assertThat(SNAKE_CASE.convertTo(KEBAB_CASE, "foo_bar")).isEqualTo(requireNonNullElse("foo-bar", ""));
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
        assertThat(SNAKE_CASE.convertTo(COBOL_CASE, "foo_bar", "//")).isEqualTo(requireNonNullElse("FOO//BAR", ""));
    }

    @ParameterizedTest
    @MethodSource("createTestExampleTestVectors")
    void testExample(TextCase textCase) {
        List<String> titleWords = StandardWordsSplitters.SPACES.split(textCase.title());
        String expectedExample = textCase.convert(titleWords);
        assertThat(expectedExample).isEqualTo(requireNonNullElse(textCase.example(), ""));
    }

    static Stream<Arguments> createTestExampleTestVectors() {
        return Stream.of(
                arguments(STRICT_CAMEL_CASE),
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
