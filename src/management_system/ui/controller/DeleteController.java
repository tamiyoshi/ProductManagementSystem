package management_system.ui.controller;

import management_system.domain.model.Product;
import management_system.domain.service.ProductCommandService;
import management_system.domain.service.ProductQueryService;
import management_system.ui.input.InputResult;
import management_system.ui.input.InputValidator;
import management_system.ui.view.ContinuePrompt;
import management_system.ui.view.LinePrinter;
import management_system.ui.view.ProductPrinter;
import management_system.ui.view.TextFormatter;

public class DeleteController {

	private final ProductQueryService queryService;
	private final ProductCommandService commandService;
	private final InputValidator validator;
	private final ContinuePrompt continuePrompt;

	public DeleteController(
			ProductCommandService commandService,
			ProductQueryService queryService,
			InputValidator validator,
			ContinuePrompt continuePrompt) {

		this.queryService = queryService;
		this.commandService = commandService;
		this.validator = validator;
		this.continuePrompt = continuePrompt;
	}

	public void deleteProduct() {

		do {

			LinePrinter.printShortLine();
			System.out.println("商品情報を削除します。");

			// ============================
			// 削除対象の商品検索
			// ============================

			Product target = null;

			while (target == null) {

				System.out.println("削除する商品IDを入力してください。");

				InputResult<String> idResult = validator.inputProductId();

				if (idResult.getStatus() == InputResult.Status.EXIT)

					return;

				if (idResult.getStatus() == InputResult.Status.INVALID)
					continue;

				String id = idResult.getValue();

				target = queryService.findById(id).orElse(null);

				if (target == null) {
					LinePrinter.printLine();
					TextFormatter.printError("存在しないIDです。商品IDを確認してください。");
				}
			}

			// ============================
			// 削除対象の商品表示
			// ============================

			LinePrinter.printResultLine();
			ProductPrinter.printSingle(target);
			LinePrinter.printResultLine();

			// ============================
			// 削除確認
			// ============================

			if (!validator.confirmYN("本当に削除しますか？")) {
				LinePrinter.printResultLine();
				System.out.println("削除を中止しました。");
				LinePrinter.printShortLine();
				continue;
			}

			// ============================
			// 削除処理
			// ============================

			Product deleted = commandService.deleteProduct(target.getId());

			if (deleted == null) {
				TextFormatter.printError("他ユーザーにより更新。削除処理の最初からやり直してください。");
				return;
			}
			//	

			LinePrinter.printShortLine();
			System.out.println("商品情報を削除しました");

			ProductPrinter.printActionResult("削除", deleted);

		} while (continuePrompt.ask("削除"));

		return;

	}
}
