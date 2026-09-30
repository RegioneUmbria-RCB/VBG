package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ForumDAO;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.Forum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ForumService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class ForumServiceImpl extends BaseServiceImpl<Forum, PkId> implements ForumService {

    private ForumDAO forumDAO;

    @Autowired
    public void setForumDAO(ForumDAO forumDAO) {

	this.forumDAO = forumDAO;
    }

    @Override
    protected Class<Forum> getEntityClass() {

	return Forum.class;
    }

    @Override
    public List<Forum> findAll(Integer firstResult, Integer maxResult) {

	return forumDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Forum entity) {

	if (validateEntity(entity)) {
	    forumDAO.insert(entity);
	}
    }

    @Override
    public Forum findById(PkId id) {

	return forumDAO.findById(id);
    }

    @Override
    public void update(Forum entity) {

	if (validateEntity(entity)) {
	    forumDAO.update(entity);
	}
    }

    @Override
    public void delete(Forum entity) {

	if (isDeleteAllowed(entity)) {
	    forumDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Faq entity) {

	boolean delete = true;
	return delete;
    }
}
