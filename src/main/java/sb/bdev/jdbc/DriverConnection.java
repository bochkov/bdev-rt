package sb.bdev.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DriverConnection implements DBConnection {

    private final String url;
    private final String user;
    private final String password;

    @Override
    public Connection connection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
