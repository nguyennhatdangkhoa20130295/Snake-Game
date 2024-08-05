package model;

import static constant.Constants.CELL_SIZE;
import static constant.Constants.HEIGHT;
import static constant.Constants.WIDTH;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Snake {
	private List<Point> body;
	private Direction currentDirection;

	public Snake(int startX, int startY) {
		body = new ArrayList<>();
		body.add(new Point(startX, startY));
		body.add(new Point(startX - 1, startY));
		currentDirection = Direction.RIGHT;
	}

	/**
	 * @return the body
	 */
	public List<Point> getBody() {
		return body;
	}

	/**
	 * @return the currentDirection
	 */
	public Direction getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(Direction direction) {
		Point head = new Point(body.get(0));
		// Chỉ cho phép thay đổi hướng nếu hướng mới không đối diện với hướng hiện tại
		if (direction == Direction.UP && currentDirection != Direction.DOWN && head.getX() != body.get(1).getX()
				|| direction == Direction.DOWN && currentDirection != Direction.UP && head.getX() != body.get(1).getX()
				|| direction == Direction.LEFT && currentDirection != Direction.RIGHT
						&& head.getY() != body.get(1).getY()
				|| direction == Direction.RIGHT && currentDirection != Direction.LEFT
						&& head.getY() != body.get(1).getY()) {
			currentDirection = direction;
		}
	}

	public void move(Direction direction) {
		// Di chuyển rắn theo hướng đã cho
		Point head = body.get(0);
		int newX = head.x;
		int newY = head.y;

		switch (direction) {
		case UP:
			newY--;
			break;
		case DOWN:
			newY++;
			break;
		case LEFT:
			newX--;
			break;
		case RIGHT:
			newX++;
			break;
		}
		// Kiểm tra đi xuyên qua những nơi không có chướng ngại vật
		if (newX < 0) {
			newX = (int) Math.floor(WIDTH / CELL_SIZE) - 1;
		} else if (newX >= Math.floor(WIDTH / CELL_SIZE)) {
			newX = 0;
		} else if (newY < 0) {
			newY = (int) Math.floor(HEIGHT / CELL_SIZE) - 1;
		} else if (newY >= Math.floor(HEIGHT / CELL_SIZE)) {
			newY = 0;
		}

		// Thêm ô mới vào đầu rắn
		Point newHead = new Point(newX, newY);
		body.add(0, newHead);

		// Xoá ô cuối cùng của rắn
		body.remove(body.size() - 1);
	}

	public void grow() {
		// Khi rắn ăn mồi, rắn sẽ dài thêm một ô mà không cần xoá ô đuôi
		Point tail = body.get(body.size() - 1);
		body.add(new Point(tail));
	}

	public boolean collidesWithSelf() {
		// Kiểm tra xem rắn có va chạm với chính nó không
		Point head = body.get(0);
		for (int i = 1; i < body.size(); i++) {
			if (head.equals(body.get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean collidesWithFood(Food food) {
		// Kiểm tra xem rắn có ăn được mồi không
		Point head = body.get(0);
		return head.equals(food.getPosition());
	}

	public boolean collidesWithObstacle(List<Cell> cells) {
		// Kiểm tra xem rắn có va chạm với chướng ngại vật không
		Point head = body.get(0);
		for (Cell cell : cells) {
			if (cell.getType() == CellType.OBSTACLE && head.equals(new Point(cell.getX(), cell.getY()))) {
				return true;
			}
		}
		return false;
	}

	public boolean collidesWithBonusFood(BonusFood bonusFood) {
		// Kiểm tra xem rắn có ăn được mồi bonus không
		Point head = body.get(0);
		int bonusX = (int) bonusFood.getPosition().getX();
		int bonusY = (int) bonusFood.getPosition().getY();
		return head.equals(bonusFood.getPosition()) || head.equals(new Point(bonusX + 1, bonusY))
				|| head.equals(new Point(bonusX, bonusY + 1)) || head.equals(new Point(bonusX + 1, bonusY + 1));

	}

	public boolean collidesWithNextLevelFood(NextLevelFood nextLevelFood) {
		// Kiểm tra xem rắn có ăn được mồi qua màn không
		Point head = body.get(0);
		int x = (int) nextLevelFood.getPosition().getX();
		int y = (int) nextLevelFood.getPosition().getY();
		return head.equals(nextLevelFood.getPosition()) || head.equals(new Point(x + 1, y))
				|| head.equals(new Point(x, y + 1)) || head.equals(new Point(x + 1, y + 1));

	}

	public boolean collidesWithBox(Box box) {
		// Kiểm tra xem rắn có chạm vào hộp không
		Point head = body.get(0);
		return head.equals(box.getPosition());
	}
}
