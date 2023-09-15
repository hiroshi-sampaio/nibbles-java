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
}
