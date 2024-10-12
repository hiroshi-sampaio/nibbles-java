package com.sampaio.hiroshi.nibbles.core.field;

public enum Block {
  EMPTY,
  WALL,
  ITEM,
  SNAKE_ONE,
  SNAKE_TWO;

  public boolean isSnake() {
    return this == SNAKE_ONE || this == SNAKE_TWO;
  }

  public boolean canWalkOn() {
    return this == EMPTY || this == ITEM;
  }
}
