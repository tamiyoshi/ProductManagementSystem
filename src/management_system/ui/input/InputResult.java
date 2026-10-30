package management_system.ui.input;

public class InputResult<T> {

	public enum Status {
		OK, // 正常入力
		EMPTY, // 空欄（任意入力）
		EXIT, // exit or /q
		INVALID // 無効な入力（禁止文字など）
	}

	private final Status status;
	private final T value;

	public InputResult(Status status, T value) {
		this.status = status;
		this.value = value;
	}

	public Status getStatus() {
		return status;
	}

	public T getValue() {
		return value;
	}
}
