using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari
{
    public class ProtocollazioneArrivo : IProtocollazioneInsiel4
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
            var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.ToMittenteInsProtoFromAnagrafe(_srv, this._vertInsiel, _logs));
            var amministrazioni = _datiProto.AmministrazioniEsterne.Select(x => x.ToMittenteInsProtoFromAmministrazione(_srv, this._vertInsiel, _logs));

            var retVal = anagrafiche.Union(amministrazioni);

            return retVal.ToArray();
        }

        public DestinatarioIOPInsProto[] GetDestinatari()
        {
            return null;
        }

        public Verso Flusso
        {
            get { return Verso.arrivo; }
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
                Codice = _datiProto.Uo,
                GiaInviato = true
            };

            if (!string.IsNullOrEmpty(this._vertInsiel.Iteratti))
            {
                ufficio.Tipo = this._vertInsiel.Iteratti;
            }

            return new UfficioInsProto[]
            {
                ufficio
            };
        }
    }
}
