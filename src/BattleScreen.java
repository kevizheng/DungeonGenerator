import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BattleScreen extends JPanel{
	
	Player player;
	Monster monster;
	JLabel playerIcon = new JLabel();
	JLabel monsterIcon = new JLabel();
	JLabel playerHealthBar = new JLabel();
	JLabel monsterHealthBar = new JLabel();
	String filePath = "C:/JavaProjects/DungeonGenerator/Images/";
	
	public BattleScreen(Player player, Monster monster){
		super();
		this.player = player;
		this.monster = monster;
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon background = new ImageIcon(filePath + "background.png");
		Image backgroundImage = background.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
		background = new ImageIcon(backgroundImage, background.getDescription());
		g.drawImage(background.getImage(), 0, 0, null);
		this.setLayout(new GridLayout(2,2));
		try {
			
			playerIcon.setSize(new Dimension(getWidth()/2, getHeight()/2));
			monsterIcon.setSize(new Dimension(getWidth()/2, getHeight()/2));
			
			File file = new File(filePath + "player.png");
			Image icon = ImageIO.read(file);
			icon = icon.getScaledInstance(playerIcon.getWidth(), playerIcon.getHeight(), Image.SCALE_SMOOTH);
			ImageIcon image = new ImageIcon(icon);
			
			playerIcon.setIcon(image);
			
			file = new File(filePath + "monster.png");
			icon = ImageIO.read(file);
			icon = icon.getScaledInstance(playerIcon.getWidth(), playerIcon.getHeight(), Image.SCALE_SMOOTH);
			image = new ImageIcon(icon);
			
			monsterIcon.setIcon(image);
			
			playerHealthBar.setText(this.player.getCurrentHP() + "/" + this.player.getMaxHP());
			playerHealthBar.setForeground(Color.GREEN);
			monsterHealthBar.setText(this.monster.getCurrentHP() + "/" + this.monster.getMaxHP());
			monsterHealthBar.setForeground(Color.GREEN);
			add(monsterHealthBar);
			add(monsterIcon);
			add(playerIcon);
			add(playerHealthBar);
			setVisible(true);
		}
		catch(IOException io) {
			System.out.println(io);
		}
		revalidate();
		repaint();
	}
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Player player = new Player();
		Goblin goblin = new Goblin();
		BattleScreen screen = new BattleScreen(player, goblin);
		frame.add(screen);
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


