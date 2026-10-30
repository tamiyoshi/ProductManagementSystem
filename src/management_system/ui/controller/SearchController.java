package management_system.ui.controller;

import java.util.List;

import management_system.domain.model.Product;
import management_system.domain.service.ProductQueryService;
import management_system.ui.input.InputResult;
import management_system.ui.input.InputValidator;
import management_system.ui.view.ContinuePrompt;
import management_system.ui.view.LinePrinter;
import management_system.ui.view.ProductPrinter;

public class SearchController {

	private final ProductQueryService queryService;
	private final InputValidator validator;
	private final ContinuePrompt continuePrompt;

	public SearchController(
			ProductQueryService queryService,
			InputValidator validator,
			ContinuePrompt continuePrompt) {

		this.queryService = queryService;
		this.validator = validator;
		this.continuePrompt = continuePrompt;
	}

	public void searchProduct() {

		do {

			System.out.println("商品情報を検索します。");
			System.out.println("検索キーワードを入力してください。");
			LinePrinter.printShortLine();
			// ============================
			// キーワード入力（任意）
			// 空欄 → 全件表示
			// ============================

			InputResult<String> keywordResult = validator.inputSearch();

			if (keywordResult.getStatus() == InputResult.Status.EXIT)
				return;

			String keyword = keywordResult.getValue();

			// ============================
			// 検索処理
			// ============================

			List<Product> results = queryService.searchByKeyword(keyword);

			// ============================
			// 検索結果表示
			// ============================

			System.out.println("検索結果は" + results.size() + "件です。");

			LinePrinter.printResultLine();

			ProductPrinter.printCategory();

			LinePrinter.printResultLine();
			for (Product p : results) {
				ProductPrinter.print(p);
			}
			LinePrinter.printResultLine();

		} while (continuePrompt.ask("検索"));

		return;

	}
}
