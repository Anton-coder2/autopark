package MainPackage.orm;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
}
