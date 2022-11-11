package ttnt;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class <b>TreeState</b> được sử dụng để tạo cây trạng thái cho bài toán tháp
 * Hà Nội.
 * 
 *
 */
public class TreeState {

	/**
	 * Tạo cây trạng thái của bài toán tháp Hà Nội
	 * 
	 * @param roof
	 */
	public static void treeState(Node roof) {
		Queue<Node> OPEN = new LinkedList<>();
		Queue<Node> CLOSE = new LinkedList<>();

		OPEN.add(roof);

		while (!OPEN.isEmpty()) {
			Node temp = OPEN.poll();

			for (State state : temp.val.operator(roof)) {
				temp.add(state);
			}

			CLOSE.add(temp);

			for (int i = 0; i < 2; i++) {
				if (temp.leaf[i] != null) {
					OPEN.add(temp.leaf[i]);
				}
			}
		}
	}

	/**
	 * In ra cây trạng thái
	 * 
	 * @param roof
	 */
	public static void printAll(Node roof) {
		Queue<Node> OPEN = new LinkedList<>();
		Queue<Node> CLOSE = new LinkedList<>();

		OPEN.add(roof);

		while (!OPEN.isEmpty()) {
			Node temp = OPEN.poll();

			System.out.println(temp.val);

			CLOSE.add(temp);

			for (int i = 0; i < 2; i++) {
				if (temp.leaf[i] != null) {
					OPEN.add(temp.leaf[i]);
				}
			}
		}
	}

}