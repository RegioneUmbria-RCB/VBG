using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari
{
    public class ProtocollazionePartenza : IProtocollazioneInsiel4
    {
        private readonly IDatiProtocollo _datiProto;
        private readonly ProtocolloService _srv;
        private readonly InsielVerticalizzazioniConfiguration _vertInsiel;
        private readonly ProtocolloLogs _logs;

        public ProtocollazionePartenza(IDatiProtocollo datiProto, ProtocolloService srv, InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            this._datiProto = datiProto;
            this._srv = srv;
            this._logs = logs;
            this._vertInsiel = vert;
        }

        public MittenteInsProto[] GetMittenti()
        {
            return null;
        }

        public DestinatarioIOPInsProto[] GetDestinatari()
        {
            var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.GetDestinatarioIOPFromAnagrafe(this._srv, this._vertInsiel, this._logs));
            var amministrazione = _datiProto.AmministrazioniEsterne.Select(x => x.GetDestinatarioIOPFromAmministrazione(this._srv, this._vertInsiel, this._logs));


            var retVal = anagrafiche.Union(amministrazione);

            return retVal.ToArray();
        }

        public Verso Flusso
        {
            get { return Verso.partenza; }
        }


        public bool InvioTelematicoAttivo
        {
            get { return true; }
        }

        public string MittentePec => this._vertInsiel.MittentePec;

        public UfficioInsProto[] GetUffici()
        {
            var ufficio = new UfficioInsProto
            {
                Codice = _datiProto.Uo,
                GiaInviato = true,
                //giaInviatoSpecified = true
            };

            if (!String.IsNullOrEmpty(this._vertInsiel.Iteratti))
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
