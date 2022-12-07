package badstream;

import java.util.stream.IntStream;

public class Example {
  static long counter = 0;
  public static void main(String[] args) {
    long c = IntStream.range(0, 1_000_000_000)
//        .parallel()
//        .filter(x -> true)
        .peek(x -> counter++)
        .count();
    System.out.println("c is " + c);
    System.out.println("counter is " + counter);
  }
}
