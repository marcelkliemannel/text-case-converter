package dev.turingcomplete.textcaseconverter

// -- Properties ---------------------------------------------------------------------------------------------------- //
// -- Exposed Methods ----------------------------------------------------------------------------------------------- //

/**
 * Creates a [WordsSplitter] which uses [this] [String] to
 */
fun String.toWordsSplitter(): WordsSplitter = WordsSplitter.splitByString(this)

// -- Private Methods ----------------------------------------------------------------------------------------------- //
// -- Type ---------------------------------------------------------------------------------------------------------- //