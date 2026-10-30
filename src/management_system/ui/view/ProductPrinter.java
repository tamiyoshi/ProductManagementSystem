package management_system.ui.view;

import management_system.domain.model.Product;

public class ProductPrinter {

	public static void printActionResult(String actionName, Product p) {

		System.out.println("▼" + actionName + "した商品");
		LinePrinter.printResultLine();
		printSingle(p);
		LinePrinter.printResultLine();
	}

	public static void printCategory() {
		System.out.println(
				TextFormatter.padRight("商品ID", 15) +
						TextFormatter.padRight("商品コード", 19) +
						TextFormatter.padRight("商品名", 20) +
						TextFormatter.padRight("商品分類", 17) +
						TextFormatter.padRight("販売単価", 15) +
						TextFormatter.padRight("仕入単価", 15) +
						TextFormatter.padRight("登録日", 15));
	}

	public static void printSingle(Product p) {

		System.out.println(p.toString());

	}

	public static void print(Product p) {

		String id = TextFormatter.padRight(p.getId(), 15);
		String code = TextFormatter.padRight(p.getCode(), 19);
		String name = TextFormatter.padRight(p.getName(), 20);
		String category = TextFormatter.padRight(p.getCategory(), 17);
		String sell = TextFormatter.padRight(p.getSalePrice(), 15);
		String buy = TextFormatter.padRight(p.getPurchasePrice(), 15);
		String created = TextFormatter.padRight(p.getRegistrationDate(), 15);

		System.out.println(id + code + name + category + sell + buy + created);
	}
}
