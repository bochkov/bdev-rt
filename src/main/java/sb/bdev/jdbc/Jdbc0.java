package sb.bdev.jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Jdbc0 implements Jdbc {

    abstract Connection getConnection() throws SQLException;

    @Override
    public void close() {
        //
    }

    @Override
    public void update(PreparedStatementInit psi) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement ps = psi.statement(con)) {
            ps.executeUpdate();
        }
    }

    @Override
    public void queryRow(String sql, RowCallback rowCallback) throws SQLException {
        try (Connection con = getConnection();
             Statement st = con.createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next())
                    rowCallback.processRow(rs);
            }
        }
    }

    @Override
    public void queryRow(PreparedStatementInit psi, RowCallback rowCallback) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement ps = psi.statement(con)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    rowCallback.processRow(rs);
            }
        }
    }

    @Override
    public <T> T query(PreparedStatementInit psi, ExtractedData<T> extractedData, T defaultValue) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement ps = psi.statement(con)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return extractedData.extractData(rs);
            }
        }
        return defaultValue;
    }

    @Override
    public void execute(String sql) throws SQLException {
        try (Connection con = getConnection();
             Statement st = con.createStatement()) {
            st.setEscapeProcessing(false);
            st.execute(sql);
        }
    }

    @Override
    public void execute(PreparedStatementInit psi) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement ps = psi.statement(con)) {
            ps.execute();
        }
    }

    @Override
    public void call(CallableStatementInit csi) throws SQLException {
        try (Connection con = getConnection();
             CallableStatement cs = csi.statement(con)) {
            cs.execute();
        }
    }

    @Override
    public Map<String, Object> callQuery(CallableStatementInit csi) throws SQLException {
        Map<String, Object> result = new HashMap<>();
        try (Connection con = getConnection();
             CallableStatement cs = csi.statement(con)) {
            cs.execute();
            result.put("result", cs.getObject(1, String.class));
        }
        return result;
    }
}
