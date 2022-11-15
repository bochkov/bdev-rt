package sb.bdev.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class JdbcTemplate extends Jdbc0 {

    private final DataSource ds;

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
