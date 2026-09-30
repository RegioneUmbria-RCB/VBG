using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Corrispondenti;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.TipiMezzo;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Protocollazione
{
    public class ProtocollazioneArrivo : IProtocollazioneUrbi
    {
        readonly IDatiProtocollo _datiProtocollo;
        readonly VerticalizzazioniWrapper _vert;
        readonly string _operatore;
        readonly ProtocolloLogs _logs;
        readonly ProtocolloSerializer _serializer;
        readonly IEnumerable<IAnagraficaAmministrazione> _mittenti;

        public ProtocollazioneArrivo(IDatiProtocollo datiProtocollo, VerticalizzazioniWrapper vert, string operatore, ProtocolloLogs logs, ProtocolloSerializer serializer, IEnumerable<IAnagraficaAmministrazione> mittenti)
        {
            _datiProtocollo = datiProtocollo;
            _vert = vert;
            _operatore = operatore;
            _logs = logs;
            _serializer = serializer;
            _mittenti = mittenti;
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
            {
                parametri.Add("PRCORE03_99991009_TipoDocumento", _datiProtocollo.ProtoIn.TipoDocumento);
            }

            parametri.Add("PRCORE03_99991009_Num_Uffici_Destinatari", "1");
            parametri.Add("PRCORE03_99991009_1_Ufficio_Destinatario", _datiProtocollo.Uo);
            parametri.Add("PRCORE03_99991009_1_Ufficio_Destinatario_Utenti_CO_Automatici", _vert.DestinatariUtentiCOAutomatici);
            parametri.Add("PRCORE03_99991009_Num_Corrispondenti", _mittenti.Count().ToString());

            if (!String.IsNullOrEmpty(_datiProtocollo.Ruolo))
            {
                parametri.Add("PRCORE03_99991009_1_Ufficio_Destinatario_Originale", _datiProtocollo.Ruolo);
            }

            var wrapperCorrispondenti = new CorrispondentiServiceWrapper(_logs, _serializer, _vert.Username, _vert.Password, _vert.Url);
            int idx = 0;
            _mittenti.ToList().ForEach(x =>
            {
                idx++;
                var factoryCorrispondente = CorrispondenteFactory.Create(x, wrapperCorrispondenti);
                var codiceCorr = 0;
                var corrispondente = factoryCorrispondente.SearchCorrispondente();
                if (corrispondente.getElencoCorrispondenti_Result != null && corrispondente.getElencoCorrispondenti_Result.NumCorrispondenti != "0")
                    codiceCorr = Convert.ToInt32(corrispondente.getElencoCorrispondenti_Result.SEQ_Corrispondente[0].CodiceSoggetto);

                if (codiceCorr == 0)
                {
                    codiceCorr = Convert.ToInt32(factoryCorrispondente.InsertCorrispondente().insCorrispondente_Result.CodiceSoggetto);
                }

                parametri.Add(String.Format("PRCORE03_99991009_{0}_Corrispondente_CodiceSoggetto", idx), codiceCorr.ToString());
            });

            if (!_datiProtocollo.ProtoIn.HaAllegati())
            {
                parametri.Add("PRCORE03_99991009_Num_Allegati", "0");
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
