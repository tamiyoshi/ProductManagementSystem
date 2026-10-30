package management_system.ui.controller;

import java.time.LocalDate;

import management_system.domain.model.Product;
import management_system.domain.service.ProductCommandService;
import management_system.domain.service.ProductQueryService;
import management_system.ui.input.InputResult;
import management_system.ui.input.InputValidator;
import management_system.ui.view.ContinuePrompt;
import management_system.ui.view.LinePrinter;
import management_system.ui.view.ProductPrinter;
import management_system.ui.view.TextFormatter;

public class RegisterController {

	private final ProductCommandService commandService;
	private final ProductQueryService queryService;
	private final InputValidator validator;
	private final ContinuePrompt continuePrompt;

	public RegisterController(
			ProductCommandService commandService,
			ProductQueryService queryService,
			InputValidator validator,
			ContinuePrompt continuePrompt) {

		this.commandService = commandService;
		this.queryService = queryService;
		this.validator = validator;
		this.continuePrompt = continuePrompt;
	}

	public void registerProduct() {

		do {

			LinePrinter.printShortLine();
			System.out.println("商品情報を登録します。");
			System.out.println("商品情報を入力してください。");
			LinePrinter.printShortLine();

			// 商品ID（必須）
			String id = null;
			while (true) {

				InputResult<String> idResult = validator.inputProductId();

				if (idResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (idResult.getStatus() == InputResult.Status.INVALID)
					continue;

				id = idResult.getValue();

				if (queryService.existsById(id)) {
					LinePrinter.printShortLine();
					TextFormatter.printError("この商品IDは既に使用されています。新しい商品IDを設定してください。");
					continue;
				}

				break;
			}

			LinePrinter.printShortLine();

			// 商品コード（必須）
			String code = null;
			while (true) {

				InputResult<String> codeResult = validator.inputProductCode();

				if (codeResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (codeResult.getStatus() == InputResult.Status.INVALID)
					continue;

				code = codeResult.getValue();

				if (queryService.existsByCode(code)) {
					LinePrinter.printShortLine();
					TextFormatter.printError("この商品コードは既に使用されています。あたらあしい商品コードを設定してください。");
					continue;
				}

				break;
			}

			LinePrinter.printShortLine();

			// 商品名（必須）
			String name = null;
			while (true) {

				InputResult<String> nameResult = validator.inputProductName();

				if (nameResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (nameResult.getStatus() == InputResult.Status.INVALID)
					continue;

				name = nameResult.getValue();
				LinePrinter.printShortLine();
				break;
			}

			// 分類（必須）
			String category = null;
			while (true) {

				InputResult<String> categoryResult = validator.inputCategory();

				if (categoryResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (categoryResult.getStatus() == InputResult.Status.INVALID)
					continue;

				category = categoryResult.getValue();
				LinePrinter.printShortLine();
				break;
			}

			// 販売単価（任意）
			Integer sellingPrice = null;
			while (true) {

				InputResult<String> sellingPriceResult = validator.inputOptionalPrice("販売単価", "");

				if (sellingPriceResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (sellingPriceResult.getStatus() == InputResult.Status.INVALID)
					continue;

				if (sellingPriceResult.getStatus() == InputResult.Status.EMPTY ||
						sellingPriceResult.getValue() == null) {

					sellingPrice = null;

				} else {
					sellingPrice = Integer.parseInt(sellingPriceResult.getValue());
				}

				LinePrinter.printShortLine();
				break;
			}

			// 仕入単価（任意）
			Integer purchasePrice = null;
			while (true) {

				InputResult<String> purchasePriceResult = validator.inputOptionalPrice("仕入単価", "");

				if (purchasePriceResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (purchasePriceResult.getStatus() == InputResult.Status.INVALID)
					continue;

				if (purchasePriceResult.getStatus() == InputResult.Status.EMPTY ||
						purchasePriceResult.getValue() == null) {

					purchasePrice = null;

				} else {
					purchasePrice = Integer.parseInt(purchasePriceResult.getValue());
				}

				LinePrinter.printShortLine();
				break;
			}

			// 登録日
			LocalDate registrationDate = null;

			while (true) {

				InputResult<String> createdAtResult = validator.inputOptionalRegistrationDate("");

				if (createdAtResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (createdAtResult.getStatus() == InputResult.Status.INVALID)
					continue;

				if (createdAtResult.getStatus() == InputResult.Status.EMPTY ||
						createdAtResult.getValue() == null) {

					registrationDate = null;
					break;
				}

				String raw = createdAtResult.getValue();
				registrationDate = LocalDate.parse(raw);

				LinePrinter.printShortLine();
				break;
			}

			// 確認
			if (!validator.confirmYN("この内容で登録しますか？")) {
				LinePrinter.printShortLine();
				System.out.println("登録を中止しました。");
				LinePrinter.printShortLine();
				continue;
			}

			//	ここで再度IDとコードのチェックをする
			if (queryService.existsById(id)) {
				LinePrinter.printShortLine();
				TextFormatter.printError("既に使用。新しい商品IDを設定。");
				return;
			}
			if (queryService.existsByCode(code)) {
				LinePrinter.printShortLine();
				TextFormatter.printError("既に使用。新しい商品コードを設定。");
				return;
			}

			// 登録処理
			Product created = commandService.registerProduct(
					id,
					code,
					name,
					category,
					sellingPrice,
					purchasePrice,
					registrationDate);

			LinePrinter.printShortLine();
			System.out.println("商品情報を登録しました。");

			ProductPrinter.printActionResult("登録", created);

		} while (continuePrompt.ask("登録"));

		return;
	}
}
