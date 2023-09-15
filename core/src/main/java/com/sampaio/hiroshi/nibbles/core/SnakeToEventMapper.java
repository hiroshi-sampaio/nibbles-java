package com.sampaio.hiroshi.nibbles.core;

public class SnakeToEventMapper {

  public Event mapToSlitherEvent(final Block snakeBlock) {
    return switch (snakeBlock) {
      case SNAKE_ONE -> Event.SNAKE_ONE_SLITHERS;
      case SNAKE_TWO -> Event.SNAKE_TWO_SLITHERS;
      default -> throw new IllegalStateException("Unexpected value: " + snakeBlock);
    };
  }

  public Event mapToEatEvent(final Block snakeBlock) {
    return switch (snakeBlock) {
      case SNAKE_ONE -> Event.SNAKE_ONE_EATS;
      case SNAKE_TWO -> Event.SNAKE_TWO_EATS;
      default -> throw new IllegalStateException("Unexpected value: " + snakeBlock);
    };
  }

  public Event mapToRunIntoWallEvent(final Block snakeBlock) {
    return switch (snakeBlock) {
      case SNAKE_ONE -> Event.SNAKE_ONE_RUNS_INTO_WALL;
      case SNAKE_TWO -> Event.SNAKE_TWO_RUNS_INTO_WALL;
      default -> throw new IllegalStateException("Unexpected value: " + snakeBlock);
    };
  }

  public Event mapToHitSnakeOne(final Block snakeBlock) {
    return switch (snakeBlock) {
      case SNAKE_ONE -> Event.SNAKE_ONE_TRIPS_ON_ITSELF;
      case SNAKE_TWO -> Event.SNAKE_ONE_BUMPS_INTO_SNAKE_TWO;
      default -> throw new IllegalStateException("Unexpected value: " + snakeBlock);
    };
  }

  public Event mapToHitSnakeTwo(final Block snakeBlock) {
    return switch (snakeBlock) {
      case SNAKE_ONE -> Event.SNAKE_TWO_BUMPS_INTO_SNAKE_ONE;
      case SNAKE_TWO -> Event.SNAKE_TWO_TRIPS_ON_ITSELF;
      default -> throw new IllegalStateException("Unexpected value: " + snakeBlock);
    };
  }
}
