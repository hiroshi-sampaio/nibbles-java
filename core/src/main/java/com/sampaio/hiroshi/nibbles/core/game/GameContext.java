package com.sampaio.hiroshi.nibbles.core.game;

import com.sampaio.hiroshi.nibbles.core.field.Block;
import com.sampaio.hiroshi.nibbles.core.field.Field;
import com.sampaio.hiroshi.nibbles.core.food.Chef;
import com.sampaio.hiroshi.nibbles.core.snake.Snake;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class GameContext {
  private final Field field;
  private final EnumMap<Block, Snake> blockToSnakeMap;
  //private final Chef chef;

  public Optional<Snake> getSnakeByBlock(final Block snakeBlock) {
    return Optional.ofNullable(this.blockToSnakeMap.get(snakeBlock));
  }

  public Collection<Snake> getSnakes() {
    return blockToSnakeMap.values();
  }
}
