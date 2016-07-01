using System;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using System.Data.OleDb;
using System.Xml;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Authentication;
using PersonalLib2.Data;
using System.Collections.Generic;
using Init.SIGePro.DatiDinamici;

namespace RuntimeNamespace {
	public class RuntimeClass 
	{

		private string m_token;

		public string Token
		{
			get{return m_token;}
			set{m_token = value;}
		}
	
		Istanze m_istanzaCorrente;
		public Istanze IstanzaCorrente
		{
			get{return m_istanzaCorrente;}
			set{m_istanzaCorrente = value;}
		}


#if Anagrafica		
		Anagrafe m_anagraficaCorrente;
		public Anagrafe AnagraficaCorrente
		{
			get{return m_anagraficaCorrente;}
			set{m_anagraficaCorrente = value;}
		}
#endif

#if Attivita		
		I_Attivita m_attivitaCorrente;
		public I_Attivita AttivitaCorrente
		{
			get{return m_attivitaCorrente;}
			set{m_attivitaCorrente = value;}
		}
#endif

		ModelloDinamicoBase m_modelloCorrente;
		public ModelloDinamicoBase ModelloCorrente
		{
			get{return m_modelloCorrente;}
			set{m_modelloCorrente = value;}
		}

		public object RuntimeMethod() 
		{
			/******************************************************************
 * CREAZIONE DI UN RECORD IN ISTANZEATTIVITA * 
 * La funzione recupera il valore del dettaglio informazione da inserire da 
 * un campo dinamico, e lo inserisce tra i dettagli informazione di un'istanza.
 * Prima dell'inserimento tutti i dettagli informazioni appartenenti ad 
 * un determinato tipo informazione vengono cancellati per evitare di lasciare
 * record vecchi
 */

// Codice del tipo informazione da utilizzare per la cancellazione dei dettagli
string codiceSettore = "'1C','TIPO','SPEC','APERT','TOT','CAM','BICAM','JSUITE','SUITE','UNITAB','CAM2','EDIF'";

//campo dinamico da salvare tra i dettagli informazione dell'istanza
CampoDinamicoBase classifica = ModelloCorrente.TrovaCampo("CLASSIFICAZIONE STELLE"); //291
CampoDinamicoBase tipologia = ModelloCorrente.TrovaCampo("TIPOLOGIA E DENOMINAZIONE"); //292
CampoDinamicoBase specificazione = ModelloCorrente.TrovaCampo("SPECIFICAZIONE AGGIUNTIVA"); //293
CampoDinamicoBase periodo = ModelloCorrente.TrovaCampo("PERIODO DI APERTURA"); //327
CampoDinamicoBase stagionalita = ModelloCorrente.TrovaCampo("STAGIONALITA"); //328
CampoDinamicoBase totpostiletto = ModelloCorrente.TrovaCampo("TOTALE POSTI LETTO"); //297
CampoDinamicoBase totalealloggi = ModelloCorrente.TrovaCampo("TOTALE ALLOGGI"); //310
CampoDinamicoBase totsingoleconbagno = ModelloCorrente.TrovaCampo("TOTALE CAMERE SINGOLE CON BAGNO"); //294
CampoDinamicoBase totdoppieconbagno = ModelloCorrente.TrovaCampo("TOTALE CAMERE DOPPIE CON BAGNO"); //295
CampoDinamicoBase tottripleconbagno = ModelloCorrente.TrovaCampo("TOTALE CAMERE TRIPLE CON BAGNO"); //296
CampoDinamicoBase totquadrupleconbagno = ModelloCorrente.TrovaCampo("TOTALE CAMERE QUADRUPLE CON BAGNO"); //298
CampoDinamicoBase totquintupleconbagno = ModelloCorrente.TrovaCampo("TOTALE CAMERE 5 POSTI LETTO CON BAGNO"); //299
CampoDinamicoBase totsingolenobagno = ModelloCorrente.TrovaCampo("CAMERE SINGOLE SENZA BAGNO"); //323
CampoDinamicoBase totdoppienobagno = ModelloCorrente.TrovaCampo("CAMERE DOPPIE SENZA BAGNO"); //324
CampoDinamicoBase tottriplenobagno = ModelloCorrente.TrovaCampo("CAMERE TRIPLE SENZA BAGNO"); //325
CampoDinamicoBase totquadruplenobagno = ModelloCorrente.TrovaCampo("CAMERE QUADRUPLE SENZA BAGNO"); //326
CampoDinamicoBase bicameradueletti = ModelloCorrente.TrovaCampo("UNITA BICAMERA A 2 POSTI LETTO"); //300
CampoDinamicoBase bicameratreletti = ModelloCorrente.TrovaCampo("UNITA BICAMERA A 3 POSTI LETTO"); //301
CampoDinamicoBase bicameraquattroletti = ModelloCorrente.TrovaCampo("UNITA BICAMERA A 4 POSTI LETTO"); //302
CampoDinamicoBase bicameracinqueletti = ModelloCorrente.TrovaCampo("UNITA BICAMERA A 5 POSTI LETTO"); //303
CampoDinamicoBase juniordueletti = ModelloCorrente.TrovaCampo("JUNIOR SUITE 2 POSTI LETTO"); //304
CampoDinamicoBase juniortreletti = ModelloCorrente.TrovaCampo("JUNIOR SUITE 3 POSTI LETTO"); //305
CampoDinamicoBase juniorquattroletti = ModelloCorrente.TrovaCampo("JUNIOR SUITE 4 POSTI LETTO"); //306
CampoDinamicoBase suitedueletti = ModelloCorrente.TrovaCampo("SUITE 2 POSTI LETTO"); //307
CampoDinamicoBase suitetreletti = ModelloCorrente.TrovaCampo("SUITE 3 POSTI LETTO"); //308
CampoDinamicoBase suitequattroletti = ModelloCorrente.TrovaCampo("SUITE 4 POSTI LETTO"); //309
CampoDinamicoBase monounletto = ModelloCorrente.TrovaCampo("MONOLOCALI 1 POSTO LETTO"); //311
CampoDinamicoBase monodueletti = ModelloCorrente.TrovaCampo("MONOLOCALI 2 POSTO LETTO"); //312
CampoDinamicoBase monotreletti = ModelloCorrente.TrovaCampo("MONOLOCALI 3 POSTO LETTO"); //313
CampoDinamicoBase monoquattroletti = ModelloCorrente.TrovaCampo("MONOLOCALI 4 POSTO LETTO"); //314
CampoDinamicoBase monocinqueletti = ModelloCorrente.TrovaCampo("MONOLOCALI 5 POSTO LETTO"); //315
CampoDinamicoBase unitaunletto = ModelloCorrente.TrovaCampo("UNITA ABITATIVA 1 POSTO LETTO"); //316
CampoDinamicoBase unitadueletti = ModelloCorrente.TrovaCampo("UNITA ABITATIVA 2 POSTI LETTO"); //317
CampoDinamicoBase unitatreletti = ModelloCorrente.TrovaCampo("UNITA ABITATIVA 3 POSTI LETTO"); //318
CampoDinamicoBase unitaquattroletti = ModelloCorrente.TrovaCampo("UNITA ABITATIVA 4 POSTI LETTO"); //319
CampoDinamicoBase unitacinqueletti = ModelloCorrente.TrovaCampo("UNITA ABITATIVA 5 POSTI LETTO"); //320
CampoDinamicoBase unitaseiletti = ModelloCorrente.TrovaCampo("UNITA ABITATIVA 6 POSTI LETTO"); //321
CampoDinamicoBase unitasetteletti = ModelloCorrente.TrovaCampo("UNITA ABITATIVA 7 POSTI LETTO"); //322
CampoDinamicoBase datacostruzione = ModelloCorrente.TrovaCampo("DATA DI COSTRUZIONE DELL IMMOBILE"); //329
CampoDinamicoBase dataristrutturazione = ModelloCorrente.TrovaCampo("DATA ULTIMA RISTRUTTURAZIONE"); //300
try
{
	// Effettuo l'autenticazione utilizzando il token preimpostato
	AuthenticationInfo authInfo = AuthenticationManager.CheckToken( Token );
	
	//Creo la classe da utilizzare come filtro
	IstanzeAttivita ia = new IstanzeAttivita();
        ia.IDCOMUNE = authInfo.IdComune;
        ia.CODICEISTANZA = IstanzaCorrente.CODICEISTANZA;
        ia.OthersTables.Add("ATTIVITA");
        ia.OthersWhereClause.Add("ATTIVITA.IDCOMUNE = ISTANZEATTIVITA.IDCOMUNE");
        ia.OthersWhereClause.Add("ATTIVITA.CODICEISTAT = ISTANZEATTIVITA.CODICEATTIVITA");
        ia.OthersWhereClause.Add("ATTIVITA.CODICESETTORE IN (" + codiceSettore + ")");
                
	// Creo il manager da utilizzare per la cancellazione dei dettagli informazione
	IstanzeAttivitaMgr mgr = new IstanzeAttivitaMgr(authInfo.CreateDatabase());
	List<IstanzeAttivita> lIa = mgr.GetList(ia);
        foreach (IstanzeAttivita istatt in lIa)
        {
        	mgr.Delete(istatt);
        }
	
	//Recupero il dettaglio informazione da inserire
//	CampoDinamicoBase campo = ModelloCorrente.TrovaCampo( classifica );
	
	// Se il campo è stato trovato e ha un valore impostato ...
//	if (campo != null && !String.IsNullOrEmpty(campo.Valore))
//	{
  //              IstanzeAttivita ist_att = new IstanzeAttivita();
    //            ist_att.CODICEATTIVITA = campo.Valore;
      //          ist_att.CODICEISTANZA = IstanzaCorrente.CODICEISTANZA;
        //        ist_att.IDCOMUNE = IstanzaCorrente.IDCOMUNE;
          //      ist_att.SOFTWARE = IstanzaCorrente.SOFTWARE;
                
          //      mgr.Insert( ist_att );
	//}
}
catch (Exception ex)
{
	// Gestire l'eccezione, si può risollevare l'eccezione per 
	// mostrare un errore all'utente oppure si può procedere con l'elaborazione
	throw ex;
}
        
        
			
			return null;
		}
	}
} 