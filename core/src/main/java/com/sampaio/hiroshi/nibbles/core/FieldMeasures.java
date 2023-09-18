package com.sampaio.hiroshi.nibbles.core;

import java.util.function.BiConsumer;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class FieldMeasures {
  private final int width;
  private final int height;

  public Arena arenaOf() {
    return Arena.of(this);
  }

  public Point pointOf(final int x, final int y) {
    return Point.of(x, y, this);
  }

  public Point lopLeftPointOf(final Direction direction) {
    return Point.of(0, 0, this);
  }

  public Point lopRightPointOf(final Direction direction) {
    return Point.of(width - 1, 0, this);
  }

  public Point bottomLeftPointOf(final Direction direction) {
    return Point.of(0, height - 1, this);
  }

  public Point bottomRightPointOf(final Direction direction) {
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
