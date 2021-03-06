package cn.sexycode.sql.type;

/**
 * Marker interface for user types which want to perform custom
 * logging of their corresponding values
 *
 */
public interface LoggableUserType {
    /**
     * Generate a loggable string representation of the collection (value).
     *
     * @param value   The collection to be logged; guarenteed to be non-null and initialized.
     * @param factory The factory.
     * @return The loggable string representation.
     */
    public String toLoggableString(Object value);
}
