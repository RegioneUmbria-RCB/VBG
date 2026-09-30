using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari.GestioneAnagrafiche;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari
{
    public static class DestinatarioIOPExtensions
    {
        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAnagrafe(this ProtocolloAnagrafe anagrafica, ProtocolloService srv, InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            var factory = GestioneAnagraficheFactory.Create(vert, logs);
            factory.Gestisci(new AnagraficaService(anagrafica), srv);

            var retVal = new DestinatarioIOPInsProto
            {
                Descrizione = factory.Nominativo,
                InvioTelematicoTramitePec = vert.InviaPec,
                //invioTelemPecSpecified = true,
                InvioTelematicoTramiteMail = anagrafica.PecProtocollazione
            };

            if (!String.IsNullOrEmpty(anagrafica.ModalitaTrasmissione))
            {
                retVal.ModalitaTrasmissione = anagrafica.ModalitaTrasmissione;
            }

            return retVal;

        }

        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAmministrazione(this ProtocolloAmministrazioni amm, ProtocolloService srv, InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            var factory = GestioneAnagraficheFactory.Create(vert, logs);
            factory.Gestisci(new AmministrazioneService(amm), srv);

            var retVal = new DestinatarioIOPInsProto()
            {
                Descrizione = factory.Nominativo,
                InvioTelematicoTramitePec = vert.InviaPec,
                //invioTelemPecSpecified = true,
                InvioTelematicoTramiteMail = amm.PEC
            };

            if (!String.IsNullOrEmpty(amm.ModalitaTrasmissione))
            {
                retVal.ModalitaTrasmissione = amm.ModalitaTrasmissione;
            }

            return retVal;
        }
    }
}
