package co.istad.mbakingapi.util;

import java.util.Random;

public class RandomUtil {
   public static String generate9Digit(){
       Random random = new Random();
       int randomNumber = random.nextInt(1000000000); // Generates a random integer from 0 to 999999999
       return String.format("%09d", randomNumber);
    }
}
