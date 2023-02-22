package dev.turingcomplete.textcaseconverter

// -- Properties -------------------------------------------------------------------------------------------------- //
// -- Exposed Methods --------------------------------------------------------------------------------------------- //

/**
 * Transforms [this] [String] to the given [textCase].
 *
 * @param textCase the target [TextCase].
 * @param originWordsSplitter the [WordsSplitter] to split [this] [String]
 * into separated words. The default is [StandardWordsSplitters.SPACE].
 * @param targetWordsDelimiter the delimiter with that the words of [this]
 * [String] will be joined together. The default is the
 * [TextCase.wordsDelimiter] of the given [textCase].
 */
fun String.toTextCase(
  textCase: TextCase,
  originWordsSplitter: WordsSplitter = StandardWordsSplitters.SPACE,
  targetWordsDelimiter: String = textCase.wordsDelimiter()
) = textCase.transform(originWordsSplitter.split(this), targetWordsDelimiter)

// -- Private Methods --------------------------------------------------------------------------------------------- //
// -- Type ---------------------------------------------------------------------------------------------------------- //
