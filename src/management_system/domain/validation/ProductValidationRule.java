package management_system.domain.validation;

import java.time.LocalDate;

public class ProductValidationRule {

	// ============================
	// 基本バリデーション（単機能）
	// ============================

	// 半角英数字 + _ + -
	public static boolean isAlphaNumUnderscoreHyphen(String text) {
		return text != null && text.matches("^[A-Za-z0-9_-]+$");
	}

	// 半角数字のみ
	public static boolean isNumeric(String text) {
		return text != null && text.matches("^\\d+$");
	}

	// 桁数チェック
	public static boolean hasLength(String text, int length) {
		return text != null && text.length() == length;
	}

	// バイト数チェック（Shift_JIS）
	public static boolean isWithinBytes(String text, int maxBytes) {

		if (text == null)
			return false;

		return text.getBytes(java.nio.charset.Charset.forName("windows-31j")).length <= maxBytes;
	}

	// "null" キーワード判定
	public static boolean isNullKeyword(String text) {
		return "null".equalsIgnoreCase(text);
	}

	// 空文字判定
	public static boolean isEmpty(String text) {
		return text != null && text.isEmpty();
	}

	// ============================
	// 商品ID：10桁・英数字・_・-
	// ============================
	public static boolean isValidIdFormat(String id) {
		if (id == null)
			return false;

		if (!isAlphaNumUnderscoreHyphen(id))
			return false;
		if (!hasLength(id, 10))
			return false;

		return true;
	}

	// ============================
	// 商品コード：13桁数字
	// ============================
	public static boolean isValidCodeFormat(String code) {
		if (code == null)
			return false;

		if (!isNumeric(code))
			return false;
		if (!hasLength(code, 13))
			return false;

		return true;
	}

	// ============================
	// 商品名：1文字以上・100バイト以内
	// ============================
	public static boolean isValidNameFormat(String name) {
		if (name == null)
			return false;

		if (isEmpty(name))
			return false;
		if (!isWithinBytes(name, 100))
			return false;

		return true;
	}

	// ============================
	// 商品分類：1文字以上・100バイト以内
	// ============================
	public static boolean isValidCategoryFormat(String category) {
		if (category == null)
			return false;

		if (isEmpty(category))
			return false;
		if (!isWithinBytes(category, 100))
			return false;

		return true;
	}

	// ============================
	// 金額：数字・8桁以内
	// ============================
	public static boolean isValidPriceFormat(String price) {
		if (price == null)
			return false;

		if (!isNumeric(price))
			return false;
		if (price.length() > 8)
			return false;

		return true;
	}

	// ============================
	// 登録日：8桁の日付
	// ============================
	public static boolean isValidDateFormat(String date) {

		if (date == null)
			return false;

		if (date.length() != 10)
			return false;

		if (date.charAt(4) != '-' || date.charAt(7) != '-')
			return false;

		String y = date.substring(0, 4);
		String m = date.substring(5, 7);
		String d = date.substring(8, 10);

		if (!isNumeric(y) || !isNumeric(m) || !isNumeric(d))
			return false;

		try {
			LocalDate.parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
