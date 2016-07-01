using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using System.Data;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti
{
	public class DocumentiWriteInterface : IDocumentiWriteInterface
	{
		PresentazioneIstanzaDbV2 _database;

		public DocumentiWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;
		}

		private void EliminaDocumento(PresentazioneIstanzaDbV2.OGGETTIRow riga)
		{
			if (riga == null)
				return;

			RimuoviAllegatoDaDocumento(riga);

			riga.Delete();

			this._database.AcceptChanges();
		}

		private void RimuoviAllegatoDaDocumento(PresentazioneIstanzaDbV2.OGGETTIRow documentiRow)
		{
			documentiRow.SetIdAllegatoNull();
		}

		private void EliminaDocumentiDiTipo(string tipoDocumento)
		{
			var righeDaEliminare = this._database.OGGETTI.Where(x => x.TIPODOCUMENTO == tipoDocumento);

			foreach (var riga in righeDaEliminare)
				EliminaDocumento(riga);
		}


		#region IDocumentiWriteInterface Members

		public void EliminaDocumentiInterventoSenzaAllegati()
		{
			var righeDaEliminare = this._database.OGGETTI.Where(x => x.TIPODOCUMENTO == GestioneDocumentiConstants.ProvenienzaDocumento.Intervento && x.IsIdAllegatoNull());

			foreach (var riga in righeDaEliminare.ToList())
				riga.Delete();

			this._database.AcceptChanges();
		}

		public void EliminaDocumento(int idDocumento)
		{
			EliminaDocumento( this._database.OGGETTI.FindByID(idDocumento) );
		}



		public void AggiungiOAggiornaDocumentoIntervento(int codiceDocumento, string descrizione, string linkInformazioni, int? codiceOggetto, bool richiesto, bool richiedeFirma, string tipoDownload, int ordine, string nomeFileModello, bool riepilogoDomanda, int codiceCategoria, string descrizioneCategoria, string note)
		{
			var idIntervento = this._database.ISTANZE[0].CODICEINTERVENTO;

			AllegatiInterventoBuilder.BeginBuild(this._database, idIntervento, codiceDocumento)
									 .WithDescrizione(descrizione)
									 .WithLinkInformazioni(linkInformazioni)
									 .WithIdModello(codiceOggetto)
									 .WithFlagRichiesto(richiesto)
									 .WithFlagRichiedeFirma(richiedeFirma)
									 .WithFlagRiepilogoDomanda(riepilogoDomanda)
									 .WithTipoDownload(tipoDownload)
									 .WithOrdine(ordine)
									 .WithNomeFileModello(nomeFileModello)
									 .WithCategoria(codiceCategoria, descrizioneCategoria)
									 .WithNote(note)
									 .Build();
		}

		public void AggiungiDocumentoInterventoLibero(string descrizione, int codiceOggetto, string nomeFile, int codiceCategoria, string descrizioneCategoria, bool isFirmatoDigitalmente)
		{
			var idIntervento = this._database.ISTANZE[0].CODICEINTERVENTO;

			AllegatiInterventoBuilder.BeginBuild(this._database, idIntervento, null)
						 .WithDescrizione(descrizione)
						 .WithIdModello(null)
						 .WithFlagRichiesto(false)
						 .WithFlagRichiedeFirma(false)
						 .WithTipoDownload(String.Empty)
						 .WithOrdine(9999)
						 .WithNomeFileModello(String.Empty)
						 .WithCategoria(codiceCategoria, descrizioneCategoria)
						 .WithOggetto(codiceOggetto, nomeFile, isFirmatoDigitalmente)
						 .Build();
		}

		public void AggiungiDocumentoSchedaDinamica(string descrizione, int codiceOggetto, string nomeFile)
		{
			var idIntervento = this._database.ISTANZE[0].CODICEINTERVENTO;

			AllegatiInterventoBuilder.BeginBuild(this._database, idIntervento, null)
						 .WithDescrizione(descrizione)
						 .WithIdModello(null)
						 .WithFlagRichiesto(false)
						 .WithFlagRichiedeFirma(false)
						 .WithTipoDownload(String.Empty)
						 .WithOrdine(9999)
						 .WithNomeFileModello(String.Empty)
						 .WithCategoria(-1, "Altri allegati")
						 .WithOggetto(codiceOggetto, nomeFile, false)
						 .FromDatiDinamici()
						 .Build();
		}

		public void AggiungiOAggiornaDocumentoEndo(int codiceDocumento, string descrizione, string linkInformazioni, int? idModello, bool richiesto, bool richiedeFirma, string tipoDownload, int codiceEndo, int ordine, string nomeFileModello, string note)
		{
			AllegatiEndoBuilder.BeginBuild(this._database, codiceEndo, codiceDocumento)
				   .WithDescrizione(descrizione)
				   .WithLinkInformazioni(linkInformazioni)
				   .WithIdModello(idModello)
				   .WithFlagRichiesto(richiesto)
				   .WithFlagRiepilogoDomanda(false)
				   .WithFlagRichiedeFirma(richiedeFirma)
				   .WithTipoDownload(tipoDownload)
				   .WithOrdine(ordine)
				   .WithNomeFileModello(nomeFileModello)
				   .WithNote(note)
				   .Build();
		}

		internal void AggiungiDocumentoEndoLibero(int codiceEndo, string descrizione, int codiceOggetto, string nomeFile, bool isFirmatoDigitalmente)
		{
			AllegatiEndoBuilder.BeginBuild(this._database, codiceEndo, null)
							   .WithDescrizione(descrizione)
							   .WithOggetto( codiceOggetto , nomeFile , isFirmatoDigitalmente )
							   .WithFlagRiepilogoDomanda(false)
							   .WithFlagRichiesto(false)
							   .Build();
		}

		public void EliminaDocumentoEndoDaIdEndo(int idEndo)
		{
			var righeDaEliminare = this._database.OGGETTI.Where(x => x.TIPODOCUMENTO == GestioneDocumentiConstants.ProvenienzaDocumento.Endo && x.CodiceEndoOIntervento == idEndo );

			foreach (var riga in righeDaEliminare)
				riga.Delete();
		}

		public void AllegaFileADocumento(int idDocumento, int codiceOggetto, string nomeFile, bool isFirmatoDigitalmente)
		{
			this.AllegaFileADocumento(idDocumento, codiceOggetto, nomeFile, isFirmatoDigitalmente, String.Empty);
		}

		public void AllegaFileADocumento(int idDocumento, int codiceOggetto, string nomeFile, bool isFirmatoDigitalmente, string md5)
		{
			var documentiRow = this._database.OGGETTI.FindByID(idDocumento);

			if (documentiRow == null)
				throw new Exception(String.Format("Impossibile trovare il documento con id {0} nella datatable oggetti", idDocumento));

			if (!documentiRow.IsIdAllegatoNull())
				RimuoviAllegatoDaDocumento(documentiRow);

			var allegatiRow = this._database.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, md5, isFirmatoDigitalmente, String.Empty);

			documentiRow.IdAllegato = allegatiRow.Id;
		}

		public void RimuoviAllegatoDaDocumento(int idDocumento)
		{
			var documentiRow = this._database.OGGETTI.FindByID(idDocumento);

			if (documentiRow == null)
				throw new Exception(String.Format("Impossibile trovare il documento con id {0} nella datatable oggetti", idDocumento));

			RimuoviAllegatoDaDocumento(documentiRow);
		}




		public void EliminaDocumentiProvenientiDaIntervento()
		{
			EliminaDocumentiDiTipo(GestioneDocumentiConstants.ProvenienzaDocumento.Intervento);
		}

		public void EliminaDocumentiProvenientiDaEndo()
		{
			EliminaDocumentiDiTipo(GestioneDocumentiConstants.ProvenienzaDocumento.Endo);
		}


		public void EliminaAllegatoADocumentoDaCodiceOggetto(int codiceOggetto)
		{
			var rigaAllegati = this._database.Allegati.Where(x => x.CodiceOggetto == codiceOggetto)
														.FirstOrDefault();

			if (rigaAllegati == null)
				return;

			var rigaOggetti = this._database.OGGETTI
											.Where(x => !x.IsIdAllegatoNull() && x.IdAllegato == rigaAllegati.Id)
											.FirstOrDefault();

			if (rigaOggetti == null)
				return;

			EliminaDocumento(rigaOggetti);
		}


		void IDocumentiWriteInterface.AggiungiDocumentoEndoLibero(int codiceEndo, string descrizione, int codiceOggetto, string nomeFile, bool isFirmatoDigitalmente)
		{
			AllegatiEndoBuilder.BeginBuild(this._database, codiceEndo, null)
							   .WithDescrizione(descrizione)
							   .WithOggetto( codiceOggetto , nomeFile , isFirmatoDigitalmente )
							   .WithFlagRiepilogoDomanda(false)
							   .WithFlagRichiesto(false)
							   .Build();

		}

		public void EliminaAllegatoADocumentoDaIdDocumento(int idDocumento)
		{
			var riga = this._database.OGGETTI
								.Where(x => x.ID == idDocumento)
								.FirstOrDefault();

			if (riga == null)
				return;

			RimuoviAllegatoDaDocumento(riga);
		}

		


		public void EliminaRiepiloghiDomandainEccesso(int idDocumentoDaMantenere)
		{
			var righeDaEliminare = this._database.OGGETTI.Where(x => !x.IsRIEPILOGODOMANDANull() && x.RIEPILOGODOMANDA == 1 && !x.IsCODICEDOCUMENTONull() && x.CODICEDOCUMENTO != idDocumentoDaMantenere).ToList();

			foreach (var riga in righeDaEliminare)
				riga.Delete();
		}

		#endregion
	}
}
