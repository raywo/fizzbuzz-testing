package de.datev.training.FizzBuzzTesting

import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration(classes = Calculator.class)
class CalculatorGroovyTest extends Specification {

  @Autowired
  private Calculator sut

  def "single value #input is converted to #expectedResult"() {
    when: "single value is requested"
    def actualResult = sut.calculateSingle(input)

    then: "the expected value is returned"
    expectedResult == actualResult

    where:
    input | expectedResult
    1     | "1"
    2     | "2"
    4     | "4"
    3     | "Fizz"
    6     | "Fizz"
    9     | "Fizz"
    5     | "Buzz"
    10    | "Buzz"
    20    | "Buzz"
    15    | "Fizz-Buzz"
    30    | "Fizz-Buzz"
    45    | "Fizz-Buzz"
  }


  def "sequence up to #limit is converted to #expected"() {
    when: "sequence is requested"
    def actual = sut.calculateUpTo(limit)

    then:
    Assertions.assertThat(actual).isEqualTo(expected)

    where:
    limit | expected
    0     | []
    15    | ["1",
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
             "Fizz-Buzz"]
  }
}
