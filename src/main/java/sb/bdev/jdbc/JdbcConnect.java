package sb.bdev.jdbc;

import java.sql.SQLException;

public final class JdbcConnect extends Jdbc0 {
    public JdbcConnect(DBConnection con) throws SQLException {
        super(con.connection());
    }
}
