package it.gruppoinit.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.dao.JDBCConnectioDAO;
import it.gruppoinit.dao.QuerybackofficeDAO;
import it.gruppoinit.domain.helper.ModuloHelper;

@Repository
public class QuerybackofficeDAOImpl implements QuerybackofficeDAO {

    private static Logger log = LoggerFactory.getLogger(QuerybackofficeDAOImpl.class);
    private JDBCConnectioDAO jDBCConnectioDAO;

    @Autowired
    public void setjDBCConnectioDAO(JDBCConnectioDAO jDBCConnectioDAO) {

	this.jDBCConnectioDAO = jDBCConnectioDAO;
    }

    public List<ModuloHelper> findCodici(String idComune, List<String> codici) {

	Connection connection = jDBCConnectioDAO.getSimpleConnectionBackend();
	QueryRunner queryRunner = new QueryRunner();
	ResultSetHandler<List<ModuloHelper>> h = new BeanListHandler<ModuloHelper>(ModuloHelper.class);
	List<ModuloHelper> moduli = new ArrayList<ModuloHelper>();
	try {
	    String query = "SELECT" + //
			   "  inventarioprocedimentipeople.idcomune, replace(inventarioprocedimentipeople.cod_proc_people, 'INFOCAMEREINFOCAMERE','') as codProcPeople" + //
			   ", inventarioprocedimentipeople.codiceinventario, inventarioprocedimenti.procedimento" +
			   ", amministrazioni.codiceamministrazione, amministrazioni.amministrazione" + //
			   " FROM inventarioprocedimentipeople" + //
			   " INNER JOIN inventarioprocedimenti ON" + //
			   " inventarioprocedimentipeople.idcomune =inventarioprocedimenti.idcomune" +
			   " AND inventarioprocedimentipeople.codiceinventario=inventarioprocedimenti.codiceinventario" + //
			   " INNER JOIN alberoproc_endo ON" + //
			   " inventarioprocedimenti.idcomune=alberoproc_endo.idcomune" +
			   " AND inventarioprocedimenti.codiceinventario=alberoproc_endo.codiceinventario" + //
			   " INNER JOIN amministrazioni ON" + " amministrazioni.idcomune=inventarioprocedimenti.idcomune" +
			   " AND amministrazioni.codiceamministrazione=inventarioprocedimenti.amministrazione" + //
			   " WHERE" + " inventarioprocedimentipeople.idcomune='" + StringUtils.upperCase(idComune) + "'" + //
			   " AND cod_proc_people IN (" + setINInQuery(codici) + ")" + //
			   "GROUP BY" + "  inventarioprocedimentipeople.idcomune" + ", inventarioprocedimentipeople.cod_proc_people" +
			   ", inventarioprocedimentipeople.codiceinventario" + ", inventarioprocedimenti.procedimento" +
			   ", amministrazioni.codiceamministrazione" + ", amministrazioni.amministrazione";
	    moduli = queryRunner.query(connection, query, h);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    jDBCConnectioDAO.gracefulReleaseConnections(connection);
	}
	return moduli;
    }

    private String setINInQuery(List<String> codici) {

	if (codici == null) {
	    throw new RuntimeException("Impossibile eseguire la query senza ricevere i codici in input");
	}
	StringBuffer sb = new StringBuffer();
	int i = 0;
	for (String string : codici) {
	    sb.append("'INFOCAMEREINFOCAMERE").append(string).append("'");
	    i++;
	    if (i < codici.size()) {
		sb.append(", ");
	    }
	}
	return sb.toString();
    }
}
