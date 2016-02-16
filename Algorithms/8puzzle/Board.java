import java.util.Arrays;

public class Board {
	private int[][] blocks;
	private int n;

	public Board(int[][] initialBlocks) {
		n = initialBlocks.length;
		this.blocks =  copy(initialBlocks);
				
				
	}

	private int[][] copy(int[][] initial) {
		int[][] copy = new int[n][n];
		for (int i = 0; i < n-1; i++) {
			for (int j = 0; j < n-1; j++) {
				copy[n][n] = initial[n][n];
			}
		}
		return copy;
	} 

	public int dimension() {
		return n;
	} // board dimension N

	public int hamming() {
	} // number of blocks out of place

	public int manhattan() {
	} // sum of Manhattan distances between blocks and goal

	public boolean isGoal() {
		for (int i = 0; i < n-1; i++) {
			for (int j = 0; j < n-1; j++) {
				int expectedValue = (i*n)+j+1;
				if (blocks[i][j] != expectedValue){
					return ((i==n-1) && (j==n-1) && blocks[i][j]==0);
				}
			}
		}
		return true;
		
		
	} // is this board the goal board?

	public Board twin() {
		Board twinBoard = new Board(blocks);
		int[][] twinBlocks = twinBoard.blocks;
		if (twinBlocks[0][0]==0 || twinBlocks[1][0]==0){
			int tmp = twinBlocks[0][1];
			twinBlocks[0][1]=twinBlocks[1][1];
			twinBlocks[1][1]=tmp;
		} else {
			int tmp = twinBlocks[0][0];
			twinBlocks[0][0]=twinBlocks[1][0];
			twinBlocks[1][0]=tmp;
		}
		return twinBoard;
		
		
	} // a board that is obtained by exchanging any pair of blocks

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.deepEquals(blocks, other.blocks))
			return false;
		if (n != other.n)
			return false;
		return true;
	}

	public Iterable<Board> neighbors() {
	} // all neighboring boards

	public String toString() {
	} // string representation of this board (in the output format specified
		// below){}

	public static void main(String[] args) {
	} // unit tests (not graded){}
}
