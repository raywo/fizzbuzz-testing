package de.datev.training.FizzBuzzTesting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FizzBuzzTestingApplicationTests {

	@Test()
	void contextLoads() {
    // Just startup the app to see if all loads up
	}


  @Test
  void testMain() {
    // If you really, really want to achieve 100% test coverage.
    FizzBuzzTestingApplication.main(new String[] {});
  }
}
