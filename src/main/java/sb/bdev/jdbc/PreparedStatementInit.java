package sb.bdev.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementInit {

    PreparedStatement statement(Connection con) throws SQLException;

}
