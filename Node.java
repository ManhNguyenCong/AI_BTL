package ttnt;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class <b>Node</b> được sử dụng để lưu các trạng thái vào cây. Mỗi node là một
 * điểm trên cây. <br>
 * Node <b>Father</b> được sử dụng để lưu lại trạng thái cha của node khi sử
 * dụng thuật toán BFS để tìm kiếm. <br>
 * Mảng Node <b>leaf</b> lưu trữ các trạng thái được sỉnh ra. Toán tử chuyển 1
 * đĩa sang 1 trong hai tháp nên chỉ có tối đa 2 trạng thái mới được tạo ra.
 *
 */
public class Node {

	// Object's properties
	public Node father;
	public State val;
	public Node[] leaf = new Node[2];

	// Constructor
	public Node() {
		val = new State();
	}

	public Node(State roof) {
		val = new State(roof);
	}

	/**
	 * Thêm một trạng thái vào cây
	 * 
	 * @param treeState
	 * @param state
	 */
	public void add(State state) {
		Node temp = new Node();
		temp.val = state;
		temp.father = this;

		for (int i = 0; i < 2; i++) {
			if (this.leaf[i] == null) {
				this.leaf[i] = temp;
				return;
			}
		}
	}

	/**
	 * Kiểm tra xem trạng thái đã tồn tại hay chưa? <br>
	 * <i>Sử dụng BFS</i>
	 * 
	 * @param roof
	 * @param state
	 * @return
	 */
	public static boolean checkExist(Node roof, State state) {
		Queue<Node> OPEN = new LinkedList<>();
		Queue<Node> CLOSE = new LinkedList<>();

		OPEN.add(roof);
		while (!OPEN.isEmpty()) {
			Node temp = OPEN.poll();

			if (temp.val.equals(state)) {
				return true;
			}

			CLOSE.add(temp);

			for (int i = 0; i < 2; i++) {
				if (temp.leaf[i] != null) {
					OPEN.add(temp.leaf[i]);
				}
			}
		}

		return false;
	}
}