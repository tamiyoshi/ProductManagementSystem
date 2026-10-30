package management_system.ui.input;

import java.text.Normalizer;
import java.util.Scanner;

import management_system.domain.validation.ProductValidationRule;
import management_system.ui.view.LinePrinter;
import management_system.ui.view.TextFormatter;

public class InputValidator {

	private final Scanner scanner;

	public InputValidator(Scanner scanner) {
		this.scanner = scanner;
	}

	// readInput：入力を受け取る最下層
	private InputResult<String> readInput() {

		String input = scanner.nextLine().trim();

		if (input.equalsIgnoreCase("exit")
				|| input.equalsIgnoreCase("\\q")) {

			System.out.println("メニューに戻ります。");
			return new InputResult<>(
					InputResult.Status.EXIT,
					null);
		}

		return new InputResult<>(
				InputResult.Status.OK,
				input);
	}

	//　基本入力
	private InputResult<String> rawInput() {

		InputResult<String> result = readInput();

		if (result.getStatus() != InputResult.Status.OK) {
			return result;
		}

		String input = result.getValue();

		if (input.contains(",")) {

			TextFormatter.printError("カンマは使用できません。");

			return new InputResult<>(
					InputResult.Status.INVALID,
					null);
		}

		return result;
	}

	// 検索入力
	public InputResult<String> inputSearch() {

		System.out.print("キーワード ＞ ");

		return readInput();
	}

	// Y/N確認メソッド
	public boolean confirmYN(String message) {

		while (true) {

			System.out.println(message);
			System.out.print("Y/N ＞ ");

			InputResult<String> result = rawInput();

			if (result.getStatus() == InputResult.Status.EXIT)
				return false;

			if (result.getStatus() == InputResult.Status.INVALID)
				continue;

			String input = Normalizer
					.normalize(
							result.getValue().trim(),
							Normalizer.Form.NFKC)
					.toLowerCase();

			if (input.equals("y"))
				return true;

			if (input.equals("n"))
				return false;

			LinePrinter.printShortLine();
			TextFormatter.printError("YかNを入力してください。");
		}
	}

	// 商品ID（必須）
	public InputResult<String> inputProductId() {

		while (true) {

			System.out.print(
					"商品ID ＞ ");

			InputResult<String> result = rawInput();

			if (result.getStatus() != InputResult.Status.OK)
				return result;

			String input = result.getValue();

			// 空欄チェック
			if (ProductValidationRule.isEmpty(input)) {

				TextFormatter.printError("商品IDを入力してください。");

				continue;
			}

			// nullチェック
			if (ProductValidationRule.isNullKeyword(input)) {

				TextFormatter.printError("nullは使用できません。");

				continue;
			}

			// 使用可能文字チェック
			if (!ProductValidationRule
					.isAlphaNumUnderscoreHyphen(input)) {

				TextFormatter.printError("商品IDは半角英数字・半角アンダースコア・半角ハイフンで入力してください。");

				continue;
			}

			// 10桁チェック
			if (!ProductValidationRule.hasLength(input, 10)) {

				TextFormatter.printError("商品IDは10桁で入力してください。");

				continue;
			}

			return new InputResult<>(
					InputResult.Status.OK,
					input);
		}
	}

	// 商品コード（必須）
	public InputResult<String> inputProductCode() {

		while (true) {

			System.out.print(
					"商品コード ＞ ");

			InputResult<String> result = rawInput();

			if (result.getStatus() != InputResult.Status.OK)
				return result;

			String input = result.getValue();

			// 空欄チェック
			if (ProductValidationRule.isEmpty(input)) {

				TextFormatter.printError("商品コードを入力してください");

				continue;
			}

			// nullチェック
			if (ProductValidationRule.isNullKeyword(input)) {

				TextFormatter.printError("nullは使用できません。");

				continue;
			}

			// 半角数字チェック
			if (!ProductValidationRule.isNumeric(input)) {

				TextFormatter.printError("商品コードは13桁の半角数字で入力してください。");

				continue;
			}

			// 13桁チェック
			if (!ProductValidationRule.hasLength(input, 13)) {

				TextFormatter.printError("商品コードは13桁の半角数字で入力してください。");

				continue;
			}

			return new InputResult<>(
					InputResult.Status.OK,
					input);
		}
	}

	// 商品コード（任意・更新用）
	public InputResult<String> inputOptionalProductCode(
			String currentValue) {

		while (true) {

			System.out.print(
					"商品コード【" + currentValue + "】＞ ");

			InputResult<String> result = rawInput();

			if (result.getStatus() != InputResult.Status.OK)
				return result;

			String input = result.getValue();

			// 空欄 → 変更なし
			if (ProductValidationRule.isEmpty(input)) {

				return new InputResult<>(
						InputResult.Status.EMPTY,
						currentValue);
			}

			// null → 禁止
			if (ProductValidationRule.isNullKeyword(input)) {

				TextFormatter.printError("nullは使用できません。");

				continue;
			}

			// 半角数字チェック
			if (!ProductValidationRule.isNumeric(input)) {

				TextFormatter.printError("商品コードは13桁の半角数字で入力してください。");

				continue;
			}

			// 13桁チェック
			if (!ProductValidationRule.hasLength(input, 13)) {

				TextFormatter.printError("商品コードは13桁の半角数字で入力してください。");

				continue;
			}

			return new InputResult<>(
					InputResult.Status.OK,
					input);
		}
	}

