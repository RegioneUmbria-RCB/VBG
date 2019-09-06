using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
	[ToolboxData("<{0}:FiltriArchivioIstanzeControl runat=server></{0}:FiltriArchivioIstanzeControl>")]
	public class FiltriArchivioIstanzeControl : FiltriVisuraControlBase
	{

		public FiltriArchivioIstanzeControl()
			: base(new FiltriArchivioIstanzeControlProvider())
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
            req.MeseIstanza = _meseIstanza.Value;
			req.NumeroAutorizzazione = _numAutorizzazione.Value;
			req.NumeroPratica = _codiceIstanza.Value;
			req.NumeroProtocollo = _numProtocollo.Value;
			req.Oggetto = _oggetto.Value;
			req.Stato = _statoIstanza.Value;
			req.CodSportello = Software;
			/*
			// TODO: Aggiungere i dati relativi alla partita iva o codice fiscale
			bool utenteTecnico = dettagliUtente.Tipologia.GetValueOrDefault(0) != 0;

			var parametriConfigurazione = ConfigurazioneFrontEnd.Parametri(IdComune, Software);
			var parametriRicercaUtente = utenteTecnico ? parametriConfigurazione.ParametriRicercaVisuraTecnico : parametriConfigurazione.ParametriRicercaVisuraNonTecnico;

			RichiestaListaPraticheCodFiscale cfUtente = new RichiestaListaPraticheCodFiscale();

			cfUtente.Value = dettagliUtente.Codicefiscale;

			if (string.IsNullOrEmpty(cfUtente.Value))
				cfUtente.Value = dettagliUtente.Partitaiva;

			cfUtente.cercaComeTecnico = parametriRicercaUtente.CercaComeTecnico;
			cfUtente.cercaComeRichiedente = parametriRicercaUtente.CercaComeRichiedente;
			cfUtente.cercaComeAzienda = parametriRicercaUtente.CercaComeAzienda;
			cfUtente.cercaNeiSoggettiCollegati = parametriRicercaUtente.CercaSoggettiCollegati;
			cfUtente.cercaAncheComePartitaIva = parametriRicercaUtente.CercaPartitaIva;

			req.CodFiscale = m_richiedente.Value != "" ? new RichiestaListaPraticheCodFiscale[2] : new RichiestaListaPraticheCodFiscale[1];

			req.CodFiscale[0] = cfUtente;
*/
			if (_richiedente.Value != "")
			{
				RichiestaListaPraticheCodFiscale cfRichiedente = new RichiestaListaPraticheCodFiscale();

				cfRichiedente.Value = _richiedente.Value;
				cfRichiedente.cercaComeTecnico = false;
				cfRichiedente.cercaComeRichiedente = true;
				cfRichiedente.cercaComeAzienda = true;
				cfRichiedente.cercaNeiSoggettiCollegati = false;
				cfRichiedente.cercaAncheComePartitaIva = false;

				req.CodFiscale = new[] { cfRichiedente };
			}
			
			return req;
		}
	}
}
