package management_system.ui.view;

import java.util.Scanner;

public class Menu {

	private final Scanner scanner;
	private static final double version = 1.3;

	public Menu(Scanner scanner) {
		this.scanner = scanner;
	}

	public void showMenu() {
		System.out.print("[メニュー] 1:検索 2:登録 3:変更 4:削除 0:終了 ＞");
	}

	public void showVersion() {
		LinePrinter.printLine();
		System.out.println("商品管理システム　var" + version);
	}

	public String input() {

		String input = scanner.nextLine().trim();

		if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("\\q")) {
			return "0";
		}

		return input;
	}

}
