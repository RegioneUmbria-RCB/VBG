using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Urbi.Corrispondenti;
using Init.SIGePro.Protocollo.Urbi.TipiMezzo;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.Protocollazione
{
    public class ProtocollazionePartenza : IProtocollazione
    {
        IDatiProtocollo _datiProtocollo;
        VerticalizzazioniWrapper _vert;
        string _operatore;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        IEnumerable<IAnagraficaAmministrazione> _destinatari;


        public ProtocollazionePartenza(IDatiProtocollo datiProtocollo, VerticalizzazioniWrapper vert, string operatore, ProtocolloLogs logs, ProtocolloSerializer serializer, IEnumerable<IAnagraficaAmministrazione> destinatari)
        {
            _datiProtocollo = datiProtocollo;
            _vert = vert;
            _operatore = operatore;
            _logs = logs;
            _serializer = serializer;
            _destinatari = destinatari;
        }

        public NameValueCollection GetParameters()
        {
            var parametri = new NameValueCollection();
            parametri.Add("PRCORE03_99991009_IDAOO", _vert.Aoo);
            parametri.Add("PRCORE03_99991009_Sezione", _datiProtocollo.Flusso);
            parametri.Add("PRCORE03_99991009_Oggetto", _datiProtocollo.ProtoIn.Oggetto);
            parametri.Add("PRCORE03_99991009_Utente_Registratore", _operatore);

            if (!String.IsNullOrEmpty(_datiProtocollo.ProtoIn.TipoDocumento))
                parametri.Add("PRCORE03_99991009_TipoDocumento", _datiProtocollo.ProtoIn.TipoDocumento);

            parametri.Add("PRCORE03_99991009_Num_Uffici_Mittenti", "1");
            parametri.Add("PRCORE03_99991009_1_Ufficio_Mittente", _datiProtocollo.Uo);
            parametri.Add("PRCORE03_99991009_Num_Corrispondenti", _destinatari.Count().ToString());

            var wrapperCorrispondenti = new CorrispondentiServiceWrapper(_logs, _serializer, _vert.Username, _vert.Password, _vert.Url);
            int idx = 0;
            int numPecPresenti = 0;
            _destinatari.ToList().ForEach(x =>
            {
                idx++;
                var factoryCorrispondente = CorrispondenteFactory.Create(x, wrapperCorrispondenti);

                var codiceCorr = factoryCorrispondente.SearchCorrispondente();
                if (codiceCorr == 0)
                    codiceCorr = factoryCorrispondente.InsertCorrispondente();

                parametri.Add(String.Format("PRCORE03_99991009_{0}_Corrispondente_CodiceSoggetto", idx), codiceCorr.ToString());

                if (!String.IsNullOrEmpty(x.Pec))
                {
                    parametri.Add(String.Format("PRCORE03_99991009_{0}_Corrispondente_Indirizzo_Email_PEC", idx), x.Pec);
                    numPecPresenti++;
                }

                if (x.ModalitaTrasmissione == "S")
                    parametri.Add(String.Format("PRCORE03_99991009_{0}_Corrispondente_PerConoscenza", idx), "S");
            });

            if (_datiProtocollo.ProtoIn.Allegati.Count() == 0)
            {
                parametri.Add("PRCORE03_99991009_Num_Allegati", "0");
                parametri.Add("PRCORE03_99991009_0_Allegato_Classificazione_1", _datiProtocollo.ProtoIn.Classifica);
            }
            else
            {
                parametri.Add("PRCORE03_99991009_Num_Allegati", (_datiProtocollo.ProtoIn.Allegati.Count() - 1).ToString());
                for (var i = 0; i < _datiProtocollo.ProtoIn.Allegati.Count(); i++)
                    parametri.Add(String.Format("PRCORE03_99991009_{0}_Allegato_Classificazione_1", i.ToString()), _datiProtocollo.ProtoIn.Classifica);

            }

            if (!String.IsNullOrEmpty(_datiProtocollo.ProtoIn.TipoSmistamento))
            {
                var mezziSrv = new TipiMezzoServiceWrapper(_logs, _serializer, _vert.Username, _vert.Password, _vert.Url);
                var responseMezzi = mezziSrv.GetTipiMezzo();

                var mezzo = responseMezzi.getElencoTipiMezzo_Result.SEQ_Mezzo.Where(x => x.Codice == _datiProtocollo.ProtoIn.TipoSmistamento).ToList();
                if (mezzo.Count() == 0)
                    _logs.WarnFormat("NON SONO STATE TROVATE TIPOLOGIE PER IL MEZZO CODICE {0}, NON SARA' POSSIBILE INVIARE LA PEC", _datiProtocollo.ProtoIn.TipoSmistamento);
                else
                {
                    _logs.InfoFormat("MEZZO TROVATO CODICE: {0}, DESCRIZIONE: {1}, VALIDO PEC: {2}", mezzo[0].Codice, mezzo[0].TipoMezzo, mezzo[0].ValidoPEC);

                    if (!_vert.InvioPec)
                    {
                        if ((mezzo[0].ValidoPEC ?? "") != "S")
                            parametri.Add("PRCORE03_99991009_TipoMezzo", mezzo[0].TipoMezzo);
                    }
                    else
                    {
                        if (mezzo[0].ValidoPEC == "S" && numPecPresenti > 0)
                        {
                            parametri.Add("PRCORE03_99991009_TipoMezzo", mezzo[0].TipoMezzo);
                            parametri.Add("PRCORE03_99991009_Invio_Immediato_PEC", "S");
                            parametri.Add("PRCORE03_99991009_Oggetto_PEC", _datiProtocollo.ProtoIn.Oggetto);
                            parametri.Add("PRCORE03_99991009_Testo_PEC", _datiProtocollo.ProtoIn.Corpo);
                            parametri.Add("PRCORE03_99991009_TipoInvio_PEC", "1");
                        }
                    }
                }
            }

            return parametri;
        }
    }
}
