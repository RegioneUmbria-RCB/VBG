using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Web.UI;

namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
    /// <summary>
    /// Descrizione di riepilogo per FiltriVisuraControl.
    /// </summary>
    [ToolboxData("<{0}:FiltriVisuraControl runat=server></{0}:FiltriVisuraControl>")]
    public class FiltriVisuraControl : FiltriVisuraControlBase
    {

        public FiltriVisuraControl() : base(new FiltriVisuraControlProvider())
        {

        }

        public override RichiestaListaPraticheV3 GetRichiestaListaPratiche(AnagraficaUtente dettagliUtente)
        {
            var req = new RichiestaListaPraticheV3();

            req.PersonaAventeTitolo = new FiltroPersonaAventeTitoloDiVisura
            {
                CodiceFiscale = dettagliUtente.Codicefiscale
            };

            req.Software = Software;
            req.CodiceIntervento = !String.IsNullOrEmpty(_intervento.Value) ? Convert.ToInt32(_intervento.Value) : (int?)null;

            if (_datiCatasto.AlmenoUnValoreSpecificato())
            {
                req.DatiCatastali = new FiltriDatiCatastali
                {
                    TipoCatasto = _datiCatasto.TipoCatasto,
                    Foglio = _datiCatasto.Foglio,
                    Particella = _datiCatasto.Particella,
                    Subalterno = _datiCatasto.Subalterno
                };
            }

            if (!String.IsNullOrEmpty(_numProtocollo.Value) || !String.IsNullOrEmpty(_dataProtocollo.Value))
            {
                req.DatiProtocollo = new FiltroDatiProtocollo
                {
                    Numero = _numProtocollo.Value,
                    Data = _dataProtocollo.DateValue
                };
            }

            if (!String.IsNullOrEmpty(_stradario.Value))
            {
                req.Indirizzo = new FiltroIndirizzo
                {
                    CodiceStradario = Convert.ToInt32(_stradario.Value),
                    Civico = _civico.Value
                };
            }

            req.Fabbricato = _fabbricato.Value;
            req.NomeOCfRichiedente = _richiedente.Value;
            req.NumeroAutorizzazione = _numAutorizzazione.Value;
            req.NumeroIstanza = _codiceIstanza.Value;
            req.Oggetto = _oggetto.Value;

            if (!String.IsNullOrEmpty(_annoIstanza.Value))
            {
                req.PeriodoPresentazione = new FiltroPeriodoPresentazione
                {
                    Anno = Convert.ToInt32(_annoIstanza.Value),
                    Mese = String.IsNullOrEmpty(_meseIstanza.Value) ? (int?)null : Convert.ToInt32(_meseIstanza.Value)
                };
            }

            req.StatoPratica = _statoIstanza.Value;

            req.PosizioneArchivio = _posizioneArchivio.Value;

            return req;

        }
    }
}