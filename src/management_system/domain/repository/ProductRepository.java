package management_system.domain.repository;

import java.util.List;
import java.util.Optional;

import management_system.domain.model.Product;

public interface ProductRepository {

	List<Product> findAll();

	Optional<Product> findById(String id);

	Optional<Product> findByCode(String code);

	void add(Product product);

	boolean update(Product product);

	boolean delete(String id);
}
