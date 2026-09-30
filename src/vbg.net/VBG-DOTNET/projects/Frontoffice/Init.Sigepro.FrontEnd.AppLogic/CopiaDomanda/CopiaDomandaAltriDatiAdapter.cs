using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.CopiaDomanda
{
    public class CopiaDomandaAltriDatiAdapter : ICopiaDomandaDatiAdapter
    {
        public IResolveDescrizioneIntervento _resolveDescrizioneIntervento { get; set; }

        public CopiaDomandaAltriDatiAdapter(IResolveDescrizioneIntervento resolveDescrizioneIntervento)
        {
            this._resolveDescrizioneIntervento = resolveDescrizioneIntervento;
        }


        public void Adatta(Istanze istanzaTemplate, DomandaOnline domanda)
        {
            // CodiceComune
            domanda.WriteInterface.AltriDati.ImpostaCodiceComune(istanzaTemplate.CODICECOMUNE, istanzaTemplate.ComuneIstanza?.CODICEISTAT ?? "");
            domanda.WriteInterface.AltriDati.ImpostaDomicilioElettronico(istanzaTemplate.DOMICILIO_ELETTRONICO);

            var idIntervento = Convert.ToInt32(istanzaTemplate.CODICEINTERVENTOPROC);
            domanda.WriteInterface.AltriDati.ImpostaIntervento(idIntervento, null, this._resolveDescrizioneIntervento);
        }
    }
}
