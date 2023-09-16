package com.sampaio.hiroshi.nibbles.core;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class GameContext {
  private final Arena arena;
  private final EnumMap<Block, Snake> blockToSnakeMap;

  public Optional<Snake> getSnakeByBlock(final Block snakeBlock) {
    return Optional.ofNullable(this.blockToSnakeMap.get(snakeBlock));
  }

  public Collection<Snake> getSnakes() {
    return blockToSnakeMap.values();
  }
}
