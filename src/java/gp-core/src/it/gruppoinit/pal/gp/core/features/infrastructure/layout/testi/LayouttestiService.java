package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Layouttesti;
import it.gruppoinit.pal.gp.core.domain.LayouttestiId;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.rest.RipristinaTestoRequest;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.rest.SalvataggioTestoRequest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EtichettaApp;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface LayouttestiService extends BaseService<Layouttesti, LayouttestiId> {

    public String resolveCode(String code, String software);

    public void overrideCode(String code, String value);

    /**
     * Il metodo recupera tutte le etichette che iniziano con un determinato prefisso per idcomune e il software
     * corrente. <br/>
     * La logica è la seguente:<br />
     * Per ogni chiave trovata si verifica se presente per il software corrente altrimenti per TT. Se non trovata la si
     * cerca in LaytoutTestiBase. <b>ATTENZIONE!!! </b>Nella chiave dell'etichetta viene rimosso il prefisso indicato
     * come filtro
     * 
     * @param etichetteBorsellino
     * @return
     */
    public List<EtichettaApp> findEtichetteConPrefisso(String etichetteBorsellino);

    /**
     * Il metodo ritorna il testo a partire da una etichetta, richiamando i vari service per controllare prima
     * l'esistenza sulle tabelle layouttesti e layouttestibase e poi, eventualmente, sul messages.properties
     * 
     * @param etichetta
     * @param software
     * @return
     */
    public String testoDaEtichetta(String etichetta, String software);

    /**
     * Il metodo ritorna la lista di tutti i testi di uno specifico comune unendo i dati secondo questi criteri 0. Mette
     * in relazione le tabelle in base a SOFTWARE e CODICETESTO 1. Estrapola l'elenco di tutti i testi presenti sulla
     * tabella di base con eventuali override 2. Estrapola l'elenco di tutti i testi presenti SOLO su layouttesti
     * 
     * @return
     */
    public List<LayoutTestiDTO> findTesti();

    public void salvaTesto(SalvataggioTestoRequest request);

    public void ripristinaTesto(RipristinaTestoRequest request);
}
