package Part2;

import java.util.ArrayList;

public class State {
	int[][] data;
	int blankRow;
	int blankCol;
	int cost;
	int h;
	State parent;
	public static HeuMethod heu = HeuMethod.MAN;
	
	public static int[] correct = {0,1,2,3,4,5,6,7,8};
	
	public State(){
		data = new int[3][3];
		randomInit();
		cost = 0;
		if (heu == HeuMethod.GAS) {
			h = gas();
		} else if (heu == HeuMethod.MIS) {
			h = misplace();
		} else if (heu == HeuMethod.MAN) {
			h = manhattan();
		}
		parent = null;
	}
	
	public State(int[][] data) {
		this.data = data;
		SearchBlank:for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (data[i][j] == 0) {
					blankRow = i;
					blankCol = j;
					break SearchBlank;
				}
			}
		}
		
		cost = 0;
		if (heu == HeuMethod.GAS) {
			h = gas();
		} else if (heu == HeuMethod.MIS) {
			h = misplace();
		} else if (heu == HeuMethod.MAN) {
			h = manhattan();
		}
		parent = null;
	}
	
	public State(int[][] data, int blankRow, int blankCol, State parent){
		this.data = data;
		this.blankRow = blankRow;
		this.blankCol = blankCol;
		this.parent = parent;
		this.cost = parent == null ? 0 : parent.cost + 1;
		if (heu == HeuMethod.GAS) {
			h = gas();
		} else if (heu == HeuMethod.MIS) {
			h = misplace();
		} else if (heu == HeuMethod.MAN) {
			h = manhattan();
		}
	}
	
	public ArrayList<State> children(){
		ArrayList<State> ret = new ArrayList<State>();
		//blank up
		if (blankRow > 0) {
			int[][] newData = cloneData(data);
			newData[blankRow][blankCol] = newData[blankRow - 1][blankCol];
			newData[blankRow - 1][blankCol] = 0;
			ret.add(new State(newData, blankRow - 1, blankCol, this));
		}
		//blank down
		if (blankRow < 2) {
			int[][] newData = cloneData(data);
			newData[blankRow][blankCol] = newData[blankRow + 1][blankCol];
			newData[blankRow + 1][blankCol] = 0;
			ret.add(new State(newData, blankRow + 1, blankCol, this));
		}
		//blank left
		if (blankCol > 0) {
			int[][] newData = cloneData(data);
			newData[blankRow][blankCol] = newData[blankRow][blankCol - 1];
			newData[blankRow][blankCol - 1] = 0;
			ret.add(new State(newData, blankRow, blankCol - 1, this));
		}
		//blank right
		if (blankCol < 2) {
			int[][] newData = cloneData(data);
			newData[blankRow][blankCol] = newData[blankRow][blankCol + 1];
			newData[blankRow][blankCol + 1] = 0;
			ret.add(new State(newData, blankRow, blankCol + 1, this));
		}
		return ret;
	}
	
	public boolean goalTest(){
		int k = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (data[i][j] != correct[k++]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void print(){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (data[i][j] != 0){
					sb.append(data[i][j]);
				} else {
					sb.append(' ');
				}
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
	
	private int manhattan(){
		int ret = 0;
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (data[i][j] != 0) {
					ret += distance(i,j);
				}
			}
		}
		return ret;
	}
	
	private int misplace() {
		int ret = 0;
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (data[i][j] != 0) {
					ret += (distance(i,j) == 0 ? 0 : 1);
				}
			}
		}
		return ret;
	}
	
	private int gas(){
		int[] temp = new int[9];
		int k = 0;
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				temp[k++] = data[i][j];
			}
		}
		int step = 0;
		while (!goal(temp)) {
			if (temp[0] == 0) {
				int next = nextUnordered(temp);
				temp[0] = temp[next];
				temp[next] = 0;
			} else {
				int zero = find(temp, 0);
				int num = find(temp, zero);
				temp[zero] = zero;
				temp[num] = 0;
			}
			step++;
		}
		return step;
	}
	
	private boolean goal(int[] temp) {
		for (int i = 0; i < 9; i++) {
			if (temp[i] != i) {
				return false;
			}
		}
		return true;
	}
	
	private int nextUnordered(int[] temp) {
		for (int i = 0; i < 9; i++) {
			if (temp[i] != i) {
				return i;
			}
		}
		return -1;
	}
	
	private int find(int[] temp, int num) {
		for (int i = 0; i < 9; i++) {
			if (temp[i] == num) {
				return i;
			}
		}
		return -1;
	}
	
	private int[] correctPosition(int num){
		int[] ret = new int[2];
		if (num == 0) {
			ret[0] = 0;
			ret[1] = 0;
			return ret;
		}
		ret[0] = num / 3;
		ret[1] = num % 3;
		return ret;
	}
	
	private int distance(int x, int y) {
		int[] c = correctPosition(data[x][y]);
		return Math.abs(c[0] - x) + Math.abs(c[1] - y);
	}
	
	private void randomInit(){
		ArrayList<Integer> candidates = new ArrayList<Integer>();
		candidates.add(1);
		candidates.add(2);
		candidates.add(3);
		candidates.add(4);
		candidates.add(5);
		candidates.add(6);
		candidates.add(7);
		candidates.add(8);
		candidates.add(0);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int index = (int)(Math.random() * candidates.size());
				data[i][j] = candidates.get(index);
				candidates.remove(index);
				if (data[i][j] == 0) {
					blankRow = i;
					blankCol = j;
				}
			}
		}
	}
	
	public static int[][] cloneData(int[][] data){
		int[][] copy = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				copy[i][j] = data[i][j];
			}
		}
		return copy;
	}
	
	public int hashCode(){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sb.append(data[i][j]);
			}
		}
		return sb.toString().hashCode();
	}
	
	public boolean equals(Object o) {
		State other = (State)o;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (data[i][j] != other.data[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
}
