package management_system.domain.service;

import java.time.LocalDate;
import java.util.Optional;

import management_system.domain.model.Product;
import management_system.domain.repository.ProductRepository;

public class ProductCommandService {

	private final ProductRepository repository;

	public ProductCommandService(ProductRepository repository) {
		this.repository = repository;
	}

	// 商品登録
	public Product registerProduct(
			String id,
			String code,
			String name,
			String category,
			Integer sellingPrice, // null = 未設定
			Integer purchasePrice, // null = 未設定
			LocalDate registrationDate // ★ LocalDate に変更
	) {

		Product p = new Product(
				id,
				code,
				name,
				category,
				sellingPrice,
				purchasePrice,
				registrationDate);

		repository.add(p);
		return p;
	}

	// 商品更新
	public Product updateProduct(
			Product target,
			String code,
			String name,
			String category,
			Integer sellingPrice,
			Integer purchasePrice,
			LocalDate registrationDate) {

		target.setCode(code);
		target.setName(name);
		target.setCategory(category);
		target.setSalePrice(sellingPrice);
		target.setPurchasePrice(purchasePrice);
		target.setRegistrationDate(registrationDate);

		boolean updated = repository.update(target);

		if (!updated) {
			return null;
		}
		return target;
	}

	// 商品削除
	public Product deleteProduct(String id) {

		Optional<Product> opt = repository.findById(id);

		if (opt.isEmpty()) {
			return null;
		}

		Product target = opt.get();

		boolean deleted = repository.delete(id);

		if (!deleted) {
			return null;
		}

		return target;
	}

	public ProductRepository getRepository() {
		return repository;
	}
}
