using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione.MittentiDestinatari
{
    public class ProtocollazionePartenza : IProtocollazioneInsiel3
    {
        IDatiProtocollo _datiProto;
        ProtocolloService _srv;
        InsielVerticalizzazioniConfiguration _vertInsiel;

        //TipoGestioneAnagraficaEnum.TipoGestione _tipoGestioneAnagrafica;
        //TipoGestioneAnagraficaEnum.TipoAggiornamento _tipoAggiornamento;
        ProtocolloLogs _logs;
        //bool _inviaPec;
        //string _iteratti;

        public ProtocollazionePartenza(IDatiProtocollo datiProto, ProtocolloService srv, InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            this._datiProto = datiProto;
            this._srv = srv;
            //this._tipoGestioneAnagrafica = vert.TipoGestionePec;
            //this._tipoAggiornamento = vert.TipoAggiornamentoAnagrafica;
            this._logs = logs;
            //this._iteratti = vert.Iteratti;
            //this._inviaPec = vert.InviaPec;
            this._vertInsiel = vert;
        }

        public MittenteInsProto[] GetMittenti()
        {
            return null;
        }

        public DestinatarioIOPInsProto[] GetDestinatari()
        {
            //var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.GetDestinatarioIOPFromAnagrafe(_srv, _tipoGestioneAnagrafica, this._tipoAggiornamento, this._inviaPec, _logs));
            //var amministrazione = _datiProto.AmministrazioniEsterne.Select(x => x.GetDestinatarioIOPFromAmministrazione(_srv, _tipoGestioneAnagrafica, this._tipoAggiornamento, this._inviaPec, _logs));

            var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.GetDestinatarioIOPFromAnagrafe(_srv, this._vertInsiel, _logs));
            var amministrazione = _datiProto.AmministrazioniEsterne.Select(x => x.GetDestinatarioIOPFromAmministrazione(_srv, this._vertInsiel, _logs));


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

        public string MittentePec => this._vertInsiel.MittentePec;

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
