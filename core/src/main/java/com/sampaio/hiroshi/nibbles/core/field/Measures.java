package com.sampaio.hiroshi.nibbles.core.field;

import java.util.function.BiConsumer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Measures {

  private final int width;
  private final int height;
  private final int heightBits;

  public static Measures of(final int width, final int height) {
    return new Measures(width, height);
  }

  private Measures(final int width, final int height) {
    this.width = width;
    this.height = height;
    this.heightBits = Integer.SIZE - Integer.numberOfLeadingZeros(height);
  }

  public Field fieldOf() {
    return Field.of(this);
  }

  public Point pointOf(final int x, final int y) {
    return Point.of(x, y, this);
  }

  public Point lopLeftPointOf() {
    return Point.of(0, 0, this);
  }

  public Point lopRightPointOf() {
    return Point.of(width - 1, 0, this);
  }

  public Point bottomLeftPointOf() {
    return Point.of(0, height - 1, this);
  }

  public Point bottomRightPointOf() {
    return Point.of(width - 1, height - 1, this);
  }

  public void forEach(final BiConsumer<Integer, Integer> coordsConsumer) {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        coordsConsumer.accept(x, y);
      }
    }
  }
}
