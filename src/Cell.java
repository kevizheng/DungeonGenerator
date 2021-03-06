import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

public class Cell {
	int row, column;
	private Set<Cell> adjacentCells;
	private CellType cellType;
	
	public Cell(int row, int column, CellType cellType) {
		this.row = row;
		this.column = column;
		this.cellType = cellType;
	}
	
	public void addAdjacency(Cell cell) {
		adjacentCells.add(cell);
	}
	
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}
	
	public CellType getCellType() {
		return cellType;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	// Color the cell based on what type of cell it is
	public void draw(int cellWidth, int cellHeight, int xOffset, int yOffset, Graphics g) {
		switch(cellType) {
		case UNUSED:
			g.setColor(Color.BLACK);
			break;
		case WALKWAY:
			g.setColor(Color.YELLOW);
			break;
		case TREASURE:
			g.setColor(Color.GREEN);
			break;
		case MONSTER:
			g.setColor(Color.RED);
			break;
		case EXIT:
			g.setColor(Color.WHITE);
			break;
		}
		g.fillRect(xOffset, yOffset, cellWidth, cellHeight);
			
	}
}
