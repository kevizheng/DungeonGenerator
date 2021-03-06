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


// The graphic that will display the player, monster, and both of their HP
public class BattleScreen extends JPanel{
	
	private Player player;
	private Monster monster;
	private JLabel playerIcon = new JLabel();
	private JLabel monsterIcon = new JLabel();
	private JLabel playerHealthBar = new JLabel();
	private JLabel monsterHealthBar = new JLabel();
	private String filePath = "Images/";
	
	public BattleScreen(Player player, Monster monster){
		super();
		this.player = player;
		this.monster = monster;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Get the background image and have it dynamically size itself with the window
		ImageIcon background = new ImageIcon(filePath + "background.png");
		Image backgroundImage = background.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
		background = new ImageIcon(backgroundImage, background.getDescription());
		g.drawImage(background.getImage(), 0, 0, null);
		this.setLayout(new GridLayout(2,2));
		// Get the player image and monster image and add them to the JPanel and add their health bars
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
}


