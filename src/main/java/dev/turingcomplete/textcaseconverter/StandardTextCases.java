package dev.turingcomplete.textcaseconverter;

import dev.turingcomplete.textcaseconverter._internal.TextUtilities;

import java.util.*;
import java.util.function.Function;

import static dev.turingcomplete.textcaseconverter.StandardWordsSplitters.*;
import static dev.turingcomplete.textcaseconverter._internal.TextUtilities.*;

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
     * A {@link TextCase} that represents the strict form of camel case.
     *
     * <p>In the strict form, every upper case character will be treated as the
     * start of a new word. For example, {@code SQL} would be three words:
     * {@code S}, {@code Q} and {@code L}.
     *
     * <p>Example: {@code camelCase}.
     */
    public static final TextCase STRICT_CAMEL_CASE = new StandardTextCase(
            "Strict Camel Case",
            "strictCamelCaseSQL",
            "",
            createCamelcaseConverter(true),
            StandardWordsSplitters.STRICT_UPPER_CASE
    );

    /**
     * A {@link TextCase} that represents the soft form of camel case.
     *
     * <p>In the soft form, every upper case character will be treated as the
     * start of a new word if the previous character is not upper case. For
     * example, {@code SQL} would be one word.
     *
     * <p>Example: {@code camelCase}.
     */
    public static final TextCase SOFT_CAMEL_CASE = new StandardTextCase(
            "Soft Camel Case",
            "softCamelCaseSql",
            "",
            createCamelcaseConverter(false),
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
            changeWordCaseConverter(CaseConversionOfFirstCharacterInWord.TO_UPPER_CASE),
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
            changeWordCaseConverter(CaseConversionOfFirstCharacterInWord.TO_UPPER_CASE),
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
            changeWordCaseConverter(CaseConversionOfFirstCharacterInWord.TO_UPPER_CASE),
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
            changeWordCaseConverter(CaseConversionOfFirstCharacterInWord.TO_LOWER_CASE),
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

    private static WordCaseConversion changeWordCaseConverter(
            CaseConversionOfFirstCharacterInWord firstWordFirstCharacterConversion
    ) {
        return (index, previousWord, word) -> {
            int wordLength = word.length();
            if (wordLength == 0) {
                return "";
            } else if (wordLength == 1 && index == 0) {
                return firstWordFirstCharacterConversion.convert(word);
            } else {
                String firstCharacter = word.substring(0, 1);
                firstCharacter = index == 0 ? firstWordFirstCharacterConversion.convert(firstCharacter)
                        : toUpperCase(firstCharacter);

                String restOfWord = word.substring(1);
                restOfWord = toLowerCase(restOfWord);

                return firstCharacter + restOfWord;
            }
        };
    }

    private static WordCaseConversion createCamelcaseConverter(boolean strict) {
        return (index, previousWord, word) -> {
            int wordLength = word.length();
            if (wordLength == 0) {
                return "";
            } else if (wordLength == 1 && index == 0) {
                return toLowerCase(word);
            } else if (wordLength == 1) {
                if (strict) {
                    return toUpperCase(word);
                } else {
                    return previousWord.length() == 1 && isUpperCase(previousWord) ? toLowerCase(word) : toUpperCase(word);
                }
            } else {
                String firstCharacter = word.substring(0, 1);
                firstCharacter = index == 0 ? toLowerCase(firstCharacter) : toUpperCase(firstCharacter);

                String restOfWord = toLowerCase(word.substring(1));

                return firstCharacter + restOfWord;
            }
        };
    }

    private static WordCaseConversion createWordToLowerCaseConverter() {
        return (__, ___, word) -> toLowerCase(word);
    }

    private static WordCaseConversion createWordToUpperCaseConverter() {
        return (__, ___, word) -> toUpperCase(word);
    }

    private static WordCaseConversion createWordToInvertedCaseConverter() {
        return (__, ___, word) -> {
            int wordLength = word.length();
            if (wordLength == 0) {
                return "";
            } else {
                var result = new StringBuilder();
                for (int i = 0; i < wordLength; i++) {
                    String character = word.substring(i, i + 1);
                    character = isUpperCase(character)
                            ? toLowerCase(character)
                            : toUpperCase(character);
                    result.append(character);
                }
                return result.toString();
            }
        };
    }

    private static WordCaseConversion createWordToAlternatingCaseConverter() {
        return (__, ___, word) -> {
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
                        character = toLowerCase(character);
                        lastUpperCase = false;
                    } else {
                        character = toUpperCase(character);
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
            WordCaseConversion wordToTextCaseConverter,
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

            String previousWord = null;
            var result = new StringJoiner(joinDelimiter);
            for (int i = 0; i < words.size(); i++) {
                String word = words.get(i);
                result.add(wordToTextCaseConverter.convert(i, previousWord, word));
                previousWord = word;
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

    @FunctionalInterface
    private interface WordCaseConversion {

        String convert(int index, String previousWord, String word);
    }

    // -- Inner Type ------------------------------------------------------------------------------------------------ //

    private enum CaseConversionOfFirstCharacterInWord {
        TO_UPPER_CASE(TextUtilities::toUpperCase),
        TO_LOWER_CASE(TextUtilities::toLowerCase);

        private final Function<String, String> converter;

        CaseConversionOfFirstCharacterInWord(Function<String, String> converter) {
            this.converter = converter;
        }

        String convert(String text) {
            return converter.apply(text);
        }
    }
}
