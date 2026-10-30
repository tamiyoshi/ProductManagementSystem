package management_system.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Product {

	private String id;
	private String code;
	private String name;
	private String category;

	private Integer salePrice;
	private Integer purchasePrice;
	private LocalDate registrationDate;

	private String deleted;
	private Integer versionNo;
	private LocalDateTime recordCreationTimestamp;
	private LocalDateTime recordUpdateTimestamp;

	public Product(
			String id,
			String code,
			String name,
			String category,
			Integer salePrice,
			Integer purchasePrice,
			LocalDate registrationDate) {

		this.id = id;
		this.code = code;
		this.name = name;
		this.category = category;
		this.salePrice = salePrice;
		this.purchasePrice = purchasePrice;
		this.registrationDate = registrationDate;

		// DB管理項目の初期値
		this.deleted = "0";
		this.versionNo = 1;

		LocalDateTime now = LocalDateTime.now();
		this.recordCreationTimestamp = now;
		this.recordUpdateTimestamp = now;
	}

	public Product(
			String id,
			String code,
			String name,
			String category,
			Integer salePrice,
			Integer purchasePrice,
			LocalDate registrationDate,
			String deleted,
			Integer versionNo,
			LocalDateTime recordCreationTimestamp,
			LocalDateTime recordUpdateTimestamp) {

		this.id = id;
		this.code = code;
		this.name = name;
		this.category = category;
		this.salePrice = salePrice;
		this.purchasePrice = purchasePrice;
		this.registrationDate = registrationDate;
		this.deleted = deleted;
		this.versionNo = versionNo;
		this.recordCreationTimestamp = recordCreationTimestamp;
		this.recordUpdateTimestamp = recordUpdateTimestamp;
	}

	// ===== Getter =====

	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public Integer getSalePrice() {
		return salePrice;
	}

	public Integer getPurchasePrice() {
		return purchasePrice;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public String getDeleted() {
		return deleted;
	}

	public Integer getVersionNo() {
		return versionNo;
	}

	public LocalDateTime getRecordCreationTimestamp() {
		return recordCreationTimestamp;
	}

	public LocalDateTime getRecordUpdateTimestamp() {
		return recordUpdateTimestamp;
	}

	// ===== Setter =====

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

	public void setPurchasePrice(Integer purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public void setRecordCreationTimestamp(LocalDateTime recordCreationTimestamp) {
		this.recordCreationTimestamp = recordCreationTimestamp;
	}

	public void setRecordUpdateTimestamp(LocalDateTime recordUpdateTimestamp) {
		this.recordUpdateTimestamp = recordUpdateTimestamp;
	}

	@Override
	public String toString() {
		return String.format(
				"商品ID = %s%n商品コード = %s%n商品名 =%s%n分類 = %s%n販売単価 = %s%n仕入単価 = %s%n登録日 = %s",
				id,
				code,
				name,
				category,
				salePrice,
				purchasePrice,
				registrationDate);
	}
}
