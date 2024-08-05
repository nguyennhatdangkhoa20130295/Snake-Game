package model;

import static constant.Constants.BONUS_SCORE;
import static constant.Constants.BONUS_TIME;
import static constant.Constants.CELL_SIZE;
import static constant.Constants.FOOD_SCORE;
import static constant.Constants.HEIGHT;
import static constant.Constants.WIDTH;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import observer.Observer;

public class SnakeModel implements ISnakeModel {
	private Snake snake;
	private Food food;
	private BonusFood bonusFood;
	private Box box;
	private BoxGoal boxGoal;
	private NextLevelFood nextLevelFood;
	private List<Obstacle> obstacles;
	private int width;
	private int height;
	private int rows;
	private int cols;
	private int foodScore;
	private int foodCount;
	private int currentScore;
	private int savedScore;
	private int currentLevel;
	private int currentLive;
	private int bonusScore;
	private int bonusTime;
	private int progressBarValue;
	private boolean isPlaying;
	private boolean isShowBonusProgressBar;
	private boolean isLevelMode;
	private boolean shouldGenerateNextLevelFood;
	private boolean hasEatenNextLevelFood;
	private boolean shouldGenerateFood;
	private boolean shouldGenerateBox;
	private List<Observer> observers;

	public SnakeModel(int level, boolean levelMode) {
		this.observers = new ArrayList<Observer>();
		this.width = WIDTH;
		this.height = HEIGHT;
		this.cols = width / CELL_SIZE;
		this.rows = height / CELL_SIZE;
		this.foodScore = FOOD_SCORE;
		this.currentScore = 0;
		this.savedScore = 0;
		this.currentLevel = level;
		this.currentLive = 3;
		this.foodCount = 0;
		this.bonusScore = BONUS_SCORE;
		this.bonusTime = BONUS_TIME;
		this.progressBarValue = 0;
		this.food = new Food(-1, -1);
		this.bonusFood = new BonusFood(-2, -2);
		this.nextLevelFood = new NextLevelFood(-2, -2);
		this.box = new Box(-1, -1);
		this.boxGoal = new BoxGoal(-1, -1);
		this.isPlaying = false;
		this.snake = new Snake(cols / 2, (rows / 2) - 1);
		this.isShowBonusProgressBar = false;
		this.isLevelMode = levelMode;
		this.shouldGenerateNextLevelFood = false;
		this.shouldGenerateFood = true;
		this.shouldGenerateBox = false;
		this.hasEatenNextLevelFood = false;
		obstacles = new ArrayList<>();
		generateObstacle();
		initializeFood();
		initialzeBox();
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}

	@Override
	public int getCurrentScore() {
		return currentScore;
	}

	/**
	 * @param score the currentScore to set
	 */
	public void setCurrentScore(int score) {
		this.currentScore = score;
	}

	/**
	 * @param savedScore the savedScore to set
	 */
	public void setSavedScore(int savedScore) {
		this.savedScore = savedScore;
	}

	@Override
	public int getCurrentLevel() {
		return currentLevel;
	}

	@Override
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	@Override
	public int getFoodCount() {
		return foodCount;
	}

	@Override
	public int getFoodScore() {
		return foodScore;
	}

	/**
	 * @param bonusTime the bonusTime to set
	 */
	public void setBonusTime(int bonusTime) {
		this.bonusTime = bonusTime;
	}

	@Override
	public boolean isPlaying() {
		return isPlaying;
	}

	@Override
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	@Override
	public boolean isShowBonusProgressBar() {
		return isShowBonusProgressBar;
	}

	@Override
	public void setShowBonusProgressBar(boolean isShowBonusProgressBar) {
		this.isShowBonusProgressBar = isShowBonusProgressBar;
	}

	@Override
	public Snake getSnake() {
		return snake;
	}

	@Override
	public int getProgressBarValue() {
		return progressBarValue;
	}

	@Override
	public void setProgressBarValue(int progressBarValue) {
		this.progressBarValue = progressBarValue;
	}

	@Override
	public boolean isHasEatenNextLevelFood() {
		return hasEatenNextLevelFood;
	}

	@Override
	public int getCurrentLive() {
		return currentLive;
	}

