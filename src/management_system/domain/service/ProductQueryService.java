package management_system.domain.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import management_system.domain.model.Product;
import management_system.domain.repository.ProductRepository;

public class ProductQueryService {

	private final ProductRepository repository;

	public ProductQueryService(ProductRepository repository) {
		this.repository = repository;
	}

	// 共通ソート
	private List<Product> sortById(List<Product> list) {
		return list.stream()
				.sorted(Comparator.comparing(Product::getId))
				.toList();
	}

	// 全件取得
	public List<Product> getAllProducts() {
		return sortById(repository.findAll());
	}

	// Optional で返す
	public Optional<Product> findById(String id) {
		return repository.findById(id);
	}

	//ID重複確認、存在するならtrue、ないならfaiseをかえす
	public boolean existsById(String id) {
		return repository.findById(id).isPresent();
	}

	//code重複確認、存在するならtrue、ないならfaiseをかえす
	public boolean existsByCode(String code) {
		return repository.findByCode(code).isPresent();
	}

	// キーワード検索
	public List<Product> searchByKeyword(String keyword) {

		List<Product> list = repository.findAll();

		if (keyword == null || keyword.isEmpty()) {
			return sortById(list);
		}

		String lower = keyword.toLowerCase();

		List<Product> filtered = list.stream()
				.filter(p -> p.getId().toLowerCase().contains(lower)
						|| p.getCode().toLowerCase().contains(lower)
						|| p.getName().toLowerCase().contains(lower))
				.toList();

		return sortById(filtered);
	}
}
