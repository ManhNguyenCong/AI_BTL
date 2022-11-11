package ttnt;

import java.util.ArrayList;

/**
 * Class <b>State</b> được sử dụng để lưu trạng thái của bài toán Tháp Hà
 * Nội.<br>
 * <b>Mô tả trạng thái:</b> Gọi xi là tháp chứa đĩa lớn thứ i, trong đó xi thuộc
 * {1, 2, 3}, i thuộc {1, 2, ... , <i>n</i>}. Khi đó bộ {x1, x2,x3,..., xn} là
 * mô tả trạng thái của bài toán.<br>
 * <b>Trạng thái ban đầu:</b> {1, 1, 1, ..., 1} <br>
 * <b>Trạng thái đích:</b> {2, 2, 2, ..., 2} hoặc {3, 3, 3, ..., 3} <br>
 * <b>Toán tử:</b> Chuyển đĩa thứ i sang tháp có đĩa đầu lớn hơn đĩa i
 *
 */
public class State {

	// Object's properties
	public int state[];

	// Constructor
	public State() {

	}

	public State(State otherState) {
		int nDisk = otherState.state.length;
		this.state = new int[nDisk];

		for (int i = 0; i < nDisk; i++) {
			this.state[i] = otherState.state[i];
		}
	}

	/**
	 * Khởi tạo trạng thái ban đầu của bài toán Tháp Hà Nội
	 * 
	 * @param nDisk
	 */
	public State(int nDisk) {
		state = new int[nDisk];
		for (int i = 0; i < nDisk; i++) {
			state[i] = 1;
		}
	}

	/**
	 * Xác định trạng thái đích của bài toán.
	 * 
	 * @param stateBegin
	 * @return
	 */
	public static State stateEnd(State stateBegin) {
		State res = new State(stateBegin);

		for (int i = 0; i < stateBegin.state.length; i++) {
			res.state[i] = 2;
		}

		return res;
	}

	/**
	 * Đĩa thứ <i>disk</i> có thể được chuyển sang những tháp nào?
	 * 
	 * @param disk
	 * @return
	 */
	public ArrayList<Integer> check(int disk) {
		ArrayList<Integer> res = new ArrayList<>();
		boolean flags[] = { true, true, true };

		if (disk == this.state.length - 1) { // Nếu là đia bé nhất thì có thể chuyển sang 2 tháp còn lại
			flags[this.state[disk] - 1] = false;
		} else {
			for (int i = disk; i < this.state.length; i++) {
				for (int j = 0; j < 3; j++) {
					if (this.state[i] == j + 1) { // Kiểm tra xem các đĩa bé hơn đã nằm ở những tháp nào
						flags[j] = false;
					}
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			if (flags[i]) {
				res.add(i + 1);
			}
		}

		return res;
	}

	/**
	 * Chuyển đĩa <i>disk</i> sang tháp <i>tower</i>
	 * 
	 * @param disk
	 * @param tower
	 * @return
	 */
	public State change(int disk, int tower) {
		State temp = new State(this);

		temp.state[disk] = tower;

		return temp;
	}

	/**
	 * So sánh hai trạng thái
	 * 
	 * @param otherState
	 * @return
	 */
	public boolean equals(State otherState) {
		if (this.state.length != otherState.state.length) {
			return false;
		} else {
			for (int i = 0; i < this.state.length; i++) {
				if (this.state[i] != otherState.state[i]) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Kiểm tra xem trạng thái đã tồn tại trong cây trạng thái chưa?
	 * 
	 * @param treeState
	 * @param disk
	 * @return
	 */
	public boolean isExist(Node treeState, int disk) {
		boolean flag = false;

		if (!this.check(disk).isEmpty()) {
			for (int val : check(disk)) {
				if (!Node.checkExist(treeState, new State(this.change(disk, val)))) {
					flag = true;
				}
			}
		}

		return !flag;
	}

	/**
	 * Toán tử của bài toán
	 * 
	 * @param treeState
	 * @return
	 */
	public ArrayList<State> operator(Node treeState) {
		ArrayList<State> res = new ArrayList<>();

		for (int i = this.state.length - 1; i >= 0; i--) {
			if (!this.isExist(treeState, i)) {
				for (int val : check(i)) {
					res.add(new State(this.change(i, val)));
				}
				break;
			}
		}

		return res;
	}

	public String toString() {
		String res = "";

		res += this.state[0];

		for (int i = 1; i < this.state.length; i++) {
			res += (" " + this.state[i]);
		}

		return res;
	}
}
