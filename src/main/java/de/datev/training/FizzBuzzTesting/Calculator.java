package de.datev.training.FizzBuzzTesting;

import org.springframework.stereotype.Service;

@Service
public class Calculator {
  public String calculateSingle(Integer number) {
    if (number % 3 == 0 && number % 5 == 0) {
      return "Fizz-Buzz";
    }

    if (number % 3 == 0) {
      return "Fizz";
    }

    if (number % 5 == 0) {
      return "Buzz";
    }

    return number.toString();
  }
}
