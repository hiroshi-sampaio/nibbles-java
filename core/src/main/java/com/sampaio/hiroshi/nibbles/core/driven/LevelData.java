package com.sampaio.hiroshi.nibbles.core.driven;

import com.sampaio.hiroshi.nibbles.core.Arena;
import com.sampaio.hiroshi.nibbles.core.FieldMeasures;
import com.sampaio.hiroshi.nibbles.core.Snake;
import java.util.List;

public interface LevelData {

  int count();

  Arena loadArena(int level);

  List<Snake> createSnakes(int level, FieldMeasures fieldMeasures, List<String> names);
}
