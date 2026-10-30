package management_system.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConfigManager {

	private static final Properties PROPERTIES = new Properties();

	static {
		try (InputStream input = ConfigManager.class.getClassLoader()
				.getResourceAsStream("app.properties")) {

			if (input == null) {
				throw new IllegalStateException("app.propertiesが見つかりません。");
			}

			PROPERTIES.load(input);

			Class.forName(PROPERTIES.getProperty("db.driver"));

		} catch (IOException | ClassNotFoundException e) {
			throw new IllegalStateException("DB設定の読み込みに失敗しました。", e);
		}
	}

	public static Connection connect() throws SQLException {

		String url = PROPERTIES.getProperty("db.url");
		String user = PROPERTIES.getProperty("db.user");
		String password = PROPERTIES.getProperty("db.password");

		return DriverManager.getConnection(url, user, password);
	}
}