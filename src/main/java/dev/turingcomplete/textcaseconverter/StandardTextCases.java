package dev.turingcomplete.textcaseconverter;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static dev.turingcomplete.textcaseconverter.Configuration.TO_LOWER_CASE_LOCALE;
import static dev.turingcomplete.textcaseconverter.Configuration.TO_UPPER_CASE_LOCALE;
import static dev.turingcomplete.textcaseconverter.StandardWordsSplitters.DASH;
import static dev.turingcomplete.textcaseconverter.StandardWordsSplitters.NOOP;
import static dev.turingcomplete.textcaseconverter.StandardWordsSplitters.SPACES;
import static dev.turingcomplete.textcaseconverter.StandardWordsSplitters.UNDERSCORE;
import static dev.turingcomplete.textcaseconverter._internal.TextUtilities.isUpperCase;

/**
 * A collection of common {@link TextCase}s.
 *
 * <p>Implementation detail: For the conversion of characters to upper or
 * lower case the {@link String#toUpperCase(Locale)} or
 * {@link String#toLowerCase(Locale)} gets used instead of
 * {@link Character#toUpperCase(char)} or {@link Character#toLowerCase(char)}.
 * The reason for this is, that the {@link String} ones take the {@link Locale}
 * into account which may have an effect on the lower or upper case character.
 */
public final class StandardTextCases {
    // -- Class Fields ---------------------------------------------------------------------------------------------- //

    /**
     * A {@link TextCase} that represents the strict form of Camel Case.
     *
     * <p>In the strict form, every upper case character will be treated as the
     * start of a new word. For example, {@code SQL} would be three words:
     * {@code S}, {@code Q} and {@code L}.
     *
     * <p>Example: {@code camelCase}.
     */
    public static final TextCase STRICT_CAMEL_CASE = new StandardTextCase(
            "Strict Camel Case",
            "strictCamelCase",
            "",
            changeWordCaseConverter(WordCaseConversion.TO_LOWER_CASE),
            StandardWordsSplitters.STRICT_UPPER_CASE
    );

    /**
     * A {@link TextCase} that represents the soft form of Camel Case.
     *
     * <p>In the soft form, every upper case character will be treated as the
     * start of a new word if the previous character is not upper case. For
     * example, {@code SQL} would be one word.
     *
     * <p>Example: {@code camelCase}.
     */
    public static final TextCase SOFT_CAMEL_CASE = new StandardTextCase(
            "Strict Camel Case",
            "strictCamelCase",
            "",
            changeWordCaseConverter(WordCaseConversion.TO_LOWER_CASE),
            StandardWordsSplitters.SOFT_UPPER_CASE
    );

    /**
     * A {@link TextCase} that represents the kebab case.
     *
     * <p>Example: {@code kebab-case}.
     */
    public static final TextCase KEBAB_CASE = new StandardTextCase(
            "Kebab Case",
            "kebab-case",
            "-",
            createWordToLowerCaseConverter(),
            DASH
    );

    /**
     * A {@link TextCase} that represents the snake case.
     *
     * <p>Example: {@code snake_case}.
     */
    public static final TextCase SNAKE_CASE = new StandardTextCase(
            "Snake Case",
            "snake_case",
            "_",
            createWordToLowerCaseConverter(),
            UNDERSCORE
    );

    /**
     * A {@link TextCase} that represents the screaming snake case.
     *
     * <p>Example: {@code SCREAMING_SNAKE_CASE}.
     */
    public static final TextCase SCREAMING_SNAKE_CASE = new StandardTextCase(
            "Screaming Snake Case",
            "SCREAMING_SNAKE_CASE",
            "_",
            createWordToUpperCaseConverter(),
            UNDERSCORE
    );

    /**
     * A {@link TextCase} that represents the train case.
     *
     * <p>Example: {@code Train-Case}.
     */
    public static final TextCase TRAIN_CASE = new StandardTextCase(
            "Train Case",
            "Train-Case",
            "-",
            changeWordCaseConverter(WordCaseConversion.TO_UPPER_CASE),
            DASH
    );

