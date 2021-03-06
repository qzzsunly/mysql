package cn.sexycode.sql.type;


import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * Optional {@link Type} contract for implementations enabled
 * to set store procedure OUT/INOUT parameters values by name.
 *
 */
public interface ProcedureParameterNamedBinder {

    /**
     * Can the given instance of this type actually set the parameter value by name
     *
     * @return {@code true} indicates that @{link #nullSafeSet} calls will not fail
     */
    boolean canDoSetting();

    /**
     * Bind a value to the JDBC prepared statement, ignoring some columns as dictated by the 'settable' parameter.
     * Implementors should handle the possibility of null values.
     * Does not support multi-column type
     *
     * @param statement The CallableStatement to which to bind
     * @param value     the object to write
     * @param name      parameter bind name
     * @param session   The originating session
     * @throws HibernateException An error from Hibernate
     * @throws SQLException       An error from the JDBC driver
     */
    void nullSafeSet(CallableStatement statement, Object value, String name) throws SQLException;
}
