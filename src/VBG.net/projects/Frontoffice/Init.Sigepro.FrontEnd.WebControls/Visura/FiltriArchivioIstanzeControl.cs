using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;


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

			if (m_richiedente.Value != "")
			{
				RichiestaListaPraticheCodFiscale cfRichiedente = new RichiestaListaPraticheCodFiscale();

				cfRichiedente.Value = m_richiedente.Value;
				cfRichiedente.cercaComeTecnico = parametriConfigurazione.ParametriRicercaVisuraFiltroRichiedente.CercaComeTecnico;
				cfRichiedente.cercaComeRichiedente = parametriConfigurazione.ParametriRicercaVisuraFiltroRichiedente.CercaComeRichiedente;
				cfRichiedente.cercaComeAzienda = parametriConfigurazione.ParametriRicercaVisuraFiltroRichiedente.CercaComeAzienda;
				cfRichiedente.cercaNeiSoggettiCollegati = parametriConfigurazione.ParametriRicercaVisuraFiltroRichiedente.CercaSoggettiCollegati;
				cfRichiedente.cercaAncheComePartitaIva = parametriConfigurazione.ParametriRicercaVisuraFiltroRichiedente.CercaPartitaIva;

				req.CodFiscale[1] = cfRichiedente;
			}
			*/
			return req;
		}
	}
}
