package com.sampaio.hiroshi.nibbles.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor(staticName = "of")
@ToString
public class SnakeMovement {
  private final Snake snake;
  private final Point nextHead;
  private final Point tailTipToRemove;

  public Block getSnakeBlock() {
    return snake.getSnakeBlock();
  }

  public boolean sameSnake(final SnakeMovement other) {
    return this == other
        || this.snake == other.snake
        || this.snake.getSnakeBlock() == other.snake.getSnakeBlock();
  }

  public boolean sameNewHead(final SnakeMovement other) {
    return this == other || this.nextHead.equals(other.nextHead);
  }

  public void execute() {
    snake.executeMovement(this);
  }
}
