package management_system.application;

import java.util.Scanner;

import management_system.data.db.postgres.PostgresRepository;
import management_system.domain.repository.ProductRepository;
import management_system.domain.service.ProductCommandService;
import management_system.domain.service.ProductQueryService;
import management_system.ui.controller.DeleteController;
import management_system.ui.controller.RegisterController;
import management_system.ui.controller.SearchController;
import management_system.ui.controller.UpdateController;
import management_system.ui.input.InputValidator;
import management_system.ui.router.ActionResult;
import management_system.ui.router.Router;
import management_system.ui.view.ContinuePrompt;
import management_system.ui.view.Menu;

public class Application {

	public void start() {

		Scanner scanner = new Scanner(System.in);

		ProductRepository repository = new PostgresRepository();

		ProductQueryService queryService = new ProductQueryService(repository);

		ProductCommandService commandService = new ProductCommandService(repository);

		InputValidator validator = new InputValidator(scanner);

		ContinuePrompt continuePrompt = new ContinuePrompt(scanner);

		Menu menu = new Menu(scanner);

		RegisterController register = new RegisterController(
				commandService,
				queryService,
				validator,
				continuePrompt);

		SearchController search = new SearchController(
				queryService,
				validator,
				continuePrompt);

		UpdateController update = new UpdateController(
				commandService,
				queryService,
				validator,
				continuePrompt);

		DeleteController delete = new DeleteController(
				commandService,
				queryService,
				validator,
				continuePrompt);

		Router router = new Router(
				register,
				search,
				update,
				delete);

		menu.showVersion();

		while (true) {

			menu.showMenu();

			String selected = menu.input();

			ActionResult result = router.dispatch(selected);

			if (result == ActionResult.EXIT) {
				System.out.println("プログラムを終了します。");
				break;
			}
		}
	}

}
