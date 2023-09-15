package com.sampaio.hiroshi.nibbles.core;

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

  public Point pointOf(final int x, final int y, final Direction direction) {
    return Point.of(x, y, direction, this);
  }

  public Point lopLeftPointOf(final Direction direction) {
    return Point.of(0, 0, direction, this);
  }

  public Point lopRightPointOf(final Direction direction) {
    return Point.of(width - 1, 0, direction, this);
  }

  public Point bottomLeftPointOf(final Direction direction) {
    return Point.of(0, height - 1, direction, this);
  }

  public Point bottomRightPointOf(final Direction direction) {
    return Point.of(width - 1, height - 1, direction, this);
  }
}
