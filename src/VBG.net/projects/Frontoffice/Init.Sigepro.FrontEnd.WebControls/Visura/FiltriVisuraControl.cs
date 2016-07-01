using System;
using System.Web.UI;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;


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
			req.AnnoIstanza = m_annoIstanza.Value;
			req.Civico = m_civico.Value;
			req.CodiceIntervento = m_intervento.Value;
			req.CodSportello = Software;
			req.CodViario = m_stradario.Value;
			req.DataProtocollo = m_dataProtocollo.Value;
			req.Foglio = m_datiCatasto.Foglio;
			req.Particella = m_datiCatasto.Particella;
			req.Subalterno = m_datiCatasto.Subalterno;
			req.TipoCatasto = m_datiCatasto.TipoCatasto;
			req.MeseIstanza = String.IsNullOrEmpty(m_meseIstanza.Value) ? "" : m_meseIstanza.Value.PadLeft(2, '0');
			req.NumeroAutorizzazione = m_numAutorizzazione.Value;
			req.NumeroPratica = m_codiceIstanza.Value;
			req.NumeroProtocollo = m_numProtocollo.Value;
			req.Oggetto = m_oggetto.Value;
			req.Stato = m_statoIstanza.Value;
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

			req.CodFiscale = !String.IsNullOrEmpty(m_richiedente.Value ) ? new RichiestaListaPraticheCodFiscale[2] : new RichiestaListaPraticheCodFiscale[1];

			req.CodFiscale[0] = cfUtente;

			if (m_richiedente.Value != "")
			{
				req.CodFiscale[1] = new RichiestaListaPraticheCodFiscale
				{

					Value = m_richiedente.Value,
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