package com.sampaio.hiroshi.nibbles.core.item;

import com.sampaio.hiroshi.nibbles.core.field.Point;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(staticName = "of")
public class Item {

  private final List<Point> points;
  private final int lengthToGrow;
  private final int lengthToShrink;
  private final int speedToAdd;
  private final int speedToRemove;
}
