import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Grid{
	private static Grid game = new Grid();
	private static final int ROW_SIZE = 8;
	private static final int COLUMN_SIZE = 8;
	private static final int MAP_SIZE = Math.round((float) (ROW_SIZE * COLUMN_SIZE * 0.65));
	private static final int MAX_TREASURE = 5;
	private Cell[][] board = new Cell[ROW_SIZE][COLUMN_SIZE];
	private ArrayList<Cell> walkwayCells = new ArrayList<Cell>();
	private int startRow;
	private int startColumn;
	private int currentMapSize = 0;
	private int treasureCount = 0;
	
	private Grid() {
		super();
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
		int treasureCount = 0;
		currentMapSize = 1;
		int direction = 0;
		startRow = rowNumber;
		startColumn = columnNumber;
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
				selectTreasure(rowNumber, columnNumber);
				break;
			case 1:
				columnNumber++;
				if(columnNumber >= COLUMN_SIZE) {
					columnNumber = COLUMN_SIZE - 1;
					continue;
				}
				setWalkway(rowNumber, columnNumber);
				selectTreasure(rowNumber, columnNumber);
				break;
			case 2:
				rowNumber++;
				if(rowNumber >= ROW_SIZE) {
					rowNumber = ROW_SIZE - 1;
					continue;
				}
				setWalkway(rowNumber, columnNumber);
				selectTreasure(rowNumber, columnNumber);
				break;
			case 3:
				columnNumber--;
				if(columnNumber < 0) {
					columnNumber = 0;
					continue;
				}
				setWalkway(rowNumber, columnNumber);
				selectTreasure(rowNumber, columnNumber);
				break;
			default:
				break;
			}
		}
		if(treasureCount == 0) {
			walkwayCells.get(random.nextInt(walkwayCells.size())).setCellType(CellType.TREASURE);
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
	
	private void selectTreasure(int rowNumber, int columnNumber) {
		if(treasureCount >= MAX_TREASURE) {
			return;
		}
		Random random = new Random();
		int result = random.nextInt(101);
		if(result % 2 == 0 && result % 3 == 0) {
			board[rowNumber][columnNumber].setCellType(CellType.TREASURE);
			treasureCount++;
		}
	}
	
	public static Grid getGame() {
		return game;
	}
	
	public Cell[][] getBoard(){
		return board;
	}
	public static void main(String[] args) {
		Grid game = Grid.getGame();
		game.initialize();
		Cell[][] board = game.getBoard();
		for(Cell[] row : board) {
			for(Cell cell : row) {
				if(cell.getCellType() == CellType.WALKWAY) {
					System.out.print("[1]");
				}
				else if(cell.getCellType() == CellType.TREASURE) {
					System.out.print("[2]");
				}
				else {
					System.out.print("[0]");
				}
			}
			System.out.println();
		}
	}
}
