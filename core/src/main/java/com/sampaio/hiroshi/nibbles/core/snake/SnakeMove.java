package com.sampaio.hiroshi.nibbles.core.snake;

import com.sampaio.hiroshi.nibbles.core.field.Block;
import com.sampaio.hiroshi.nibbles.core.field.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor(staticName = "of")
@ToString
public class SnakeMove {
  private final Snake snake;
  private final Point nextHead;
  private final Point tailTipToRemove;

  public Block getSnakeBlock() {
    return snake.getSnakeBlock();
  }

  public boolean sameSnake(final SnakeMove other) {
    return this == other
        || this.snake == other.snake
        || this.snake.getSnakeBlock() == other.snake.getSnakeBlock();
  }

  public boolean sameNewHead(final SnakeMove other) {
    return this == other || this.nextHead.equals(other.nextHead);
  }

  public void execute() {
    snake.executeMove(this);
  }
}
