package dev.turingcomplete.textcaseconverter.dev.turingcomplete.textcaseconverter

import dev.turingcomplete.textcaseconverter.StandardTextCases.SNAKE_CASE
import dev.turingcomplete.textcaseconverter.StandardWordsSplitters.UNDERSCORE
import dev.turingcomplete.textcaseconverter.StandardWordsSplitters.UPPER_CASE
import dev.turingcomplete.textcaseconverter.toTextCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TextCasesExtensionsTest {
  // -- Properties -------------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  fun testToTextCase() {
    assertThat("foo bar".toTextCase(SNAKE_CASE)).isEqualTo("foo_bar")
    assertThat("fooBar".toTextCase(SNAKE_CASE, UPPER_CASE)).isEqualTo("foo_bar")
    assertThat("FOO_BAR".toTextCase(SNAKE_CASE, UNDERSCORE, "//")).isEqualTo("foo//bar")
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
  // -- Companion Object -------------------------------------------------------------------------------------------- //
}