package dev.turingcomplete.textcaseconverter._internal;

import dev.turingcomplete.textcaseconverter.Configuration;

public final class TextUtilities {
    // -- Class Fields ---------------------------------------------------------------------------------------------- //
    // -- Instance Fields ------------------------------------------------------------------------------------------- //

    private TextUtilities() {
        throw new UnsupportedOperationException();
    }

    // -- Initialization -------------------------------------------------------------------------------------------- //
    // -- Exposed Methods ------------------------------------------------------------------------------------------- //

    public static boolean isUpperCase(String text) {
        return text.toUpperCase(Configuration.TO_UPPER_CASE_LOCALE).equals(text);
    }

    // -- Private Methods ------------------------------------------------------------------------------------------- //
    // -- Inner Type ------------------------------------------------------------------------------------------------ //
}
