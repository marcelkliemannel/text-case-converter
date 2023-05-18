package dev.turingcomplete.textcaseconverter;

import java.util.List;

/**
 * Represents the formatting of a text in specific text case.
 */
public interface TextCase {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * A readable title of this {@link TextCase}.
   *
   * @return the title as a {@link String}; never null.
   */
  String title();

  /**
   * An example of this {@link TextCase}.
   *
   * @return the example as a {@link String}; never null.
   */
  String example();

  /**
   * The delimiter with which the individual words in this {@link TextCase} are
   * joined.
   *
   * <p>For example, for snake case ({@code foo_bar}) this would be the
   * underscore ({@code _}).
   *
   * @return the words delimiter as a {@link String}; never null.
   */
  String joinDelimiter();

  /**
   * A {@link WordsSplitter} that can be used to split a text that is in
   * {@code this} {@link TextCase} into separated words.
   *
   * @return a {@link WordsSplitter}; never null.
   */
  WordsSplitter wordsSplitter();

  /**
   * Transforms the given {@code words} into this {@link TextCase}.
   *
   * @param words an array of {@link String} to be transformed; never null.
   * @return the word in this {@link TextCase}; never null.
   */
  String transform(String ...words);

  /**
   * Transforms the given {@code words} into this {@link TextCase}. The words
   * will be joined by the {@link #joinDelimiter()}.
   *
   * @param words a {@link List} of {@link String}s to be transformed;
   *              never null.
   * @return a text based on the given words in this {@link TextCase};
   * never null.
   */
  String transform(List<String> words);

  /**
   * Transforms the given {@code words} into this {@link TextCase}. The words
   * will be joined by the given {@code joinDelimiter}.
   *
   * @param words          a {@link List} of {@link String}s to be transformed;
   *                       never null.
   * @param joinDelimiter  a delimiting {@link String}; never null.
   * @return a text based on the given words in this {@link TextCase};
   * never null.
   */
  String transform(List<String> words, String joinDelimiter);

  /**
   * Transforms the given {@code text} into this {@link TextCase}. The text
   * will be split into words by the given {@code wordsSplitter} and joined
   * by the {@link #joinDelimiter()}.
   *
   * @param text          a text as a {@link String} to be transformed; never null.
   * @param wordsSplitter a delimiting {@link String}; never null.
   * @return a text based on the given words in this {@link TextCase};
   * never null.
   */
  String transform(String text, WordsSplitter wordsSplitter);

  /**
   * Transforms the given {@code text} into this {@link TextCase}. The text
   * will be split into words by the given {@code wordsSplitter} and joined
   * by the given {@code joinDelimiter}.
   *
   * @param text          a text as a {@link String} to be transformed; never null.
   * @param wordsSplitter a delimiting {@link String}; never null.
   * @return a text based on the given words in this {@link TextCase};
   * never null.
   */
  String transform(String text, WordsSplitter wordsSplitter, String joinDelimiter);

  /**
   * Transforms the given {@code originText} which is in the given
   * {@code originTextCase} to {@code this} {@link TextCase}. The words
   * will be joined by {@link #joinDelimiter()}.
   *
   * @param originTextCase the {@link TextCase} of the given {@code originText};
   *                       never null.
   * @param originText     the {@link String} to transform; never null.
   * @return a text transformed to {@code this} {@link TextCase}; never null.
   */
  String transformFrom(TextCase originTextCase, String originText);

  /**
   * Transforms the given {@code originText} which is in the given
   * {@code originTextCase} to {@code this} {@link TextCase}. The words
   * will be joined by the given {@code joinDelimiter}.
   *
   * @param originTextCase the {@link TextCase} of the given {@code originText};
   *                       never null.
   * @param originText     the {@link String} to transform; never null.
   * @param joinDelimiter  a delimiting {@link String}; never null.
   * @return a text transformed to {@code this} {@link TextCase}; never null.
   */
  String transformFrom(TextCase originTextCase, String originText, String joinDelimiter);

  /**
   * Transforms the given {@code originText} which is in {@code this}
   * {@link TextCase} to the {@code targetTextCase}. The words will be joined
   * by the {@link #joinDelimiter()} of the {@code targetTextCase}.
   *
   * @param targetTextCase the {@link TextCase} to transform the given
   *                       {@code originText} into; never null.
   * @param originText     the {@link String} to transform; never null.
   * @return a text transformed to the {@code targetTextCase}; never null.
   */
  String transformTo(TextCase targetTextCase, String originText);

  /**
   * Transforms the given {@code originText} which is in {@code this}
   * {@link TextCase} to the {@code targetTextCase}. The words will be joined
   * by the given {@code joinDelimiter}.
   *
   * @param targetTextCase the {@link TextCase} to transform the given
   *                       {@code originText} into; never null.
   * @param originText     the {@link String} to transform; never null.
   * @param joinDelimiter  a delimiting {@link String}; never null.
   * @return a text transformed to the {@code targetTextCase}; never null.
   */
  String transformTo(TextCase targetTextCase, String originText, String joinDelimiter);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
