package com.sampaio.hiroshi.nibbles.core;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class GameContext {
  private final Arena arena;
  private final List<Snake> snakes;
  private final EnumMap<Block, Snake> blockToSnakeMap;

  public GameContext(final Arena arena, final List<Snake> snakes) {
    this.arena = arena;
    this.snakes = snakes;
    this.blockToSnakeMap = createBlockToSnakeMap();
  }

  private EnumMap<Block, Snake> createBlockToSnakeMap() {
    return snakes.stream()
        .collect(
            Collectors.toMap(
                Snake::getSnakeBlock,
                Function.identity(),
                (o, o2) -> {
                  throw new IllegalArgumentException();
                },
                () -> new EnumMap<>(Block.class)));
  }

  public Snake getSnakeByBlock(final Block snakeBlock) {
    if (!this.blockToSnakeMap.containsKey(snakeBlock)) throw new IllegalArgumentException();
    return this.blockToSnakeMap.get(snakeBlock);
  }
}
