using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using VBG.Frontend.AppLogic.WsAnagraficheService;
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

		public override RichiestaListaPraticheV3 GetRichiestaListaPratiche(AnagraficaUtente dettagliUtente)
		{
            var req = new RichiestaListaPraticheV3();

            req.Software = this.Software;
            req.CodiceIntervento = !String.IsNullOrEmpty(_intervento.Value) ? Convert.ToInt32(_intervento.Value) : (int?)null;

            if (this._datiCatasto.AlmenoUnValoreSpecificato())
            {
                req.DatiCatastali = new FiltriDatiCatastali
                {
                    TipoCatasto = this._datiCatasto.TipoCatasto,
                    Foglio = this._datiCatasto.Foglio,
                    Particella = this._datiCatasto.Particella,
                    Subalterno = this._datiCatasto.Subalterno
                };
            }

            if (!String.IsNullOrEmpty(this._numProtocollo.Value) || !String.IsNullOrEmpty(this._dataProtocollo.Value))
            {
                req.DatiProtocollo = new FiltroDatiProtocollo
                {
                    Numero = this._numProtocollo.Value,
                    Data = this._dataProtocollo.DateValue
                };
            }

            if (!String.IsNullOrEmpty(this._stradario.Value))
            {
                req.Indirizzo = new FiltroIndirizzo
                {
                    CodiceStradario = Convert.ToInt32(this._stradario.Value),
                    Civico = this._civico.Value
                };
            }

            req.Fabbricato = this._fabbricato.Value;
            req.NomeOCfRichiedente = this._richiedente.Value;
            req.NumeroAutorizzazione = this._numAutorizzazione.Value;
            req.NumeroIstanza = this._codiceIstanza.Value;
            req.Oggetto = this._oggetto.Value;

            if (!String.IsNullOrEmpty(this._annoIstanza.Value))
            {
                req.PeriodoPresentazione = new FiltroPeriodoPresentazione
                {
                    Anno = Convert.ToInt32(this._annoIstanza.Value),
                    Mese = String.IsNullOrEmpty(this._meseIstanza.Value) ? (int?)null : Convert.ToInt32(this._meseIstanza.Value)
                };
            }

            req.StatoPratica = this._statoIstanza.Value;

            req.PosizioneArchivio = this._posizioneArchivio.Value;

            return req;
        }
	}
}
