package ttnt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

	public static Scanner scanner = new Scanner(System.in);

	/**
	 * Khởi tạo bài toán tháp Hà Nội
	 * 
	 * @return
	 */
	public static Node input() {
		int nDisk;

		do {
			System.out.print("Mời bạn nhập số đĩa(0 < Số đĩa <= 7): ");
			nDisk = scanner.nextInt();
		} while (nDisk <= 0 || nDisk > 7);

		State state = new State(nDisk);
		Node roof = new Node(state);

		return roof;
	}

	/**
	 * Thuật toán tìm kiếm theo chiểu rộng (BFS).
	 * 
	 * @param roof
	 * @param state
	 * @return
	 */
	public static Node BFS(Node roof, State state) {
		Queue<Node> OPEN = new LinkedList<>();
		Queue<Node> CLOSE = new LinkedList<>();

		OPEN.add(roof);
		while (!OPEN.isEmpty()) {
			Node temp = OPEN.poll();

			if (temp.val.equals(state)) {
				return temp;
			}

			CLOSE.add(temp);

			for (int i = 0; i < 2; i++) {
				if (temp.leaf[i] != null) {
					OPEN.add(temp.leaf[i]);
				}
			}
		}

		return null;
	}

	/**
	 * Lấy các trạng thái trong các bước thực hiện giải bài toán Tháp Hà Nội
	 * 
	 * @param node
	 * @return
	 */
	public static ArrayList<State> solution(Node node) {
		ArrayList<State> temp = new ArrayList<>();

		while (node != null) {
			temp.add(node.val);
			node = node.father;
		}

		ArrayList<State> res = new ArrayList<>(temp.size());

		for (int i = temp.size() - 1; i >= 0; i--) {
			res.add(temp.get(i));
		}

		return res;
	}

	/**
	 * Hiển thị ra các bước giải bài toán Tháp Hà Nội
	 * 
	 * @param states
	 */
	public static void displaySteps(ArrayList<State> states) {

		int nDisk = states.get(0).state.length;

		System.out.println("Bước 0: Cho " + nDisk + " đĩa vào tháp thứ nhất");
		System.out.println("\tTrạng thái: " + states.get(0));

		for (int i = 0; i < states.size() - 1; i++) {
			int j = 0;

			while (j < nDisk && states.get(i).state[j] == states.get(i + 1).state[j]) {
				j++;
			}

			System.out.print("Bước " + (i + 1) + ": ");
			System.out.println("Chuyển đĩa " + (j + 1) + " từ tháp " + states.get(i).state[j] + " sang tháp "
					+ states.get(i + 1).state[j]);
			System.out.println("\tTrạng thái: " + states.get(i + 1));
		}
	}

	public static void main(String[] args) {

		Node roof = input();

		TreeState.treeState(roof);

		displaySteps(solution(BFS(roof, State.stateEnd(roof.val))));
	}
}
