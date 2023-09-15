package com.sampaio.hiroshi.nibbles.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PointTest {
  @Test
  void arenaWrapTest() {
    final FieldMeasures fieldMeasures = FieldMeasures.of(10, 10);
    final Point point = fieldMeasures.pointOf(9, 9, Direction.RIGHT);
    final Point nextPoint = point.rightPoint();
    assertEquals(0, nextPoint.getX());
  }
}
