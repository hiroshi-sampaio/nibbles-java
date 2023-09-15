package com.sampaio.hiroshi.nibbles.core;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@AllArgsConstructor(staticName = "of", access = AccessLevel.PROTECTED)
public class Point {

  @EqualsAndHashCode.Include private final int x;
  @EqualsAndHashCode.Include private final int y;
  private final Direction direction;
  private final FieldMeasures fieldMeasures;

  public Point rightPoint() {
    final int newX = (x + 1) % fieldMeasures.getWidth();
    return Point.of(newX, y, Direction.RIGHT, fieldMeasures);
  }

  public Point leftPoint() {
    final int newX = (fieldMeasures.getWidth() + x - 1) % fieldMeasures.getWidth();
    return Point.of(newX, y, Direction.LEFT, fieldMeasures);
  }

  public Point lowerPoint() {
    final int newY = (y + 1) % fieldMeasures.getHeight();
    return Point.of(x, newY, Direction.DOWN, fieldMeasures);
  }

  public Point upperPoint() {
    final int newY = (fieldMeasures.getHeight() + y - 1) % fieldMeasures.getHeight();
    return Point.of(x, newY, Direction.UP, fieldMeasures);
  }
}