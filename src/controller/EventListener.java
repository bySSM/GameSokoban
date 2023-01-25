package controller;

import model.Direction;

public interface EventListener {

    // передвинуть объект в определенном направлении
    void move(Direction direction);

    // начать заново текущий уровень
    void restart();

    // начать следующий уровень
    void startNextLevel();

    // уровень с номером level завершён
    void levelCompleted(int level);
}
