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
      final Point pointToSetAsSnake = snakeMovement.getNextHead();
      final Block snakeBlock = snakeMovement.getSnakeBlock();

      // Limits to headbutt between two snakes only (must be changed if add more snakes)
      final Optional<Block> headButtWithOtherSnakeBlock =
          snakeMovements.values().stream()
              .filter(not(snakeMovement::sameSnakeBlockAs))
              .filter(snakeMovement::samePointToSetAsSnake)
              .map(SnakeMovement::getSnakeBlock)
              .findAny();

      if (headButtWithOtherSnakeBlock.isPresent()) events.add(Event.HEADS_BUTT);

      final Block actualArenaAt = arena.getAt(pointToSetAsSnake.getX(), pointToSetAsSnake.getY());

      final Block foreseenArenaAt;
      if ((actualArenaAt == Block.SNAKE_ONE || actualArenaAt == Block.SNAKE_TWO)
          && foreseenEmptyPoints.contains(pointToSetAsSnake)) {
        foreseenArenaAt = Block.EMPTY;
      } else if (actualArenaAt == Block.EMPTY && headButtWithOtherSnakeBlock.isPresent()) {
        foreseenArenaAt = headButtWithOtherSnakeBlock.get();
      } else {
        foreseenArenaAt = actualArenaAt;
      }

      final Event event =
          switch (foreseenArenaAt) {
            case WALL -> snakeToEventMapper.mapToRunIntoWallEvent(snakeBlock);
            case SNAKE_ONE -> snakeToEventMapper.mapToHitSnakeOne(snakeBlock);
            case SNAKE_TWO -> snakeToEventMapper.mapToHitSnakeTwo(snakeBlock);
            case FOOD -> snakeToEventMapper.mapToEatEvent(snakeBlock);
            case EMPTY -> snakeToEventMapper.mapToSlitherEvent(snakeBlock);
          };

      events.add(event);
    }

    return events;
  }

  private static List<Point> getForeseenEmptyPoints(
      final EnumMap<Block, SnakeMovement> snakeMovements) {
    return snakeMovements.values().stream()
        .map(SnakeMovement::getCurrentTailTip)
        .filter(Objects::nonNull)
        .toList();
  }
}
