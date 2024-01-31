package com.sampaio.hiroshi.nibbles.core.food;

import com.sampaio.hiroshi.nibbles.core.field.Point;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class Food {
  private final List<Point> points;
  private final int lengthToGrow;
  private final int speedToAdd;
}
