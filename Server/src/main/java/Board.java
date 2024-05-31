import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Board {
    private CellState[][] board;
    private final int BOARD_ROWS = 3, BOARD_COLS = 3;
    
    public Board() { 
        board = new CellState[BOARD_ROWS][BOARD_COLS]; 
        
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                board[i][j] = CellState.EMPTY;
            }
        }
    }

    public boolean hasEnded() {
    	return checkWin() || isBoardFull();
    }
    
	public void makeMove(int i, int j, CellState cellState) {
		this.board[i][j]= cellState;
	}

    // Método para obtener el estado actual del tablero
    public String getBoardAsJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(board);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private boolean checkRows() {
		for (int row = 0; row < BOARD_ROWS; row++) {
			if (board[row][0] != CellState.EMPTY && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
				return true;
			}
		}
		return false;
	}

    private boolean checkCols() {
		for (int col = 0; col < BOARD_COLS; col++) {
			if (board[0][col] != CellState.EMPTY && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
				return true;
			}
		}
		return false;
	}

    private boolean checkDiagonals() {
		return checkFirstDiagonal() || checkSecondDiagonal();
	}

    private boolean checkSecondDiagonal() {
		return board[0][2] != CellState.EMPTY && board[0][2] == board[1][1] && board[1][1] == board[2][0];
	}

    private boolean checkFirstDiagonal() {
		return board[0][0] != CellState.EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2];
	}

	public boolean checkWin() {
		return checkRows() || checkCols() || checkDiagonals();
	}
	
	private boolean isBoardFull() {
		for (int row = 0; row < BOARD_ROWS; row++) {
			for (int col = 0; col < BOARD_COLS; col++) {
				if (board[row][col] == CellState.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}


}
