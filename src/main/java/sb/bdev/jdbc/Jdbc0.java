package sb.bdev.jdbc;

import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public abstract class Jdbc0 implements Jdbc {

    private final Connection con;

    @Override
    public void update(PreparedStatementCreator psc) throws SQLException {
        try (PreparedStatement ps = psc.statement(con)) {
            ps.executeUpdate();
        }
    }

    @Override
    public void queryRow(String sql, RowCallback rowCallback) throws SQLException {
        try (Statement st = con.createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next())
                    rowCallback.processRow(rs);
            }
        }
    }

    @Override
    public void queryRow(PreparedStatementCreator psc, RowCallback rowCallback) throws SQLException {
        try (PreparedStatement ps = psc.statement(con)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    rowCallback.processRow(rs);
            }
        }
    }

    @Override
    public <T> T query(PreparedStatementCreator psc, ExtractedData<T> extractedData, T defaultValue) throws SQLException {
        try (PreparedStatement ps = psc.statement(con)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return extractedData.extractData(rs);
            }
        }
        return defaultValue;
    }

    @Override
    public void execute(String sql) throws SQLException {
        try (Statement st = con.createStatement()) {
            st.setEscapeProcessing(false);
            st.execute(sql);
        }
    }

    @Override
    public void execute(PreparedStatementCreator psc) throws SQLException {
        try (PreparedStatement ps = psc.statement(con)) {
            ps.execute();
        }
    }

    @Override
    public void call(CallableStatementCreator csc) throws SQLException {
        try (CallableStatement cs = csc.statement(con)) {
            cs.execute();
        }
    }

    @Override
    public Map<String, Object> callQuery(CallableStatementCreator csc) throws SQLException {
        Map<String, Object> result = new HashMap<>();
        try (CallableStatement cs = csc.statement(con)) {
            cs.execute();
            result.put("result", cs.getObject(1, String.class));
        }
        return result;
    }

    @Override
    public void close() {
        try {
            con.close();
        } catch (SQLException ex) {
            //
        }
    }
}
