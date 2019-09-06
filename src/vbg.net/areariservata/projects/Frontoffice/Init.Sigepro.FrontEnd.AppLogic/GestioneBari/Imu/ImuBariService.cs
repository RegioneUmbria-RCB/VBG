// -----------------------------------------------------------------------
// <copyright file="ImuBariService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Imu
{
	using System;
	using System.Linq;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
	using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
	using Init.Sigepro.FrontEnd.Bari.IMU;
	using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;
	using Init.Sigepro.FrontEnd.Bari.TARES;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ImuBariService
	{
        BariTaresService _taresBariService;
		IUtenzeImuService _utenzeService;
		ISalvataggioDomandaStrategy _persistenzaStrategy;
		IAnagraficheService _anagraficheService;

        public ImuBariService(IUtenzeImuService utenzeService, ISalvataggioDomandaStrategy persistenzaStrategy, IAnagraficheService anagraficheService, BariTaresService taresBariService)
		{
			this._utenzeService = utenzeService;
			this._persistenzaStrategy = persistenzaStrategy;
			this._anagraficheService = anagraficheService;
            this._taresBariService = taresBariService;
		}

		public DatiContribuenteImuDto TrovaUtenze(string codiceFiscaleOperatore, string codFiscaleOPIvaUtente)
		{
			return this._utenzeService.GetDatiImmobili(codiceFiscaleOperatore, codFiscaleOPIvaUtente);
		}

		public void ImpostaDatiImmobili(ImpostaDatiContribuenteImuCommand cmd)
		{
			var domanda = _persistenzaStrategy.GetById(cmd.IdDomanda);

			var anagraficheDaRimuovere = domanda.ReadInterface
									.Anagrafiche
									.Anagrafiche
									.Where(x => x.TipoSoggetto.Id == cmd.IdTipoSoggetto)
									.ToList();

			foreach (var anagraficaDaRimuovere in anagraficheDaRimuovere)
			{
				this._anagraficheService.RimuoviAnagrafica(domanda, anagraficaDaRimuovere.Id.Value);
			}

			var anagrafica = cmd.DatiContribuente.ToAnagraficaDomanda(cmd.ComuniService);

			anagrafica.TipoSoggetto = new TipoSoggettoDomanda
			{
				Id = cmd.IdTipoSoggetto,
				Descrizione = "Tipo soggetto " + cmd.IdTipoSoggetto
			};

			this._anagraficheService.SalvaAnagrafica(domanda, anagrafica);

			domanda.WriteInterface.ImuBari.ImpostaDatiImmobili(cmd.DatiContribuente);

			this._persistenzaStrategy.Salva(domanda);
		}


		public void ImpostaLocalizzazioneDomanda(int idDomanda, LocalizzazioniService localizzazioniService, int idIndirizzoNonDefinito)
		{
			var domanda = _persistenzaStrategy.GetById(idDomanda);

			var idComune = domanda.ReadInterface.AltriDati.AliasComune;
			var codiceComune = domanda.ReadInterface.AltriDati.CodiceComune;
			var utenza = domanda.ReadInterface.ImuBari.DatiImmobili.ListaImmobili[0];
			var datiindirizzo = utenza.Ubicazione;
			var nomeVia = datiindirizzo.Via;

			var searcher = new IndirizzoSearcher(idComune, localizzazioniService, idIndirizzoNonDefinito);
			var match = searcher.TrovaDaMatchParziale(codiceComune, String.Empty, nomeVia);

			var localizzazione = new NuovaLocalizzazione(match.Codice, match.Descrizione, datiindirizzo.Civico)
			{

				Cap = datiindirizzo.Cap,
				Esponente = datiindirizzo.Esponente,
				Interno = datiindirizzo.Interno,
				Fabbricato = datiindirizzo.Palazzina,
				Piano = datiindirizzo.Piano,
				Scala = datiindirizzo.Scala,
				Note = nomeVia
			};

			var riferimentiCatastali = (NuovoRiferimentoCatastale)null;

			if (utenza.RiferimentiCatastali != null)
			{
				riferimentiCatastali = new NuovoRiferimentoCatastale("F", "Fabbricati", utenza.RiferimentiCatastali.Foglio, utenza.RiferimentiCatastali.Particella, utenza.RiferimentiCatastali.Subalterno);
			}

			localizzazioniService.AggiungiLocalizzazione(idDomanda, localizzazione, riferimentiCatastali);
		}


        public void InserisciOperatoreeCafNeiSoggettiDellaDomanda(int idDomanda, string codiceFiscaleOperatore, int codiceTipoSoggetto, int codiceTipoSoggettoAnagraficaCollegata)
        {
            var domanda = this._persistenzaStrategy.GetById(idDomanda);

            if (domanda.ReadInterface.Anagrafiche.Anagrafiche.Count() > 0)
            {
                return;
            }

            var codiceFiscaleCaf = this._taresBariService.GetCodiceFiscaleCafDaCodiceFiscaleOperatore(codiceFiscaleOperatore);

            // Se l'utente collegato è associato ad un CAF allora il CAF va inserito come 
            // azienda collegata dell'utente
            if (!string.IsNullOrEmpty(codiceFiscaleCaf))
                this._anagraficheService.AggiungiAnagraficaConSoggettoCollegato(idDomanda, codiceFiscaleOperatore, codiceTipoSoggetto, codiceFiscaleCaf, codiceTipoSoggettoAnagraficaCollegata);
        }


		public void PopolaCampiSchede(int idDomanda, MappaturaCampiSchedeImu mappa)
		{
			var domanda = _persistenzaStrategy.GetById(idDomanda);

			var valoriCampiDinamici = mappa.Mappa(domanda.ReadInterface.ImuBari.DatiImmobili);

			foreach (var valore in valoriCampiDinamici)
			{
				domanda.WriteInterface.DatiDinamici.AggiornaOCrea(valore.IdCampo, 0, 0, valore.Valore, valore.ValoreDecodificato, string.Empty);
			}

			this._persistenzaStrategy.Salva(domanda);
		}
	}
}
