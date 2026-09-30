using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione.MittentiDestinatari.GestioneAnagrafiche;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione.MittentiDestinatari
{
    public static class DestinatarioIOPExtensions
    {
        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAnagrafe(this ProtocolloAnagrafe anagrafica, ProtocolloService srv, InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            var factory = GestioneAnagraficheFactory.Create(vert, logs);
            factory.Gestisci(new AnagraficaService(anagrafica), srv);

            var retVal = new DestinatarioIOPInsProto
            {
                descrizione = factory.Nominativo,
                invioTelemPec = vert.InviaPec,
                invioTelemPecSpecified = true,
                invioTelemMail = anagrafica.PecProtocollazione
            };

            if (!String.IsNullOrEmpty(anagrafica.ModalitaTrasmissione))
            {
                retVal.modalitaTrasmissione = anagrafica.ModalitaTrasmissione;
            }

            return retVal;

        }

        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAmministrazione(this ProtocolloAmministrazioni amm, ProtocolloService srv, InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            var factory = GestioneAnagraficheFactory.Create(vert, logs);
            factory.Gestisci(new AmministrazioneService(amm), srv);

            var retVal = new DestinatarioIOPInsProto()
            {
                descrizione = factory.Nominativo,
                invioTelemPec = vert.InviaPec,
                invioTelemPecSpecified = true,
                invioTelemMail = amm.PEC
            };

            if (!String.IsNullOrEmpty(amm.ModalitaTrasmissione))
            {
                retVal.modalitaTrasmissione = amm.ModalitaTrasmissione;
            }

            return retVal;
        }
    }
}