	// 商品名（必須）
	public InputResult<String> inputProductName() {

		while (true) {

			System.out.print(
					"商品名 ＞ ");

			InputResult<String> result = rawInput();

			if (result.getStatus() != InputResult.Status.OK)
				return result;

			String input = result.getValue();

			// 空欄チェック
			if (ProductValidationRule.isEmpty(input)) {

				TextFormatter.printError("商品名を入力してください。");

				continue;
			}

			// nullチェック
			if (ProductValidationRule.isNullKeyword(input)) {

				TextFormatter.printError("nullは使用できません。");

				continue;
			}

			// バイト数チェック
			if (!ProductValidationRule
					.isWithinBytes(input, 100)) {

				TextFormatter.printError("商品名は100文字以下で入力してください。");

				continue;
			}

			return new InputResult<>(
					InputResult.Status.OK,
					input);
		}
	}

	// 商品名（任意・更新用）
	public InputResult<String> inputOptionalProductName(
			String currentValue) {

		while (true) {

			System.out.print(
					"商品名[" + currentValue + "］＞ ");

			InputResult<String> result = rawInput();

			if (result.getStatus() != InputResult.Status.OK)
				return result;

			String input = result.getValue();

			// 空欄 → 変更なし
			if (ProductValidationRule.isEmpty(input)) {

				return new InputResult<>(
						InputResult.Status.EMPTY,
						currentValue);
			}

			// null → 禁止
			if (ProductValidationRule.isNullKeyword(input)) {

				TextFormatter.printError("nullは使用できません。");

				continue;
			}

			// バイト数チェック
			if (!ProductValidationRule
					.isWithinBytes(input, 100)) {

				TextFormatter.printError("商品名は100文字以下で入力してください。");

				continue;
			}

			return new InputResult<>(
					InputResult.Status.OK,
					input);
		}
	}

	// 分類（必須）
	public InputResult<String> inputCategory() {

		while (true) {

			System.out.print(
					"分類 ＞ ");

			InputResult<String> result = rawInput();

			if (result.getStatus() != InputResult.Status.OK)
				return result;

			String input = result.getValue();

			// 空欄チェック
			if (ProductValidationRule.isEmpty(input)) {

				TextFormatter.printError("商品分類を入力してください。");

				continue;
			}

			// nullチェック
			if (ProductValidationRule.isNullKeyword(input)) {

				TextFormatter.printError("nullは使用できません。");

				continue;
			}

			// バイト数チェック
			if (!ProductValidationRule
					.isWithinBytes(input, 100)) {

				TextFormatter.printError("商品分類は100文字以下で入力してください。");

				continue;
			}

			return new InputResult<>(
					InputResult.Status.OK,
					input);
		}
	}

	// 分類（任意・更新用）
	public InputResult<String> inputOptionalCategory(
			String currentValue) {

		while (true) {

			System.out.print(
					"商品分類[" + currentValue + "］＞ ");

			InputResult<String> result = rawInput();

			if (result.getStatus() != InputResult.Status.OK)
				return result;

			String input = result.getValue();

			// 空欄 → 変更なし
			if (ProductValidationRule.isEmpty(input)) {

				return new InputResult<>(
						InputResult.Status.EMPTY,
						currentValue);
			}

			// null → 禁止
			if (ProductValidationRule.isNullKeyword(input)) {

				TextFormatter.printError("nullは使用できません。");

				continue;
			}

			// バイト数チェック
			if (!ProductValidationRule
					.isWithinBytes(input, 100)) {

				TextFormatter.printError("商品分類は100文字以下で入力してください。");

				continue;
			}

			return new InputResult<>(
					InputResult.Status.OK,
					input);
		}
	}

	// 金額（任意）
	public InputResult<String> inputOptionalPrice(
			String label,
			String currentValue) {

		while (true) {

			if (currentValue.isEmpty()) {
				System.out.print(label + " ＞ ");
			} else {
				System.out.print(label + "[" + currentValue + "］ ＞ ");
			}
			InputResult<String> result = rawInput();

			if (result.getStatus() != InputResult.Status.OK)
				return result;

			String input = result.getValue();

			// null → null
			if (ProductValidationRule.isNullKeyword(input)) {

				return new InputResult<>(
						InputResult.Status.OK,
						null);
			}

			// 空欄 → 変更なし
			if (ProductValidationRule.isEmpty(input)) {

				return new InputResult<>(
						InputResult.Status.EMPTY,
						currentValue);
			}

			// 半角数字チェック
			if (!ProductValidationRule.isNumeric(input)) {

				TextFormatter.printError(label + "は半角数字8桁以下で入力してください。");

				continue;
			}

			// 8桁以下チェック
			if (input.length() > 8) {

				TextFormatter.printError(label + "は半角数字8桁以下で入力してください。");

				continue;
			}

			return new InputResult<>(
					InputResult.Status.OK,
					input);
		}
	}

	// 登録日（任意）
	public InputResult<String> inputOptionalRegistrationDate(
			String currentValue) {

		while (true) {

			if (currentValue.isEmpty()) {
				System.out.print("登録日 ＞ ");
			} else {
				System.out.print("登録日[" + currentValue + "］ ＞ ");
			}

			InputResult<String> result = rawInput();

			if (result.getStatus() != InputResult.Status.OK)
				return result;

			String input = result.getValue();

			// null → null
			if (ProductValidationRule.isNullKeyword(input)) {

				return new InputResult<>(
						InputResult.Status.OK,
						null);
			}

			// 空欄 → 変更なし
			if (ProductValidationRule.isEmpty(input)) {

				return new InputResult<>(
						InputResult.Status.EMPTY,
						currentValue);
			}

			// 日付チェック
			if (!ProductValidationRule
					.isValidDateFormat(input)) {

				TextFormatter.printError("登録日はyyyy-MM-dd形式で入力してください。");

				continue;
			}

			return new InputResult<>(
					InputResult.Status.OK,
					input);
		}
	}

}
