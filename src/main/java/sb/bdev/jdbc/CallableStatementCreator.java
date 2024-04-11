package sb.bdev.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public interface CallableStatementCreator {

    CallableStatement statement(Connection con) throws SQLException;

}
