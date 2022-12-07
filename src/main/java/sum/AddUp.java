package sum;

import java.util.stream.IntStream;

public class AddUp {
  public static void main(String[] args) {
    int sum = IntStream.range(1, 11)
        .parallel()
//        .reduce((a, b) -> a + b)
//        .ifPresent(System.out::println);
        .reduce(0, (a, b) -> a + b)
        ;
    System.out.println("sum is " + sum);
  }
}
