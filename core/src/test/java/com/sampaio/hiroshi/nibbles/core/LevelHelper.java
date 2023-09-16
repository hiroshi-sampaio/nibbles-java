package com.sampaio.hiroshi.nibbles.core;

import java.io.StringReader;
import java.util.*;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LevelHelper {

  final BlockCharMapper mapper;

  public Arena createArena(final String arenaFieldAsText) {
    final List<String> fieldLines = readFieldLines(arenaFieldAsText);
    final int width = fieldLines.stream().mapToInt(String::length).max().orElseThrow();
    final int height = fieldLines.size();

    final Arena arena = FieldMeasures.of(width, height).arenaOf();

    fillArena(fieldLines, arena);
    return arena;
  }

  public GameContext createGameContext(
      final String arenaFieldAsText,
      final Direction snakeOneDirection,
      final Direction snakeTwoDirection) {

    final List<String> fieldLines = readFieldLines(arenaFieldAsText);

    final int width = fieldLines.stream().mapToInt(String::length).max().orElseThrow();
    final int height = fieldLines.size();

    final Arena arena = FieldMeasures.of(width, height).arenaOf();

    fillArena(fieldLines, arena);

    final EnumMap<Block, Snake> blockSnakeEnumMap =
        createSnakesHeads(
            fieldLines, arena.getFieldMeasures(), snakeOneDirection, snakeTwoDirection);

    extendSnakeTailsIfNeeded(arena, blockSnakeEnumMap.values());

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

  private void fillArena(final List<String> fieldLines, final Arena arena) {
    for (int y = 0; y < arena.getFieldMeasures().getHeight(); y++) {
      final String line = fieldLines.get(y);
      final char[] charArray = line.toCharArray();

      for (int x = 0; x < charArray.length; x++) {
        final char ch = charArray[x];
        final Block block = mapper.map(ch);

        arena.setBlockAt(x, y, block);
      }
    }
  }

  private EnumMap<Block, Snake> createSnakesHeads(
      final List<String> fieldLines,
      final FieldMeasures fieldMeasures,
      final Direction snakeOneDirection,
      final Direction snakeTwoDirection) {

    final EnumMap<Block, Snake> blockSnakeEnumMap = new EnumMap<>(Block.class);

    for (int y = 0; y < fieldMeasures.getHeight(); y++) {
      final String line = fieldLines.get(y);
      final char[] charArray = line.toCharArray();

      for (int x = 0; x < charArray.length; x++) {
        final char ch = charArray[x];
        final Block block = mapper.map(ch);

        if (mapper.isSnakeHead(ch)) {
          blockSnakeEnumMap.put(
              block,
              Snake.builder()
                  .snakeBlock(block)
                  .name(block == Block.SNAKE_ONE ? "Sammy" : "Jake")
                  .initialLength(1)
                  .initialSpeed(1)
                  .initialPosition(
                      fieldMeasures.pointOf(
                          x, y, block == Block.SNAKE_ONE ? snakeOneDirection : snakeTwoDirection))
                  .build());
        }
      }
    }

    return blockSnakeEnumMap;
  }

  private static void extendSnakeTailsIfNeeded(final Arena arena, final Collection<Snake> snakes) {
    for (final Snake snake : snakes) {
      Point currentPoint = snake.getHead();

      while (true) {
        final List<Point> pointsAround =
            List.of(
                currentPoint.upperPoint(),
                currentPoint.rightPoint(),
                currentPoint.lowerPoint(),
                currentPoint.leftPoint());

        final Optional<Point> nextTailPoint =
            pointsAround.stream()
                .filter(point -> snake.getSnakeBlock() == arena.getAt(point))
                .filter(Predicate.not(snake::contains))
                .findAny();

        if (nextTailPoint.isEmpty()) break;
        currentPoint = nextTailPoint.get();
        snake.grow(currentPoint);
      }
    }
  }
}
