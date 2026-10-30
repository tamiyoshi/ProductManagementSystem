package management_system.ui.view;

import java.util.Scanner;

public class ContinuePrompt {

	private final Scanner scanner;

	public ContinuePrompt(Scanner scanner) {
		this.scanner = scanner;
	}

	public boolean ask(String actionName) {

		while (true) {
			System.out.println("続けて商品を" + actionName + "しますか？");
			System.out.print(" 1：続けて" + actionName + "する 2：メニューへ戻る ＞");

			String input = scanner.nextLine().trim();

			if (input.equals("1")) {
				LinePrinter.printContinueLine();
				return true;
			}

			if (input.equals("2")) {
				LinePrinter.printResultLine();
				return false;
			}

			LinePrinter.printShortLine();
			TextFormatter.printError("1 か 2 を入力してください。");
		}
	}
}
