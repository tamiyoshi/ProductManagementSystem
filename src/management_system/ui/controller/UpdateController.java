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

public class UpdateController {

	private final InputValidator validator;
	private final ContinuePrompt continuePrompt;

	private final ProductQueryService queryService;
	private final ProductCommandService commandService;

	public UpdateController(
			ProductCommandService commandService,
			ProductQueryService queryService,
			InputValidator validator,
			ContinuePrompt continuePrompt) {

		this.queryService = queryService;
		this.commandService = commandService;
		this.validator = validator;
		this.continuePrompt = continuePrompt;
	}

	public void updateProduct() {

		do {

			System.out.println("商品情報を変更します。");
			System.out.println("変更する商品IDを入力してください。");
			LinePrinter.printShortLine();

			Product target = null;

			while (target == null) {

				InputResult<String> idResult = validator.inputProductId();

				if (idResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (idResult.getStatus() == InputResult.Status.INVALID)
					continue;

				String id = idResult.getValue();

				target = queryService.findById(id).orElse(null);

				if (target == null) {
					TextFormatter.printError("存在しない商品IDです。商品IDを確認してください。");
				}
			}

			// 現在の情報表示
			LinePrinter.printShortLine();
			System.out.println("変更する項目のみ入力してください。");
			LinePrinter.printShortLine();

			// 商品コード
			String newCode = target.getCode();

			while (true) {

				InputResult<String> codeResult = validator.inputOptionalProductCode(target.getCode());

				if (codeResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (codeResult.getStatus() == InputResult.Status.INVALID)
					continue;

				if (codeResult.getStatus() == InputResult.Status.EMPTY) {
					newCode = target.getCode();
					break;
				}

				String value = codeResult.getValue();

				if (!value.equals(target.getCode())
						&& queryService.existsByCode(value)) {

					System.out.println("この商品コードはすでに使用されています。新しい商品コードを設定してください。");
					continue;
				}

				newCode = value;
				break;
			}

			LinePrinter.printShortLine();

			// 商品名
			String newName = target.getName();

			while (true) {

				InputResult<String> nameResult = validator.inputOptionalProductName(target.getName());

				if (nameResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (nameResult.getStatus() == InputResult.Status.INVALID)
					continue;

				if (nameResult.getStatus() == InputResult.Status.EMPTY) {
					newName = target.getName();
					break;
				}

				newName = nameResult.getValue();
				break;
			}

			LinePrinter.printShortLine();

			// 分類
			String newCategory = target.getCategory();

			while (true) {

				InputResult<String> categoryResult = validator.inputOptionalCategory(target.getCategory());

				if (categoryResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (categoryResult.getStatus() == InputResult.Status.INVALID)
					continue;

				if (categoryResult.getStatus() == InputResult.Status.EMPTY) {
					newCategory = target.getCategory();
					break;
				}

				newCategory = categoryResult.getValue();
				break;
			}

			LinePrinter.printShortLine();

			// 販売単価
			Integer newSellingPrice = target.getSalePrice();

			String currentSellingPrice = target.getSalePrice() == null
					? ""
					: target.getSalePrice().toString();

			while (true) {

				InputResult<String> sellingPriceResult = validator.inputOptionalPrice("販売単価", currentSellingPrice);

				if (sellingPriceResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (sellingPriceResult.getStatus() == InputResult.Status.INVALID)
					continue;

				if (sellingPriceResult.getStatus() == InputResult.Status.EMPTY) {
					newSellingPrice = target.getSalePrice();
					break;
				}

				if (sellingPriceResult.getValue() == null) {
					newSellingPrice = null;
					break;
				}

				newSellingPrice = Integer.parseInt(sellingPriceResult.getValue());
				break;
			}

			LinePrinter.printShortLine();

			// 仕入単価
			Integer newPurchasePrice = target.getPurchasePrice();

			String currentPurchasePrice = target.getPurchasePrice() == null
					? ""
					: target.getPurchasePrice().toString();

			while (true) {

				InputResult<String> purchasePriceResult = validator.inputOptionalPrice("仕入単価", currentPurchasePrice);

				if (purchasePriceResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (purchasePriceResult.getStatus() == InputResult.Status.INVALID)
					continue;

				if (purchasePriceResult.getStatus() == InputResult.Status.EMPTY) {
					newPurchasePrice = target.getPurchasePrice();
					break;
				}

				if (purchasePriceResult.getValue() == null) {
					newPurchasePrice = null;
					break;
				}

				newPurchasePrice = Integer.parseInt(purchasePriceResult.getValue());
				break;
			}

			LinePrinter.printShortLine();

			// 登録日
			LocalDate newRegistrationDate = target.getRegistrationDate();

			String currentRegistrationDate = target.getRegistrationDate() == null
					? ""
					: target.getRegistrationDate().toString().replace("-", "");

			while (true) {

				InputResult<String> createdAtResult = validator.inputOptionalRegistrationDate(currentRegistrationDate);

				if (createdAtResult.getStatus() == InputResult.Status.EXIT)
					return;

				if (createdAtResult.getStatus() == InputResult.Status.INVALID)
					continue;

				if (createdAtResult.getStatus() == InputResult.Status.EMPTY) {
					newRegistrationDate = target.getRegistrationDate();
					break;
				}

				if (createdAtResult.getValue() == null) {
					newRegistrationDate = null;
					break;
				}

				// YYYYMMDD → LocalDate
				String raw = createdAtResult.getValue();
				LocalDate parsed = LocalDate.parse(raw);

				newRegistrationDate = parsed;
				break;
			}

			LinePrinter.printShortLine();

			// 最終確認
			if (!validator.confirmYN("この内容で変更しますか？")) {
				LinePrinter.printShortLine();
				System.out.println("変更を中止しました。");
				LinePrinter.printShortLine();
				continue;
			}

			//			最終、再チェック。 自分自身のコードは重複扱いにしない
			if (!newCode.equals(target.getCode()) && queryService.existsByCode(newCode)) {
				LinePrinter.printLine();
				TextFormatter.printError("既に使用されています。新しい商品コードを設定してください。");
				return;
			}

			// 更新処理
			Product updated = commandService.updateProduct(
					target,
					newCode,
					newName,
					newCategory,
					newSellingPrice,
					newPurchasePrice,
					newRegistrationDate);

			if (updated == null) {
				TextFormatter.printError("他のユーザーにより更新。変更処理の最初からやり直し。");
				return;
			}

			LinePrinter.printShortLine();
			System.out.println("商品情報を変更しました。");
			ProductPrinter.printActionResult("変更", updated);

		} while (continuePrompt.ask("変更"));

		return;
	}
}
