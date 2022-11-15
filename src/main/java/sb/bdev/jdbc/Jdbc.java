package sb.bdev.jdbc;

import java.sql.SQLException;
import java.util.Map;

public interface Jdbc extends AutoCloseable {

    void close();

    void update(PreparedStatementInit psi) throws SQLException;

    void queryRow(String sql, RowCallback rowCallback) throws SQLException;

    void queryRow(PreparedStatementInit psi, RowCallback rowCallback) throws SQLException;

    <T> T query(PreparedStatementInit psi, ExtractedData<T> extractedData, T defaultValue) throws SQLException;

    void execute(String sql) throws SQLException;

    void execute(PreparedStatementInit psi) throws SQLException;

    void call(CallableStatementInit csi) throws SQLException;

    Map<String, Object> callQuery(CallableStatementInit csi) throws SQLException;

}
