package com.sampaio.hiroshi.nibbles.core;

/** Order matters! todo */
public enum Event {
  SNAKE_ONE_SLITHERS,
  SNAKE_TWO_SLITHERS,
  SNAKE_ONE_EATS,
  SNAKE_TWO_EATS,
  SNAKE_ONE_RUNS_INTO_WALL,
  SNAKE_TWO_RUNS_INTO_WALL,
  SNAKE_ONE_TRIPS_ON_ITSELF,
  SNAKE_ONE_BUMPS_INTO_SNAKE_TWO,
  SNAKE_TWO_TRIPS_ON_ITSELF,
  SNAKE_TWO_BUMPS_INTO_SNAKE_ONE,
}
