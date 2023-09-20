package com.sampaio.hiroshi.nibbles.core;

import static java.util.function.Predicate.not;

import java.io.StringReader;
import java.util.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LevelHelper {

  final BlockCharMapper mapper;

  public Arena createArena(final String arenaFieldAsText) {
    final List<String> fieldLines = readFieldLines(arenaFieldAsText);
    final int width = fieldLines.stream().mapToInt(String::length).max().orElseThrow();
    final int height = fieldLines.size();

    final Arena arena = FieldMeasures.of(width, height).arenaOf();

    fillArenaFieldAndGetSnakeHeads(fieldLines, arena);
    return arena;
  }

  public GameContext createGameContext(final String arenaFieldAsText) {

    final List<String> fieldLines = readFieldLines(arenaFieldAsText);

    final int width = fieldLines.stream().mapToInt(String::length).max().orElseThrow();
    final int height = fieldLines.size();

    final Arena arena = FieldMeasures.of(width, height).arenaOf();

    final EnumMap<Block, Point> snakeHeads = fillArenaFieldAndGetSnakeHeads(fieldLines, arena);

    final EnumMap<Block, Snake> blockSnakeEnumMap = createSnakes(arena, snakeHeads);

    return GameContext.of(arena, blockSnakeEnumMap);
  }

  private static List<String> readFieldLines(final String arenaFieldAsText) {
    final StringReader reader = new StringReader(arenaFieldAsText);
    final Scanner scanner = new Scanner(reader);

    final List<String> lines = new ArrayList<>();
    while (scanner.hasNext()) {
      final String line = scanner.nextLine();
      lines.add(line);
    }
    return lines;
  }

  private EnumMap<Block, Point> fillArenaFieldAndGetSnakeHeads(
      final List<String> arenaFieldLines, final Arena arena) {
    final EnumMap<Block, Point> snakeHeadsMap = new EnumMap<>(Block.class);

    for (int y = 0; y < arena.getFieldMeasures().getHeight(); y++) {
      final String line = arenaFieldLines.get(y);
      final char[] charArray = line.toCharArray();

      for (int x = 0; x < charArray.length; x++) {
        final char ch = charArray[x];
        final Block block = mapper.map(ch);

        arena.setAt(x, y, block);

        if (mapper.isSnakeHead(ch)) {
          snakeHeadsMap.put(block, arena.getFieldMeasures().pointOf(x, y));
        }
      }
    }

    return snakeHeadsMap;
  }

  private static EnumMap<Block, Snake> createSnakes(
      final Arena arena, final EnumMap<Block, Point> snakeHeads) {

    final EnumMap<Block, Snake> blockSnakeEnumMap = new EnumMap<>(Block.class);

    for (final Map.Entry<Block, Point> snakeHead : snakeHeads.entrySet()) {

      final Block block = snakeHead.getKey();
      final Point headPoint = snakeHead.getValue();

      final Snake snake =
          Snake.builder()
              .snakeBlock(block)
              .name(block == Block.SNAKE_ONE ? "Sammy" : "Jake")
              .initialLength(1)
              .initialSpeed(1)
              .initialPosition(headPoint)
              .initialDirection(block == Block.SNAKE_ONE ? Direction.RIGHT : Direction.LEFT)
              .build();

      blockSnakeEnumMap.put(block, snake);

      Point currentPoint = snake.getHead();

      while (true) {
        final Point upperPoint = currentPoint.upperPoint();
        final Point rightPoint = currentPoint.rightPoint();
        final Point lowerPoint = currentPoint.lowerPoint();
        final Point leftPoint = currentPoint.leftPoint();

        final List<Point> pointsAround = List.of(upperPoint, rightPoint, lowerPoint, leftPoint);

        final Optional<Point> nextTailPointOptional =
            pointsAround.stream()
                .filter(point -> snake.getSnakeBlock() == arena.getAt(point))
                .filter(not(snake::contains))
                .findAny();

        if (nextTailPointOptional.isEmpty()) break;
        final Point nextTailPoint = nextTailPointOptional.get();

        if (snake.getHead().equals(currentPoint)) {
          final Direction relativeDirection;
          if (nextTailPoint.equals(upperPoint)) {
            relativeDirection = Direction.UP;
          } else if (nextTailPoint.equals(rightPoint)) {
            relativeDirection = Direction.RIGHT;
          } else if (nextTailPoint.equals(lowerPoint)) {
            relativeDirection = Direction.DOWN;
          } else {
            relativeDirection = Direction.LEFT;
          }
          snake.setDirection(relativeDirection.opposite());
        }

        snake.grow(nextTailPoint);
        currentPoint = nextTailPoint;
      }
    }

    return blockSnakeEnumMap;
  }
}
