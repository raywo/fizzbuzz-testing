package de.datev.training.FizzBuzzTesting

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import java.security.SecureRandom

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@ActiveProfiles("test")
@WebMvcTest(controllers = Controller)
class FizzBuzzApiGroovyTest extends Specification {
  private static final String BASE_URL = "/api/v1"
  private static final String DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE

  @Autowired
  MockMvc mvc

  @SpringBean
  Calculator calculator = Mock()

  def "get on single returns OK"() {
    given: "an input"
    def number = aNumber()

    when: "the GET request is done"
    def response = mvc.perform(get("$BASE_URL/single/$number"))

    then: "the response should be ok"
    response.andExpect(MockMvcResultMatchers.status().isOk())
    and:
    response.andExpect(MockMvcResultMatchers.content().contentType(DEFAULT_MEDIA_TYPE))
    and:
    response.andExpect(MockMvcResultMatchers.content().json("$number"))
    and:
    1 * calculator.calculateSingle(number) >> "$number"
  }

  private static def aNumber() {
    new SecureRandom().nextInt()
  }
}
