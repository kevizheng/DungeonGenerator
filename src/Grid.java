import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Grid extends JPanel implements KeyListener{
	private static Grid game = new Grid();
	private static final int ROW_SIZE = 8;
	private static final int COLUMN_SIZE = 8;
	private static final int MAP_SIZE = Math.round((float) (ROW_SIZE * COLUMN_SIZE * 0.65));
	private static final int MAX_TREASURE = 5;
	private static final int MAX_MONSTERS = 5;
	
	private Cell[][] board = new Cell[ROW_SIZE][COLUMN_SIZE];
	private ArrayList<Monster> potentialMonsters = new ArrayList<Monster>();
	private ArrayList<Cell> walkwayCells = new ArrayList<Cell>();
	private ArrayList<Cell> treasureCells = new ArrayList<Cell>();
	private ArrayList<Cell> monsterCells = new ArrayList<Cell>();
	private Cell exitCell;
	private int startRow;
	private int startColumn;
	private int currentMapSize = 0;
	private Player player = new Player();
	private InfoPanel info;
	
	private Grid() {
		super();
		addKeyListener(this);
		setFocusable(true);
		potentialMonsters.add(new Goblin());
		potentialMonsters.add(new Zombie());
	}
	
	public void initialize() {
		for(int i = 0; i < ROW_SIZE; i++) {
			for(int j = 0; j < COLUMN_SIZE; j++) {
				Cell temp = new Cell(i, j, CellType.UNUSED);
				board[i][j] = temp;
			}
		}
		createMap();
	}
	
	private void createMap() {
		Random random = new Random();
		int rowNumber = random.nextInt(ROW_SIZE);
		int columnNumber = random.nextInt(COLUMN_SIZE);
		currentMapSize = 1;
		int direction = 0;
		startRow = rowNumber;
		startColumn = columnNumber;
		player.setRow(startRow);
		player.setColumn(startColumn);
		board[rowNumber][columnNumber].setCellType(CellType.WALKWAY);
		while(currentMapSize < MAP_SIZE) {
			// 0 = up, 1 = right, 2 = down, 3 = left
			direction = random.nextInt(4);
			switch(direction) {
			case 0:
				rowNumber--;
				if(rowNumber < 0) {
					rowNumber = 0;
					continue;
				}
				setWalkway(rowNumber, columnNumber);
				break;
			case 1:
				columnNumber++;
				if(columnNumber >= COLUMN_SIZE) {
					columnNumber = COLUMN_SIZE - 1;
					continue;
				}
				setWalkway(rowNumber, columnNumber);
				break;
			case 2:
				rowNumber++;
				if(rowNumber >= ROW_SIZE) {
					rowNumber = ROW_SIZE - 1;
					continue;
				}
				setWalkway(rowNumber, columnNumber);
				break;
			case 3:
				columnNumber--;
				if(columnNumber < 0) {
					columnNumber = 0;
					continue;
				}
				setWalkway(rowNumber, columnNumber);
				break;
			default:
				break;
			}
		}
		for(int i = 0; i < MAX_TREASURE; i++) {
			Cell potentialTreasure = walkwayCells.get(random.nextInt(walkwayCells.size()));
			selectSpecialCell(potentialTreasure, CellType.TREASURE);
		}
		for(int i = 0; i < MAX_MONSTERS; i++) {
			Cell potentialMonster = walkwayCells.get(random.nextInt(walkwayCells.size()));
			selectSpecialCell(potentialMonster, CellType.MONSTER);
		}
		while(true) {
			int selectCell = random.nextInt(walkwayCells.size());
			Cell potentialExit = walkwayCells.get(selectCell);
			int potentialRow = potentialExit.getRow();
			int potentialColumn = potentialExit.getColumn();
			if(potentialRow != startRow && potentialColumn != startColumn && potentialExit.getCellType() == CellType.WALKWAY) {
				potentialExit.setCellType(CellType.EXIT);
				exitCell = potentialExit;
				break;
			}
		}
	}
	private void setWalkway(int rowNumber, int columnNumber) {
		if(board[rowNumber][columnNumber].getCellType() == CellType.WALKWAY) {
			return;
		}
		board[rowNumber][columnNumber].setCellType(CellType.WALKWAY);
		walkwayCells.add(board[rowNumber][columnNumber]);
		currentMapSize++;
	}
	
	private void selectSpecialCell(Cell cell, CellType type) {
		Random random = new Random();
		int select = random.nextInt(101);
		if(cell.getCellType() == CellType.WALKWAY && cell.getRow() != startRow && cell.getColumn() != startColumn) {
			if(select % 2 == 0 && select % 4 == 0) {
				cell.setCellType(type);
				if(type == CellType.TREASURE) {
					treasureCells.add(cell);
				}
				else if (type == CellType.MONSTER){
					monsterCells.add(cell);
				}
			}
		}
	}
	
	public static Grid getGame() {
		return game;
	}
	
	public Cell[][] getBoard(){
		return board;
	}
	
	public int getStartRow() {
		return startRow;
	}

	public int getStartColumn() {
		return startColumn;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int cellWidth = this.getWidth() / COLUMN_SIZE;
		int cellHeight = this.getHeight() / ROW_SIZE;
		for(int i = 0; i < ROW_SIZE; i++) {
			for(int j = 0; j < COLUMN_SIZE; j++) {
				board[i][j].draw(cellWidth, cellHeight, j * cellWidth, i * cellHeight, g);
			}
		}
		player.draw(cellWidth, cellHeight, player.getColumn() * cellWidth, player.getRow() * cellHeight, g);
		
	}
	
	public Player getPlayer() {
		return player;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyChar()) {
		case 'w':
			if(checkBounds(Direction.UP)) {
				player.movePlayer(Direction.UP);
			}
			break;
		case 'a':
			if(checkBounds(Direction.LEFT)) {
				player.movePlayer(Direction.LEFT);
			}
			break;
		case 's':
			if(checkBounds(Direction.DOWN)) {
				player.movePlayer(Direction.DOWN);
			}
			break;
		case 'd':
			if(checkBounds(Direction.RIGHT)) {
				player.movePlayer(Direction.RIGHT);
			}
			break;
		default:
			break;
		}
		treasureCheck();
		monsterCheck();
		exitCheck();
		info.initialize();
		revalidate();
		repaint();
	}
	
	private boolean checkBounds(Direction direction) {
		int newColumn = 0;
		int newRow = 0;
		switch(direction) {
		case UP:
			newRow = player.getRow() - 1;
			if(newRow < 0 || board[newRow][player.getColumn()].getCellType() == CellType.UNUSED) {
				return false;
			}
			return true;
		case LEFT:
			newColumn = player.getColumn() - 1;
			if(newColumn < 0 || board[player.getRow()][newColumn].getCellType() == CellType.UNUSED) {
				return false;
			}
			return true;
		case RIGHT:
			newColumn = player.getColumn() + 1;
			if(newColumn > COLUMN_SIZE - 1 || board[player.getRow()][newColumn].getCellType() == CellType.UNUSED) {
				return false;
			}
			return true;
		case DOWN:
			newRow = player.getRow() + 1;
			if(newRow > ROW_SIZE - 1 || board[newRow][player.getColumn()].getCellType() == CellType.UNUSED) {
				return false;
			}
			return true;
		default:
			return false;
		}
	}
	private void treasureCheck() {
		for(Cell treasure : treasureCells) {
			if(checkLocation(treasure, player)) {
				treasure.setCellType(CellType.WALKWAY);
				treasureCells.remove(treasure);
				player.addTreasureMoney();
				repaint();
				break;
			}
		}
	}
	private void monsterCheck() {
		for(Cell monster : monsterCells) {
			if(checkLocation(monster, player)) {
				monster.setCellType(CellType.WALKWAY);
				monsterCells.remove(monster);
				BattleWindow window = new BattleWindow(player, potentialMonsters.get(0));
				window.setGrid(this);
				this.setEnabled(false);
				repaint();
				break;
			}
		}
		if(player.getCurrentHP() <= 0) {
			gameOver();
		}
	}
	
	private void exitCheck() {
		revalidate();
		repaint();
		if(checkLocation(exitCell, player)) {
			Object selectedValue = JOptionPane.showConfirmDialog(null, "Would you like to continue to the next floor?", "Delve Further?", JOptionPane.YES_NO_OPTION);
			if((Integer) selectedValue == JOptionPane.YES_OPTION) {
				this.removeAll();
				walkwayCells.clear();
				treasureCells.clear();
				monsterCells.clear();
				initialize();
				revalidate();
				repaint();
			}
		}
	}
	
	private boolean checkLocation(Cell cell, Player player) {
		return(player.getColumn() == cell.getColumn() && player.getRow() == cell.getRow());
	}
	
	public void setInfoPanel(InfoPanel info) {
		this.info = info;
	}
	
	private void gameOver() {
		JOptionPane.showMessageDialog(null, "You have lost all your HP and died.", "Game Over.", JOptionPane.INFORMATION_MESSAGE);
		this.setEnabled(false);
	}
}
