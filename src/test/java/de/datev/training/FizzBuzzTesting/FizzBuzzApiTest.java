package de.datev.training.FizzBuzzTesting;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc // entspricht einem HTTP-Client
public class FizzBuzzApiTest {

  private static final String BASE_URL = "/api/v1";
  private static final String DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

  @Autowired
  MockMvc mvc;

  @MockBean
  Calculator calculator;


  @Test
  void getOnSingleReturnsOk() throws Exception {
    // Arrange
    String expected = "1";
    int number = anyInt();
    when(calculator.calculateSingle(number)).thenReturn(expected);

    // Act + Assert
    mvc
        .perform(MockMvcRequestBuilders.get(BASE_URL + "/single/" + number))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(DEFAULT_MEDIA_TYPE))
        .andExpect(MockMvcResultMatchers.content().json(expected));
    verify(calculator, times(1)).calculateSingle(number);
  }
}
