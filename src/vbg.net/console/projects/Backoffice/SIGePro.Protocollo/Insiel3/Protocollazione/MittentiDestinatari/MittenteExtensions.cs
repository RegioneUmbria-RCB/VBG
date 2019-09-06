using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari
{
    public static class MittenteExtensions
    {
        public static MittenteInsProto ToMittenteInsProtoFromAnagrafe(this ProtocolloAnagrafe a, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafica)
        {
            var mgr = new AnagraficheManager(new AnagraficaService(a), tipoGestioneAnagrafica);
            mgr.Gestisci(srv);

            return new MittenteInsProto
            {
                descrizione = mgr.Nominativo.Replace("  ", " "),
                modalitaTrasmissione = a.ModalitaTrasmissione
            };
        }

        public static MittenteInsProto ToMittenteInsProtoFromAmministrazione(this Amministrazioni a, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafica)
        {
            var mgr = new AnagraficheManager(new AmministrazioneService(a), tipoGestioneAnagrafica);
            mgr.Gestisci(srv);

            return new MittenteInsProto
            {
                descrizione = mgr.Nominativo.Replace("  ", " "),
                modalitaTrasmissione = a.ModalitaTrasmissione
            };
        }
    }
}
