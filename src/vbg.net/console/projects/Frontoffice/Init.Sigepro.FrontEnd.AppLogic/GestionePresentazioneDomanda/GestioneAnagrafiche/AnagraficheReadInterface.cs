using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Ninject;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	public class AnagraficheReadInterface : IAnagraficheReadInterface
	{
        PresentazioneIstanzaDbV2 _database;
		List<AnagraficaDomanda> _anagrafiche;
		IEnumerable<AnagraficaDomanda> _richiedenti;
		AnagraficaDomanda _tecnico;
		AnagraficaDomanda _azienda;
		IEnumerable<AnagraficaDomanda> _altriSoggetti;

        [Inject]
        protected ILogicaRisoluzioneTecnico LogicaRisoluzioneTecnico { get; set; }

		public AnagraficheReadInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;

            FoKernelContainer.Inject(this);

			PreparaAnagrafiche();
			CollegaAnagrafiche();
			EstraiSoggettiDiInteresse();
		}

		private void EstraiSoggettiDiInteresse()
		{
			this._richiedenti = this._anagrafiche
									.Where(x => x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente);

            this._tecnico = this.LogicaRisoluzioneTecnico.Risolvi(this._anagrafiche);

			this._azienda = this._anagrafiche
								.Where(x => x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Azienda)
								.FirstOrDefault();

			this._altriSoggetti = this._anagrafiche
									  .Where( x => 
										  this.GetRichiedente() != x && 
										  x != this._tecnico && 
										  x != this._azienda );
		}

		private void CollegaAnagrafiche()
		{
			this._anagrafiche.ForEach(x =>
			{
				if (!x.IdAnagraficaCollegata.HasValue)
					return;

				x.CollegaAnagrafica(GetById(x.IdAnagraficaCollegata.Value));
			});
		}

		public AnagraficaDomanda GetById(int idAnagrafica)
		{
			return this._anagrafiche.Where(x => x.Id.Value == idAnagrafica).FirstOrDefault();
		}

		private void PreparaAnagrafiche()
		{
			this._anagrafiche = this._database.ANAGRAFE
											  .Cast<PresentazioneIstanzaDbV2.ANAGRAFERow>()
											  .Select(x => AnagraficaDomanda.FromAnagrafeRow(x)).ToList();
		}


		public IEnumerable<AnagraficaDomanda> Anagrafiche
		{
			get { return _anagrafiche; }
		}

		public IEnumerable<AnagraficaDomanda> GetRichiedenti()
		{
			return this._richiedenti;
		}

		public IEnumerable<AnagraficaDomanda> GetAltriSoggetti()
		{
			return this._altriSoggetti;
		}

		public AnagraficaDomanda GetRichiedente()
		{
			var listaRichiedenti = GetRichiedenti();

			if (listaRichiedenti.Count() == 0)
				return null;

			// Leggo la lista dei richiedenti che hanno una PEC valida
			var listaRichiedentiConPec = listaRichiedenti.Where(x => !String.IsNullOrEmpty(x.Contatti.Pec));
			
			// Se esistono richiedenti con pec hanno la priorità sui richiedenti senza PEC
			if (listaRichiedentiConPec.Count() > 0)
			{
				// Nella lista esistono soggetti senza procura?
				var soggettoConPecSenzaProcura = EstraiPrimoRichiedenteSenzaProcura(listaRichiedentiConPec);

				if (soggettoConPecSenzaProcura != null)
					return soggettoConPecSenzaProcura;

				return listaRichiedentiConPec.ElementAt(0);
			}

			// Non esistono soggetti con PEC, cerco nella lista di tutti i richiedenti se ne esiste uno senza procura
			var soggettoSenzaPecSenzaProcura = EstraiPrimoRichiedenteSenzaProcura(listaRichiedenti);

			if (soggettoSenzaPecSenzaProcura != null)
				return soggettoSenzaPecSenzaProcura;

			// Nessun soggetto ha la pec e tutti i soggetti hanno la procura. A questo punto un soggetto vale l'altro 
			// e restituisco il primo soggetto della lista
			return listaRichiedenti.ElementAt(0);
		}

		private AnagraficaDomanda EstraiPrimoRichiedenteSenzaProcura(IEnumerable<AnagraficaDomanda> listaRichiedenti)
		{
			foreach (var richiedente in listaRichiedenti)
			{
				var codiceProcuratore = this._database.Procure
													  .Where( x => x.CodiceAnagrafe.ToUpperInvariant() == richiedente.Codicefiscale.ToUpperInvariant())
													  .Select( x => x.CodiceProcuratore );

				if (String.IsNullOrEmpty(codiceProcuratore.ElementAt(0)))
					return richiedente;
			}

			return null;
		}

		public AnagraficaDomanda GetTecnico()
		{
			return this._tecnico;
		}

		public AnagraficaDomanda GetAzienda()
		{
			return this._azienda;
		}

		public AnagraficaDomanda FindByRiferimentiSoggetto(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
		{
			return this._anagrafiche.Where(x => x.TipoPersona == tipoPersona &&
												(x.Codicefiscale.ToUpperInvariant() == codiceFiscalePartitaIva.ToUpperInvariant() ||
												  x.PartitaIva.ToUpperInvariant() == codiceFiscalePartitaIva.ToUpperInvariant()))
									.FirstOrDefault();
		}


		public IEnumerable<AnagraficaDomanda> GetPossibiliProcuratoriDi(string codiceFiscaleUtente)
		{
			return this._anagrafiche.Where(x => x.TipoPersona == TipoPersonaEnum.Fisica &&
												 x.Codicefiscale.ToUpperInvariant() != codiceFiscaleUtente.ToUpperInvariant());
		}

		public IEnumerable<AnagraficaDomanda> GetSoggettiSottoscrittori()
		{
			var rVal = new List<AnagraficaDomanda>();

			foreach (var procurato in this._database.Procure)
			{
				var soggettoProcurato	= !String.IsNullOrEmpty(procurato.CodiceProcuratore);
				var codiceFiscale		= soggettoProcurato ? procurato.CodiceProcuratore : procurato.CodiceAnagrafe;

				if (rVal.FirstOrDefault(r => r.Codicefiscale == codiceFiscale) != null)
					continue;
				
				var q = this._anagrafiche.Where( x => x.Codicefiscale.ToUpperInvariant() == codiceFiscale.ToUpperInvariant());

				// Se ho più di una riga il soggetto potrebbe essere in qualità di altro tipo soggetto che non può firmare
				// come ad esempio tecnico. Verifico prima che ci sia una qualifica che permette la firma altrimenti
				// uso il tipo soggetto impostato
				if (q.Count() == 1)
				{
					rVal.Add(q.First());
				}
				else
				{
					bool tipoSoggettoTrovato = false;

					foreach (var soggetto in q)
					{
						if (soggetto.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente)
						{
							rVal.Add(soggetto);
							tipoSoggettoTrovato = true;
							break;
						}
					}

					if (!tipoSoggettoTrovato)
						rVal.Add(q.First());
				}
			}

			return rVal;
		}

		public IEnumerable<AnagraficaDomanda> GetSoggettiNonSottoscrittori()
		{
			var rVal = new List<AnagraficaDomanda>();

			var listaProcurati = this._database.Procure.Where( x => !String.IsNullOrEmpty(x.CodiceProcuratore));

			foreach (var procurato in listaProcurati)
			{
				if (rVal.FirstOrDefault(x => x.Codicefiscale.ToUpperInvariant() == procurato.CodiceAnagrafe.ToUpperInvariant()) != null)
					continue;

				rVal.Add(FindByRiferimentiSoggetto(TipoPersonaEnum.Fisica,procurato.CodiceAnagrafe));
			}

			return rVal;
		}

		public IEnumerable<AnagraficaDomanda> GetAnagraficheCollegabili()
		{
			return this._anagrafiche.Where(x => x.TipoPersona == TipoPersonaEnum.Giuridica);
		}


		public AnagraficaDomanda GetLegaleRappresentanteDi(AnagraficaDomanda azienda, ITipiSoggettoService tipiSoggettoService)
		{
			// Provo ad estrarre il legale rappresentante (è la prima persona fisica collegata a questa anagrafica con flag_legalerapp = 1)
			var res = from lr in this.Anagrafiche
					  where lr.TipoPersona == TipoPersonaEnum.Fisica &&
							lr.AnagraficaCollegata != null &&
							lr.IdAnagraficaCollegata == azienda.Id &&
							IsLegaleRappresentante(tipiSoggettoService,lr.TipoSoggetto.Id.Value)
					  select lr;

			return res.FirstOrDefault();
		}

		private bool IsLegaleRappresentante(ITipiSoggettoService tipiSoggettoService,int codiceTipoSoggetto)
		{
			return tipiSoggettoService.GetById(codiceTipoSoggetto).FlagLegaleRappresentante;
		}
	}
}
