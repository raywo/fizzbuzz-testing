package de.datev.training.FizzBuzzTesting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest
@ContextConfiguration(classes = Calculator.class)
public class CalculatorTest {

  @Autowired
  Calculator sut; // System under Test - sut

  @ParameterizedTest
  @ValueSource(ints = { 1, 2, 4 })
  void normalNumberReturnsSameNumber(Integer expected) {
    // Arrange
    String expectedResult = expected.toString();

    // Act
    String actualResult = sut.calculateSingle(expected);

    // Assert
    assertEquals(expectedResult, actualResult);
  }


  @ParameterizedTest
  @ValueSource(ints = { 3, 6, 9 })
  void fizzNumberReturnsFizz(Integer number) {
    // Arrange
    String expected = "Fizz";

    // Act
    String actualResult = sut.calculateSingle(number);

    // Assert
    assertEquals(expected, actualResult);
  }


  @ParameterizedTest
  @ValueSource(ints = { 5, 10, 20 })
  void buzzNumberReturnsBuzz(Integer number) {
    String expectedResult = "Buzz";
    String actualResult = sut.calculateSingle(number);
    assertEquals(expectedResult, actualResult);
  }


  @ParameterizedTest
  @ValueSource(ints = { 15, 30, 45 })
  void fizzBuzzNumberReturnsFizzBuzz(Integer number) {
    String expectedResult = "Fizz-Buzz";
    String actualResult = sut.calculateSingle(number);
    assertEquals(expectedResult, actualResult);
  }


  @ParameterizedTest
  @CsvSource(value = {
      "1:1",
      "2:2",
      "3:Fizz",
      "6:Fizz",
      "5:Buzz",
      "10:Buzz",
      "15:Fizz-Buzz",
      "30:Fizz-Buzz"
  }, delimiter = ':')
  void arbitraryNumberReturnsCorrectResult(String input, String expected) {
    String actualResult = sut.calculateSingle(Integer.valueOf(input));
    assertEquals(expected, actualResult);
  }


  @ParameterizedTest(name = "Test case {index}: input {0} should return {1}")
  @MethodSource("provideArguments")
  void arbitraryNumberReturnsCorrectResultAlternative(Integer input, String expected) {
    assertEquals(expected, sut.calculateSingle(input));
  }

  private static Stream<Arguments> provideArguments() {
    return Stream.of(
        Arguments.of(1, "1"),
        Arguments.of(2, "2"),
        Arguments.of(3, "Fizz"),
        Arguments.of(6, "Fizz"),
        Arguments.of(5, "Buzz"),
        Arguments.of(10, "Buzz"),
        Arguments.of(15, "Fizz-Buzz"),
        Arguments.of(30, "Fizz-Buzz")
    );
  }


  @Test
  void calculateNumbersUpTo() {
    Integer limit = 15;
    Stream<String> expectedResult = Stream.of(
        "1",
        "2",
        "Fizz",
        "4",
        "Buzz",
        "Fizz",
        "7",
        "8",
        "Fizz",
        "Buzz",
        "11",
        "Fizz",
        "13",
        "14",
        "Fizz-Buzz"
    );
    Iterable<String> expectedIterable = expectedResult.collect(Collectors.toList());

    Stream<String> actualResult = sut.calculateUpTo(limit);

    assertIterableEquals(expectedIterable, actualResult.collect(Collectors.toList()));
  }
}
