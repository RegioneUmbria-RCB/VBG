package it.gruppoinit.pal.gp.core.service;

import java.util.List;

public interface CartInvioDizionarioService extends CartBaseServiceERO {

    /**
     * <pre>
     * Inserisce all'interno della tabella Alberoproc dei nuovi record a partire dalle informazioni del cart, e in particolare 
     * dai record della tabella TipologieEndo2, in più tiene traccia di ogni insermimentosulla tabella TipologieEndo2 inserendo
     * a sua volta un nuovo record . Secondo la seguente logica:
     *  <ol>
     *  <li>Effettuare una query su TipologieEndo2 filtrando per 
     *  	<ul>
     * 			<li>Idcomune = ORMHelper.idcomune</li>
     * 		<li>Tipo = “ENDO”</li>
     * 		<li>Codice_tipologia_endo = 1</li>
     * 	</ul>
     *  </li>
     *  <li>Per ogni record trovato deve essere inserito un record su alberoproc per ogni tipologia passata (@param  codiciStpTipologiaEndo2) 
     *      secondo il mapping:
     * 	<ul>
     * 		<li>sc_descrizione = stpTipologieEndo2.descrizione</li>
     * 		<li>sc_ordine= stpTipologieEndo2.id</li>
     * 		<li>sc_codice= calcolato in base al padre (il padre lo si può calcolare prendendo il padre dell’intervento “Avvio” 
     * 		   che è già presente come foglia per quel tipoEndo che andiamo ad inserire)</li>
     *          	<li>Inserire un inventario procedimento e associarlo alla voce dell’albero che è stato creato</li>
     *          </ul> 
     *  </li>
     *  <li>
     *  	Per ogni record inserito sulla tabella alberoproc deve essere creato un nuovo record all’interno della tabella stpEndoTipo2 
     *  	con il seguente mapping:
     *  	<ul>
     * 		<li>Idcomune = ORMHelper.idcomune</li>
     * 		<li>Id = nuovo progressivo</li>
     * 		<li>Fk_sc_id :=codice della voce dell’albero inserito</li>
     *          	<li>Codice_stp= null</li>
     *          	<li>Tipo = ”Endo”</li>
     *          	<li>codiceinventario = codice dell’inventario procedimento inserito</li>
     *          	<li>codice_tipologia_endo= 1</li>
     *          </ul> 
     *  </li>
     *  
     *  
     * </ol>
     * 
     * @param codiciStpTipologiaEndo2
     * </pre>
     */
    public void insertInterventiDaCart(List<Integer> codiciStpTipologiaEndo2);

    public void bonificaGerarchiaAlbero();
}
