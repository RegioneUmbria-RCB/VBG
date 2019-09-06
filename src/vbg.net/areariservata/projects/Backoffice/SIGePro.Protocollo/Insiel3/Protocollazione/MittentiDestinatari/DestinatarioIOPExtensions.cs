using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari.GestioneAnagrafiche;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari
{
    public static class DestinatarioIOPExtensions
    {
        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAnagrafe(this ProtocolloAnagrafe anagrafica, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafica, TipoGestioneAnagraficaEnum.TipoAggiornamento tipoAggiornamento, bool inviaPec, ProtocolloLogs logs)
        {
            var factory = GestioneAnagraficheFactory.Create(tipoGestioneAnagrafica, tipoAggiornamento, logs);
            factory.Gestisci(new AnagraficaService(anagrafica), srv);

            var retVal = new DestinatarioIOPInsProto
            {
                descrizione = factory.Nominativo,
                invioTelemPec = inviaPec,
                invioTelemPecSpecified = true,
                invioTelemMail = anagrafica.Pec
            };

            if (!String.IsNullOrEmpty(anagrafica.ModalitaTrasmissione))
            {
                retVal.modalitaTrasmissione = anagrafica.ModalitaTrasmissione;
            }

            return retVal;

        }

        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAmministrazione(this Amministrazioni amm, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafica, TipoGestioneAnagraficaEnum.TipoAggiornamento tipoAggiornamento, bool inviaPec, ProtocolloLogs logs)
        {
            var factory = GestioneAnagraficheFactory.Create(tipoGestioneAnagrafica, tipoAggiornamento, logs);
            factory.Gestisci(new AmministrazioneService(amm), srv);

            var retVal = new DestinatarioIOPInsProto()
            {
                descrizione = factory.Nominativo,
                invioTelemPec = inviaPec,
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
