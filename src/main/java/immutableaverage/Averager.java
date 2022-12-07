package immutableaverage;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

class Average {
  private double sum;
  private long count;

  public Average(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public Average merge(Average other) {
    return new Average(this.sum + other.sum, this.count + other.count);
  }

  public Optional<Double> get() {
    if (this.count > 0) {
      return Optional.of(this.sum / this.count);
    } else {
      return Optional.empty();
    }
  }
}

public class Averager {
  public static void main(String[] args) {
    long start = System.nanoTime();
    DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble(-1, +1))
        .parallel()
        .limit(3_000_000_000L)
        .mapToObj(d -> new Average(d, 1))
//        .reduce(new Average(0, 0), (a1, a2) -> a1.merge(a2))
        .reduce(new Average(0, 0), Average::merge)
        .get()
        .ifPresent(System.out::println);

    long time = System.nanoTime() - start;
    System.out.printf("Time taken is %7.3f\n", (time / 1_000_000_000.0));
  }
}
