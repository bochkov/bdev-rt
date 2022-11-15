package sb.bdev.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnection {

    Connection connection() throws SQLException;

}
