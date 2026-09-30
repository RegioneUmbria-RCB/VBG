package it.gruppoinit.pal.gp.core.features.ftp;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AccountFtp;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class AccountFTPDAOImpl extends BaseDAOImpl<AccountFtp, PkId> implements IAccountFTPDAO {

    @Override
    public Class<AccountFtp> getEntityClass() {

	return AccountFtp.class;
    }
}
