package dev.turingcomplete.textcaseconverter

// -- Properties ---------------------------------------------------------------------------------------------------- //
// -- Exposed Methods ----------------------------------------------------------------------------------------------- //

/**
 * Converts [this] [String] to the given [textCase].
 *
 * @param textCase the target [TextCase].
 * @param originWordsSplitter the [WordsSplitter] to split [this] [String]
 * into separated words. The default is [StandardWordsSplitters.SPACES].
 * @param targetWordsDelimiter the delimiter with that the words of [this]
 * [String] will be joined together. The default is the
 * [TextCase.joinDelimiter] of the given [textCase].
 */
fun String.toTextCase(
    textCase: TextCase,
    originWordsSplitter: WordsSplitter = StandardWordsSplitters.SPACES,
    targetWordsDelimiter: String = textCase.joinDelimiter()
): String = textCase.convert(originWordsSplitter.split(this), targetWordsDelimiter)

// -- Private Methods ----------------------------------------------------------------------------------------------- //
// -- Type ---------------------------------------------------------------------------------------------------------- //
