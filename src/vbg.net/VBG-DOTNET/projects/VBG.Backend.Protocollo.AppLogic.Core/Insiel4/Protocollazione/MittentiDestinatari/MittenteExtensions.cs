using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari.GestioneAnagrafiche;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari
{
    public static class MittenteExtensions
    {
        public static MittenteInsProto ToMittenteInsProtoFromAnagrafe(this ProtocolloAnagrafe a, ProtocolloService srv, InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            var factory = GestioneAnagraficheFactory.Create(vert, logs);
            factory.Gestisci(new AnagraficaService(a), srv);

            return new MittenteInsProto
            {
                Descrizione = factory.Nominativo,
                ModalitaTrasmissione = a.ModalitaTrasmissione
            };
        }

        public static MittenteInsProto ToMittenteInsProtoFromAmministrazione(this ProtocolloAmministrazioni a, ProtocolloService srv, InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            var factory = GestioneAnagraficheFactory.Create(vert, logs);
            factory.Gestisci(new AmministrazioneService(a), srv);

            return new MittenteInsProto
            {
                Descrizione = factory.Nominativo,
                ModalitaTrasmissione = a.ModalitaTrasmissione
            };
        }
    }
}
