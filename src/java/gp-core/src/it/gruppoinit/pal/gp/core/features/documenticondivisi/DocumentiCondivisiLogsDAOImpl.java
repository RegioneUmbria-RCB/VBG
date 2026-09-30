package it.gruppoinit.pal.gp.core.features.documenticondivisi;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.DocumentiCondivisiLogs;

@Repository
public class DocumentiCondivisiLogsDAOImpl extends BaseDAOImpl implements IDocumentiCondivisiLogsDAO {

    @Override
    public Class getEntityClass() {

	return DocumentiCondivisiLogs.class;
    }
}
