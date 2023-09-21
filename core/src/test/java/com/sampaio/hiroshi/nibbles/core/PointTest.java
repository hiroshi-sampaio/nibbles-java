package com.sampaio.hiroshi.nibbles.core;

import static org.junit.jupiter.api.Assertions.*;

import com.sampaio.hiroshi.nibbles.core.field.Measures;
import com.sampaio.hiroshi.nibbles.core.field.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PointTest {
  @ParameterizedTest
  @CsvSource({"1,1", "9,9", "10,10"})
  void arena10x10WrapTest(final int width, final int height) {
    final Measures measures = Measures.of(width, height);
    final Point point = measures.bottomRightPointOf();

    final Point rightPoint = point.rightPoint();
    assertEquals(0, rightPoint.getX());

    final Point lowerPoint = point.lowerPoint();
    assertEquals(0, lowerPoint.getY());
  }
}
