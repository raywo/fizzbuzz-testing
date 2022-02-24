package de.datev.training.FizzBuzzTesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // entspricht einem HTTP-Client
public class FizzBuzzApiTest {

  private static final String BASE_URL = "/api/v1";
  private static final String DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

  @Autowired
  MockMvc mvc;

  // Kein Mock. Hier wollen wir die echte Implementierung, wie sie auch zur
  // Laufzeit vorliegt.
  @Autowired
  ObjectMapper mapper;

  @MockBean
  Calculator calculator;


  @Test
  void getOncalculateSingleReturnsOk() throws Exception {
    // Arrange
    Integer number = anyInt();
    String expectedReturn = "1";
    when(calculator.calculateSingle(number)).thenReturn(expectedReturn);

    // MockMvcRequestBuilders.get()
    // MockMvcResultMatchers.status()
    // Act + Assert
    mvc
        .perform(get(BASE_URL + "/single/" + number))
        .andExpect(status().isOk())
        .andExpect(content().contentType(DEFAULT_MEDIA_TYPE))
        .andExpect(content().json(expectedReturn));

    verify(calculator, times(1)).calculateSingle(anyInt());
  }


  @Test
  void getOnSequenceReturnsOk() throws Exception {
    // Arrange
    Integer limit = 15;
    List<String> stringList = List.of("1", "2", "Fizz");
    String expectedJson = mapper.writeValueAsString(stringList);
    when(calculator.calculateUpTo(limit)).thenReturn(stringList.stream());

    // Act + Assert
    mvc
        .perform(get(BASE_URL + "/sequence?limit=" + limit))
        .andExpect(status().isOk())
        .andExpect(content().contentType(DEFAULT_MEDIA_TYPE))
        .andExpect(content().json(expectedJson));
    verify(calculator, times(1)).calculateUpTo(limit);
  }


  @DisplayName("get on sequence without limit param returns list for first 100 numbers")
  @Test
  void getOnSequenceWithoutLimitReturnOk() throws Exception {
    // Arrange
    when(calculator.calculateUpTo(anyInt())).thenReturn(Stream.empty());

    // Act + Assert
    mvc
        .perform(get(BASE_URL + "/sequence"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(DEFAULT_MEDIA_TYPE));
    verify(calculator, times(1)).calculateUpTo(100);
  }
}
