package de.datev.training.FizzBuzzTesting;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
