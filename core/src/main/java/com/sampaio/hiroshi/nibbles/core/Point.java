package com.sampaio.hiroshi.nibbles.core;

import lombok.*;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PROTECTED)
public class Point {

  @ToString.Include @EqualsAndHashCode.Include private final int x;
  @ToString.Include @EqualsAndHashCode.Include private final int y;
  private final FieldMeasures fieldMeasures;

  public Point rightPoint() {
    final int newX = (x + 1) % fieldMeasures.getWidth();
    return Point.of(newX, y, fieldMeasures);
  }

  public Point leftPoint() {
    final int newX = (fieldMeasures.getWidth() + x - 1) % fieldMeasures.getWidth();
    return Point.of(newX, y, fieldMeasures);
  }

  public Point lowerPoint() {
    final int newY = (y + 1) % fieldMeasures.getHeight();
    return Point.of(x, newY, fieldMeasures);
  }

  public Point upperPoint() {
    final int newY = (fieldMeasures.getHeight() + y - 1) % fieldMeasures.getHeight();
    return Point.of(x, newY, fieldMeasures);
  }
}
