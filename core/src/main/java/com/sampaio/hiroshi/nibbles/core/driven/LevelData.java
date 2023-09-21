package com.sampaio.hiroshi.nibbles.core.driven;

import com.sampaio.hiroshi.nibbles.core.field.Field;
import com.sampaio.hiroshi.nibbles.core.field.Measures;
import com.sampaio.hiroshi.nibbles.core.snake.Snake;
import java.util.List;

public interface LevelData {

  int count();

  Field createField(int level);

  List<Snake> createSnakes(int level, Measures measures, List<String> names);
}
