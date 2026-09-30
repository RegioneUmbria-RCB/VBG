package it.gruppoinit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface JDBCConnectioDAO {

    /**
     * La connessione viene effettuata tramite la stringa jdbc recuperata da deploy.properties, sul file viene
     * utilizzara la stringa di connessione che non utilizza l'LDAP
     * 
     * @return
     */
    public Connection getSimpleConnectionBackend();

    public void gracefulReleaseConnections(ResultSet rs, PreparedStatement pstmt, Connection conn);

    public void gracefulReleaseConnections(Connection conn);
}
