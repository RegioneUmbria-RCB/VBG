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
        TipoGestioneAnagraficaEnum.TipoAggiornamento _tipoAggiornamento;
        ProtocolloLogs _logs;
        string _iteratti;

        public ProtocollazioneArrivo(IDatiProtocollo datiProto, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafe, TipoGestioneAnagraficaEnum.TipoAggiornamento tipoAggiornamento, ProtocolloLogs logs, string iteratti)
        {
            this._datiProto = datiProto;
            this._tipoGestioneAnagrafe = tipoGestioneAnagrafe;
            this._tipoAggiornamento = tipoAggiornamento;
            this._srv = srv;
            this._logs = logs;
            this._iteratti = iteratti;
        }

        public MittenteInsProto[] GetMittenti()
        {
            var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.ToMittenteInsProtoFromAnagrafe(_srv, _tipoGestioneAnagrafe, this._tipoAggiornamento, _logs));
            var amministrazioni = _datiProto.AmministrazioniEsterne.Select(x => x.ToMittenteInsProtoFromAmministrazione(_srv, _tipoGestioneAnagrafe, this._tipoAggiornamento, _logs));

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
            var ufficio = new UfficioInsProto
            {
                codice = _datiProto.Uo,
                giaInviato = true,
                giaInviatoSpecified = true
            };

            if (!String.IsNullOrEmpty(this._iteratti))
            {
                ufficio.tipo = this._iteratti;
            }

            return new UfficioInsProto[] 
            {
                ufficio
            };
        }
    }
}
