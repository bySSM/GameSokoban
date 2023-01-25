package model;

import controller.EventListener;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Model {
    // Размер ячейки игрового поля.
    public static final int FIELD_CELL_SIZE = 20;

    // Будет хранить наши игровые объекты
    private GameObjects gameObjects;
    // Поле отвечающее за текущий уровень
    private int currentLevel = 1;
    private EventListener eventListener;
    // Поле отвечающие за загрузчик уровней
    LevelLoader levelLoader;

    public Model() {
        try {
            levelLoader = new LevelLoader(Paths.get(getClass().getResource("../res/levels.txt").toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void move(Direction direction) {
        if (checkWallCollision(gameObjects.getPlayer(), direction)) {
            return;
        }

        if (checkBoxCollisionAndMoveIfAvailable(direction)) {
            return;
        }

        int dx = direction == Direction.LEFT ? -FIELD_CELL_SIZE : (direction == Direction.RIGHT ? FIELD_CELL_SIZE : 0);
        int dy = direction == Direction.UP ? -FIELD_CELL_SIZE : (direction == Direction.DOWN ? FIELD_CELL_SIZE : 0);
        gameObjects.getPlayer().move(dx, dy);

        checkCompletion();
    }

    // Перезапуск текущего уровня
    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        restartLevel(currentLevel);
    }

    // метод проверяет столкновение со стеной
    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        for (Wall wall : gameObjects.getWalls()) {
            if (gameObject.isCollision(wall, direction)) {
                return true;
            }
        }
        return false;
    }

    // метод проверяет столкновение с ящиками
    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        for (Box box : gameObjects.getBoxes()) {
            if (gameObjects.getPlayer().isCollision(box, direction)) {
                for (Box item : gameObjects.getBoxes()) {
                    if (!box.equals(item)) {
                        if (box.isCollision(item, direction)) {
                            return true;
                        }
                    }
                    if (checkWallCollision(box, direction)) {
                        return true;
                    }
                }
                int dx = direction == Direction.LEFT ? -FIELD_CELL_SIZE : (direction == Direction.RIGHT ? FIELD_CELL_SIZE : 0);
                int dy = direction == Direction.UP ? -FIELD_CELL_SIZE : (direction == Direction.DOWN ? FIELD_CELL_SIZE : 0);
                box.move(dx, dy);
            }
        }
        return false;
    }

    // метод должен проверить пройден ли уровень
    // (на всех ли домах стоят ящики, их координаты должны совпадать)
    public void checkCompletion() {
        int numberOfHomes = gameObjects.getHomes().size();
        int numberOfHomesWithBox = 0;

        for (Home home : gameObjects.getHomes()) {
            for (Box box : gameObjects.getBoxes()) {
                if (box.getX() == home.getX() && box.getY() == home.getY()) {
                    numberOfHomesWithBox++;
                }
            }
        }

        if (numberOfHomesWithBox == numberOfHomes) {
            eventListener.levelCompleted(currentLevel);
        }
    }

    // Перезапуск конкретного уровня
    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }
}
