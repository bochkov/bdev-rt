package sb.bdev.jdbc;

import javax.sql.DataSource;
import java.sql.SQLException;

public final class JdbcTemplate extends Jdbc0 {
    public JdbcTemplate(DataSource ds) throws SQLException {
        super(ds.getConnection());
    }
}
