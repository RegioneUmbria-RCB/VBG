using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche
{
	public class RiepiloghiSchedeDinamicheWriteInterface : IRiepiloghiSchedeDinamicheWriteInterface
	{
		PresentazioneIstanzaDbV2 _database;

		public RiepiloghiSchedeDinamicheWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;
		}

		#region IRiepiloghiSchedeDinamicheWriteInterface Members

		public void RigeneraRiepiloghiModelli(IGeneratoreRiepilogoModelloDinamico generatoreRiepilogo, bool generaRiepilogoSchedeCheNonRichiedonoFirma)
		{
			foreach (var modello in _database.Dyn2Modelli)
			{
				if (!modello.Compilato)
				{
					EliminaByIdModello(modello.IdModello);
					continue;
				}

				if (!generaRiepilogoSchedeCheNonRichiedonoFirma && modello.TipoFirma == ModelloDinamico.TipiFirmaSigepro.Nessuna)
				{
					continue;
				}

				if (modello.TipoFirma == ModelloDinamico.TipiFirmaSigepro.ABlocchi)
				{
					for (int indiceMolteplicita = 0; indiceMolteplicita < modello.MaxMolteplicita + 1; indiceMolteplicita++)
					{
						SalvaOggettoRiepilogo(modello.IdModello, modello.NomeScheda, indiceMolteplicita, generatoreRiepilogo);
					}
				}
				else
				{
					SalvaOggettoRiepilogo(modello.IdModello, modello.NomeScheda, -1, generatoreRiepilogo);
				}
			}
		}




		private void SalvaOggettoRiepilogo(int idModello, string nomeScheda, int indiceMolteplicita, IGeneratoreRiepilogoModelloDinamico generatoreRiepilogo)
		{
			AggiungiOAggiorna(idModello, indiceMolteplicita, nomeScheda);

			var row = GetByIdModelloIndiceMolteplicita(idModello, indiceMolteplicita);

			if (!row.IsIdAllegatoNull())
				return;

			var esitoGenerazione = generatoreRiepilogo.GeneraRiepilogoEAllega(idModello, nomeScheda, indiceMolteplicita);

			var allegatiRow = this._database.Allegati.AddAllegatiRow(esitoGenerazione.File.FileName, esitoGenerazione.CodiceOggetto, esitoGenerazione.Md5, false, String.Empty);

			ImpostaMd5Confronto(idModello, indiceMolteplicita, esitoGenerazione.Md5);

			row.IdAllegato = allegatiRow.Id;
		}

		private void ImpostaMd5Confronto(int idModello, int indiceMolteplicita, string hash)
		{
			var row = this._database.RiepilogoDatiDinamici.FindByIdModelloIndiceMolteplicita(idModello, indiceMolteplicita);

			row.HashConfronto = hash;

			//if (row.IsIdAllegatoNull())
			//    throw new Exception("La riga di riepilogo non contiene un file allegato");

			//var allegatiRow = this._database.Allegati.FindById(row.IdAllegato);

			//allegatiRow.Md5 = hash;

		}

		private PresentazioneIstanzaDbV2.RiepilogoDatiDinamiciRow GetByIdModelloIndiceMolteplicita(int idModello, int indiceMolteplicita)
		{
			return this._database.RiepilogoDatiDinamici
								 .Where(x => x.IdModello == idModello && x.IndiceMolteplicita == indiceMolteplicita)
								 .FirstOrDefault();
		}

		private void AggiungiOAggiorna(int idModello, int indiceMolteplicita, string nomeScheda)
		{
			var row = GetByIdModelloIndiceMolteplicita(idModello, indiceMolteplicita);

			if (row == null)
			{
				row = this._database.RiepilogoDatiDinamici.NewRiepilogoDatiDinamiciRow();

				row.IdModello			= idModello;
				row.IndiceMolteplicita	= indiceMolteplicita;

				this._database.RiepilogoDatiDinamici.AddRiepilogoDatiDinamiciRow(row);
			}

			row.Descrizione = String.Format("{0} {1}", nomeScheda, indiceMolteplicita >= 0 ? (indiceMolteplicita + 1).ToString() : String.Empty);
		}

		public void EliminaByIdModello(int idModello)
		{
			var l = this._database.RiepilogoDatiDinamici.Where( x => x.IdModello == idModello ).ToList();

			foreach (var riepilogo in l)
				EliminaRecordRiepilogo(riepilogo);
		}

		private void EliminaRecordRiepilogo(PresentazioneIstanzaDbV2.RiepilogoDatiDinamiciRow riepilogo)
		{
			RimuoviAllegato(riepilogo);

			riepilogo.Delete();

			this._database.AcceptChanges();
		}

		private void RimuoviAllegato(PresentazioneIstanzaDbV2.RiepilogoDatiDinamiciRow riepilogo)
		{
			if (!riepilogo.IsIdAllegatoNull())
			{
				var allegatirow = this._database.Allegati.FindById(riepilogo.IdAllegato);
				allegatirow.Delete();
				riepilogo.SetIdAllegatoNull();
			}

			this._database.AcceptChanges();
		}

		public void SincronizzaConModelli()
		{
			var riepiloghiDaEliminare = new List<PresentazioneIstanzaDbV2.RiepilogoDatiDinamiciRow>();

			// Elimino i riepiloghi che hanno un tipo di firma diversa da quella attualmente impostata nel modello
			foreach (var row in this._database.RiepilogoDatiDinamici)
			{
				var datiModello = this._database.Dyn2Modelli.FindByIdModello(row.IdModello);

				// Il modello non esiste più, elimino il riepilogo
				if (datiModello == null)
				{
					riepiloghiDaEliminare.Add(row);
					continue;
				}

				// Elimino la riga se il tipo firma è a blocchi e l'allegato salvato ha tipo firma a documento
				if (row.IndiceMolteplicita == -1 && datiModello.TipoFirma == ModelloDinamico.TipiFirmaSigepro.ABlocchi)
				{
					riepiloghiDaEliminare.Add(row);
					continue;
				}

				// Elimino la riga se il tipo firma è a documento ma il modello richiede una firma a blocchi
				if (row.IndiceMolteplicita >= 0 && datiModello.TipoFirma != ModelloDinamico.TipiFirmaSigepro.ABlocchi)
				{
					//EliminaRigaRiepilogo(row);
					riepiloghiDaEliminare.Add(row);
					continue;
				}

				// Elimino la riga se è ha un indice di molteplicità maggiore della massima molteplicità
				// del modello a cui è associata
				if (row.IndiceMolteplicita >= 0 &&
					datiModello.TipoFirma == ModelloDinamico.TipiFirmaSigepro.ABlocchi &&
					row.IndiceMolteplicita > datiModello.MaxMolteplicita)
				{
					//EliminaRigaRiepilogo(row);
					riepiloghiDaEliminare.Add(row);

					continue;
				}
			}

			foreach (var item in riepiloghiDaEliminare.ToList())
				this.EliminaByIdModello(item.IdModello);
		}

		public void EliminaOggettoRiepilogo(int idModello, int indiceMolteplicita)
		{
			var row = this._database.RiepilogoDatiDinamici.FindByIdModelloIndiceMolteplicita(idModello, indiceMolteplicita);

			RimuoviAllegato(row);
		}

		public void SalvaOggettoRiepilogo(int idModello, int indiceMolteplicita, int codiceOggetto, string nomeFile, bool firmatoDigitalmente)
		{
			var row = this._database.RiepilogoDatiDinamici.FindByIdModelloIndiceMolteplicita(idModello, indiceMolteplicita);

			RimuoviAllegato(row);

			var rowAllegati = this._database.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, String.Empty, firmatoDigitalmente, String.Empty);

			row.IdAllegato = rowAllegati.Id;
		}



		#endregion
	}
}
