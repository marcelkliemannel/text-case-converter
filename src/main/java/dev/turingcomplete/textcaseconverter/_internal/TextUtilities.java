package dev.turingcomplete.textcaseconverter._internal;

import dev.turingcomplete.textcaseconverter.Configuration;

/**
 * Utility methods for text operations.
 */
public final class TextUtilities {
    // -- Class Fields ---------------------------------------------------------------------------------------------- //
    // -- Instance Fields ------------------------------------------------------------------------------------------- //

    private TextUtilities() {
        throw new UnsupportedOperationException();
    }

    // -- Initialization -------------------------------------------------------------------------------------------- //
    // -- Exposed Methods ------------------------------------------------------------------------------------------- //

    /**
     * Checks if the given {@code text} is in upper case.
     *
     * @param text the text to be checked; may be null.
     * @return true if the {@code text} is in upper case; false otherwise.
     */
    public static boolean isUpperCase(String text) {
        return text.toUpperCase(Configuration.TO_UPPER_CASE_LOCALE).equals(text);
    }

    // -- Private Methods ------------------------------------------------------------------------------------------- //
    // -- Inner Type ------------------------------------------------------------------------------------------------ //
}
