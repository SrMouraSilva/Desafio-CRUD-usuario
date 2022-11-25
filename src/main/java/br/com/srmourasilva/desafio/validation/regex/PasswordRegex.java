package br.com.srmourasilva.desafio.validation.regex;

public class PasswordRegex {
    public static final String CONTAINS_A_DIGIT = "(?=.*[0-9])";
    public static final String CONTAINS_A_LOWER_CASE_ALPHABET = "(?=.*[a-z])";
    public static final String CONTAINS_A_UPPER_CASE_ALPHABET = "(?=.*[A-Z])";
    public static final String CONTAINS_A_SPECIAL_CHARACTER = "(?=.*[!@#$%^&-+=()])";

    /**
     * A password requires at least once each element
     *
     * <ul>
     *     <li>A digit</li>
     *     <li>A lower case alphabet</li>
     *     <li>A upper case alphabet</li>
     *     <li>A special character</li>
     * </ul>
     *
     */
    public static final String PASSWORD = "^"+ CONTAINS_A_DIGIT + CONTAINS_A_LOWER_CASE_ALPHABET + CONTAINS_A_UPPER_CASE_ALPHABET + CONTAINS_A_SPECIAL_CHARACTER +".{4,100}$";
}
