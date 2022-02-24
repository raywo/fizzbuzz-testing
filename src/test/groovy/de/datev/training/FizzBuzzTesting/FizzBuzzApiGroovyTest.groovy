package de.datev.training.FizzBuzzTesting

import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.security.SecureRandom
import java.util.stream.Stream

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@WebMvcTest(controllers = Controller)
class FizzBuzzApiGroovyTest extends Specification {
  private static final String BASE_URL = "/api/v1"
  private static final String DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE

  @Autowired
  MockMvc mvc

  @Autowired
  ObjectMapper mapper

  @SpringBean
  Calculator calculator = Mock()

  def "get on single returns OK"() {
    given: "an input"
    def number = aNumber()

    when: "the GET request is done"
    def response = mvc.perform(get("$BASE_URL/single/$number"))

    then: "the response should be ok"
    response.andExpect(status().isOk())
    and:
    response.andExpect(content().contentType(DEFAULT_MEDIA_TYPE))
    and:
    response.andExpect(content().json("$number"))
    and:
    1 * calculator.calculateSingle(number) >> "$number"
  }


  def "get on sequence with a limit returns ok"() {
    given: "a limit"
    def limit = 15
    def list = ["1", "2", "Fizz"]
    def expectedJson = mapper.writeValueAsString(list)

    when: "call is done"
    def response = mvc.perform(get("$BASE_URL/sequence?limit=$limit"))

    then: "the response should be ok"
    response.andExpect(status().isOk())
    and: "the content type should be JSON"
    response.andExpect(content().contentType(DEFAULT_MEDIA_TYPE))
    and: "the content should be a valid JSON-array"
    response.andExpect(content().json(expectedJson))
    and: "the Calculator should be exactly once"
    1 * calculator.calculateUpTo(limit) >> list.stream()
  }

  def "get on sequence with no limit returns list of first 100 numbers and ok"() {
    when: "call is done"
    def response = mvc.perform(get("$BASE_URL/sequence"))

    then: "the response should be ok"
    response.andExpect(status().isOk())
    and: "the content type should be JSON"
    response.andExpect(content().contentType(DEFAULT_MEDIA_TYPE))
    and: "the calculator should be called with a limit of 100 exactly once"
    1 * calculator.calculateUpTo(100) >> Stream.empty()
  }

  private static def aNumber() {
    new SecureRandom().nextInt()
  }
}
