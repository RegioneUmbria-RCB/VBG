using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti
{
	public interface IDocumentiWriteInterface
	{
		// Operazioni generali sui documenti
		void EliminaDocumento(int idDocumento);
		void AllegaFileADocumento(int idDocumento, int codiceOggetto, string nomeFile, bool isFirmatoDigitalmente);
		void AllegaFileADocumento(int idDocumento, int codiceOggetto, string nomeFile, bool isFirmatoDigitalmente, string md5);
		void RimuoviAllegatoDaDocumento(int idDocumento);
		void EliminaDocumentiProvenientiDaIntervento();
		void EliminaDocumentiProvenientiDaEndo();
		void EliminaAllegatoADocumentoDaCodiceOggetto(int codiceOggetto);

		// Allegati dell'intervento
		void EliminaDocumentiInterventoSenzaAllegati();
		void AggiungiOAggiornaDocumentoIntervento(int codiceDocumento, string descrizione, string linkInformazioni, int? codiceOggetto, bool richiesto, bool richiedeFirma, string tipoDownload, int ordine, string nomeFileModello, bool riepilogoDomanda, int codiceCategoria, string descrizioneCategoria, string note);
		void AggiungiDocumentoInterventoLibero(string descrizione, int codiceOggetto, string nomeFile, int codiceCategoria, string descrizioneCategoria, bool isFirmatoDigitalmente);
		void AggiungiDocumentoSchedaDinamica(string descrizione, int codiceOggetto, string nomeFile);

		// Allegati endo
		void AggiungiOAggiornaDocumentoEndo(int codiceDocumento, string descrizione, string linkInformazioni, int? idModello, bool richiesto, bool richiedeFirma, string tipoDownload, int codiceEndo, int ordine, string nomeFileModello, string note);
		void EliminaDocumentoEndoDaIdEndo(int idEndo);
		void AggiungiDocumentoEndoLibero(int codiceEndo, string descrizione, int codiceOggetto, string nomeFile, bool isFirmatoDigitalmente);

		void EliminaAllegatoADocumentoDaIdDocumento(int idDocumento);

		/// <summary>
		/// A seguito della selezione di un intervento potrebbero essere erroneamente riportati 
		///	più documenti identificati come riepilogo domanda. Questo metodo permette di eliminare quelli in eccesso mantenendo solo quello specificato
		/// </summary>		
		void EliminaRiepiloghiDomandainEccesso(int idDocumentoDaMantenere);

	}
}
