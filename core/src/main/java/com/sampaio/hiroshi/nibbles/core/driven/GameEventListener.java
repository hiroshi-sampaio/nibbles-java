package com.sampaio.hiroshi.nibbles.core.driven;

import com.sampaio.hiroshi.nibbles.core.*;
import java.util.EnumMap;
import java.util.EnumSet;

public interface GameEventListener {
  void initialGameContextSet(GameContext gameContext);

  void arenaUpdated(Arena arena);

  void snakeMovementsForeseen(EnumMap<Block, SnakeMovement> snakeMovements);

  void eventsForeseen(EnumSet<Event> events);
}
