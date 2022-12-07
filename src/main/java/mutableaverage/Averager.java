package mutableaverage;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

class Average {
  private double sum;
  private long count;

  public Average() {
    this(0, 0);
  }

  public Average(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public void include(double d) {
    this.sum += d;
    this.count++;
  }

  public void merge(Average other) {
    this.sum += other.sum;
    this.count += other.count;
  }

  public Optional<Double> get() {
    if (count > 0) {
      return Optional.of(this.sum / this.count);
    } else {
      return Optional.empty();
    }
  }
}

public class Averager {
  public static void main(String[] args) {
    long start = System.nanoTime();
//    DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble(-1, +1))
    DoubleStream.iterate(0.0, x -> ThreadLocalRandom.current().nextDouble(-1, +1))
//    ThreadLocalRandom.current().doubles(9_000_000_000L, -1, +1)
        .parallel()
        .limit(3_000_000_000L)
        // lots of heavy, CPU intensive, ZERO IO operations
        .collect(
//            () -> new Average(),
//            (a, d) -> a.include(d),
//            (af, ai) -> af.merge(ai))
            Average::new,
            Average::include,
            Average::merge)
        .get()
        .ifPresent(a -> System.out.println("Average is " + a));
    long time = System.nanoTime() - start;
    System.out.printf("time taken was %7.3f\n", (time / 1_000_000_000.0));
  }
}




