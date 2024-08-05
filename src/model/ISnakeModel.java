package model;

import java.util.List;

import observer.Observer;

public interface ISnakeModel {
	Snake getSnake();

	List<Cell> getCells();

	void registerObserver(Observer o);

	void removeObserver(Observer o);

	void notifyObservers();

	void initializeFood();

	void generateFood();

	void generateBonusFood();

	void generateNextLevelFood();

	void generateRandomObstacle();

	void generateBox();

	void generateBoxGoal();

	void generateObstacle();

	void nextStep();

	void resetPassLevel();

	void resetGame();

	void resetBonusFood();

	void saveScore();

	void resetScore();

	int getCurrentScore();

	int getFoodScore();

	int getFoodCount();

	int getCurrentLive();

	int getCurrentLevel();

	int getProgressBarValue();

	int getInitialBonusTime();

	int decreaseBonusTime();

	int getCols();

	int getRows();

	boolean isShowBonusProgressBar();

	boolean isHasEatenNextLevelFood();

	boolean isPlaying();

	boolean isLevelMode();

	void setPlaying(boolean isPlaying);

	void setShowBonusProgressBar(boolean isShowBonusProgressBar);

	void setProgressBarValue(int progressBarValue);

	void decreaseBonusScore(int amount);

	void setCurrentLevel(int currentLevel);

	void resetGameLevelMode(int level);
}
