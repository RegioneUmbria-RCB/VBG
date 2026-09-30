using VBG.Backend.Protocollo.AppLogic.Core.Urbi.TipiMezzo;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Protocollazione
{
    public class ProtocollazioneInterna : IProtocollazioneUrbi
    {
        readonly IDatiProtocollo _datiProtocollo;
        readonly VerticalizzazioniWrapper _vert;
        readonly string _operatore;
        readonly ProtocolloLogs _logs;
        readonly ProtocolloSerializer _serializer;

        public ProtocollazioneInterna(IDatiProtocollo datiProtocollo, VerticalizzazioniWrapper vert, string operatore, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _datiProtocollo = datiProtocollo;
            _vert = vert;
            _operatore = operatore;
            _logs = logs;
            _serializer = serializer;
        }

        public NameValueCollection GetParameters()
        {
            var parametri = new NameValueCollection
            {
                { "PRCORE03_99991009_IDAOO", _vert.Aoo },
                { "PRCORE03_99991009_Sezione", _datiProtocollo.Flusso },
                { "PRCORE03_99991009_Oggetto", _datiProtocollo.ProtoIn.Oggetto },
                { "PRCORE03_99991009_Utente_Registratore", _operatore },
                { "PRCORE03_99991009_No_Avvio_Iter", _vert.NoAvvioIter }
            };

            if (!String.IsNullOrEmpty(_datiProtocollo.ProtoIn.TipoDocumento))
                parametri.Add("PRCORE03_99991009_TipoDocumento", _datiProtocollo.ProtoIn.TipoDocumento);

            parametri.Add("PRCORE03_99991009_Num_Uffici_Mittenti", "1");
            parametri.Add("PRCORE03_99991009_1_Ufficio_Mittente", _datiProtocollo.Uo);

            parametri.Add("PRCORE03_99991009_Num_Uffici_Destinatari", "1");
            parametri.Add("PRCORE03_99991009_1_Ufficio_Destinatario", _datiProtocollo.AmministrazioniInterne[0].PROT_UO);

            if (!_datiProtocollo.ProtoIn.HaAllegati())
            {
                parametri.Add("PRCORE03_99991009_Num_Allegati", "0");
                parametri.Add("PRCORE03_99991009_0_Allegato_Classificazione_1", _datiProtocollo.ProtoIn.Classifica);
            }
            else
            {
                parametri.Add("PRCORE03_99991009_Num_Allegati", (_datiProtocollo.ProtoIn.NumeroAllegatiPresenti - 1).ToString());
                for (var i = 0; i < _datiProtocollo.ProtoIn.NumeroAllegatiPresenti; i++)
                    parametri.Add(String.Format("PRCORE03_99991009_{0}_Allegato_Classificazione_1", i.ToString()), _datiProtocollo.ProtoIn.Classifica);
            }

            if (!String.IsNullOrEmpty(_datiProtocollo.ProtoIn.TipoSmistamento))
            {
                var mezziSrv = new TipiMezzoServiceWrapper(_logs, _serializer, _vert.Username, _vert.Password, _vert.Url);
                var responseMezzi = mezziSrv.GetTipiMezzo();

                var mezzo = responseMezzi.getElencoTipiMezzo_Result.SEQ_Mezzo.Where(x => x.Codice == _datiProtocollo.ProtoIn.TipoSmistamento).ToList();
                if (!mezzo.Any())
                {
                    _logs.WarnFormat("NON SONO STATE TROVATE TIPOLOGIE PER IL MEZZO CODICE {0}", _datiProtocollo.ProtoIn.TipoSmistamento);
                }
                else
                {
                    _logs.InfoFormat("MEZZO TROVATO CODICE: {0}, DESCRIZIONE: {1}", mezzo[0].Codice, mezzo[0].TipoMezzo);
                    parametri.Add("PRCORE03_99991009_TipoMezzo", mezzo[0].TipoMezzo);
                }
            }

            return parametri;
        }
    }
}
