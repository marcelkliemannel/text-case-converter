package dev.turingcomplete.textcaseconverter;

import dev.turingcomplete.textcaseconverter._internal.TextUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * A collection of common {@link WordsSplitter}s.
 *
 * <p>Implementation detail: For the transformation of characters to upper or
 * lower case the {@link String#toUpperCase(Locale)} or
 * {@link String#toLowerCase(Locale)} gets used instead of
 * {@link Character#toUpperCase(char)} or {@link Character#toLowerCase(char)}.
 * The reason for this is, that the {@link String} ones take the {@link Locale}
 * into account which may have an effect on the lower or upper case character.
 */
public final class StandardWordsSplitters {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A {@link WordsSplitter} that splits a text around space characters. Blank
   * words will be omitted.
   */
  public static final WordsSplitter SPACE = WordsSplitter.splitByPattern(Pattern.compile("\\s+"));

  /**
   * A {@link WordsSplitter} that splits a text around the dash character
   * {@code -}.
   */
  public static final WordsSplitter DASH = WordsSplitter.splitByString("-");

  /**
   * A {@link WordsSplitter} that splits a text around the underscore character
   * {@code _}.
   */
  public static final WordsSplitter UNDERSCORE = WordsSplitter.splitByString("_");

  /**
   * A {@link WordsSplitter} that splits a text around every upper case
   * characters.
   */
  public static final WordsSplitter UPPER_CASE = StandardWordsSplitters::toWordsByUpperCaseCharacter;

  /**
   * A {@link WordsSplitter} that will handle any input as one word.
   */
  public static final WordsSplitter NOOP = List::of;

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static List<String> toWordsByUpperCaseCharacter(String text) {
    int textLength = text.length();
    if (textLength == 0) {
      return List.of();
    }
    else if (textLength == 1) {
      return List.of(text);
    }
    else {
      List<String> words = new ArrayList<>();
      var wordBuilder = new StringBuilder();

      char firstCharacter = text.charAt(0);
      wordBuilder.append(firstCharacter);

      boolean previousCharacterWasUppercase = Character.isUpperCase(firstCharacter);
      for (int i = 1; i < textLength; i++) {
        String character = text.substring(i, i + 1);
        boolean isUpperCase = TextUtilities.isUpperCase(character);
        if (isUpperCase && !previousCharacterWasUppercase) {
          // New word
          words.add(wordBuilder.toString());
          wordBuilder = new StringBuilder();
        }
        wordBuilder.append(character);
        previousCharacterWasUppercase = isUpperCase;
      }
      if (!wordBuilder.isEmpty()) {
        words.add(wordBuilder.toString());
      }
      return words;
    }
  }
}
