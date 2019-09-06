using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using CuttingEdge.Conditions;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
	using Init.Sigepro.FrontEnd.Infrastructure;

	public interface IValidazioneAnagraficheDomanda : ISpecification<IAnagraficheReadInterface>
	{
		IEnumerable<string> GetListaErrori();
	}

	public abstract class ValidazioneAnagraficheDomandaBase : IValidazioneAnagraficheDomanda
	{
		List<string> _listaErrori = new List<string>();

		#region IValidazioneAnagraficheDomanda Members

		public IEnumerable<string> GetListaErrori()
		{
			return this._listaErrori;
		}

		#endregion

		#region ISpecification<AnagraficheDomanda> Members

		public bool IsSatisfiedBy(IAnagraficheReadInterface readInterface)
		{
			this._listaErrori = new List<string>();

			DoValidation(readInterface);

			return this._listaErrori.Count == 0;
		}

		#endregion

		protected void AddError(string message)
		{
			this._listaErrori.Add(message);
		}

		protected abstract void DoValidation(IAnagraficheReadInterface readInterface);

	}

	/// <summary>
	/// Verifico se tutti i tipi soggetto obbligatori per il software siano presenti
	/// </summary>
	public class TuttiISoggettiObbligatoriSonoPresentiSpecification : ValidazioneAnagraficheDomandaBase
	{
		ITipiSoggettoService _tipiSoggettoService;
		int? _codiceIntervento;

		public TuttiISoggettiObbligatoriSonoPresentiSpecification(ITipiSoggettoService tipiSoggettoService, int? codiceIntervento)
		{
			Condition.Requires(tipiSoggettoService, "tipiSoggettoService").IsNotNull();

			this._tipiSoggettoService = tipiSoggettoService;
			this._codiceIntervento = codiceIntervento;
		}

		protected override void DoValidation(IAnagraficheReadInterface readInterface)
		{
			var tipiSoggettoObbligatori = this._tipiSoggettoService.GetTipiSoggettoObbligatori(this._codiceIntervento);

			foreach (var tipoSoggetto in tipiSoggettoObbligatori)
			{
				if (readInterface.Anagrafiche.Where( x => x.TipoSoggetto.Id.Value == tipoSoggetto.Codice ).Count() == 0)
					AddError(tipoSoggetto.Descrizione + " non specificato.");
			}
		}

	}



	/// <summary>
	/// Verifico se è stato trovato almeno un soggetto con flag "R" (Richiedente)
	/// </summary>
	public class EsisteAlmenoUnSoggettoRichiedente : ValidazioneAnagraficheDomandaBase
	{
		protected override void DoValidation(IAnagraficheReadInterface readInterface)
		{
			var listaRichiedenti = readInterface.GetRichiedenti();

			if (listaRichiedenti.Count() == 0)
				AddError("Tra le anagrafiche non è stato individuato nessun tipo soggetto identificabile come richiedente");
		}
	}



	/// <summary>
	/// Verifico che sia stata specificata un'anagrafica collegata per tutti i soggetti che la richiedono
	/// </summary>
	public class EsisteUnAnagraficaCollegataPerTuttiISoggettiCheLaRichiedono : ValidazioneAnagraficheDomandaBase
	{
		protected override void DoValidation(IAnagraficheReadInterface readInterface)
		{
			// Verifico che sia stata specificata un'anagrafica collegata per tutti i soggetti che la richiedono
			foreach (var anagrafica in readInterface.Anagrafiche.Where( x => x.TipoSoggetto.RichiedeAnagraficaCollegata))
			{
				if (anagrafica.AnagraficaCollegata == null)
					AddError("Il tipo soggetto \"" + anagrafica.TipoSoggetto.ToString() + "\" richiede un'azienda collegata");
			}
		}

	}



	/// <summary>
	/// Se è attivo il parametro che richiede la verifica della PEC controllo che almeno uno tra i richiedenti o il tecnico
	/// abbia specificato un indirizzo pec nei suoi dati anagrafici
	/// </summary>
	public class AlmenoUnSoggettoHaUnIndirizzoPecSeRichiesta : ValidazioneAnagraficheDomandaBase
	{
		bool _pecObbligatoria;

		public AlmenoUnSoggettoHaUnIndirizzoPecSeRichiesta(bool pecObbligatoria)
		{
			this._pecObbligatoria = pecObbligatoria;
		}

		protected override void DoValidation(IAnagraficheReadInterface readInterface)
		{
			if (!this._pecObbligatoria)
				return;

			var soggettiConPec = readInterface.Anagrafiche.Where( x => !String.IsNullOrEmpty(x.Contatti.Pec) && 
																		( x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente ||
																		  x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Tecnico ) );

			if (soggettiConPec.Count() > 0)
				return;

			var errMsg = new StringBuilder();

			var listaQualificheRichiedenti = new List<string>();
			var listaRichiedenti = readInterface.GetRichiedenti();

			foreach (var soggetto in listaRichiedenti)
				listaQualificheRichiedenti.Add(soggetto.TipoSoggetto.ToString());

			if (listaQualificheRichiedenti.Count() == 1)
				errMsg.AppendFormat("Occorre specificare l'indirizzo di Posta Elettronica Certificata della seguente tipologia di soggetto: {0}", String.Join(",", listaQualificheRichiedenti.ToArray()));
			else
				errMsg.AppendFormat("Occorre specificare l'indirizzo di Posta Elettronica Certificata di almeno una delle seguenti tipologie di soggetti: {0}", String.Join(",", listaQualificheRichiedenti.ToArray()));

			AddError(errMsg.ToString());
		}
	}


	/// <summary>
	/// Verifico che l'utente corrente sia stato inserito nella lista delle anagrafiche
	/// </summary>
	public class UtenteCorrentePresenteTraLeAnagrafiche : ValidazioneAnagraficheDomandaBase
	{
		string _codiceFiscaleUtenteCorrente;
		string _messaggioErrore;

		public UtenteCorrentePresenteTraLeAnagrafiche(string codiceFiscaleUtenteCorrente, string messaggioErrore)
		{
			this._codiceFiscaleUtenteCorrente = codiceFiscaleUtenteCorrente.ToUpperInvariant();
			this._messaggioErrore = messaggioErrore;
		}

		protected override void DoValidation(IAnagraficheReadInterface readInterface)
		{
			var anagraficaTrovata = readInterface.Anagrafiche
												 .Where(x => x.Codicefiscale.ToUpperInvariant() == this._codiceFiscaleUtenteCorrente)
												 .FirstOrDefault();

			if (anagraficaTrovata == null)
				AddError(this._messaggioErrore);
		}
	}

}
