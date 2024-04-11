package sb.bdev.jdbc;

import java.sql.SQLException;
import java.util.Map;

public interface Jdbc extends AutoCloseable {

    void close();

    void update(PreparedStatementCreator psi) throws SQLException;

    void queryRow(String sql, RowCallback rowCallback) throws SQLException;

    void queryRow(PreparedStatementCreator psi, RowCallback rowCallback) throws SQLException;

    <T> T query(PreparedStatementCreator psi, ExtractedData<T> extractedData, T defaultValue) throws SQLException;

    void execute(String sql) throws SQLException;

    void execute(PreparedStatementCreator psi) throws SQLException;

    void call(CallableStatementCreator csi) throws SQLException;

    Map<String, Object> callQuery(CallableStatementCreator csi) throws SQLException;

}
