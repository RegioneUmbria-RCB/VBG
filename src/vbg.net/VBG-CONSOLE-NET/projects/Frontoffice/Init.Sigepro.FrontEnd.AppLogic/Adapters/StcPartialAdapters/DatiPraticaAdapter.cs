using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    internal class DatiPraticaAdapter : IStcPartialAdapter
    {
        public void Adapt(IDomandaOnlineReadInterface _readInterface, DettaglioPraticaType _dettaglioPratica)
        {
            _dettaglioPratica.idPratica = _readInterface.AltriDati.IdentificativoDomanda;
            _dettaglioPratica.numeroPratica = _readInterface.AltriDati.IdentificativoDomanda;
            _dettaglioPratica.dataPratica = DateTime.Now;
            _dettaglioPratica.oraDataPratica = DateTime.Now.ToString("HH':'mm");
            _dettaglioPratica.oggetto = _readInterface.AltriDati.DescrizioneLavori;
            _dettaglioPratica.domicilioElettronico = _readInterface.AltriDati.DomicilioElettronico;
            _dettaglioPratica.annotazioni = _readInterface.AltriDati.Note;

            if (!String.IsNullOrEmpty(_readInterface.AltriDati.NaturaBase))
            {
                _dettaglioPratica.naturaFo = (NaturaFoType)Enum.Parse(typeof(NaturaFoType), _readInterface.AltriDati.NaturaBase);
                _dettaglioPratica.naturaFoSpecified = true;
            }


            var intervento = _readInterface.AltriDati.Intervento;

            _dettaglioPratica.intervento = new InterventoType
            {
                codice = intervento.Codice.ToString(),  // rigaIstanze.CODICEINTERVENTO.ToString(),
                descrizione = intervento.Descrizione    // EstraiDescrizioneEstesaIntervento((int)rigaIstanze.CODICEINTERVENTO)
            };
        }
    }
}
