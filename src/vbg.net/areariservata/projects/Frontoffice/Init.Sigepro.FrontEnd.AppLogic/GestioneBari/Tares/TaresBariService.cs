// -----------------------------------------------------------------------
// <copyright file="TaresBariService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
	using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
	using Init.Sigepro.FrontEnd.Bari.TARES;
	using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;

	public class TaresBariService
	{
		public class MappaturaCampiSchede
		{
			public class CampoDinamicoMappato
			{
				public readonly int IdCampo;
				public readonly string Valore;
				public readonly string ValoreDecodificato;

				public CampoDinamicoMappato(int idCampo, string valore, string valoreDecodificato)
				{
					this.IdCampo = idCampo;
					this.Valore = valore;
					this.ValoreDecodificato = valoreDecodificato;
				}

				public CampoDinamicoMappato(int idCampo, string valore)
				{
					this.IdCampo = idCampo;
					this.Valore = valore;
					this.ValoreDecodificato = valore;
				}
			}

			private int[] _idCampoIdUtenza;
			private int[] _idCampoVia;
			private int[] _idCampoCivico;
			private int[] _idcampoPalazzo;
			private int[] _idCampoScala;
			private int[] _idCampoPiano;
			private int[] _idCampoInterno;
			private int[] _idCampoMq;
			private int[] _idCampoSezione;
			private int[] _idCampoFoglio;
			private int[] _idCampoParticella;
			private int[] _idCampoSub;
			private int[] _idCampoInizioUtenza;
			private int[] _idCampoVariazioneUtenza;

			public int IdCampoIdContribuente { get; set; }

			public void SetIdCampoIdUtenza(string value)
			{
				this._idCampoIdUtenza = SplitToIntArray(value);
			}

			public void SetIdCampoVia(string value)
			{
				this._idCampoVia = SplitToIntArray(value);
			}

			public void SetIdCampoCivico(string value)
			{
				this._idCampoCivico = SplitToIntArray(value);
			}

			public void SetIdcampoPalazzo(string value)
			{
				this._idcampoPalazzo = SplitToIntArray(value);
			}

			public void SetIdCampoScala(string value)
			{
				this._idCampoScala = SplitToIntArray(value);
			}

			public void SetIdCampoPiano(string value)
			{
				this._idCampoPiano = SplitToIntArray(value);
			}

			public void SetIdCampoInterno(string value)
			{
				this._idCampoInterno = SplitToIntArray(value);
			}

			public void SetIdCampoMq(string value)
			{
				this._idCampoMq = SplitToIntArray(value);
			}

			public void SetIdCampoSezione(string value)
			{
				this._idCampoSezione = SplitToIntArray(value);
			}

			public void SetIdCampoFoglio(string value)
			{
				this._idCampoFoglio = SplitToIntArray(value);
			}

			public void SetIdCampoParticella(string value)
			{
				this._idCampoParticella = SplitToIntArray(value);
			}

			public void SetIdCampoSub(string value)
			{
				this._idCampoSub = SplitToIntArray(value);
			}

			public void SetIdCampoInizioUtenza(string value)
			{
				this._idCampoInizioUtenza = SplitToIntArray(value);
			}

			public void SetIdCampoVariazioneUtenza(string value)
			{
				this._idCampoVariazioneUtenza = SplitToIntArray(value);
			}

			private int[] SplitToIntArray(string value)
			{
				return value.Split(',').Select(x => Convert.ToInt32(x)).ToArray();
			}

			internal IEnumerable<CampoDinamicoMappato> Mappa(DatiContribuenteDto datiContribuenteDto)
			{
				var abitazione = datiContribuenteDto.ElencoUtenzeAttive.Where(x => x.TipoUtenza == TipoUtenzaTaresEnum.Abitazione).First();
				var pertinenza1 = datiContribuenteDto.ElencoUtenzeAttive.Where(x => x.TipoUtenza == TipoUtenzaTaresEnum.Pertinenza1).FirstOrDefault();
				var pertinenza2 = datiContribuenteDto.ElencoUtenzeAttive.Where(x => x.TipoUtenza == TipoUtenzaTaresEnum.Pertinenza2).FirstOrDefault();

				var result = new List<CampoDinamicoMappato>();

				if (IdCampoIdContribuente != -1)
				{
					result.Add(new CampoDinamicoMappato(IdCampoIdContribuente, datiContribuenteDto.IdentificativoContribuente.ToString()));
				}

				var utenzaVuota = DatiUtenzaDto.Vuoto();

				result.AddRange(MappaInternal(0, utenzaVuota));
				result.AddRange(MappaInternal(1, utenzaVuota));
				result.AddRange(MappaInternal(2, utenzaVuota));

				// Abitazione
				result.AddRange(MappaInternal(0, abitazione));

				// Pertinenza 1
				if (pertinenza1 != null)
				{
					result.AddRange(MappaInternal(1, pertinenza1));
				}

				// Pertinenza 3
				if (pertinenza2 != null)
				{
					result.AddRange(MappaInternal(2, pertinenza2));
				}

				return result;
			}

			private IEnumerable<CampoDinamicoMappato> MappaInternal(int idx, DatiUtenzaDto datiUtenza)
			{
				yield return new CampoDinamicoMappato(_idCampoIdUtenza[idx], datiUtenza.IdentificativoUtenza);

				yield return new CampoDinamicoMappato(_idCampoVia[idx], datiUtenza.DatiIndirizzo.Via);

				yield return new CampoDinamicoMappato(_idCampoCivico[idx], datiUtenza.DatiIndirizzo.NumeroCivico);

				yield return new CampoDinamicoMappato(_idCampoScala[idx], datiUtenza.DatiIndirizzo.Scala);

				yield return new CampoDinamicoMappato(_idCampoPiano[idx], datiUtenza.DatiIndirizzo.Piano);

				yield return new CampoDinamicoMappato(_idCampoInterno[idx], datiUtenza.DatiIndirizzo.Interno);

				yield return new CampoDinamicoMappato(_idCampoMq[idx], datiUtenza.Superficie.ToString());

				yield return new CampoDinamicoMappato(_idCampoSezione[idx], datiUtenza.DatiCatastali.Sezione);

				yield return new CampoDinamicoMappato(_idCampoFoglio[idx], datiUtenza.DatiCatastali.Foglio);

				yield return new CampoDinamicoMappato(_idCampoParticella[idx], datiUtenza.DatiCatastali.Particella);

				yield return new CampoDinamicoMappato(_idCampoSub[idx], datiUtenza.DatiCatastali.Subalterno);

				yield return new CampoDinamicoMappato(_idCampoInizioUtenza[idx], datiUtenza.DataInizioUtenza);

				yield return new CampoDinamicoMappato(_idCampoVariazioneUtenza[idx], datiUtenza.DataVariazioneUtenza);
			}
		}

		public class ImpostaUtenzaCommand
		{
			public readonly int IdDomanda;
			public readonly DatiContribuenteDto DatiContribuente;
			public readonly int CodiceTiposoggettoRichiedente;
			public readonly IComuniService ComuniService;

			public ImpostaUtenzaCommand(int idDomanda, DatiContribuenteDto datiContribuente, int codiceTipoSoggettoRichiedente, IComuniService comuniService)
			{
				this.IdDomanda = idDomanda;
				this.DatiContribuente = datiContribuente;
				this.CodiceTiposoggettoRichiedente = codiceTipoSoggettoRichiedente;
				this.ComuniService = comuniService;
			}
		}

		private BariTaresService _taresBariService;
		private ISalvataggioDomandaStrategy _persistenzaStrategy;
		private IAnagraficheService _anagraficheService;

		public TaresBariService(ISalvataggioDomandaStrategy persistenzaStrategy, BariTaresService taresBariService, IAnagraficheService anagraficheService)
		{
			this._taresBariService = taresBariService;
			this._persistenzaStrategy = persistenzaStrategy;
			this._anagraficheService = anagraficheService;
		}

		public void InserisciOperatoreeCafNeiSoggettiDellaDomanda(int idDomanda, string codiceFiscaleOperatore, int codiceTipoSoggetto, int codiceTipoSoggettoAnagraficaCollegata)
		{
			var domanda = this._persistenzaStrategy.GetById(idDomanda);

			if (domanda.ReadInterface.Anagrafiche.Anagrafiche.Count() > 0)
			{
				return;
			}

			var codiceFiscaleCaf = this._taresBariService.GetCodiceFiscaleCafDaCodiceFiscaleOperatore(codiceFiscaleOperatore);

			this._anagraficheService.AggiungiAnagraficaConSoggettoCollegato(idDomanda, codiceFiscaleOperatore, codiceTipoSoggetto, codiceFiscaleCaf, codiceTipoSoggettoAnagraficaCollegata);
		}

		public IEnumerable<DatiContribuenteDto> TrovaUtenze(string codiceFiscaleOperatore, string codFiscaleOCodUtente)
		{
			return this._taresBariService.TrovaUtenze(codiceFiscaleOperatore, codFiscaleOCodUtente);
		}

		public DatiContribuenteDto GetDettagliUtenza(string codiceFiscaleOperatore, int identificativoContribuente/*, string identificativoUtenza*/)
		{
			return this._taresBariService.GetDettagliUtenza(codiceFiscaleOperatore, identificativoContribuente/*, identificativoUtenza*/);
		}

		public void ImpostaUtenza(ImpostaUtenzaCommand cmd)
		{
			var daticontribuente = cmd.DatiContribuente.DatiAnagraficiContribuente;
			var indirizzo = cmd.DatiContribuente.DatiResidenzaContribuente;

			// Comune nascita
			var comuneNascita = cmd.DatiContribuente.GetDatiComuneNascita(cmd.ComuniService);
			var codiceComuneNascita = comuneNascita == null ? string.Empty : comuneNascita.CodiceComune;
			var provinciaNascita = comuneNascita == null ? string.Empty : comuneNascita.SiglaProvincia;

			// Comune residenza
			var comuneResidenza = cmd.DatiContribuente.GetDatiComuneResidenza(cmd.ComuniService);
			var codiceComuneResidenza = comuneResidenza == null ? string.Empty : comuneResidenza.CodiceComune;
			var provinciaResidenza = comuneResidenza == null ? string.Empty : comuneResidenza.SiglaProvincia;

			var anagrafica = AnagraficaDomanda.DaCodiceFiscaleTipoPersona(TipoPersonaEnum.Fisica, daticontribuente.CodiceFiscale);

			anagrafica.Nome = daticontribuente.Nome;
			anagrafica.Nominativo = daticontribuente.Cognome;
			anagrafica.DatiNascita = new DatiNascitaAnagrafica(codiceComuneNascita, provinciaNascita, DateTime.ParseExact(daticontribuente.DataNascita, "dd/MM/yyyy", null));
			anagrafica.IndirizzoResidenza = new IndirizzoAnagraficaDomanda(indirizzo.NomeViaConCivico, indirizzo.Comune, indirizzo.Cap, provinciaResidenza, codiceComuneResidenza);
			anagrafica.TipoSoggetto = new TipoSoggettoDomanda
			{
				Id = cmd.CodiceTiposoggettoRichiedente,
				Descrizione = "Tipo soggetto " + cmd.CodiceTiposoggettoRichiedente
			};

			var domanda = _persistenzaStrategy.GetById(cmd.IdDomanda);

			var anagraficheDaRimuovere = domanda.ReadInterface
												.Anagrafiche
												.Anagrafiche
												.Where(x => x.TipoSoggetto.Id == cmd.CodiceTiposoggettoRichiedente)
												.ToList();

			foreach (var anagraficaDaRimuovere in anagraficheDaRimuovere)
			{
				this._anagraficheService.RimuoviAnagrafica(domanda, anagraficaDaRimuovere.Id.Value);
			}

			this._anagraficheService.SalvaAnagrafica(domanda, anagrafica);

			domanda.WriteInterface.TaresBari.ImpostaUtenza(cmd.DatiContribuente);

			this._persistenzaStrategy.Salva(domanda);
		}

		public void PopolaCampiSchede(int idDomanda, MappaturaCampiSchede mappaturaCampi)
		{
			var domanda = _persistenzaStrategy.GetById(idDomanda);

			var valoriCampiDinamici = mappaturaCampi.Mappa(domanda.ReadInterface.TaresBari.DatiContribuente);

			foreach (var valore in valoriCampiDinamici)
			{
				domanda.WriteInterface.DatiDinamici.AggiornaOCrea(valore.IdCampo, 0, 0, valore.Valore, valore.ValoreDecodificato, string.Empty);
			}

			this._persistenzaStrategy.Salva(domanda);
		}

		public void ImpostaLocalizzazioneDomanda(int idDomanda, LocalizzazioniService localizzazioniService, int idIndirizzoNonDefinito)
		{
			var domanda = _persistenzaStrategy.GetById(idDomanda);

			var idComune = domanda.ReadInterface.AltriDati.AliasComune;
			var codiceComune = domanda.ReadInterface.AltriDati.CodiceComune;
			var utenza = domanda.ReadInterface.TaresBari.DatiContribuente.ElencoUtenzeAttive[0];
			var datiindirizzo = utenza.DatiIndirizzo;
			var nomeVia = datiindirizzo.Via;

			var searcher = new IndirizzoSearcher(idComune, localizzazioniService, idIndirizzoNonDefinito);
			var match = searcher.TrovaDaMatchParziale(codiceComune, String.Empty, nomeVia);
			var localizzazione = new NuovaLocalizzazione(match.Codice, match.Descrizione, datiindirizzo.NumeroCivico)
			{
				Cap = datiindirizzo.Cap,
				Esponente = datiindirizzo.Esponente,
				Interno = datiindirizzo.Interno,
				Km = datiindirizzo.Km,
				Piano = datiindirizzo.Piano,
				Scala = datiindirizzo.Scala,
				Note = nomeVia
			};
			var riferimentiCatastali = (NuovoRiferimentoCatastale)null;

			if (utenza.DatiCatastali != null)
			{
				riferimentiCatastali = new NuovoRiferimentoCatastale("F", "Fabbricati", utenza.DatiCatastali.Foglio, utenza.DatiCatastali.Particella, utenza.DatiCatastali.Subalterno);
			}

			localizzazioniService.AggiungiLocalizzazione(idDomanda, localizzazione, riferimentiCatastali);
		}
	}
}
