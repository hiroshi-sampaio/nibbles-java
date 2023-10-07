package com.sampaio.hiroshi.nibbles.core.event;

import static java.util.function.Predicate.not;

import com.sampaio.hiroshi.nibbles.core.field.Block;
import com.sampaio.hiroshi.nibbles.core.field.Field;
import com.sampaio.hiroshi.nibbles.core.field.Point;
import com.sampaio.hiroshi.nibbles.core.snake.Snake;
import com.sampaio.hiroshi.nibbles.core.snake.SnakeMove;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Foreseer {

  private final SnakeToEventMapper snakeToEventMapper;
  private final EventListenerDrivenPort eventListener;

  private static List<Point> foreseeEmptyPoints(final EnumMap<Block, SnakeMove> snakesMoves) {
    return snakesMoves.values().stream()
        .map(SnakeMove::getTailTipToRemove)
        .filter(Objects::nonNull)
        .toList();
  }

  public EnumMap<Block, SnakeMove> foreseeSnakeMoves(final Collection<Snake> snakes) {
    final EnumMap<Block, SnakeMove> foreseenSnakeMoves =
        snakes.stream()
            .map(Snake::foreseeMove)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(
                Collectors.toMap(
                    SnakeMove::getSnakeBlock,
                    Function.identity(),
                    (o, o2) -> {
                      throw new IllegalArgumentException();
                    },
                    () -> new EnumMap<>(Block.class)));

    eventListener.snakeMovesForeseen(foreseenSnakeMoves);

    return foreseenSnakeMoves;
  }

  public EnumSet<Event> foreseeEvents(
      final EnumMap<Block, SnakeMove> snakeMoves, final Field field) {

    final EnumSet<Event> events = EnumSet.noneOf(Event.class);

    final List<Point> foreseenEmptyPoints = foreseeEmptyPoints(snakeMoves);

    for (final SnakeMove snakeMove : snakeMoves.values()) {
      final Point nextHeadPoint = snakeMove.getNextHead();
      final Block snakeBlock = snakeMove.getSnakeBlock();

      final Block currentBlockAt = field.getAt(nextHeadPoint);

      if (Block.WALL == currentBlockAt) {
        // It is not expected to have wall removed so there is no point on foreseeing changes on
        // the point where the head of the snake is going to be.
        events.add(snakeToEventMapper.mapToRunIntoWallEvent(snakeBlock));
      } else {
        final EnumSet<Block> foreseenBlocksAt = EnumSet.noneOf(Block.class);
        if (foreseenEmptyPoints.contains(nextHeadPoint)) {
          // The point is occupied by the tail tip of some snake but will be empty on next frame so
          // the current snake can go there
          foreseenBlocksAt.add(Block.EMPTY);
        }

        // Which snakes are going to the same point except the current one
        snakeMoves.values().stream()
            .filter(not(snakeMove::sameSnake))
            .filter(snakeMove::sameNewHead)
            .map(SnakeMove::getSnakeBlock)
            .forEach(foreseenBlocksAt::add);

        if (currentBlockAt.canWalkOn()) {
          if (currentBlockAt == Block.FOOD) {
            events.add(snakeToEventMapper.mapToEatEvent(snakeBlock));
          }
          if (foreseenBlocksAt.contains(Block.SNAKE_ONE)) {
            events.add(snakeToEventMapper.mapToHitSnakeOne(snakeBlock));
          } else if (foreseenBlocksAt.contains(Block.SNAKE_TWO)) {
            events.add(snakeToEventMapper.mapToHitSnakeTwo(snakeBlock));
          } else {
            events.add(snakeToEventMapper.mapToSlitherEvent(snakeBlock));
          }
        } else if (currentBlockAt.isSnake()) {
          if (foreseenBlocksAt.contains(Block.EMPTY)) {
            if (foreseenBlocksAt.contains(Block.SNAKE_ONE)) {
              events.add(snakeToEventMapper.mapToHitSnakeOne(snakeBlock));
            } else if (foreseenBlocksAt.contains(Block.SNAKE_TWO)) {
              events.add(snakeToEventMapper.mapToHitSnakeTwo(snakeBlock));
            } else {
              events.add(snakeToEventMapper.mapToSlitherEvent(snakeBlock));
            }
          } else {
            if (currentBlockAt == Block.SNAKE_ONE) {
              events.add(snakeToEventMapper.mapToHitSnakeOne(snakeBlock));
            } else if (currentBlockAt == Block.SNAKE_TWO) {
              events.add(snakeToEventMapper.mapToHitSnakeTwo(snakeBlock));
            }
          }
        }
      }
    }

    eventListener.eventsForeseen(events);

    return events;
  }
}
