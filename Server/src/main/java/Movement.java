
public class Movement {
	private String userName;
	private CellState figure;
	private int row;
	private int col;
	
	public Movement(String userName, CellState figure, int row, int col) {
		this.userName = userName;
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
		return userName + " "+ figure + " " + row + " " + col;
	}
	
}
