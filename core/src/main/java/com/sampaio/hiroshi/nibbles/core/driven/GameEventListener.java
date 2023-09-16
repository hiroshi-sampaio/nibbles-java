package com.sampaio.hiroshi.nibbles.core.driven;

import com.sampaio.hiroshi.nibbles.core.Arena;
import com.sampaio.hiroshi.nibbles.core.Block;
import com.sampaio.hiroshi.nibbles.core.Event;
import com.sampaio.hiroshi.nibbles.core.SnakeMovement;
import java.util.EnumMap;
import java.util.EnumSet;

public interface GameEventListener {
  void arenaUpdated(Arena arena);

  void eventsForeseen(EnumSet<Event> events);

  void snakeMovementsForeseen(EnumMap<Block, SnakeMovement> snakeMovements);
}
