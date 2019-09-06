using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari
{
    public static class DestinatarioIOPExtensions
    {
        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAnagrafe(this ProtocolloAnagrafe anagrafica, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafica)
        {
            var mgr = new AnagraficheManager(new AnagraficaService(anagrafica), tipoGestioneAnagrafica);
            mgr.Gestisci(srv);

            return new DestinatarioIOPInsProto
            {
                descrizione = mgr.Nominativo,
                invioTelemPec = tipoGestioneAnagrafica != TipoGestioneAnagraficaEnum.TipoGestione.NO_PEC,
                invioTelemPecSpecified = true
            };
        }

        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAmministrazione(this Amministrazioni amm, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafica)
        {
            var mgr = new AnagraficheManager(new AmministrazioneService(amm), tipoGestioneAnagrafica);
            mgr.Gestisci(srv);

            return new DestinatarioIOPInsProto()
            {
                descrizione = mgr.Nominativo,
                invioTelemPec = tipoGestioneAnagrafica != TipoGestioneAnagraficaEnum.TipoGestione.NO_PEC,
                invioTelemPecSpecified = true
            };
        }
    }
}
