package dev.turingcomplete.textcaseconverter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.function.Predicate.not;

/**
 * A utility to split a text into its words.
 */
@FunctionalInterface
public interface WordsSplitter {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Splits the given {@code text} into words using the delimiting strategy
   * defined in this {@link WordsSplitter} implementation.
   *
   * @param text the {@link String} to be split; never null;
   * @return a {@link List} of {@link String}s with all words in the given
   * {@code text}; never null.
   */
  List<String> split(String text);

  /**
   * Creates a {@link WordsSplitter} which splits a text around the given
   * {@link Pattern}.
   *
   * @param pattern the delimiting {@link Pattern}; never null.
   * @return a {@link WordsSplitter}; never null.
   */
  static WordsSplitter splitByPattern(Pattern pattern) {
    Objects.requireNonNull(pattern);
    return text -> Arrays.stream(text.split(pattern.pattern())).filter(not(String::isBlank)).toList();
  }

  /**
   * Creates a {@link WordsSplitter} which splits a text around the given
   * {@link String}.
   *
   * @param string the delimiting {@link String}; never null.
   * @return a {@link WordsSplitter}; never null.
   */
  static WordsSplitter splitByString(String string) {
    Objects.requireNonNull(string);
    return text -> Arrays.stream(text.split(Pattern.quote(string))).filter(not(String::isBlank)).toList();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
