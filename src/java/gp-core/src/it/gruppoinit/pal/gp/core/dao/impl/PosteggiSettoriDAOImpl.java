package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.PosteggiSettoriDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PosteggiSettori;

import org.springframework.stereotype.Repository;

@Repository
public class PosteggiSettoriDAOImpl extends BaseDAOImpl<PosteggiSettori, PkId> implements PosteggiSettoriDAO {

    @Override
    public Class<PosteggiSettori> getEntityClass() {

	return PosteggiSettori.class;
    }
}
