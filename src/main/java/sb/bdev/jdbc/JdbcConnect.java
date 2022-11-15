package sb.bdev.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class JdbcConnect extends Jdbc0 {

    private final DBConnection con;

    @Override
    public Connection getConnection() throws SQLException {
        return con.connection();
    }
}
