package dev.turingcomplete.textcaseconverter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;

import static dev.turingcomplete.textcaseconverter.Configuration.TO_LOWER_CASE_LOCALE;
import static dev.turingcomplete.textcaseconverter.Configuration.TO_UPPER_CASE_LOCALE;
import static dev.turingcomplete.textcaseconverter.StandardWordsSplitters.DASH;
import static dev.turingcomplete.textcaseconverter.StandardWordsSplitters.NOOP;
import static dev.turingcomplete.textcaseconverter.StandardWordsSplitters.SPACE;
import static dev.turingcomplete.textcaseconverter.StandardWordsSplitters.UNDERSCORE;
import static dev.turingcomplete.textcaseconverter._internal.TextUtilities.isUpperCase;

/**
 * A collection of common {@link TextCase}s.
 *
 * <p>Implementation detail: For the transformation of characters to upper or
 * lower case the {@link String#toUpperCase(Locale)} or
 * {@link String#toLowerCase(Locale)} gets used instead of
 * {@link Character#toUpperCase(char)} or {@link Character#toLowerCase(char)}.
 * The reason for this is, that the {@link String} ones take the {@link Locale}
 * into account which may have an effect on the lower or upper case character.
 */
public final class StandardTextCases {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A {@link TextCase} that represents the camel case.
   *
   * <p>Example: {@code camelCase}.
   */
  public static final TextCase CAMEL_CASE = new StandardTextCase(
          "Camel case",
          "camelCase",
          "",
          changeWordCaseTransformer(WordCaseTransformation.TO_LOWER_CASE),
          StandardWordsSplitters.UPPER_CASE
  );

  /**
   * A {@link TextCase} that represents the kebab case.
   *
   * <p>Example: {@code kebab-case}.
   */
  public static final TextCase KEBAB_CASE = new StandardTextCase(
          "Kebab case",
          "kebab-case",
          "-",
          createWordToLowerCaseTransformer(),
          DASH
  );

  /**
   * A {@link TextCase} that represents the snake case.
   *
   * <p>Example: {@code snake_case}.
   */
  public static final TextCase SNAKE_CASE = new StandardTextCase(
          "Snake case",
          "snake_case",
          "_",
          createWordToLowerCaseTransformer(),
          UNDERSCORE
  );

  /**
   * A {@link TextCase} that represents the screaming snake case.
   *
   * <p>Example: {@code SCREAMING_SNAKE_CASE}.
   */
  public static final TextCase SCREAMING_SNAKE_CASE = new StandardTextCase(
          "Screaming snake case",
          "SCREAMING_SNAKE_CASE",
          "_",
          createWordToUpperCaseTransformer(),
          UNDERSCORE
  );

  /**
   * A {@link TextCase} that represents the train case.
   *
   * <p>Example: {@code Train-Case}.
   */
  public static final TextCase TRAIN_CASE = new StandardTextCase(
          "Train case",
          "Train-Case",
          "-",
          changeWordCaseTransformer(WordCaseTransformation.TO_UPPER_CASE),
          DASH
  );

  /**
   * A {@link TextCase} that represents the cobol case.
   *
   * <p>Example: {@code COBOL-CASE}.
   */
  public static final TextCase COBOL_CASE = new StandardTextCase(
          "Cobol case",
          "COBOL-CASE",
          "-",
          createWordToUpperCaseTransformer(),
          DASH
  );

  /**
   * A {@link TextCase} that represents the pascal case.
   *
   * <p>Example: {@code PascalCase}.
   */
  public static final TextCase PASCAL_CASE = new StandardTextCase(
          "Pascal case",
          "PascalCase",
          "",
          changeWordCaseTransformer(WordCaseTransformation.TO_UPPER_CASE),
          StandardWordsSplitters.UPPER_CASE
  );

  /**
   * A {@link TextCase} that represents a combination of the pascal case and
   * snake case (first character of the first case is in upper case and all
   * words are separated with an underscore).
   *
   * <p>Example: {@code Pascal_Snake_Case}.
   */
  public static final TextCase PASCAL_SNAKE_CASE = new StandardTextCase(
          "Pascal snake case",
          "Pascal_Snake_Case",
          "_",
          changeWordCaseTransformer(WordCaseTransformation.TO_UPPER_CASE),
          UNDERSCORE
  );

  /**
   * A {@link TextCase} that represents a combination of the camel case and
   * snake case (first character of the first case is in lower case and all
   * words are separated with an underscore).
   *
   * <p>Example: {@code camel_Snake_Case}.
   */
  public static final TextCase CAMEL_SNAKE_CASE = new StandardTextCase(
          "Camel snake case",
          "camel_Snake_Case",
          "_",
          changeWordCaseTransformer(WordCaseTransformation.TO_LOWER_CASE),
          UNDERSCORE
  );

  /**
   * A {@link TextCase} that represents the lower case.
   *
   * <p>Example: {@code lowercase}.
   */
  public static final TextCase LOWER_CASE = new StandardTextCase(
          "Lower case",
          "lowercase",
          "",
          createWordToLowerCaseTransformer(),
          NOOP
  );

  /**
   * A {@link TextCase} that represents the upper case.
   *
   * <p>Example: {@code UPPERCASE}.
   */
  public static final TextCase UPPER_CASE = new StandardTextCase(
          "Upper case",
          "UPPERCASE",
          "",
          createWordToUpperCaseTransformer(),
          NOOP
  );

  /**
   * A {@link TextCase} that represents the inverted case. In this case, the
   * case of each character will be flipped.
   *
   * <p>Example: From {@code Inverted Case} to {@code iNVERTED cASE}.
   */
  public static final TextCase INVERTED_CASE = new StandardTextCase(
          "Inverted Case",
          "iNVERTED cASE",
          " ",
          createWordToInvertedCaseTransformer(),
          SPACE
  );

