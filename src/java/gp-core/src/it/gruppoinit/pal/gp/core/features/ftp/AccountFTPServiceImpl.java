package it.gruppoinit.pal.gp.core.features.ftp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.AccountFtp;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class AccountFTPServiceImpl extends BaseServiceImpl<AccountFtp, PkId> implements IAccountFTPService {

    private IAccountFTPDAO accountFTPDAO;

    @Autowired
    public AccountFTPServiceImpl(IAccountFTPDAO accountFTPDAO) {

	this.accountFTPDAO = accountFTPDAO;
    }

    @Override
    public AccountFtp findById(Integer id) {

	return this.accountFTPDAO.findById(new PkId(id));
    }

    @Override
    public void insert(AccountFtp entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(AccountFtp entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(AccountFtp entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<AccountFtp> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public AccountFtp findById(PkId id) {

	throw new NotImplementedException();
    }

    @Override
    protected Class<AccountFtp> getEntityClass() {

	throw new NotImplementedException();
    }
}
