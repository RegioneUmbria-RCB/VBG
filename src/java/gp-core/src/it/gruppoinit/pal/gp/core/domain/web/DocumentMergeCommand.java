package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;


public class DocumentMergeCommand {
    
    private Istanze istanza;
    private Movimenti movimento;
    private Letteretipo letteraTipo;
    private DocumentMergeHelper userOptions;
    
    
    public DocumentMergeCommand() {

	this.istanza = new Istanze();
	this.movimento = new Movimenti();
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
     * @param userOptions the userOptions to set
     */
    public void setUserOptions(DocumentMergeHelper userOptions) {
    
        this.userOptions = userOptions;
    }

    
    /**
     * @return the istanza
     */
    public Istanze getIstanza() {
    
        return istanza;
    }

    
    /**
     * @param istanza the istanza to set
     */
    public void setIstanza(Istanze istanza) {
    
        this.istanza = istanza;
    }

    
    /**
     * @return the movimento
     */
    public Movimenti getMovimento() {
    
        return movimento;
    }

    
    /**
     * @param movimento the movimento to set
     */
    public void setMovimento(Movimenti movimento) {
    
        this.movimento = movimento;
    }

    
    /**
     * @return the letteraTipo
     */
    public Letteretipo getLetteraTipo() {
    
        return letteraTipo;
    }

    
    /**
     * @param letteraTipo the letteraTipo to set
     */
    public void setLetteraTipo(Letteretipo letteraTipo) {
    
        this.letteraTipo = letteraTipo;
    }
    
    
    
}
