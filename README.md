# Text Case Converter

The Java/Kotlin library provides an intuitive API for converting strings between different text cases. It has a wide range of built-in support for the most common text cases. In addition, the library is designed to be easily extended with new custom text cases, making it highly flexible and adaptable.

> ℹ️ In this repository, changes don't happen that often and the library gets updated very rarely. However, this is **not** an abandoned project. Since the code is relatively simple and has good test coverage, there is hardly any need to change anything.

## Dependencies

This library is available at [Maven Central](https://mvnrepository.com/artifact/dev.turingcomplete/text-case-converter). It consists of a main library and an optional one that provides extensions for Kotlin. 

### Gradle

```kotlin
// Main dependency
implementation 'dev.turingcomplete:test-case-converter:1.0.0' // Groovy build script
implementation("dev.turingcomplete:test-case-converter:1.0.0") // Kotlin build script

// Optional: Kotlin extensions
implementation 'dev.turingcomplete:test-case-converter-kotlin-extension:1.0.0' // Groovy build script
implementation("dev.turingcomplete:test-case-converter-kotlin-extension:1.0.0") // Kotlin build script
```

### Maven

```xml
<!-- Main dependency -->
<dependency>
    <groupId>dev.turingcomplete</groupId>
    <artifactId>test-case-converter</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- Optional: Kotlin extensions -->
<dependency>
    <groupId>dev.turingcomplete</groupId>
    <artifactId>test-case-converter-kotlin-extension</artifactId>
    <version>1.0.0</version>
</dependency>
```

## General Concept

The foundation of this library are two interfaces:

- A single text case is represented by the interface `dev.turingcomplete.textcaseconverter.TextCase`. It provides meta information (e.g., `title()` and `example()`) and, through several variations of `convert*()` methods, the ability to convert a text to or from that text case.
- The interface `dev.turingcomplete.textcaseconverter.WordsSplitter` which defines an utility that splits a text into a list of words. 

Generally, we must understand that a text case converter works on a list of words, not on raw text. This is because each text case puts the words of a text together in a different way. However, in a raw text it is not machine-visible where a word starts and ends without an additional `WordsSplitter`. For example, in `foo bar baz` the start/end would be defined by a space, but in `fooBarBaz` it would be defined by the case change.

The library has several built-in implementations of both interfaces in the classes `StandardTextCases` and `StandardWordsSplitters` to cover the most common text cases and ways of splitting a text into words (see next chapters).

## Built-in Text Cases

The class `dev.turingcomplete.textcaseconverter.StandardTextCases` contains static instances for the most common text cases:

| Name                 | Example              | Remark                                                       |
| -------------------- | -------------------- | ------------------------------------------------------------ |
| Camel Case           | camelCase            |                                                              |
| Kebab Case           | kebab-case           |                                                              |
| Snake Case           | snake_case           |                                                              |
| Screaming Snake Case | SCREAMING_SNAKE_CASE |                                                              |
| Train Case           | Train-Case           |                                                              |
| Cobol Case           | COBOL-CASE           |                                                              |
| Pascal Case          | PascalCase           |                                                              |
| Pascal Sanke Case    | Pascal_Snake_Case    | First character is always uppercase.                         |
| Camel Snake Case     | camel_Snake_Case     | First character is always lowercase.                         |
| Lower Case           | lowercase            |                                                              |
| Upper Case           | UPPERCASE            |                                                              |
| Inverted Case        | iNVERTED cASE        | The case of each character will be flipped.                  |
| Alternating Case     | aLtErNaTiNg CaSe     | Each subsequent character will have the opposite case of the previous character. The alternation starts with the opposite of the first character. |

### Example Usage

The following example shows the usage of the `convert*()` methods of the `TextCase` interface for the built-in Camel Case implementation:

```kotlin
// All will return `fooBarBaz`

// Array of words
StandardTextCases.CAMEL_CASE.convert("foo", "bar", "baz")
// List of words
StandardTextCases.CAMEL_CASE.convert(List.of("foo", "bar", "baz"))
// Given a raw text in which the start/end of a word is defined by a space
StandardTextCases.CAMEL_CASE.convert("Foo bar baz", StandardWordsSplitters.SPACE)

// Given a raw text that is in Cobol Case and convert it into Camel Case
StandardTextCases.CAMEL_CASE.convertForm(StandardTextCases.COBOL_CASE, "FOO-BAR-BAZ")
// Identical to the previous one, only the other way around
StandardTextCases.COBOL_CASE.convertTo(StandardTextCases.CAMEL_CASE, "FOO-BAR-BAZ")
```

## Built-In Words Splitters

The class `dev.turingcomplete.textcaseconverter.StandardWordsSplitters` provides static instances for the most common ways to split a text into words:

- By spaces (` `), that splits a text around (possible multiple) space characters. This leads to the fact that blank words are obmitted.
- By a single dash (`-`). Blank words will not be obmitted (e.g., `foo--bar` would have three words).
- By a single underscore (`_`). Blank words will not be obmitted (e.g., `foo__bar` would have three words).
- By an upper case character. For example `fooBar` would be the two words `foo` and `Bar`, and `SQL` would be the three words `S`, `Q` and `L`.

Note that each `TextCase` provides a `WordsSplitter` through `TextCase#wordsSplitter`, which can be used to split a text given in that text case into individual words. This is used internally, for example, when we want to convert a text case to another without having to explicitly specify a `WordsSplitter`.

## Kotlin Extension

By adding library X, some Kotlin Extensions methods are provided, making it easier to use this library in Kotlin code. These additional features can be seen in the following example:

```kotlin
// Convert the given raw text into Snake Case. Both will return `foo_bar`.
"foo bar".toTextCase(SNAKE_CASE)
"fooBar".toTextCase(SNAKE_CASE, UPPER_CASE)

// Will create a `WordsSplitter` that splits words by the delimiter `//`.
"//".toWordsSplitter()
```

## Locale Handling

Some built-in text cases and words splitters use `String#toLowerCase()` or `String#toUpperCase()`. The output of both methods is locale sensitive. All calls to these methods in this library will use the `Locale` set in the static fields of the `dev.turingcomplete.textcaseconverter.Configuration` class. By default the `Locale.ROOT` is used.

## Licensing

Copyright (c) 2023 Marcel Kliemannel

Licensed under the **Apache License, Version 2.0** (the "License"); you may not use this file except in compliance with the License.

You may obtain a copy of the License at <https://www.apache.org/licenses/LICENSE-2.0>.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the [LICENSE](./LICENSE) for the specific language governing permissions and limitations under the License.
