package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.Quesiti;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;

public class QuesitiCommand extends BaseCommand {

    public QuesitiCommand() {

	super();
	this.entity = new Quesiti();
	this.faq = new Faq();
    }

    private Quesiti entity;
    private Faq faq;
    private List<Software> softwares;

    public Quesiti getEntity() {

	return entity;
    }

    public void setEntity(Quesiti entity) {

	this.entity = entity;
    }

    public Faq getFaq() {

	return faq;
    }

    public void setFaq(Faq faq) {

	this.faq = faq;
    }

    public List<Software> getSoftwares() {

	return softwares;
    }

    public void setSoftwares(List<Software> softwares) {

	this.softwares = softwares;
    }
}
