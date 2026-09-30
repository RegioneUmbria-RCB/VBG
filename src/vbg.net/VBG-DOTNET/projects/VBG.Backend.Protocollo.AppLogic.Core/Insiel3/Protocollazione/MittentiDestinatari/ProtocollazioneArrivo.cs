using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione.MittentiDestinatari;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione.MittentiDestinatari
{
    public class ProtocollazioneArrivo : IProtocollazioneInsiel3
    {
        IDatiProtocollo _datiProto;
        ProtocolloService _srv;
        //TipoGestioneAnagraficaEnum.TipoGestione _tipoGestioneAnagrafe;
        //TipoGestioneAnagraficaEnum.TipoAggiornamento _tipoAggiornamento;
        ProtocolloLogs _logs;
        //string _iteratti;
        InsielVerticalizzazioniConfiguration _vertInsiel;

        public ProtocollazioneArrivo(IDatiProtocollo datiProto, ProtocolloService srv, InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            this._datiProto = datiProto;
            //this._tipoGestioneAnagrafe = tipoGestioneAnagrafe;
            //this._tipoAggiornamento = tipoAggiornamento;
            this._srv = srv;
            this._logs = logs;
            //this._iteratti = iteratti;
            this._vertInsiel = vert;
        }

        public MittenteInsProto[] GetMittenti()
        {
            //var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.ToMittenteInsProtoFromAnagrafe(_srv, _tipoGestioneAnagrafe, this._tipoAggiornamento, _logs));
            //var amministrazioni = _datiProto.AmministrazioniEsterne.Select(x => x.ToMittenteInsProtoFromAmministrazione(_srv, _tipoGestioneAnagrafe, this._tipoAggiornamento, _logs));

            var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.ToMittenteInsProtoFromAnagrafe(_srv, this._vertInsiel, _logs));
            var amministrazioni = _datiProto.AmministrazioniEsterne.Select(x => x.ToMittenteInsProtoFromAmministrazione(_srv, this._vertInsiel, _logs));

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

        public string MittentePec => "";

        public UfficioInsProto[] GetUffici()
        {
            var ufficio = new UfficioInsProto
            {
                codice = _datiProto.Uo,
                giaInviato = true,
                giaInviatoSpecified = true
            };

            if (!String.IsNullOrEmpty(this._vertInsiel.Iteratti))
            {
                ufficio.tipo = this._vertInsiel.Iteratti;
            }

            return new UfficioInsProto[] 
            {
                ufficio
            };
        }
    }
}
