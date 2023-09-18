package com.sampaio.hiroshi.nibbles.core;

import static java.util.Objects.nonNull;

import java.util.*;
import lombok.*;

@ToString(onlyExplicitlyIncluded = true)
public class Snake {
  @ToString.Include @Getter private final Block snakeBlock;
  @ToString.Include @Getter private final String name;
  private final Deque<Point> tail;
  @Setter private Direction direction;
  private int length;
  private int speed;
  private int countDownToMove;
  @Getter @Setter private boolean alive = true;

  @Builder
  private Snake(
      @NonNull final Block snakeBlock,
      @NonNull final String name,
      @NonNull final Point initialPosition,
      @NonNull final Direction initialDirection,
      final int initialLength,
      final int initialSpeed) {

    if (name.isBlank()) throw new IllegalArgumentException("Name must be given");

    if (initialLength < 1)
      throw new IllegalArgumentException("Initial length must be greater than 0");

    if (initialSpeed < 1)
      throw new IllegalArgumentException("Initial speed must be greater than 0");

    this.snakeBlock = snakeBlock;
    this.name = name;
    this.tail = new LinkedList<>(Collections.singletonList(initialPosition));
    this.direction = initialDirection;
    this.length = initialLength;
    this.speed = initialSpeed;
    this.countDownToMove = this.speed;
  }

  public Iterable<Point> snakePoints() {
    return tail;
  }

  public Point getHead() {
    return tail.getFirst();
  }

  public boolean contains(final Point point) {
    return tail.contains(point);
  }

  public void grow(final Point point) {
    tail.addLast(point);
    length++;
  }

  public Optional<SnakeMovement> foreseeUpdate() {
    if (!alive) return Optional.empty();

    if (countDownToMove > 0) {
      countDownToMove--;
    }

    if (countDownToMove == 0) {
      countDownToMove = speed;
      return Optional.ofNullable(foreseeMovement());
    }

    return Optional.empty();
  }

  public SnakeMovement foreseeMovement() {
    final Point currentHeadPoint = tail.getFirst();

    final Point nextHeadPoint =
        switch (direction) {
          case UP -> currentHeadPoint.upperPoint();
          case RIGHT -> currentHeadPoint.rightPoint();
          case DOWN -> currentHeadPoint.lowerPoint();
          case LEFT -> currentHeadPoint.leftPoint();
        };

    if (length == tail.size()) {
      final Point tailTipPoint = tail.getLast();
      return SnakeMovement.of(this, nextHeadPoint,  tailTipPoint);
    }

    return SnakeMovement.of(this, nextHeadPoint,  null);
  }

  public void executeMovement(final SnakeMovement snakeMovement) {
    tail.addFirst(snakeMovement.getNextHead());
    if (nonNull(snakeMovement.getCurrentTailTip())) {
      tail.removeLast();
    }
  }

  public void grow(final int lengthToGrow) {
    length += lengthToGrow;
  }

  public void increaseSpeed(final int factor) {
    speed += factor;
  }

  private void ifAbleTurnTo(final Direction direction) {
    if (this.direction != direction.opposite()) this.direction = direction;
  }

  public void turnUp() {
    ifAbleTurnTo(Direction.UP);
  }

  public void turnRight() {
    ifAbleTurnTo(Direction.RIGHT);
  }

  public void turnDown() {
    ifAbleTurnTo(Direction.DOWN);
  }

  public void turnLeft() {
    ifAbleTurnTo(Direction.LEFT);
  }
}
