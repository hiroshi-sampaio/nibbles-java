package com.sampaio.hiroshi.nibbles.core.event;

import com.sampaio.hiroshi.nibbles.core.field.Block;
import com.sampaio.hiroshi.nibbles.core.game.GameContext;
import com.sampaio.hiroshi.nibbles.core.snake.Snake;
import com.sampaio.hiroshi.nibbles.core.snake.SnakeMove;
import java.util.EnumMap;
import java.util.EnumSet;

public interface EventListenerDrivenPort {
  void initialGameContextSet(GameContext gameContext);

  void snakeMovesForeseen(EnumMap<Block, SnakeMove> snakeMoves);

  void eventsForeseen(EnumSet<Event> events);

  void gameContextChanged(GameContext gameContext);

  void snakeDied(Snake snake);
}
