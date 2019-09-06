using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure.Sincronizzazione;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure
{
	public class ProcureWriteInterface : IProcureWriteInterface
	{
		PresentazioneIstanzaDbV2 _database;

		public ProcureWriteInterface(PresentazioneIstanzaDbV2 database )
		{
			this._database = database;
		}

		#region IGestioneProcureWriteInterface Members


		public void AllegaFileProcura(string codicefiscaleProcurato, string codiceFiscaleProcuratore, int codiceOggetto, string nomeFile, bool isFirmatoDigitalmente)
		{
			var row = this._database.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, String.Empty, isFirmatoDigitalmente, String.Empty);

			var rigaProcure = this._database.Procure.FindByCodiceAnagrafeCodiceProcuratore(codicefiscaleProcurato, codiceFiscaleProcuratore);

			if (rigaProcure == null)
			{
				this._database.Procure.AddProcureRow(codicefiscaleProcurato, codiceFiscaleProcuratore, row.Id, -1);
			}
			else
			{
				rigaProcure.IdAllegato = row.Id;
			}
		}

		public void EliminaFileProcura(string codicefiscaleProcurato, string codiceFiscaleProcuratore)
		{
			var rigaProcure = this._database.Procure.FindByCodiceAnagrafeCodiceProcuratore(codicefiscaleProcurato, codiceFiscaleProcuratore);
            var vecchioCodiceDocumentoIdentita = rigaProcure.IsIdDocumentoIdentitaNull() ? -1 : rigaProcure.IdDocumentoIdentita;

            // Elimina(rigaProcure);

            // Aggiungi(codicefiscaleProcurato, codiceFiscaleProcuratore, -1, vecchioCodiceDocumentoIdentita);
            rigaProcure.SetIdAllegatoNull();
        }

		private void Elimina(PresentazioneIstanzaDbV2.ProcureRow rigaProcure)
		{
			rigaProcure.Delete();

			this._database.AcceptChanges();
		}

		public void EliminaProcureContenenti(string codiceFiscaleSoggetto)
		{
			var codiceFiscaleSoggettoSafe = codiceFiscaleSoggetto.ToUpperInvariant();

			var righeDaEliminare = this._database.Procure.Where(x => x.CodiceAnagrafe == codiceFiscaleSoggettoSafe ||
																	 x.CodiceProcuratore == codiceFiscaleSoggettoSafe);

			foreach (var riga in righeDaEliminare.ToList())
				Elimina( riga );
		}

		public void Aggiungi(string codiceFiscaleProcurato,string codiceFiscaleProcuratore, int idAllegatoProcura = -1, int idAllegatoDocumentoIdentita = -1)
		{
			var codiceFiscaleProcuratoSafe = codiceFiscaleProcurato.ToUpperInvariant();
			var codiceFiscaleProcuratoreSafe = codiceFiscaleProcuratore.ToUpperInvariant();

			var rigaEsiste = this._database
								 .Procure
								 .Where( x =>
									x.CodiceAnagrafe == codiceFiscaleProcuratoSafe &&
									x.CodiceProcuratore == codiceFiscaleProcuratoreSafe 
								 )
								 .Count() > 0;

			if (rigaEsiste)
				return;

			var newRow = this._database.Procure.AddProcureRow(codiceFiscaleProcuratoSafe, codiceFiscaleProcuratoreSafe, idAllegatoProcura, idAllegatoDocumentoIdentita);
            
            if (idAllegatoProcura == -1)
            {
                newRow.SetIdAllegatoNull();
            }

            if (idAllegatoDocumentoIdentita == -1)
            {
                newRow.SetIdDocumentoIdentitaNull();
            }
        }

		/// <summary>
		/// Sincronizza i dati relativi alle procure per le relazioni passate come argomento.
		/// - Se una relazione non esiste tra le procure esistenti viene aggiunta
		/// - Se una relazione esiste tra le procure esistenti non viene modificata
		/// - Se una relazione esistente non esiste nella lista passata viene eliminata e il relativo allegato viene cancellato
		/// </summary>
		/// <param name="command"></param>
		public void Sincronizza(SincronizzaProcureCommand command)
		{
			// Elimino tutte le procure che non sono presenti nella lista procure da sincronizzare
			var listaProcureDaEliminare = this._database.Procure.Where(procura => command.ProcureDaSincronizzare.ToList()
																						 .Where(x => procura.CodiceAnagrafe == x.CodiceFiscaleSottoscrivente.ToUpperInvariant() &&
																									 procura.CodiceProcuratore == x.CodiceFiscaleProcuratore.ToUpperInvariant())
																						 .Count() == 0);

			// Eliminazione della dataRow
			foreach (var procura in listaProcureDaEliminare.ToList())
				Elimina( procura );

			this._database.AcceptChanges();

			// Aggiungo le procure che non sono già presenti nella domanda
			foreach (var procura in command.ProcureDaSincronizzare)
			{
				var count = this._database.Procure
											.Where(x => x.CodiceAnagrafe == procura.CodiceFiscaleSottoscrivente.ToUpperInvariant() &&
														 x.CodiceProcuratore == procura.CodiceFiscaleProcuratore.ToUpperInvariant())
											.Count();

				if (count == 0)
					Aggiungi(procura.CodiceFiscaleSottoscrivente, procura.CodiceFiscaleProcuratore);
			}
		}

        public void AllegaDocumentoIdentita(string codicefiscaleProcurato, string codiceFiscaleProcuratore, int codiceOggetto, string nomeFile, bool isFirmatoDigitalmente)
        {
            var row = this._database.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, String.Empty, isFirmatoDigitalmente, String.Empty);

            var rigaProcure = this._database.Procure.FindByCodiceAnagrafeCodiceProcuratore(codicefiscaleProcurato, codiceFiscaleProcuratore);

            if (rigaProcure == null)
            {
                this._database.Procure.AddProcureRow(codicefiscaleProcurato, codiceFiscaleProcuratore, -1, row.Id);
            }
            else
            {
                rigaProcure.IdDocumentoIdentita = row.Id;
            }
        }

        public void EliminaDocumentoIdentita(string codicefiscaleProcurato, string codiceFiscaleProcuratore)
        {
            var rigaProcure = this._database.Procure.FindByCodiceAnagrafeCodiceProcuratore(codicefiscaleProcurato, codiceFiscaleProcuratore);
            var vecchioCodiceDocumentoProcura = rigaProcure.IsIdAllegatoNull() ? -1 : rigaProcure.IdAllegato;

            // Elimina(rigaProcure);

            rigaProcure.SetIdDocumentoIdentitaNull();
        }

        #endregion
    }
}
