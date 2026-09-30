package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentMergeHelper;

public class DocumentMergeCommand {

    private Letteretipo letteraTipo;
    private DocumentMergeHelper userOptions;

    public DocumentMergeCommand() {

	this.letteraTipo = new Letteretipo();
	this.userOptions = new DocumentMergeHelper();
    }

    /**
     * @return the userOptions
     */
    public DocumentMergeHelper getUserOptions() {

	return userOptions;
    }

    /**
     * @param userOptions
     *            the userOptions to set
     */
    public void setUserOptions(DocumentMergeHelper userOptions) {

	this.userOptions = userOptions;
    }

    /**
     * @return the letteraTipo
     */
    public Letteretipo getLetteraTipo() {

	return letteraTipo;
    }

    /**
     * @param letteraTipo
     *            the letteraTipo to set
     */
    public void setLetteraTipo(Letteretipo letteraTipo) {

	this.letteraTipo = letteraTipo;
    }
}
