package zozo.testing.utils;

import java.io.IOException;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testcontainers.containers.MySQLContainer;

public class SqlSessionFactoryUtil {

  public static SqlSessionFactory build(MySQLContainer mysqlContainer) throws IOException {
    var properties = new Properties();
    properties.put("driver", mysqlContainer.getDriverClassName());
    properties.put("url", mysqlContainer.getJdbcUrl());
    properties.put("userName", mysqlContainer.getUsername());
    properties.put("password", mysqlContainer.getPassword());
    return new SqlSessionFactoryBuilder()
        .build(Resources.getResourceAsStream("mybatis-config.xml"), properties);
  }
}
