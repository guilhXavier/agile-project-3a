package ifsul.agileproject.rachadinha.mapper;

import java.security.SecureRandom;

public class RandomCodeGenerator {
  // Conjunto de caracteres permitidos
  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final int CODE_LENGTH = 10;
  private static final SecureRandom random = new SecureRandom();

  public static String generateRandomCode() {
    StringBuilder code = new StringBuilder(CODE_LENGTH);
    for (int i = 0; i < CODE_LENGTH; i++) {
      int index = random.nextInt(CHARACTERS.length());
      code.append(CHARACTERS.charAt(index));
    }
    return code.toString();
  }
}
