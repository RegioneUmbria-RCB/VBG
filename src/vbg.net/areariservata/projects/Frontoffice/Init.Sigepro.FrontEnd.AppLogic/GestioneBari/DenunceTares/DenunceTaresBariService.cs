// -----------------------------------------------------------------------
// <copyright file="DenunceTaresBariService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.DenunceTares
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES;
	using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES.ServiceProxies;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
	using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core.MappatureCampiDinamici;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DenunceTaresBariService
	{
		private IDenunceTaresService _service;
		private ISalvataggioDomandaStrategy _persistenzaStrategy;
		private IAnagraficheService _anagraficheService;
		private IComuniService _comuniService;


		public DenunceTaresBariService(ISalvataggioDomandaStrategy persistenzaStrategy, IDenunceTaresService service, IAnagraficheService anagraficheService, IComuniService comuniService)
		{
			this._persistenzaStrategy = persistenzaStrategy;
			this._service = service;
			this._anagraficheService = anagraficheService;
			this._comuniService = comuniService;
		}

		public DatiAnagraficiContribuenteDenunciaTares GetDatiContribuente(AnagraficaUtente operatore, string codiceUtenza, string partitaIvaCodiceFiscale)
		{
			int iCodUtenza = 0;

			if (!Int32.TryParse(codiceUtenza, out iCodUtenza))
			{
				throw new Exception("Codice utenza non valido");
			}

			if (iCodUtenza > 999999 || 0 > iCodUtenza)
			{
				throw new Exception("il codice utenza immesso non è valido");
			}

			var utenza = new DatiUtenza(iCodUtenza, partitaIvaCodiceFiscale.ToUpper());
			var datiOperatore = new DatiOperatore(operatore.Nome + " " + operatore.Nominativo, operatore.Codicefiscale, operatore.Email);

			return this._service.GetUtenze(datiOperatore, utenza);
		}

		public bool VerificaEsistenzaContribuente(int idDomanda, AnagraficaUtente operatore)
		{
			var domanda = this._persistenzaStrategy.GetById(idDomanda);
			var azienda = domanda.ReadInterface.Anagrafiche.GetAzienda();
			var richiedente = domanda.ReadInterface.Anagrafiche.GetRichiedente();

			var cfRichiedente = richiedente.Codicefiscale.ToUpperInvariant();
			var piRichiedente = richiedente.PartitaIva.ToUpperInvariant();

			if (domanda.ReadInterface.DenunceTaresBari.DatiContribuente != null && domanda.ReadInterface.DenunceTaresBari.DatiContribuente.IdContribuente.HasValue)
			{
				var piUtenza = domanda.ReadInterface.DenunceTaresBari.DatiContribuente.PartitaIva.ToUpperInvariant();
				var cfUtenza = domanda.ReadInterface.DenunceTaresBari.DatiContribuente.CodiceFiscale.ToUpperInvariant();

				if (azienda != null)
				{
					var cfAzienda = azienda.Codicefiscale.ToUpperInvariant();
					var piAzienda = azienda.PartitaIva.ToUpperInvariant();

					if (cfAzienda != cfUtenza && piAzienda != piUtenza)
					{
						throw new Exception(String.Format("L'identificativo utenza è associato ad un'azienda diversa (p.iva: {0}, cf: {1})rispetto a quella per cui si sta presentando la domanda (p.iva: {2}, cf: {3})", piUtenza, cfUtenza, piAzienda, cfAzienda));
					}

					return true;
				}

				if (cfRichiedente != cfUtenza && piRichiedente != piUtenza)
				{
					throw new Exception(String.Format("L'identificativo utenza è associato ad un'azienda diversa (p.iva: {0}, cf: {1}) rispetto a quella per cui si sta presentando la domanda (p.iva: {2}, cf: {3})", piUtenza, cfUtenza, piRichiedente, cfRichiedente));
				}

				return true;
			}

			var errMsg = "Tornare allo step 5 (\"Ricerca utenze\") e identificarsi come contribuente esistente. Qualora il codice contribuente fosse stato smarrito la compilazione online della domanda di iscrizione non sarà possibile ed il contribuente dovrà rivolgersi alla ripartizione tributi per il recupero del codice";


			var partitaIvaCodiceFiscale = String.Empty;

			if (azienda != null)
			{
				var cfAzienda = azienda.Codicefiscale.ToUpperInvariant();
				var piAzienda = azienda.PartitaIva.ToUpperInvariant();

				if (UtenzaEsistente(operatore, cfAzienda))
					throw new Exception(String.Format("Esiste già un'utenza collegata al codice fiscale azienda {0} oggetto della presente domanda. {1}", cfAzienda, errMsg));

				if (UtenzaEsistente(operatore, piAzienda))
					throw new Exception(String.Format("Esiste già un'utenza collegata alla partita iva {0} oggetto della presente domanda. {1}", cfAzienda, errMsg));
			}

			if (UtenzaEsistente(operatore, cfRichiedente))
				throw new Exception(String.Format("Esiste già un'utenza collegata al codice fiscale {0} oggetto della presente domanda. {1}", cfRichiedente, errMsg));

			if (UtenzaEsistente(operatore, piRichiedente))
				throw new Exception(String.Format("Esiste già un'utenza collegata alla partita iva {0} oggetto della presente domanda. {1}", piRichiedente, errMsg));

			return true;
		}

		private bool UtenzaEsistente(AnagraficaUtente operatore, string partitaIvaCodiceFiscale)
		{
			var utenza = new DatiUtenza(-1, partitaIvaCodiceFiscale);
			var datiOperatore = new DatiOperatore(operatore.Nome + " " + operatore.Nominativo, operatore.Codicefiscale, operatore.Email);

			return this._service.IsContribuenteEsistente(datiOperatore, utenza);
		}

		public void ImpostaContribuente(int idDomanda, DatiAnagraficiContribuenteDenunciaTares datiContribuente)
		{
			var domanda = this._persistenzaStrategy.GetById(idDomanda);

			domanda.WriteInterface.DenunceTaresBari.ImpostaDatiContribuente(datiContribuente);

			this._persistenzaStrategy.Salva(domanda);
		}

		public DatiAnagraficiContribuenteDenunciaTares GetUtenzaSelezionata(int idDomanda)
		{
			var domanda = this._persistenzaStrategy.GetById(idDomanda);

			return domanda.ReadInterface.DenunceTaresBari.DatiContribuente;
		}

		public void InserisciUtenzaTraAnagrafiche(int dDomanda, int codiceTipoSoggettoPersonaFisica, int codiceTipoSoggettoPersonaGiuridica, int codiceTipoSoggettoLegaleRappresentante)
		{
			if (codiceTipoSoggettoPersonaFisica <= 0)
			{
				throw new Exception(String.Format("Il codice tipo soggetto da utilizzare nel caso di una persona fisica ({0}) non è valido. Verificare la configurazione", codiceTipoSoggettoPersonaFisica));
			}

			if (codiceTipoSoggettoPersonaGiuridica <= 0)
			{
				throw new Exception(String.Format("Il codice tipo soggetto da utilizzare nel caso di una persona giuridica ({0}) non è valido. Verificare la configurazione", codiceTipoSoggettoPersonaGiuridica));
			}

			if (codiceTipoSoggettoLegaleRappresentante <= 0)
			{
				throw new Exception(String.Format("Il codice tipo soggetto da utilizzare nel caso di un legale rappresentante ({0}) non è valido. Verificare la configurazione", codiceTipoSoggettoLegaleRappresentante));
			}


			var domanda = this._persistenzaStrategy.GetById(dDomanda);
			var anagrafica = domanda.ReadInterface.DenunceTaresBari.DatiContribuente;

			// Rimuove l'anagrafica con lo stesso tipo soggetto
			var anagraficheDaRimuovere = domanda.ReadInterface
						.Anagrafiche
						.Anagrafiche
						.Where(x => (
							x.TipoSoggetto.Id == codiceTipoSoggettoPersonaFisica ||
							x.TipoSoggetto.Id == codiceTipoSoggettoPersonaGiuridica ||
							x.TipoSoggetto.Id == codiceTipoSoggettoLegaleRappresentante))
						.ToList();

			foreach (var anagraficaDaRimuovere in anagraficheDaRimuovere)
			{
				this._anagraficheService.RimuoviAnagrafica(domanda, anagraficaDaRimuovere.Id.Value);
			}


			// Se è stata aggiunta un utenza esistente ne riporto i dati tra le anagrafiche
			if (domanda.ReadInterface.DenunceTaresBari.DatiContribuente.IdContribuente.HasValue)
			{
				var utenza = domanda.ReadInterface.DenunceTaresBari.DatiContribuente.ToAnagraficaDomanda(this._comuniService);
				var tipoSoggetto = utenza.TipoPersona == GestionePresentazioneDomanda.GestioneAnagrafiche.TipoPersonaEnum.Fisica ? codiceTipoSoggettoPersonaFisica : codiceTipoSoggettoPersonaGiuridica;

				utenza.TipoSoggetto = new TipoSoggettoDomanda
				{
					Id = tipoSoggetto,
					Descrizione = "Tipo soggetto " + tipoSoggetto
				};

				this._anagraficheService.SalvaAnagrafica(domanda, utenza);

				if (domanda.ReadInterface.DenunceTaresBari.DatiContribuente.LegaleRappresentante != null)
				{
					var legaleRappresentante = domanda.ReadInterface.DenunceTaresBari.DatiContribuente.LegaleRappresentante.ToAnagraficaDomanda(this._comuniService);

					legaleRappresentante.TipoSoggetto = new TipoSoggettoDomanda
					{
						Id = codiceTipoSoggettoLegaleRappresentante,
						Descrizione = "Tipo soggetto " + codiceTipoSoggettoLegaleRappresentante
					};

					this._anagraficheService.SalvaAnagrafica(domanda, legaleRappresentante);
				}
			}

			this._persistenzaStrategy.Salva(domanda);
		}



		public void MappaCampiSuSchedeDinamiche(int idDomanda, string pathFileMappature)
		{

			var domanda = this._persistenzaStrategy.GetById(idDomanda);

			var mappature = MappatureCampi.Load(pathFileMappature);

			mappature.ApplicaA(domanda);

			this._persistenzaStrategy.Salva(domanda);
		}
	}
}
