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
}
