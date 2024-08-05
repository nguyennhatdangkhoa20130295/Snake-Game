package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class WestPanel extends JPanel {
	private JButton playButton;
	private JButton homeButton;
	private ImageIcon playIcon;
	private ImageIcon homeIcon;

	public WestPanel() {
		setOpaque(false);
		setPreferredSize(new Dimension(230, 720));
		setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 10));

		playIcon = new ImageIcon("./src/images/play-button.png");
		Image playImage = playIcon.getImage();
		Image newPlayImage = playImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newPlayImage);
		EmptyBorder border = new EmptyBorder(-1, -1, -1, -1);
		playButton = new JButton(newIcon);
		playButton.setFocusPainted(false);
		playButton.setFocusable(false);
		playButton.setContentAreaFilled(false);
		playButton.setBorder(border);

		homeIcon = new ImageIcon("./src/images/home-icon.png");
		Image homeImage = homeIcon.getImage();
		Image newHomeImage = homeImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon newHomeIcon = new ImageIcon(newHomeImage);
		homeButton = new JButton(newHomeIcon);
		homeButton.setFocusPainted(false);
		homeButton.setFocusable(false);
		homeButton.setContentAreaFilled(false);
		homeButton.setBorder(border);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new GridLayout(2, 1, 0, 10));
		buttonPanel.add(playButton);
		buttonPanel.add(homeButton);

		add(buttonPanel);

	}

	public void addPlayButtonListener(ActionListener playAction) {
		playButton.addActionListener(playAction);
	}

	public void addHomeButtonListener(ActionListener homeAction) {
		homeButton.addActionListener(homeAction);
	}

	public void updatePanelForPlayMode(boolean isPlaying) {
		if (isPlaying) {
			playIcon = new ImageIcon("./src/images/pause-button.png");
		} else {
			playIcon = new ImageIcon("./src/images/play-button.png");
		}
		Image img = playIcon.getImage();
		Image newImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImg);
		playButton.setIcon(newIcon);
	}
}
