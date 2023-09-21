package com.sampaio.hiroshi.nibbles.core.testing.support;

import com.sampaio.hiroshi.nibbles.core.Block;

import static com.sampaio.hiroshi.nibbles.core.Block.*;

public class BlockCharMapper {

  public static final char SNAKE_ONE_HEAD = 'A';
  public static final char SNAKE_TWO_HEAD = 'B';

  public Character map(final Block block) {
    return switch (block) {
      case EMPTY -> ' ';
      case WALL -> '#';
      case FOOD -> 'F';
      case SNAKE_ONE -> '1';
      case SNAKE_TWO -> '2';
    };
  }
  
  public Character mapToHead(final Block block) {
    return switch (block) {
      case SNAKE_ONE -> 'A';
      case SNAKE_TWO -> 'B';
      default -> throw new IllegalStateException("Unexpected value: " + block);
    };
  }

  public Block map(final Character character) {
    return switch (character) {
      case ' ' -> EMPTY;
      case '#' -> WALL;
      case 'F' -> FOOD;
      case SNAKE_ONE_HEAD, '1' -> SNAKE_ONE;
      case SNAKE_TWO_HEAD, '2' -> SNAKE_TWO;
      default -> throw new IllegalStateException("Unexpected value: " + character);
    };
  }

  public boolean isSnakeOneHead(final Character character) {
    return character == 'A';
  }

  public boolean isSnakeTwoHead(final Character character) {
    return character == 'B';
  }

  public boolean isSnakeHead(final Character character) {
    return isSnakeOneHead(character) || isSnakeTwoHead(character);
  }
}
