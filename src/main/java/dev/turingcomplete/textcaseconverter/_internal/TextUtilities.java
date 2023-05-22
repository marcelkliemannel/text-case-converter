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

    /**
     * Converts the given {@code text} to lower case.
     *
     * @param text the text to be converted; never null.
     * @return the lower case text; never null.
     */
    public static String toLowerCase(String text) {
        return text.toLowerCase(Configuration.TO_LOWER_CASE_LOCALE);
    }

    /**
     * Converts the given {@code text} to upper case.
     *
     * @param text the text to be converted; never null.
     * @return the upper case text; never null.
     */
    public static String toUpperCase(String text) {
        return text.toUpperCase(Configuration.TO_UPPER_CASE_LOCALE);
    }

    // -- Private Methods ------------------------------------------------------------------------------------------- //
    // -- Inner Type ------------------------------------------------------------------------------------------------ //
}