    /**
     * A {@link TextCase} that represents the cobol case.
     *
     * <p>Example: {@code COBOL-CASE}.
     */
    public static final TextCase COBOL_CASE = new StandardTextCase(
            "Cobol Case",
            "COBOL-CASE",
            "-",
            createWordToUpperCaseConverter(),
            DASH
    );

    /**
     * A {@link TextCase} that represents the pascal case.
     *
     * <p>Example: {@code PascalCase}.
     */
    public static final TextCase PASCAL_CASE = new StandardTextCase(
            "Pascal Case",
            "PascalCase",
            "",
            changeWordCaseConverter(WordCaseConversion.TO_UPPER_CASE),
            StandardWordsSplitters.STRICT_UPPER_CASE
    );

    /**
     * A {@link TextCase} that represents a combination of the pascal case and
     * snake case (first character of the first case is in upper case and all
     * words are separated with an underscore).
     *
     * <p>Example: {@code Pascal_Snake_Case}.
     */
    public static final TextCase PASCAL_SNAKE_CASE = new StandardTextCase(
            "Pascal Snake Case",
            "Pascal_Snake_Case",
            "_",
            changeWordCaseConverter(WordCaseConversion.TO_UPPER_CASE),
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
            "Camel Snake Case",
            "camel_Snake_Case",
            "_",
            changeWordCaseConverter(WordCaseConversion.TO_LOWER_CASE),
            UNDERSCORE
    );

    /**
     * A {@link TextCase} that represents the lower case.
     *
     * <p>Example: {@code lowercase}.
     */
    public static final TextCase LOWER_CASE = new StandardTextCase(
            "Lower Case",
            "lowercase",
            "",
            createWordToLowerCaseConverter(),
            NOOP
    );

    /**
     * A {@link TextCase} that represents the upper case.
     *
     * <p>Example: {@code UPPERCASE}.
     */
    public static final TextCase UPPER_CASE = new StandardTextCase(
            "Upper Case",
            "UPPERCASE",
            "",
            createWordToUpperCaseConverter(),
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
            createWordToInvertedCaseConverter(),
            SPACES
    );

    /**
     * A {@link TextCase} that represents the alternating case. In this case, each
     * subsequent character will have the opposite case of the previous character.
     * The alternation starts with the opposite of the first character.
     *
     * <p>Example: From {@code Alternating Case} to {@code aLtErNaTiNg cAsE}.
     */
    public static final TextCase ALTERNATING_CASE = new StandardTextCase(
            "Alternating Case",
            "aLtErNaTiNg cAsE",
            " ",
            createWordToAlternatingCaseConverter(),
            SPACES
    );

    // -- Instance Fields ------------------------------------------------------------------------------------------- //
    // -- Initialization -------------------------------------------------------------------------------------------- //
    // -- Exposed Methods ------------------------------------------------------------------------------------------- //
    // -- Private Methods ------------------------------------------------------------------------------------------- //

    private static BiFunction<Integer, String, String> changeWordCaseConverter(
            WordCaseConversion firstWordFirstCharacterConversion
    ) {
        return (index, word) -> {
            int wordLength = word.length();
            if (wordLength == 0) {
                return "";
            } else if (wordLength == 1 && index == 0) {
                return firstWordFirstCharacterConversion.convert(word);
            } else if (wordLength == 1) {
                return word.toLowerCase(TO_LOWER_CASE_LOCALE);
            } else {
                String firstCharacter = word.substring(0, 1);
                firstCharacter = index == 0 ? firstWordFirstCharacterConversion.convert(firstCharacter)
                        : firstCharacter.toUpperCase(TO_UPPER_CASE_LOCALE);

                String restOfWord = word.substring(1);
                restOfWord = restOfWord.toLowerCase(TO_LOWER_CASE_LOCALE);

                return firstCharacter + restOfWord;
            }
        };
    }

    private static BiFunction<Integer, String, String> createWordToLowerCaseConverter() {
        return (__, word) -> word.toLowerCase(TO_LOWER_CASE_LOCALE);
    }

    private static BiFunction<Integer, String, String> createWordToUpperCaseConverter() {
        return (__, word) -> word.toUpperCase(TO_UPPER_CASE_LOCALE);
    }

