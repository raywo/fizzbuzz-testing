package de.datev.training.FizzBuzzTesting;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Controller {

  private static final String DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

  final Calculator calculator;

  public Controller(Calculator calculator) {
    this.calculator = calculator;
  }


  @GetMapping(value = "/single/{number}", produces = DEFAULT_MEDIA_TYPE)
  public String single(@PathVariable Integer number) {
    return calculator.calculateSingle(number);
  }
}
