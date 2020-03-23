package jbourne.demo.friendsternet.util;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Set;
import java.util.stream.Collectors;

public class EmailAddressUtil {
    private EmailAddressUtil() { }

    public static Set<String> getEmailAddresses(String message) {
        String[] words = message.trim().split("\\s");
        return Set.of(words).stream()
                .filter(w -> EmailValidator.getInstance().isValid(w))
                .collect(Collectors.toSet());
    }
}
