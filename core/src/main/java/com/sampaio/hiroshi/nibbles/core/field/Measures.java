package com.sampaio.hiroshi.nibbles.core.field;

import java.util.function.BiConsumer;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Measures {
  private final int width;
  private final int height;

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
