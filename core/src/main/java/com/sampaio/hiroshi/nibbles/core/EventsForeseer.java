package com.sampaio.hiroshi.nibbles.core;

import static java.util.function.Predicate.not;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EventsForeseer {

  @NonNull private final SnakeToEventMapper snakeToEventMapper;

  public EnumMap<Block, SnakeMovement> foreseeSnakeMovements(final Collection<Snake> snakes) {
    return snakes.stream()
        .map(Snake::foreseeUpdate)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(
            Collectors.toMap(
                SnakeMovement::getSnakeBlock,
                Function.identity(),
                (o, o2) -> {
                  throw new IllegalArgumentException();
                },
                () -> new EnumMap<>(Block.class)));
  }

  public EnumSet<Event> foreseeEventsOnArena(
      final EnumMap<Block, SnakeMovement> snakeMovements, final Arena arena) {

    final EnumSet<Event> events = EnumSet.noneOf(Event.class);

    final List<Point> foreseenEmptyPoints = getForeseenEmptyPoints(snakeMovements);

    for (final SnakeMovement snakeMovement : snakeMovements.values()) {
      final Point nextHeadPoint = snakeMovement.getNextHead();
      final Block snakeBlock = snakeMovement.getSnakeBlock();

      final Block currentBlockAt = arena.getAt(nextHeadPoint);

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
        snakeMovements.values().stream()
            .filter(not(snakeMovement::sameSnake))
            .filter(snakeMovement::sameNewHead)
            .map(SnakeMovement::getSnakeBlock)
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

    return events;
  }

  private static List<Point> getForeseenEmptyPoints(
      final EnumMap<Block, SnakeMovement> snakeMovements) {
    return snakeMovements.values().stream()
        .map(SnakeMovement::getTailTipToRemove)
        .filter(Objects::nonNull)
        .toList();
  }
}
