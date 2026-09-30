using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    internal class DatiPraticaAdapter : IStcPartialAdapter
    {
        public void Adapt(IDomandaOnlineReadInterface readInterface, DettaglioPraticaType dettaglioPratica)
        {
            dettaglioPratica.idPratica = readInterface.AltriDati.IdentificativoDomanda;
            dettaglioPratica.numeroPratica = readInterface.AltriDati.IdentificativoDomanda;
            dettaglioPratica.dataPratica = DateTime.Now;
            dettaglioPratica.oraDataPratica = DateTime.Now.ToString("HH':'mm");
            dettaglioPratica.oggetto = readInterface.AltriDati.DescrizioneLavori;
            dettaglioPratica.domicilioElettronico = readInterface.AltriDati.DomicilioElettronico;
            dettaglioPratica.annotazioni = readInterface.AltriDati.Note;

            if (!String.IsNullOrEmpty(readInterface.AltriDati.NaturaBase))
            {
                dettaglioPratica.naturaFo = (NaturaFoType)Enum.Parse(typeof(NaturaFoType), readInterface.AltriDati.NaturaBase);
                dettaglioPratica.naturaFoSpecified = true;
            }


            var intervento = readInterface.AltriDati.Intervento;

            dettaglioPratica.intervento = new InterventoType
            {
                codice = intervento?.Codice.ToString(),  // rigaIstanze.CODICEINTERVENTO.ToString(),
                descrizione = intervento?.Descrizione    // EstraiDescrizioneEstesaIntervento((int)rigaIstanze.CODICEINTERVENTO)
            };
        }
    }
}
