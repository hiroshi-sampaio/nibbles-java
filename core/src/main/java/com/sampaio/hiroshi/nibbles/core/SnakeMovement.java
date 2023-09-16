package com.sampaio.hiroshi.nibbles.core;

import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor(staticName = "of")
@ToString
public class SnakeMovement {
  private final Snake snake;
  private final Point pointToSetAsSnake;
  private final Point pointToSetAsEmpty;

  public Block getSnakeBlock() {
    return snake.getSnakeBlock();
  }

  public boolean sameSnake(final SnakeMovement other) {
    return this.snake == other.snake || this.getSnakeBlock() == other.getSnakeBlock();
  }

  public boolean samePointToSetAsSnake(final SnakeMovement other) {
    return Objects.equals(pointToSetAsSnake, other.pointToSetAsEmpty);
  }

  public boolean differentSnakeButSamePointToSetAsSnake(final SnakeMovement other) {
    return !sameSnake(other) && samePointToSetAsSnake(other);
  }
}