  /**
   * A {@link TextCase} that represents the alternating case. In this case, each
   * subsequent character will have the opposite case of the previous character.
   * The alternation starts with the opposite of the first character.
   *
   * <p>Example: From {@code Alternating Case} to {@code aLtErNaTiNg cAsE}.
   */
  public static final TextCase ALTERNATING_CASE = new StandardTextCase(
          "Alternating case",
          "aLtErNaTiNg CaSe",
          " ",
          createWordToAlternatingCaseTransformer(),
          SPACE
  );

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static BiFunction<Integer, String, String> changeWordCaseTransformer(
          WordCaseTransformation firstWordFirstCharacterTransformation
  ) {
    return (index, word) -> {
      int wordLength = word.length();
      if (wordLength == 0) {
        return "";
      }
      else if (wordLength == 1 && index == 0) {
        return firstWordFirstCharacterTransformation.transform(word);
      }
      else if (wordLength == 1) {
        return word.toLowerCase(TO_LOWER_CASE_LOCALE);
      }
      else {
        String firstCharacter = word.substring(0, 1);
        firstCharacter = index == 0 ? firstWordFirstCharacterTransformation.transform(firstCharacter)
                : firstCharacter.toUpperCase(TO_UPPER_CASE_LOCALE);

        String restOfWord = word.substring(1);
        restOfWord = restOfWord.toLowerCase(TO_LOWER_CASE_LOCALE);

        return firstCharacter + restOfWord;
      }
    };
  }

  private static BiFunction<Integer, String, String> createWordToLowerCaseTransformer() {
    return (__, word) -> word.toLowerCase(TO_LOWER_CASE_LOCALE);
  }

  private static BiFunction<Integer, String, String> createWordToUpperCaseTransformer() {
    return (__, word) -> word.toUpperCase(TO_UPPER_CASE_LOCALE);
  }

  private static BiFunction<Integer, String, String> createWordToInvertedCaseTransformer() {
    return (__, word) -> {
      int wordLength = word.length();
      if (wordLength == 0) {
        return "";
      }
      else {
        var result = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
          String character = word.substring(i, i + 1);
          character = isUpperCase(character)
                  ? character.toLowerCase(TO_LOWER_CASE_LOCALE)
                  : character.toUpperCase(TO_UPPER_CASE_LOCALE);
          result.append(character);
        }
        return result.toString();
      }
    };
  }

  private static BiFunction<Integer, String, String> createWordToAlternatingCaseTransformer() {
    return (__, word) -> {
      int wordLength = word.length();
      if (wordLength == 0) {
        return "";
      }
      else {
        String firstCharacter = word.substring(0, 1);
        boolean lastUpperCase = isUpperCase(firstCharacter);
        var result = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
          String character = word.substring(i, i + 1);
          if (lastUpperCase) {
            character = character.toLowerCase(TO_LOWER_CASE_LOCALE);
            lastUpperCase = false;
          }
          else {
            character = character.toUpperCase(TO_UPPER_CASE_LOCALE);
            lastUpperCase = true;
          }
          result.append(character);
        }
        return result.toString();
      }
    };
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private record StandardTextCase(
          String title,
          String example,
          String wordsDelimiter,
          BiFunction<Integer, String, String> wordToTextCaseTransformer,
          WordsSplitter wordsSplitter
  ) implements TextCase {

    @Override
    public String transform(String word) {
      Objects.requireNonNull(word);

      return transform(List.of(word), "");
    }

    @Override
    public String transform(List<String> words) {
      Objects.requireNonNull(words);

      return transform(words, wordsDelimiter);
    }

    @Override
    public String transform(List<String> words, String wordsDelimiter) {
      Objects.requireNonNull(words);
      Objects.requireNonNull(wordsDelimiter);

      var result = new StringJoiner(wordsDelimiter);
      for (int i = 0; i < words.size(); i++) {
        result.add(wordToTextCaseTransformer.apply(i, words.get(i)));
      }
      return result.toString();
    }

    @Override
    public String transformFrom(TextCase originTextCase, String originText) {
      Objects.requireNonNull(originTextCase);
      Objects.requireNonNull(originText);

      return transform(originTextCase.wordsSplitter().split(originText), wordsDelimiter);
    }

    @Override
    public String transformFrom(TextCase originTextCase, String originText, String wordsDelimiter) {
      Objects.requireNonNull(originTextCase);
      Objects.requireNonNull(originText);
      Objects.requireNonNull(wordsDelimiter);

      return transform(originTextCase.wordsSplitter().split(originText), wordsDelimiter);
    }

    @Override
    public String transformTo(TextCase targetTextCase, String originText) {
      Objects.requireNonNull(targetTextCase);
      Objects.requireNonNull(originText);

      return targetTextCase.transformFrom(this, originText);
    }

    @Override
    public String transformTo(TextCase targetTextCase, String originText, String wordsDelimiter) {
      Objects.requireNonNull(targetTextCase);
      Objects.requireNonNull(originText);
      Objects.requireNonNull(wordsDelimiter);

      return targetTextCase.transformFrom(this, originText, wordsDelimiter);
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private enum WordCaseTransformation {
    TO_UPPER_CASE(text -> text.toUpperCase(TO_UPPER_CASE_LOCALE)),
    TO_LOWER_CASE(text -> text.toLowerCase(TO_LOWER_CASE_LOCALE));

    private final Function<String, String> transformer;

    WordCaseTransformation(Function<String, String> transformer) {
      this.transformer = transformer;
    }

    String transform(String text) {
      return transformer.apply(text);
    }
  }
}
