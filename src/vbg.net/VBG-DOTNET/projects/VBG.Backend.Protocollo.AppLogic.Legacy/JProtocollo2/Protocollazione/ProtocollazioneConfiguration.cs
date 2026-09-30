using vbg.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Protocollazione
{
    public class ProtocollazioneConfiguration
    {
        public IDatiProtocollo DatiProto { get; private set; }
        public ProtocolloService Service { get; private set; }
        public VerticalizzazioniConfiguration Vert { get; private set; }
        public string Operatore { get; private set; }

        public ProtocollazioneConfiguration(IDatiProtocollo datiProto, ProtocolloService wrapper, VerticalizzazioniConfiguration vert, string operatore)
        {
            DatiProto = datiProto;
            Service = wrapper;
            Vert = vert;
            Operatore = operatore;
        }
    }
}
