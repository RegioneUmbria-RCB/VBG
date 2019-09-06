using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Insiel3.Services;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari
{
    public class ProtocollazionePartenza : IProtocollazione
    {
        IDatiProtocollo _datiProto;
        ProtocolloService _srv;
        TipoGestioneAnagraficaEnum.TipoGestione _tipoGestioneAnagrafica;

        public ProtocollazionePartenza(IDatiProtocollo datiProto, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafica)
        {
            _datiProto = datiProto;
            _srv = srv;
            _tipoGestioneAnagrafica = tipoGestioneAnagrafica;
        }

        public MittenteInsProto[] GetMittenti()
        {
            return null;
        }

        public DestinatarioIOPInsProto[] GetDestinatari()
        {
            var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.GetDestinatarioIOPFromAnagrafe(_srv, _tipoGestioneAnagrafica));
            var amministrazione = _datiProto.AmministrazioniEsterne.Select(x => x.GetDestinatarioIOPFromAmministrazione(_srv, _tipoGestioneAnagrafica));

            var retVal = anagrafiche.Union(amministrazione);

            return retVal.ToArray();
        }

        public verso Flusso
        {
            get { return verso.P; }
        }


        public bool InvioTelematicoAttivo
        {
            get { return true; }
        }


        public UfficioInsProto[] GetUffici()
        {
            /*var retVal = _datiProto.AmministrazioniInterne.Select(x => new UfficioInsProto { codice = x.PROT_UO, giaInviato = true, giaInviatoSpecified = true });
            if (retVal.Count() == 0)
                return null;*/

            return new UfficioInsProto[] { new UfficioInsProto { codice = _datiProto.Uo, giaInviato = true, giaInviatoSpecified = true } };
        }
    }
}
