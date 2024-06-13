package server.logic;

public enum CellState {
	EMPTY {
		@Override
		public String toString() {
			return "-";
		}
	},
	CROSS {
		@Override
		public String toString() {
			return "x";
		}
	},
	CIRCLE {
		@Override
		public String toString() {
			return "o";
		}
	}
}
