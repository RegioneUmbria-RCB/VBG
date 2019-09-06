using System;
using System.Web.UI;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
	/// <summary>
	/// Descrizione di riepilogo per FiltriVisuraControl.
	/// </summary>
	[ToolboxData("<{0}:FiltriVisuraControl runat=server></{0}:FiltriVisuraControl>")]
	public class FiltriVisuraControl : FiltriVisuraControlBase
	{

		public FiltriVisuraControl():base( new FiltriVisuraControlProvider())
		{

		}

		public override RichiestaListaPratiche GetRichiestaListaPratiche(AnagraficaUtente dettagliUtente)
		{
			//ConfigurationItem cfg = Config.Get(IdComune);
			RichiestaListaPratiche req = new RichiestaListaPratiche();

			req.CodEnte = IdComune;
			req.AnnoIstanza = _annoIstanza.Value;
			req.Civico = _civico.Value;
			req.CodiceIntervento = _intervento.Value;
			req.CodSportello = Software;
			req.CodViario = _stradario.Value;
            req.DataProtocollo = _dataProtocollo.DateValue.GetValueOrDefault(DateTime.MinValue); 
            req.Foglio = _datiCatasto.Foglio;
			req.Particella = _datiCatasto.Particella;
			req.Subalterno = _datiCatasto.Subalterno;
			req.TipoCatasto = _datiCatasto.TipoCatasto;
			req.MeseIstanza = String.IsNullOrEmpty(_meseIstanza.Value) ? "" : _meseIstanza.Value.PadLeft(2, '0');
			req.NumeroAutorizzazione = _numAutorizzazione.Value;
			req.NumeroPratica = _codiceIstanza.Value;
			req.NumeroProtocollo = _numProtocollo.Value;
			req.Oggetto = _oggetto.Value;
			req.Stato = _statoIstanza.Value;
			req.CodSportello = Software;

			// TODO: Aggiungere i dati relativi alla partita iva o codice fiscale
			bool utenteTecnico = dettagliUtente.Tipologia.GetValueOrDefault(0) != 0;

			var parametriConfigurazione = _configurazione.Parametri;
			var parametriRicercaUtente = utenteTecnico ? parametriConfigurazione.RicercaTecnico : parametriConfigurazione.RicercaNonTecnico;

			RichiestaListaPraticheCodFiscale cfUtente = new RichiestaListaPraticheCodFiscale();

			cfUtente.Value = dettagliUtente.Codicefiscale;

			if (string.IsNullOrEmpty(cfUtente.Value))
				cfUtente.Value = dettagliUtente.Partitaiva;

			cfUtente.cercaComeTecnico = parametriRicercaUtente.CercaComeTecnico;
			cfUtente.cercaComeRichiedente = parametriRicercaUtente.CercaComeRichiedente;
			cfUtente.cercaComeAzienda = parametriRicercaUtente.CercaComeAzienda;
			cfUtente.cercaNeiSoggettiCollegati = parametriRicercaUtente.CercaSoggettiCollegati;
			cfUtente.cercaAncheComePartitaIva = parametriRicercaUtente.CercaPartitaIva;

			req.CodFiscale = !String.IsNullOrEmpty(_richiedente.Value ) ? new RichiestaListaPraticheCodFiscale[2] : new RichiestaListaPraticheCodFiscale[1];

			req.CodFiscale[0] = cfUtente;

			if (_richiedente.Value != "")
			{
				req.CodFiscale[1] = new RichiestaListaPraticheCodFiscale
				{

					Value = _richiedente.Value,
					cercaComeTecnico = parametriConfigurazione.FiltroRichiedente.CercaComeTecnico,
					cercaComeRichiedente = parametriConfigurazione.FiltroRichiedente.CercaComeRichiedente,
					cercaComeAzienda = parametriConfigurazione.FiltroRichiedente.CercaComeAzienda,
					cercaNeiSoggettiCollegati = parametriConfigurazione.FiltroRichiedente.CercaSoggettiCollegati,
					cercaAncheComePartitaIva = parametriConfigurazione.FiltroRichiedente.CercaPartitaIva,
				};		
			}

			return req;
		}
	}
}