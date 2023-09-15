package com.sampaio.hiroshi.nibbles.core;

import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class SnakeMovement {
  private final Block snakeBlock;
  private final Point pointToSetAsSnake;
  private final Point pointToSetAsEmpty;

  public boolean sameSnakeBlock(final SnakeMovement other) {
    return snakeBlock == other.snakeBlock;
  }

  public boolean samePointToSetAsSnake(final SnakeMovement other) {
    return Objects.equals(pointToSetAsSnake, other.pointToSetAsEmpty);
  }

  public boolean differentSnakeBlockButSamePointToSetAsSnake(final SnakeMovement other) {
    return !sameSnakeBlock(other) && samePointToSetAsSnake(other);
  }
}
