package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import main.GameFrame;
import model.ISnakeModel;
import view.DialogView;
import view.EastPanel;
import view.SnakeView;
import view.SouthPanel;
import view.WestPanel;

public class GameController implements IGameController, ActionListener {
	private ISnakeModel model;
	private SnakeView view;
	private SouthPanel southPanel;
	private EastPanel eastPanel;
	private WestPanel westPanel;
	private DialogView dialogView;
	private GameFrame gameFrame;
	private Timer timer;

	public GameController(ISnakeModel model, SnakeView view, SouthPanel southPanel, EastPanel eastPanel,
			WestPanel westPanel, DialogView dialogView, GameFrame gameFrame) {
		this.model = model;
		this.view = view;
		this.southPanel = southPanel;
		this.eastPanel = eastPanel;
		this.westPanel = westPanel;
		this.dialogView = dialogView;
		this.gameFrame = gameFrame;
		timer = new Timer(200, this);
		initialViewListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateGame();
	}

	@Override
	public void startGame() {
		// Bắt đầu chạy trò chơi bằng cách kích hoạt Timer
		timer.start();
	}

	@Override
	public void stopGame() {
		// Dừng trò chơi
		if (timer != null)
			timer.stop();
	}

	@Override
	public void updateGame() {
		model.nextStep();
		updatePanels();
		checkGameStatus();
		view.update();
	}

	private void updatePanels() {
		southPanel.setScoreLabel(model.getCurrentScore());
		southPanel.updateProgressBar(model.getFoodScore(), model.getFoodCount());
		southPanel.showBonusProgressBar(model.isShowBonusProgressBar());
		eastPanel.setLiveLabel(model.getCurrentLive());
		westPanel.updatePanelForPlayMode(model.isPlaying());
	}

	// Kiểm tra trạng thái hiện tại của trò chơi.
	private void checkGameStatus() {
		if (!model.isPlaying()) {
			stopGame();
		}
		// Kiểm tra nếu rắn đã ăn thức ăn để lên cấp
		if (model.isHasEatenNextLevelFood()) {
			dialogView.showPassLevelDialog(model.getCurrentLevel(), model.getCurrentScore(), model.isLevelMode());
		}
		if (model.getCurrentLive() == 0) {
			dialogView.showGameOverDialog(model.getCurrentLevel(), model.getCurrentScore(), model.isLevelMode());
		}
	}

	// Khởi tạo các bộ lắng nghe sự kiện (listeners) cho các nút trong dialogView, westPanel và view (SnakeView).
	public void initialViewListeners() {
		dialogView.addCountinueButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.resetPassLevel();
				updateGame();
				dialogView.hideDialog();
			}
		});

		dialogView.addNewGameButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.resetGame();
				updateGame();
				dialogView.hideDialog();
			}
		});

		dialogView.addRetryButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.resetGameLevelMode(model.getCurrentLevel());
				updateGame();
				dialogView.hideDialog();
			}
		});
		dialogView.addHomeButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialogView.hideDialog();
				gameFrame.showMainScreen();
			}
		});
		westPanel.addHomeButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showMainScreen();

			}
		});
		westPanel.addPlayButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				toogleGamePlay();
			}
		});
		view.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				handleKeyPress(e);
			}
		});
	}

	private void toogleGamePlay() {
		boolean isPlaying = model.isPlaying();
		model.setPlaying(!isPlaying);
		if (isPlaying) {
			stopGame();
		} else {
			startGame();
		}
		westPanel.updatePanelForPlayMode(!isPlaying);
	}

	private void handleKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_P) {
			model.setPlaying(!model.isPlaying());
			stopGame();
			if (model.isPlaying()) {
				startGame();
				westPanel.updatePanelForPlayMode(true);
			} else {
				westPanel.updatePanelForPlayMode(false);
			}
		}
		if (!model.isPlaying()) {
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT
					|| e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT) {
				model.setPlaying(!model.isPlaying());
				startGame();
				westPanel.updatePanelForPlayMode(true);
			}
		}
	}
}
