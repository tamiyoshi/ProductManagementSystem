package management_system.data.db.postgres;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import management_system.data.ConfigManager;
import management_system.data.ErrorHandler;
import management_system.domain.model.Product;
import management_system.domain.repository.ProductRepository;

public class PostgresRepository implements ProductRepository {

	/**
	 * @author 川上
	 */
	@Override
	public List<Product> findAll() {
		ArrayList<Product> list = new ArrayList<>();

		String sql = """
				SELECT *
				FROM product
				WHERE deleted = '0'
				""";

		try (Connection conn = ConfigManager.connect()) {
			try (PreparedStatement ps = conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					list.add(mapRow(rs));
				}

			} catch (SQLException e) {
				ErrorHandler.handleSQLException(e);
			}
		} catch (SQLException e) {
			ErrorHandler.handleConnectionException(e);
		}

		return list;
	}

	/**
	 * @author 松本
	 */
	@Override
	public Optional<Product> findById(String id) {

		String sql = """
				SELECT *
				FROM product
				WHERE id = ?
				AND deleted = '0'
				""";

		try (Connection conn = ConfigManager.connect()) {
			try (PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setString(1, id);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						return Optional.of(mapRow(rs));
					}
				}

			} catch (SQLException e) {
				ErrorHandler.handleConnectionException(e);
			}
		} catch (SQLException e) {
			ErrorHandler.handleSQLException(e);
		}

		return Optional.empty();
	}

	/**
	 * @author 松本
	 */
	@Override
	public Optional<Product> findByCode(String code) {

		String sql = """
				SELECT *
				FROM product
				WHERE code = ?
				AND deleted = '0'
				""";

		try (Connection conn = ConfigManager.connect()) {
			try (PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setString(1, code);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						return Optional.of(mapRow(rs));
					}
				}

			} catch (SQLException e) {
				ErrorHandler.handleConnectionException(e);
			}
		} catch (SQLException e) {
			ErrorHandler.handleSQLException(e);
		}

		return Optional.empty();
	}

	/**
	 * @author 大内
	 */
	@Override
	public void add(Product p) {

		String sql = """
				INSERT INTO product (
				    id,
				    code,
				    name,
				    category,
				    sale_price,
				    purchase_price,
				    registration_date,
				    deleted,
				    version_no,
				    record_creation_timestamp,
				    record_update_timestamp
				)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";

		try (Connection conn = ConfigManager.connect()) {
			try (PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setString(1, p.getId());
				ps.setString(2, p.getCode());
				ps.setString(3, p.getName());
				ps.setString(4, p.getCategory());

				ps.setObject(5, p.getSalePrice());
				ps.setObject(6, p.getPurchasePrice());

				ps.setDate(7, toSqlDate(p.getRegistrationDate()));

				ps.setString(8, p.getDeleted());
				ps.setInt(9, p.getVersionNo());

				ps.setTimestamp(10, toTimestamp(p.getRecordCreationTimestamp()));
				ps.setTimestamp(11, toTimestamp(p.getRecordUpdateTimestamp()));

				ps.executeUpdate();

			} catch (SQLException e) {
				ErrorHandler.handleConnectionException(e);
			}
		} catch (SQLException e) {
			ErrorHandler.handleSQLException(e);
		}
	}

	/**
	 * @author 松元
	 */
	@Override
	public boolean update(Product p) {

		String sql = """
				UPDATE product
				SET
				    code = ?,
				    name = ?,
				    category = ?,
				    sale_price = ?,
				    purchase_price = ?,
				    registration_date = ?,
				    version_no = version_no + 1,               -- ★ 楽観ロック：version を +1
				    record_update_timestamp = CURRENT_TIMESTAMP
				WHERE
				    id = ?
				    AND version_no = ?                         -- ★ 楽観ロック：一致チェック
				    AND deleted = '0'
				""";

		try (Connection conn = ConfigManager.connect()) {
			try (PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setString(1, p.getCode());
				ps.setString(2, p.getName());
				ps.setString(3, p.getCategory());
				ps.setObject(4, p.getSalePrice());
				ps.setObject(5, p.getPurchasePrice());
				ps.setDate(6, toSqlDate(p.getRegistrationDate()));
				ps.setString(7, p.getId());
				ps.setInt(8, p.getVersionNo());

				int updatedRows = ps.executeUpdate();

				if (updatedRows == 0) {
					return false; // 他ユーザーが更新済み
				}

				// 　version_no を +1 した新しい値を反映
				p.setVersionNo(p.getVersionNo() + 1);

				return true;

			} catch (SQLException e) {
				ErrorHandler.handleConnectionException(e);
			}
		} catch (SQLException e) {
			ErrorHandler.handleSQLException(e);
		}

		return false;
	}

	/**
	 * @author 岩永
	 */
	@Override
	public boolean delete(String id) {

		String sql = """
				UPDATE product
				SET
				    deleted = '1',
				    record_update_timestamp = CURRENT_TIMESTAMP
				WHERE
				    id = ?
				    AND deleted = '0'
				""";

		try (Connection conn = ConfigManager.connect()) {
			try (PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setString(1, id);

				int deletedRows = ps.executeUpdate();
				return deletedRows > 0;

			} catch (SQLException e) {
				ErrorHandler.handleConnectionException(e);
			}
		} catch (SQLException e) {
			ErrorHandler.handleSQLException(e);
		}

		return false;
	}

	/**
	 * @author 松本
	 */
	private Product mapRow(ResultSet rs) throws SQLException {

		Integer salePrice = (Integer) rs.getObject("sale_price");
		Integer purchasePrice = (Integer) rs.getObject("purchase_price");

		Date regDate = rs.getDate("registration_date");
		LocalDate registrationDate = regDate != null ? regDate.toLocalDate() : null;

		String deleted = rs.getString("deleted");
		Integer versionNo = rs.getInt("version_no");

		Timestamp createdTs = rs.getTimestamp("record_creation_timestamp");
		LocalDateTime created = createdTs != null ? createdTs.toLocalDateTime() : null;

		Timestamp updatedTs = rs.getTimestamp("record_update_timestamp");
		LocalDateTime updated = updatedTs != null ? updatedTs.toLocalDateTime() : null;

		return new Product(
				rs.getString("id"),
				rs.getString("code"),
				rs.getString("name"),
				rs.getString("category"),
				salePrice,
				purchasePrice,
				registrationDate,
				deleted,
				versionNo,
				created,
				updated);
	}

	/**
	 * @author 松本
	 */
	// LocalDate → java.sql.Date
	private java.sql.Date toSqlDate(LocalDate date) {
		return date == null ? null : java.sql.Date.valueOf(date);
	}

	/**
	 * @author 松本
	 */
	// LocalDateTime → Timestamp
	private Timestamp toTimestamp(LocalDateTime dt) {
		return dt == null ? null : Timestamp.valueOf(dt);
	}
}
