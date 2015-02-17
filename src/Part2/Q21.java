package Part2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Q21 {
	public static void main(String[] args) {
		State.heu = HeuMethod.GAS;
		ArrayList<int[]> list = new Q21().permutations();
		ArrayList<String> ret = new ArrayList<String>();
		int threadCount = 4;
		int piece = list.size() / threadCount;
		Lock lock = new ReentrantLock();
		for (int i = 0; i < threadCount; i++) {
			Thread t = new Thread(new Computer(list, ret, lock, piece * i, Math.min(piece * i + piece - 1, list.size() - 1)));
			t.start();
		}
	}
	
	public String[] generate(){
		String[] ret = new String[50];
		ArrayList<int[]> list = permutations();
		int count = 0;
		for (int[] array : list) {
			System.out.println(count++ + "/" + list.size());
			int[][] board = new int[3][3];
			int k = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					board[i][j] = array[k++];
				}
			}
			int[][] backup = State.cloneData(board);
			Puzzle p = new Puzzle(board);
			//p.start.print();
			State s = p.search();
			if (s != null && ret[s.cost] == null) {
				//System.out.println(s.cost);
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				for (int i = 0; i < 3; i++) {
					sb.append("{");
					for (int j = 0; j < 3; j++) {
						sb.append(backup[i][j]);
						if (j != 2) {
							sb.append(",");
						}
					}
					sb.append("}");
					if (i != 2) {
						sb.append(",");
					}
				}
				sb.append("},");
				System.out.println("Generate cost " + s.cost);
				System.out.println(sb.toString());
				ret[s.cost] = sb.toString();
			}
		}
		return ret;
	}
	
	public ArrayList<int[]> permutations() {
		ArrayList<int[]> ret = new ArrayList<int[]>();
		int[] temp = new int[9];
		ArrayList<Integer> remain = new ArrayList<Integer>();
		remain.add(0);
		remain.add(1);
		remain.add(2);
		remain.add(3);
		remain.add(4);
		remain.add(5);
		remain.add(6);
		remain.add(7);
		remain.add(8);
		permutations(ret, temp, 0, remain);
//		for(int[] array : ret) {
//			for(int i : array) {
//				System.out.print(i);
//			}
//			System.out.println();
//		}
		return ret;
	}
	
	public void permutations(ArrayList<int[]> ret, int[] temp, int index, ArrayList<Integer> remain) {
		if (index == 9) {
			ret.add(Arrays.copyOf(temp, 9));
			return;
		}
		for (int i = 0; i < remain.size(); i++) {
			int num = remain.get(i);
			temp[index] = num;
			remain.remove(i);
			permutations(ret, temp, index + 1, remain);
			remain.add(i, num);
		}
	}
}