	/**
	 * @param currentLive the currentLive to set
	 */
	public void setCurrentLive(int currentLive) {
		this.currentLive = currentLive;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getCols() {
		return cols;
	}

	@Override
	public boolean isLevelMode() {
		return isLevelMode;
	}

	// Phương thức khởi tạo vị trí ngẫu nhiên mồi thường lần đầu tiên
	@Override
	public void initializeFood() {
		if (currentLevel != 5) {
			int foodX;
			int foodY;
			boolean validPosition;

			do {
				foodX = (int) Math.floor(Math.random() * cols);
				foodY = (int) Math.floor(Math.random() * rows);
				validPosition = true;

				// Kiểm tra xem vị trí này có trùng với bất kỳ chướng ngại vật nào không
				for (int i = 0; i < obstacles.size(); i++) {
					if (obstacles.get(i).getPosition().getX() == foodX
							&& obstacles.get(i).getPosition().getY() == foodY) {
						validPosition = false;
						break;
					}
				}

				// Kiểm tra xem vị trí này có trùng với bất kỳ phần nào của cơ thể rắn không
				if (validPosition) {
					for (int i = 0; i < snake.getBody().size(); i++) {
						if (snake.getBody().get(i).getX() == foodX && snake.getBody().get(i).getY() == foodY) {
							validPosition = false;
							break;
						}
					}
				}
			} while (!validPosition);

			food = new Food(foodX, foodY);
		} else {
			food.getPosition().setLocation(-1, -1);
		}
	}

	public void initialzeBox() {
		if (currentLevel == 5) {
			shouldGenerateBox = true;
			generateBox();
			generateBoxGoal();
		}
	}

	// Phương thức kiểm tra xem vị trí (x,y) có bị trùng hay không
	private boolean isOccupied(int x, int y) {
		// Kiểm tra xem vị trí trùng với bonusFood
		if (bonusFood.getPosition().getX() == x && bonusFood.getPosition().getY() == y) {
			return true;
		}
		// Kiểm tra xem vị trí trùng với thân của rắn
		for (Point point : snake.getBody()) {
			if (point.getX() == x && point.getY() == y) {
				return true;
			}
		}
		// Kiểm tra xem vị trí trùng với các chướng ngại vật
		for (Obstacle obstacle : obstacles) {
			if (obstacle.getPosition().getX() == x && obstacle.getPosition().getY() == y) {
				return true;
			}
		}
		return false;
	}

	// Phương thức khởi tạo vị trí ngẫu nhiên cho mồi thường
	@Override
	public void generateFood() {
		if (shouldGenerateFood) {
			int x, y;
			do {
				x = (int) Math.floor(Math.random() * cols);
				y = (int) Math.floor(Math.random() * rows);
			} while (isOccupied(x, y)); // Kiểm tra xem vị trí đã được sử dụng chưa
			food.getPosition().setLocation(x, y);
		} else {
			food.getPosition().setLocation(-1, -1);
		}
	}

	// Phương thức khởi tạo vị trí ngẫu nhiên cho mồi bonus
	@Override
	public void generateBonusFood() {
		if (shouldGenerateFood) {
			int x = 1 + (int) Math.floor(Math.random() * (cols - 2));
			int y = 1 + (int) Math.floor(Math.random() * (rows - 2));
			for (int i = 0; i < obstacles.size(); i++) {
				if ((obstacles.get(i).getPosition().getX() == x && obstacles.get(i).getPosition().getY() == y)
						|| (obstacles.get(i).getPosition().getX() == x + 1
								&& obstacles.get(i).getPosition().getY() == y)
						|| (obstacles.get(i).getPosition().getX() == x
								&& obstacles.get(i).getPosition().getY() == y + 1)
						|| (obstacles.get(i).getPosition().getX() == x + 1
								&& obstacles.get(i).getPosition().getY() == y + 1)
						|| (food.getPosition().getX() == x && food.getPosition().getY() == y)
						|| (nextLevelFood.getPosition().getX() == x && nextLevelFood.getPosition().getY() == y)) {
					generateBonusFood();
					return;
				}
			}
			bonusFood.getPosition().setLocation(x, y);
		}
	}

	// Phương thức khởi tạo vị trí ngẫu nhiên cho mồi qua màn
	@Override
	public void generateNextLevelFood() {
		if (shouldGenerateNextLevelFood) {
			int x = 1 + (int) Math.floor(Math.random() * (cols - 2));
			int y = 1 + (int) Math.floor(Math.random() * (rows - 2));
			for (int i = 0; i < obstacles.size(); i++) {
				if ((obstacles.get(i).getPosition().getX() == x && obstacles.get(i).getPosition().getY() == y)
						|| (obstacles.get(i).getPosition().getX() == x + 1
								&& obstacles.get(i).getPosition().getY() == y)
						|| (obstacles.get(i).getPosition().getX() == x
								&& obstacles.get(i).getPosition().getY() == y + 1)
						|| (obstacles.get(i).getPosition().getX() == x + 1
								&& obstacles.get(i).getPosition().getY() == y + 1)
						|| (bonusFood.getPosition().getX() == x && bonusFood.getPosition().getY() == y)) {
					generateNextLevelFood();
					return;
				}
			}
			nextLevelFood.getPosition().setLocation(x, y);
		}
	}

	// Phương thức khởi tạo vị trí ngẫu nhiên cho chướng ngại vật
	@Override
	public void generateRandomObstacle() {
		int x = (int) Math.floor(Math.random() * cols);
		int y = (int) Math.floor(Math.random() * rows);
		for (int i = 0; i < obstacles.size(); i++) {
			if ((obstacles.get(i).getPosition().getX() == x && obstacles.get(i).getPosition().getY() == y)
					|| (food.getPosition().getX() == x && food.getPosition().getY() == y)) {
				generateRandomObstacle();
			}
		}
		Obstacle newObstacle = new Obstacle(x, y);
		obstacles.add(newObstacle);
	}

	// Phương thức khởi tạo vị trí ngẫu nhiên cho hộp
	@Override
	public void generateBox() {
		if (shouldGenerateBox) {
			int x = 2 + (int) Math.floor(Math.random() * (cols - 4));
			int y = 2 + (int) Math.floor(Math.random() * (rows - 4));
			for (int i = 0; i < obstacles.size(); i++) {
				if ((obstacles.get(i).getPosition().getX() == x && obstacles.get(i).getPosition().getY() == y)
						|| (obstacles.get(i).getPosition().getX() == x + 1
								&& obstacles.get(i).getPosition().getY() == y)
						|| (obstacles.get(i).getPosition().getX() == x - 1
								&& obstacles.get(i).getPosition().getY() == y)
						|| (obstacles.get(i).getPosition().getX() == x
								&& obstacles.get(i).getPosition().getY() == y + 1)
						|| (obstacles.get(i).getPosition().getX() == x
								&& obstacles.get(i).getPosition().getY() == y - 1)
						|| (food.getPosition().getX() == x && food.getPosition().getY() == y)) {
					generateBox();
					return;
				}
			}
			box.getPosition().setLocation(x, y);
		}
	}

	// Phương thức khởi tạo vị trí ngẫu nhiên cho hộp mục tiêu
	@Override
	public void generateBoxGoal() {
		if (shouldGenerateBox) {
			int x = 2 + (int) Math.floor(Math.random() * (cols - 4));
			int y = 2 + (int) Math.floor(Math.random() * (rows - 4));
			for (int i = 0; i < obstacles.size(); i++) {
				if ((obstacles.get(i).getPosition().getX() == x && obstacles.get(i).getPosition().getY() == y)
						|| (obstacles.get(i).getPosition().getX() == x + 1
								&& obstacles.get(i).getPosition().getY() == y)
						|| (obstacles.get(i).getPosition().getX() == x - 1
								&& obstacles.get(i).getPosition().getY() == y)
						|| (obstacles.get(i).getPosition().getX() == x
								&& obstacles.get(i).getPosition().getY() == y + 1)
						|| (obstacles.get(i).getPosition().getX() == x
								&& obstacles.get(i).getPosition().getY() == y - 1)
						|| (food.getPosition().getX() == x && food.getPosition().getY() == y)
						|| (box.getPosition().getX() == x && box.getPosition().getY() == y)) {
					generateBoxGoal();
					return;
				}
			}
			boxGoal.getPosition().setLocation(x, y);
		}
	}

	// Phương thức khởi tạo các chướng ngại vật tương ứng với từng màn chơi
	@Override
	public void generateObstacle() {
		if (currentLevel == 2) {
			// Phân bổ chướng ngại vật trên cạnh trên và dưới
			for (int x = 0; x < cols; x++) {
				obstacles.add(new Obstacle(x, 0));
				obstacles.add(new Obstacle(x, rows - 1));
			}
			// Phân bổ chướng ngại vật trên cạnh trái và phải
			for (int y = 1; y < rows - 1; y++) {
				obstacles.add(new Obstacle(0, y));
				obstacles.add(new Obstacle(cols - 1, y));
			}
		} else if (currentLevel == 3) {
			for (int i = 7; i < 25; i++) {
				obstacles.add(new Obstacle(i, 0));
			}

			for (int i = 0; i < 14; i++) {
				obstacles.add(new Obstacle(i, 7));
			}

			for (int j = 1; j < 7; j++) {
				obstacles.add(new Obstacle(13, j));
			}

			for (int i = 17; i < cols; i++) {
				obstacles.add(new Obstacle(i, 7));
			}

			for (int i = 0; i < cols; i++) {
				obstacles.add(new Obstacle(i, 11));
			}

			for (int j = 12; j < rows; j++) {
				obstacles.add(new Obstacle(17, j));
			}

		} else if (currentLevel == 4) {
			for (int i = 0; i < cols; i++) {
				obstacles.add(new Obstacle(i, 0));
				obstacles.add(new Obstacle(i, rows - 1));
			}

			for (int i = 1; i < 8; i++) {
				obstacles.add(new Obstacle(0, i));
				obstacles.add(new Obstacle(cols - 1, i));
			}

			for (int i = 12; i < rows; i++) {
				obstacles.add(new Obstacle(0, i));
				obstacles.add(new Obstacle(cols - 1, i));
			}

			for (int i = 9; i < 23; i++) {
				obstacles.add(new Obstacle(i, 6));
				obstacles.add(new Obstacle(i, 13));
			}

		} else if (currentLevel == 5) {
			for (int i = 0; i < cols; i++) {
				obstacles.add(new Obstacle(i, 0));
				obstacles.add(new Obstacle(i, rows - 1));
			}

			for (int i = 0; i < rows; i++) {
				obstacles.add(new Obstacle(0, i));
				obstacles.add(new Obstacle(cols - 1, i));
			}

			for (int i = 0; i < 8; i++) {
				obstacles.add(new Obstacle(i, 14));
			}

			for (int i = 23; i < cols; i++) {
				obstacles.add(new Obstacle(i, 5));
			}

			for (int j = 0; j < 10; j++) {
				obstacles.add(new Obstacle(8, j));
			}

			for (int j = 10; j < rows; j++) {
				obstacles.add(new Obstacle(23, j));
			}
		}
	}

	@Override
	public void nextStep() {
		// Di chuyển rắn
		Direction currentDirection = null;
		if (isPlaying) {
			currentDirection = snake.getCurrentDirection();
			snake.move(currentDirection);
		}

		// Xử lý khi rắn va chạm với chính nó hoặc va chạm với vật cản
		if (snake.collidesWithSelf() || snake.collidesWithObstacle(getCells())) {
			isPlaying = false;
			snakeDies();
			bonusFood.getPosition().setLocation(-2, -2);
			setShowBonusProgressBar(false);
		}

		// Kiểm tra va chạm với mồi
		if (snake.collidesWithFood(food)) {
			snake.grow(); // Rắn ăn mồi và dài thêm
			incrementScore();
			foodCount++;
			if (foodCount % foodScore == 0) {
				generateBonusFood();
				setShowBonusProgressBar(true);
			}
			if (foodCount * foodScore == 100) {
				shouldGenerateFood = false;
				shouldGenerateNextLevelFood = true;
				generateNextLevelFood();
			}
			if (currentLevel == 4) {
				generateRandomObstacle();
			}
			if (currentLevel == 5) {
				shouldGenerateFood = false;
				if (shouldGenerateNextLevelFood) {
					shouldGenerateBox = false;
				} else {
					shouldGenerateBox = true;
					generateBox();
					generateBoxGoal();
				}
			}
			generateFood();
		}
		// Kiểm tra va chạm với mồi bonus
		if (snake.collidesWithBonusFood(bonusFood)) {
			currentScore += bonusScore;
			resetBonusFood();
			setShowBonusProgressBar(false);
		}
		// Kiểm tra va chạm với mồi qua màn
		if (snake.collidesWithNextLevelFood(nextLevelFood)) {
			saveScore();
			nextLevelFood.getPosition().setLocation(-2, -2);
			bonusFood.getPosition().setLocation(-2, -2);
			hasEatenNextLevelFood = true;
			setShowBonusProgressBar(false);
			isPlaying = false;
		}
		// Kiểm tra va chạm với hộp
		if (snake.collidesWithBox(box)) {
			int boxX = (int) box.getPosition().getX();
			int boxY = (int) box.getPosition().getY();
			if (currentDirection == Direction.RIGHT) {
				boxX++;
			} else if (currentDirection == Direction.LEFT) {
				boxX--;
			} else if (currentDirection == Direction.UP) {
				boxY--;
			} else if (currentDirection == Direction.DOWN) {
				boxY++;
			}
			box.getPosition().setLocation(boxX, boxY);
		}
		// Kiểm tra va chạm giữa hộp với chướng ngại vật
		for (int i = 0; i < obstacles.size(); i++) {
			if (box.collidesWithObstacles(obstacles.get(i))) {
				generateBox();
			}
		}
		// Kiểm tra va chạm giữa hộp với hộp mục tiêu
		if (box.collidesWithBoxGoal(boxGoal) && shouldGenerateBox) {
			box.getPosition().setLocation(-1, -1);
			boxGoal.getPosition().setLocation(-1, -1);
			shouldGenerateBox = false;
			shouldGenerateFood = true;
			generateFood();
		}
	}

	@Override
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<>();
		List<Point> snakeBody = snake.getBody();
		Point foodPosition = food.getPosition();
		Point bonusPosition = bonusFood.getPosition();
		Point nextLevelPosition = nextLevelFood.getPosition();
		Point boxPosition = box.getPosition();
		Point boxGoalPosition = boxGoal.getPosition();

		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				CellType type = CellType.EMPTY;

				// Kiểm tra xem ô hiện tại có phải là rắn không
				for (Point point : snakeBody) {
					if (point.getX() == x && point.getY() == y) {
						type = CellType.SNAKE;
						break;
					}
				}

				// Kiểm tra xem ô hiện tại có phải là thức ăn không
				if (foodPosition.getX() == x && foodPosition.getY() == y) {
					type = CellType.FOOD;
				}

				// Kiểm tra xem ô hiện tại có phải là thức ăn bonus không
				if (bonusPosition.getX() == x && bonusPosition.getY() == y) {
					type = CellType.BONUS;
				}
				// Kiểm tra xem ô hiện tại có phải là thức ăn qua màn không
				if (nextLevelPosition.getX() == x && nextLevelPosition.getY() == y) {
					type = CellType.NEXT_LEVEL;
				}

				// Kiểm tra xem ô hiện tại có phải là hộp không
				if (boxPosition.getX() == x && boxPosition.getY() == y) {
					type = CellType.BOX;
				}

				// Kiểm tra xem ô hiện tại có phải là hộp mục tiêu không
				if (boxGoalPosition.getX() == x && boxGoalPosition.getY() == y) {
					type = CellType.BOX_GOAL;
				}

				// Kiểm tra xem ô hiện tại có phải là chướng ngại vật không
				if (obstacles != null) {
					for (Obstacle obsPoint : obstacles) {
						if (obsPoint.getPosition().getX() == x && obsPoint.getPosition().getY() == y) {
							type = CellType.OBSTACLE;
							break;
						}
					}
				}

				// Tạo và thêm ô vào danh sách
				cells.add(new Cell(x, y, type));
			}
		}
		return cells;
	}

	// Phương thức giảm thời gian tồn tại của bonus đi một đơn vị
	@Override
	public int decreaseBonusTime() {
		return --bonusTime;
	}

	@Override
	public int getInitialBonusTime() {
		return BONUS_TIME;
	}

	// Phương thức tăng điểm hiện tại, với giá trị cộng vào là số điểm tương ứng của
	// mồi
	public int incrementScore() {
		currentScore += foodScore;
		return currentScore;
	}

	// Phương thức giảm điểm bonus theo một lượng nhất định
	@Override
	public void decreaseBonusScore(int amount) {
		bonusScore -= amount;
		if (bonusScore < 0) {
			bonusScore = 0;
		}
	}

	// Phương thức giảm số lượng mạng hiện tại đi một đơn vị
	public int decrementLive() {
		currentLive -= 1;
		return currentLive;
	}

	// Phương thức đặt lại điểm bonus về giá trị mặc định, di chuyển mồi bonus ra
	// khỏi phạm vi và đặt lại thời gian bonus về giá trị ban đầu
	@Override
	public void resetBonusFood() {
		bonusScore = BONUS_SCORE;
		bonusFood.getPosition().setLocation(-2, -2);
		setBonusTime(getInitialBonusTime());
	}

	@Override
	// Phương thức lưu điểm số hiện tại
	public void saveScore() {
		this.savedScore = this.currentScore;
	}

	@Override
	// Phương thức khôi phục điểm số khi rắn chết mà còn mạng
	public void resetScore() {
		this.currentScore = this.savedScore;
	}

	// Phương thức xử lý khi rắn chết
	public void snakeDies() {
		decrementLive();
		if (currentLive != 0) {
			resetScore();
		}

		snake.getBody().removeAll(snake.getBody());
		snake = new Snake(cols / 2, (rows / 2) - 1);
		obstacles.removeAll(obstacles);
		generateObstacle();
		initializeFood();
		setProgressBarValue(0);
		foodCount = 0;
		shouldGenerateNextLevelFood = false;
		nextLevelFood.getPosition().setLocation(-1, -1);
		if (currentLevel == 5 && currentLive != 0) {
			shouldGenerateBox = true;
			generateBox();
			generateBoxGoal();
		}
	}

	// Phương thức đặt lại khi vượt qua màn
	@Override
	public void resetPassLevel() {
		shouldGenerateFood = true;
		shouldGenerateNextLevelFood = false;
		hasEatenNextLevelFood = false;
		snake.getBody().removeAll(snake.getBody());
		snake = new Snake(cols / 2, (rows / 2) - 1);
		currentLevel++;
		obstacles.removeAll(obstacles);
		generateObstacle();
		initializeFood();
		setProgressBarValue(0);
		foodCount = 0;
		if (currentLevel == 5) {
			initialzeBox();
		}
	}

	// Phương thức đặt lại trò chơi
	@Override
	public void resetGame() {
		shouldGenerateFood = true;
		shouldGenerateNextLevelFood = false;
		hasEatenNextLevelFood = false;
		shouldGenerateBox = false;
		snakeDies();
		setCurrentLevel(1);
		setCurrentScore(0);
		setCurrentLive(3);
		obstacles.removeAll(obstacles);
		generateObstacle();
		initializeFood();
		setProgressBarValue(0);
		foodCount = 0;
		nextLevelFood = new NextLevelFood(-2, -2);
		box.getPosition().setLocation(-1, -1);
		boxGoal.getPosition().setLocation(-1, -1);
	}

	// Phương thức đặt lại trờ chơi khi ở chế độ chọn cấp độ
	@Override
	public void resetGameLevelMode(int level) {
		shouldGenerateFood = true;
		shouldGenerateNextLevelFood = false;
		hasEatenNextLevelFood = false;
		shouldGenerateBox = false;
		setSavedScore(0);
		snakeDies();
		setCurrentLevel(level);
		setCurrentScore(0);
		setCurrentLive(3);
		obstacles.removeAll(obstacles);
		generateObstacle();
		initializeFood();
		setProgressBarValue(0);
		foodCount = 0;
		nextLevelFood = new NextLevelFood(-2, -2);
		box.getPosition().setLocation(-1, -1);
		boxGoal.getPosition().setLocation(-1, -1);
		if (currentLevel == 5) {
			initialzeBox();
		}
	}

}