    private static BiFunction<Integer, String, String> createWordToInvertedCaseConverter() {
        return (__, word) -> {
            int wordLength = word.length();
            if (wordLength == 0) {
                return "";
            } else {
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

    private static BiFunction<Integer, String, String> createWordToAlternatingCaseConverter() {
        return (__, word) -> {
            int wordLength = word.length();
            if (wordLength == 0) {
                return "";
            } else {
                String firstCharacter = word.substring(0, 1);
                boolean lastUpperCase = isUpperCase(firstCharacter);
                var result = new StringBuilder();
                for (int i = 0; i < wordLength; i++) {
                    String character = word.substring(i, i + 1);
                    if (lastUpperCase) {
                        character = character.toLowerCase(TO_LOWER_CASE_LOCALE);
                        lastUpperCase = false;
                    } else {
                        character = character.toUpperCase(TO_UPPER_CASE_LOCALE);
                        lastUpperCase = true;
                    }
                    result.append(character);
                }
                return result.toString();
            }
        };
    }

    // -- Inner Type ------------------------------------------------------------------------------------------------ //

    private record StandardTextCase(
            String title,
            String example,
            String joinDelimiter,
            BiFunction<Integer, String, String> wordToTextCaseConverter,
            WordsSplitter wordsSplitter
    ) implements TextCase {

        @Override
        public String convert(String... words) {
            Objects.requireNonNull(words);

            return convert(Arrays.asList(words), "");
        }

        @Override
        public String convert(List<String> words) {
            Objects.requireNonNull(words);

            return convert(words, joinDelimiter);
        }

        @Override
        public String convert(String text, WordsSplitter wordsSplitter) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(wordsSplitter);

            return convert(wordsSplitter.split(text));
        }

        @Override
        public String convert(String text, WordsSplitter wordsSplitter, String joinDelimiter) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(wordsSplitter);
            Objects.requireNonNull(joinDelimiter);

            return convert(wordsSplitter.split(text), joinDelimiter);
        }

        @Override
        public String convert(List<String> words, String joinDelimiter) {
            Objects.requireNonNull(words);
            Objects.requireNonNull(joinDelimiter);

            var result = new StringJoiner(joinDelimiter);
            for (int i = 0; i < words.size(); i++) {
                result.add(wordToTextCaseConverter.apply(i, words.get(i)));
            }
            return result.toString();
        }

        @Override
        public String convertFrom(TextCase originTextCase, String originText) {
            Objects.requireNonNull(originTextCase);
            Objects.requireNonNull(originText);

            return convert(originTextCase.wordsSplitter().split(originText), joinDelimiter);
        }

        @Override
        public String convertFrom(TextCase originTextCase, String originText, String wordsDelimiter) {
            Objects.requireNonNull(originTextCase);
            Objects.requireNonNull(originText);
            Objects.requireNonNull(wordsDelimiter);

            return convert(originTextCase.wordsSplitter().split(originText), wordsDelimiter);
        }

        @Override
        public String convertTo(TextCase targetTextCase, String originText) {
            Objects.requireNonNull(targetTextCase);
            Objects.requireNonNull(originText);

            return targetTextCase.convertFrom(this, originText);
        }

        @Override
        public String convertTo(TextCase targetTextCase, String originText, String wordsDelimiter) {
            Objects.requireNonNull(targetTextCase);
            Objects.requireNonNull(originText);
            Objects.requireNonNull(wordsDelimiter);

            return targetTextCase.convertFrom(this, originText, wordsDelimiter);
        }
    }

    // -- Inner Type ------------------------------------------------------------------------------------------------ //

    private enum WordCaseConversion {
        TO_UPPER_CASE(text -> text.toUpperCase(TO_UPPER_CASE_LOCALE)),
        TO_LOWER_CASE(text -> text.toLowerCase(TO_LOWER_CASE_LOCALE));

        private final Function<String, String> converter;

        WordCaseConversion(Function<String, String> converter) {
            this.converter = converter;
        }

        String convert(String text) {
            return converter.apply(text);
        }
    }
}
