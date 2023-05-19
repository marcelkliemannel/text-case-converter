package dev.turingcomplete.textcaseconverter;

import java.util.Locale;

/**
 * Global configurations used in {@link StandardTextCases} and
 * {@link StandardWordsSplitters}.
 */
public final class Configuration {
    // -- Class Fields ---------------------------------------------------------------------------------------------- //

    /**
     * The {@link Locale} used for {@link String#toLowerCase(Locale)} calls.
     */
    public static final Locale TO_LOWER_CASE_LOCALE = Locale.ROOT;

    /**
     * The {@link Locale} used for {@link String#toUpperCase(Locale)} calls.
     */
    public static final Locale TO_UPPER_CASE_LOCALE = Locale.ROOT;


    // -- Instance Fields ------------------------------------------------------------------------------------------- //
    // -- Initialization -------------------------------------------------------------------------------------------- //
    // -- Exposed Methods ------------------------------------------------------------------------------------------- //
    // -- Private Methods ------------------------------------------------------------------------------------------- //
    // -- Inner Type ------------------------------------------------------------------------------------------------ //
}
