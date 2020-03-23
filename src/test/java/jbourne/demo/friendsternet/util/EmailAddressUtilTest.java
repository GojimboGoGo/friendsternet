package jbourne.demo.friendsternet.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailAddressUtilTest {

    @ParameterizedTest
    @CsvSource({
            "Hello World! kate@example.com,kate@example.com",
            "Don't @ me garth@example.com pie@example.com.au,garth@example.com;pie@example.com.au"
    })
    void shouldReturnEmailAddress(String input, String expected) {
        Set<String> result = EmailAddressUtil.getEmailAddresses(input);
        Set<String> processedExpected = Set.of(expected.split(";"));

        assertEquals(processedExpected, result);
    }

}