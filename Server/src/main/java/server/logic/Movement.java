package server.logic;

public class Movement {
	private CellState figure;
	private int row;
	private int col;
	
	public Movement(CellState figure, int row, int col) {
		this.figure = figure;
		this.row = row;
		this.col = col;
	}
	
	public CellState getFigure() {
		return figure;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public String toString() {
		return figure + " " + row + " " + col;
	}
	
}
