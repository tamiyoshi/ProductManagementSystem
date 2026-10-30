package management_system.ui.view;

//	よく使うラインなどをメソッドにしてるもの
public class LinePrinter {

	private static final String LINE = "----------------------------------------------------------------------------------------------------------------";

	public static void printLine() {
		System.out.println(LINE);
	}

	public static void printShortLine() {
		System.out.println("------");
	}

	public static void printResultLine() {
		System.out.println(
				"=============================================================================================================");
	}

	public static void printContinueLine() {
		System.out.println(
				"*************************************************************************************************************");
	}

	public static void printSection(String title) {
		System.out.println();
		printLine();
		System.out.println("■ " + title);
		printLine();
	}
}
