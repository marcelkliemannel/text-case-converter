package dev.turingcomplete.textcaseconverter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WordsSplitterExtensionsTest {
  // -- Properties -------------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  fun testToWordsSplitter() {
    val wordsSplitter = "//".toWordsSplitter()
    assertThat(wordsSplitter.split("a//b//c")).containsExactly("a", "b", "c")
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
  // -- Companion Object -------------------------------------------------------------------------------------------- //
}