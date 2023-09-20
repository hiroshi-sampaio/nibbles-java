package com.sampaio.hiroshi.nibbles.core;

public enum Block {
  EMPTY,
  WALL,
  FOOD,
  SNAKE_ONE,
  SNAKE_TWO;

  public boolean isSnake() {
    return this == SNAKE_ONE || this == SNAKE_TWO;
  }

  public boolean canWalkOn() {
    return this == EMPTY || this == FOOD;
  }
}
