package sb.bdev.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ExtractedData<T> {

    T extractData(ResultSet rs) throws SQLException;

}
