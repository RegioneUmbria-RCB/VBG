using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari.GestioneAnagrafiche;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari
{
    public static class MittenteExtensions
    {
        public static MittenteInsProto ToMittenteInsProtoFromAnagrafe(this ProtocolloAnagrafe a, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafica, TipoGestioneAnagraficaEnum.TipoAggiornamento tipoAggiornamento, ProtocolloLogs logs)
        {
            var factory = GestioneAnagraficheFactory.Create(tipoGestioneAnagrafica, tipoAggiornamento, logs);
            factory.Gestisci(new AnagraficaService(a), srv);

            return new MittenteInsProto
            {
                descrizione = factory.Nominativo,
                modalitaTrasmissione = a.ModalitaTrasmissione
            };
        }

        public static MittenteInsProto ToMittenteInsProtoFromAmministrazione(this Amministrazioni a, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafica, TipoGestioneAnagraficaEnum.TipoAggiornamento tipoAggiornamento, ProtocolloLogs logs)
        {
            var factory = GestioneAnagraficheFactory.Create(tipoGestioneAnagrafica, tipoAggiornamento, logs);
            factory.Gestisci(new AmministrazioneService(a), srv);

            return new MittenteInsProto
            {
                descrizione = factory.Nominativo,
                modalitaTrasmissione = a.ModalitaTrasmissione
            };
        }
    }
}
