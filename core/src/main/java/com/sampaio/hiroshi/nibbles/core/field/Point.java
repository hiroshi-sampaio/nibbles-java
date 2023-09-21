package com.sampaio.hiroshi.nibbles.core.field;

import lombok.*;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PROTECTED)
public class Point {

  @ToString.Include @EqualsAndHashCode.Include private final int x;
  @ToString.Include @EqualsAndHashCode.Include private final int y;
  private final Measures measures;

  public Point rightPoint() {
    final int newX = (x + 1) % measures.getWidth();
    return Point.of(newX, y, measures);
  }

  public Point leftPoint() {
    final int newX = (measures.getWidth() + x - 1) % measures.getWidth();
    return Point.of(newX, y, measures);
  }

  public Point lowerPoint() {
    final int newY = (y + 1) % measures.getHeight();
    return Point.of(x, newY, measures);
  }

  public Point upperPoint() {
    final int newY = (measures.getHeight() + y - 1) % measures.getHeight();
    return Point.of(x, newY, measures);
  }
}
