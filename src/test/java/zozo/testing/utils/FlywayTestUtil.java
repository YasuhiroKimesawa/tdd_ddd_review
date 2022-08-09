package zozo.testing.utils;

import org.flywaydb.core.Flyway;
import org.testcontainers.containers.MySQLContainer;

public class FlywayTestUtil {

  public static void migrate(MySQLContainer mysqlContainer) {
    var flyway =
        Flyway.configure()
            .dataSource(
                mysqlContainer.getJdbcUrl(),
                mysqlContainer.getUsername(),
                mysqlContainer.getPassword())
            .locations("rdb-migration")
            .load();
    flyway.migrate();
  }
}
