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
	
	public void draw(int cellWidth, int cellHeight, int xOffset, int yOffset, Graphics g) {
		g.drawRect(xOffset, yOffset , cellWidth, cellHeight);
		if(cellType == CellType.UNUSED) {
			g.setColor(Color.BLACK);
		}
		else {
			g.setColor(Color.YELLOW);
		}
		g.fillRect(xOffset, yOffset, cellWidth, cellHeight);
			
	}
}
