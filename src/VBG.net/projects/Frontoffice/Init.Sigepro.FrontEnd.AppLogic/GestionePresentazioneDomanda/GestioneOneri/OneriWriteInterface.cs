using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
	public class OneriWriteInterface : IOneriWriteInterface
	{
		public static class Constants
		{
			public const string TipoOnereIntervento = "I";
			public const string TipoOnereEndo = "E";
		}

		ILog _log = LogManager.GetLogger(typeof(OneriWriteInterface));
		PresentazioneIstanzaDbV2 _database;

		public OneriWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;
		}

		private int TotaleOneri()
		{
			return (int)(this._database.OneriDomanda.Sum(x => x.Importo) * 100.0f);
		}

		private void SetDichiarazioneAssenzaOneriDaPagare(bool valoreFlag)
		{
			if (valoreFlag)
			{
				EliminaAttestazioneDiPagamento();

				var newrow = this._database.OneriAttestazionePagamento.AddOneriAttestazionePagamentoRow(TotaleOneri(), valoreFlag, -1);
				newrow.SetIdAllegatoNull();
			}
			else
			{
				if (this._database.OneriAttestazionePagamento.Count != 0)
				{
					this._database.OneriAttestazionePagamento[0].DichiaraDiNonAvereOneriDaPagare = false;
				}
			}
		}

		#region IOneriWriteInterface Members

		public void SalvaAttestazioneDiPagamento(int codiceOggetto, string nomeFile, bool firmatoDigitalmente)
		{
			EliminaAttestazioneDiPagamento();

			var rigaAllegati = this._database.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, String.Empty, firmatoDigitalmente, String.Empty);

			this._database.OneriAttestazionePagamento
						  .AddOneriAttestazionePagamentoRow(TotaleOneri(), false, rigaAllegati.Id);
		}

		public void EliminaAttestazioneDiPagamento()
		{
			if (this._database.OneriAttestazionePagamento.Count == 0)
				return;

			foreach (var row in this._database.OneriAttestazionePagamento.ToList())
				row.Delete();
		}

		public void ImpostaDichiarazioneAssenzaOneriDaPagare()
		{
			SetDichiarazioneAssenzaOneriDaPagare(true);
		}

		public void RimuoviDichiarazioneAssenzaOneriDaPagare()
		{
			SetDichiarazioneAssenzaOneriDaPagare(false);
		}


		public void EliminaOneriIntervento()
		{
			var righeDaEliminare = this._database.OneriDomanda.Where(x => x.TipoOnere == Constants.TipoOnereIntervento);

			foreach (var riga in righeDaEliminare)
				riga.Delete();

			EliminaAttestazioneDiPagamento();
		}

		public void EliminaOneriDaIdEndo(int idEndoProcedimento)
		{
			var righeDaEliminare = this._database.OneriDomanda.Where(x => x.TipoOnere == Constants.TipoOnereEndo && x.CodiceInterventoOEndoOrigine == idEndoProcedimento);

			foreach (var riga in righeDaEliminare)
				riga.Delete();

			EliminaAttestazioneDiPagamento();
		}

		public void EliminaOneriWhereCodiceCausaleIn(IEnumerable<int> listaIdDaEliminare)
		{
			var eliminaAttestazioneDiPagamento = false;
			var elementiDaEliminare = this._database.OneriDomanda.Where(x => listaIdDaEliminare.Contains(x.CodiceCausale));

			foreach (var onereDaEliminare in elementiDaEliminare.ToList())
			{
				this._database.OneriDomanda.RemoveOneriDomandaRow(onereDaEliminare);

				eliminaAttestazioneDiPagamento = true;
			}

			if (eliminaAttestazioneDiPagamento)
				EliminaAttestazioneDiPagamento();
		}


		public void AggiungiOSalvaOnereIntervento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, float importo, float importoPagato, string note)
		{
			AggiungiOSalvaOnere(Constants.TipoOnereIntervento, codiceCausale, causale, codiceInterventoOEndoOrigine, interventoOEndoOrigine, importo, importoPagato, note);
		}

		public void AggiungiOSalvaOnereEndoprocedimento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, float importo, float importoPagato, string note)
		{
			AggiungiOSalvaOnere(Constants.TipoOnereEndo, codiceCausale, causale, codiceInterventoOEndoOrigine, interventoOEndoOrigine, importo, importoPagato, note);
		}

		private void AggiungiOSalvaOnere(string tipoOnere, int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, float importo, float importoPagato, string note)
		{
			_log.DebugFormat("Aggiunta o solvataggio dell'onere con tipoOnere={0}, codiceCausale={1}, causale={2}, codiceInterventoOEndoOrigine={3}, interventoOEndoOrigine={4}, importo={5}, note={6}",
							  tipoOnere, codiceCausale, causale, codiceInterventoOEndoOrigine, interventoOEndoOrigine, importo, note);

			var row = this._database.OneriDomanda.FindByTipoOnereCodiceCausaleCodiceInterventoOEndoOrigine(tipoOnere, codiceCausale, codiceInterventoOEndoOrigine);

			if (row == null)
			{
				_log.Debug("L'onere non esiste nella datatable OneriDomanda e verrà aggiunto");

				this._database.OneriDomanda.AddOneriDomandaRow(tipoOnere, codiceCausale, causale, codiceInterventoOEndoOrigine, interventoOEndoOrigine, importo, note, false, String.Empty, String.Empty, string.Empty, String.Empty, importo);

				EliminaAttestazioneDiPagamento();
			}
			else
			{
				_log.Debug("L'onere esiste nella datatable OneriDomanda e verrà aggiornato");

				if (row.Importo != importo)
				{
					row.Importo = importo;
					row.ImportoPagato = importoPagato;

					EliminaAttestazioneDiPagamento();					
				}

				if (row.IsImportoPagatoNull())
				{
					row.ImportoPagato = importo;
				}

				row.TipoOnere = tipoOnere;
				row.CodiceCausale = codiceCausale;
				row.Causale = causale;
				row.CodiceInterventoOEndoOrigine = codiceInterventoOEndoOrigine;
				row.InterventoOEndoOrigine = interventoOEndoOrigine;
				
				row.Note = note;

			}
		}

		public void EliminaOneriWhereCodiceCausaleNotIn(IEnumerable<IdentificativoOnereSelezionato> listaId)
		{
			var eliminaAttestazioneDiPagamento = false;
			// var elementiDaEliminare = this._database.OneriDomanda.Where(x => !listaId.Contains(x.CodiceCausale));
			var condition = new ListaIdOneriContiene(listaId);

			var elementiDaEliminare = this._database.OneriDomanda.Where( x => !condition.IsSatisfiedBy(x)).ToList();

			foreach (var onereDaEliminare in elementiDaEliminare)
			{
				this._database.OneriDomanda.RemoveOneriDomandaRow(onereDaEliminare);

				eliminaAttestazioneDiPagamento = true;
			}

			if (eliminaAttestazioneDiPagamento)
				EliminaAttestazioneDiPagamento();
		}


		public void ImpostaEstremiPagamentoOnereIntervento(int codiceCausale, int codiceIntervento, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float importoPagato)
		{
			ImpostaEstremiPagamento(Constants.TipoOnereIntervento, codiceCausale, codiceIntervento, codiceTipoPagamento, descrizioneTipoPagamento, data, riferimento, importoPagato);
		}

		public void ImpostaEstremiPagamentoOnereEndo(int codiceCausale, int codiceEndo, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float importoPagato)
		{
			ImpostaEstremiPagamento(Constants.TipoOnereEndo, codiceCausale, codiceEndo, codiceTipoPagamento, descrizioneTipoPagamento, data, riferimento, importoPagato);
		}

		public void CancellaEstremiPagamento()
		{
			foreach (var r in this._database.OneriDomanda)
			{
				r.NonPagato = true;
				r.CodiceTipoPagamento = String.Empty;
				r.DescrizioneTipoPagamento = String.Empty;
				r.DataPagmento = String.Empty;
				r.NumeroPagamento = String.Empty;
				r.ImportoPagato = r.Importo;
			}

			this._database.OneriDomanda.AcceptChanges();
		}

		#endregion

		private void ImpostaEstremiPagamento(string tipoOnere, int codiceCausale, int codiceInterventoOEndoOrigine, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float importoPagato)
		{
			var row = this._database.OneriDomanda.FindByTipoOnereCodiceCausaleCodiceInterventoOEndoOrigine(tipoOnere, codiceCausale, codiceInterventoOEndoOrigine);

			if (row == null)
				throw new InvalidOperationException(String.Format("impossibile trovare l'onere con id causale {1}, tipo onere {0} e codice endo/intervento {2}", tipoOnere, codiceCausale, codiceInterventoOEndoOrigine));

			row.NonPagato = false;
			row.CodiceTipoPagamento = codiceTipoPagamento;
			row.DescrizioneTipoPagamento = descrizioneTipoPagamento;
			row.DataPagmento = data.HasValue ? data.Value.ToString("dd/MM/yyyy") : String.Empty;
			row.NumeroPagamento = riferimento;
			row.ImportoPagato = importoPagato;

			this._database.OneriDomanda.AcceptChanges();
		}
	}
}
