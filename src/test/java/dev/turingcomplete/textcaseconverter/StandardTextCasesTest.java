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
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @CsvFileSource(
          resources = "/dev/turingcomplete/textcaseconverter/textCasesTestVectors.csv",
          useHeadersInDisplayName = true,
          nullValues = {"null"}
  )
  void testTransformWords(
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
    assertThat(CAMEL_CASE.transform(words)).isEqualTo(camelCase);
    assertThat(KEBAB_CASE.transform(words)).isEqualTo(kebabCase);
    assertThat(SNAKE_CASE.transform(words)).isEqualTo(snakeCase);
    assertThat(SCREAMING_SNAKE_CASE.transform(words)).isEqualTo(screamingSnakeCase);
    assertThat(TRAIN_CASE.transform(words)).isEqualTo(trainCase);
    assertThat(COBOL_CASE.transform(words)).isEqualTo(cobolCase);
    assertThat(PASCAL_CASE.transform(words)).isEqualTo(pascalCase);
    assertThat(PASCAL_SNAKE_CASE.transform(words)).isEqualTo(pascalSnakeCase);
    assertThat(CAMEL_SNAKE_CASE.transform(words)).isEqualTo(camelSnakeCase);
    assertThat(LOWER_CASE.transform(words)).isEqualTo(lowerCase);
    assertThat(UPPER_CASE.transform(words)).isEqualTo(upperCase);
    assertThat(INVERTED_CASE.transform(words)).isEqualTo(invertedCase);
    assertThat(ALTERNATING_CASE.transform(words)).isEqualTo(alternatingCase);
  }

  /**
   * Tests {@link TextCase#transform(String...)}.
   *
   * <p>We only test one test case here since internally everything gets
   * formatted by the same code as tested in
   * {@link StandardTextCasesTest#testTransformWords)}.
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
  void testTransformOfWordsArray(String encodedWords, String expectedResult) {
    String[] words = encodedWords.split("\\|");
    assertThat(CAMEL_CASE.transform(words)).isEqualTo(expectedResult);
    assertThat(CAMEL_CASE.transform(words)).isEqualTo(expectedResult);
    assertThat(CAMEL_CASE.transform(words)).isEqualTo(expectedResult);
  }

  /**
   * Tests {@link TextCase#transform(String, WordsSplitter)}.
   *
   * <p>We only test one test case here since internally everything gets
   * formatted by the same code as tested in
   * {@link StandardTextCasesTest#testTransformWords)}.
   */
  @Test
  void testTransformTextWithWordsSplitter() {
    assertThat(KEBAB_CASE.transform("foo bar", StandardWordsSplitters.SPACE)).isEqualTo("foo-bar");
  }

  /**
   * Tests {@link TextCase#transform(String, WordsSplitter, String)}.
   *
   * <p>We only test one test case here since internally everything gets
   * formatted by the same code as tested in
   * {@link StandardTextCasesTest#testTransformWords)}.
   */
  @Test
  void testTransformTextWithWordsSplitterAndDelimiter() {
    assertThat(KEBAB_CASE.transform("foo bar", StandardWordsSplitters.SPACE, "//")).isEqualTo("foo//bar");
  }

  /**
   * Tests {@link TextCase#transform(List, String)}.
   *
   * <p>We only test one test case here since internally everything gets
   * formatted by the same code as tested in
   * {@link StandardTextCasesTest#testTransformWords)}.
   */
  @Test
  void testTransformWordsWithCustomDelimiter() {
    assertThat(KEBAB_CASE.transform(List.of("foo", "bar"), "//")).isEqualTo("foo//bar");
  }

  /**
   * Tests {@link TextCase#transformFrom(TextCase, String)}.
   *
   * <p>We only test one test case here since internally everything gets
   * formatted by the same code as tested in
   * {@link StandardTextCasesTest#testTransformWords)}.
   */
  @Test
  void testTransformFrom() {
    assertThat(SNAKE_CASE.transformFrom(KEBAB_CASE, "foo-bar")).isEqualTo("foo_bar");
  }

  /**
   * Tests {@link TextCase#transformFrom(TextCase, String, String)}.
   *
   * <p>We only test one test case here since internally everything gets
   * formatted by the same code as tested in
   * {@link StandardTextCasesTest#testTransformWords)}.
   */
  @Test
  void testTransformFromWithCustomDelimiter() {
    assertThat(SNAKE_CASE.transformFrom(COBOL_CASE, "FOO-BAR", "//")).isEqualTo("foo//bar");
  }

  /**
   * Tests {@link TextCase#transformTo(TextCase, String)}.
   *
   * <p>We only test one test case here since internally everything gets
   * formatted by the same code as tested in
   * {@link StandardTextCasesTest#testTransformWords)}.
   */
  @Test
  void testTransformTo() {
    assertThat(SNAKE_CASE.transformTo(KEBAB_CASE, "foo_bar")).isEqualTo("foo-bar");
  }

  /**
   * Tests {@link TextCase#transformTo(TextCase, String)}.
   *
   * <p>We only test one test case here since internally everything gets
   * formatted by the same code as tested in
   * {@link StandardTextCasesTest#testTransformWords)}.
   */
  @Test
  void testTransformToWithCustomDelimiter() {
    assertThat(SNAKE_CASE.transformTo(COBOL_CASE, "foo_bar", "//")).isEqualTo("FOO//BAR");
  }

  @ParameterizedTest
  @MethodSource("createTestExampleTestVectors")
  void testExample(TextCase textCase) {
    List<String> titleWords = StandardWordsSplitters.SPACE.split(textCase.title());
    String expectedExample = textCase.transform(titleWords);
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

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
