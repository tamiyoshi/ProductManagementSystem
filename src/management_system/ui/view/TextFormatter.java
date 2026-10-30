package management_system.ui.view;

public class TextFormatter {

	// 全角は幅2、半角は幅1として幅を計算
	public static int getDisplayWidth(String s) {
		if (s == null)
			s = "";

		int width = 0;
		for (char c : s.toCharArray()) {
			Character.UnicodeBlock block = Character.UnicodeBlock.of(c);

			if (block == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS ||
					block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
					block == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION ||
					block == Character.UnicodeBlock.HIRAGANA ||
					block == Character.UnicodeBlock.KATAKANA) {

				width += 2; // 全角
			} else {
				width += 1; // 半角	
			}
		}
		return width;
	}

	// 指定幅に揃える（左寄せ）※Objectで受けて中で文字列化
	public static String padRight(Object value, int width) {
		String s = (value == null) ? "" : String.valueOf(value);

		int displayWidth = getDisplayWidth(s);
		int padding = width - displayWidth;

		if (padding > 0) {
			return s + " ".repeat(padding);
		}
		return s;
	}

	public static void printError(String message) {
		System.out.println("\u001B[31m" + message + "\u001B[0m");
	}

}
