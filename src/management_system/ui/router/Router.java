package management_system.ui.router;

import management_system.ui.controller.DeleteController;
import management_system.ui.controller.RegisterController;
import management_system.ui.controller.SearchController;
import management_system.ui.controller.UpdateController;
import management_system.ui.view.TextFormatter;

public class Router {

	private final RegisterController register;
	private final SearchController search;
	private final UpdateController update;
	private final DeleteController delete;

	public Router(
			RegisterController register,
			SearchController search,
			UpdateController update,
			DeleteController delete) {

		this.register = register;
		this.search = search;
		this.update = update;
		this.delete = delete;
	}

	public ActionResult dispatch(String selected) {

		switch (selected) {

		case "1": // 検索
			search.searchProduct();
			return ActionResult.CONTINUE;

		case "2": // 登録

			register.registerProduct();
			return ActionResult.CONTINUE;

		case "3": // 変更

			update.updateProduct();
			return ActionResult.CONTINUE;

		case "4": // 削除

			delete.deleteProduct();
			return ActionResult.CONTINUE;

		case "0": // 終了
			return ActionResult.EXIT;

		default:
			TextFormatter.printError("メニュー番号を入力してください。");
			return ActionResult.CONTINUE;
		}
	}
}
