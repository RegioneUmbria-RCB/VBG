using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari;
using Init.SIGePro.Protocollo.Insiel3.Services;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari
{
    public class ProtocollazioneArrivo : IProtocollazione
    {
        IDatiProtocollo _datiProto;
        ProtocolloService _srv;
        TipoGestioneAnagraficaEnum.TipoGestione _tipoGestioneAnagrafe;

        public ProtocollazioneArrivo(IDatiProtocollo datiProto, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafe)
        {
            _datiProto = datiProto;
            _tipoGestioneAnagrafe = tipoGestioneAnagrafe;
            _srv = srv;
        }

        public MittenteInsProto[] GetMittenti()
        {
            var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.ToMittenteInsProtoFromAnagrafe(_srv, _tipoGestioneAnagrafe));
            var amministrazioni = _datiProto.AmministrazioniEsterne.Select(x => x.ToMittenteInsProtoFromAmministrazione(_srv, _tipoGestioneAnagrafe));

            var retVal = anagrafiche.Union(amministrazioni);

            return retVal.ToArray();
        }

        public DestinatarioIOPInsProto[] GetDestinatari()
        {
            return null;
        }

        public verso Flusso
        {
            get { return verso.A; }
        }



        public bool InvioTelematicoAttivo
        {
            get { return false; }
        }


        public UfficioInsProto[] GetUffici()
        {
            return new UfficioInsProto[] { new UfficioInsProto { codice = _datiProto.Uo, giaInviato = true, giaInviatoSpecified = true } };
        }
    }
}
