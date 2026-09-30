using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Corrispondenti;
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
    public class ProtocollazionePartenza : IProtocollazioneUrbi
    {
        readonly IDatiProtocollo _datiProtocollo;
        readonly VerticalizzazioniWrapper _vert;
        readonly string _operatore;
        readonly ProtocolloLogs _logs;
        readonly ProtocolloSerializer _serializer;
        readonly IEnumerable<IAnagraficaAmministrazione> _destinatari;

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
            int Num_Uffici_Dest = 0;

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
            parametri.Add("PRCORE03_99991009_Num_Corrispondenti", _destinatari.Count().ToString());

            if (_vert.UsaUfficioDestinatarioPartenza)
            {
                _logs.Info("INSERIMENTO DELL'UFFICIO DESTINATARIO");
                Num_Uffici_Dest++;
                parametri.Add("PRCORE03_99991009_"+ Num_Uffici_Dest.ToString()+"_Ufficio_Destinatario", _datiProtocollo.Uo);
                parametri.Add("PRCORE03_99991009_"+ Num_Uffici_Dest.ToString()+"_Ufficio_Destinatario_Utenti_CO_Automatici", _vert.DestinatariUtentiCOAutomatici);
            }

            var wrapperCorrispondenti = new CorrispondentiServiceWrapper(_logs, _serializer, _vert.Username, _vert.Password, _vert.Url);
            int idx = 0;
            int numPecPresenti = 0;
            _destinatari.ToList().ForEach(x =>
            {

                if (x.Uo != null && x.Uo.Length > 0)
                {
                    Num_Uffici_Dest++;
                    parametri.Add("PRCORE03_99991009_" + Num_Uffici_Dest.ToString() + "_Ufficio_Destinatario", x.Uo);
                    parametri.Add("PRCORE03_99991009_" + Num_Uffici_Dest.ToString() + "_Ufficio_Destinatario_Utenti_CO_Automatici", _vert.DestinatariUtentiCOAutomatici);
                }

                idx++;
                var factoryCorrispondente = CorrispondenteFactory.Create(x, wrapperCorrispondenti);

                var codiceCorr = 0;
                var pecCorr = String.Empty;

                var corrispondente = factoryCorrispondente.SearchCorrispondente();
                if (corrispondente.getElencoCorrispondenti_Result != null && corrispondente.getElencoCorrispondenti_Result.NumCorrispondenti != "0")
                {
                    codiceCorr = Convert.ToInt32(corrispondente.getElencoCorrispondenti_Result.SEQ_Corrispondente[0].CodiceSoggetto);
                    pecCorr = corrispondente.getElencoCorrispondenti_Result.SEQ_Corrispondente[0].IndirizzoPEC;
                }

                if (codiceCorr == 0)
                {
                    codiceCorr = Convert.ToInt32(factoryCorrispondente.InsertCorrispondente().insCorrispondente_Result.CodiceSoggetto);
                    pecCorr = !String.IsNullOrEmpty(corrispondente.getElencoCorrispondenti_Result.SEQ_Corrispondente[0].IndirizzoPEC) ? corrispondente.getElencoCorrispondenti_Result.SEQ_Corrispondente[0].IndirizzoPEC : "";
                }

                parametri.Add(String.Format("PRCORE03_99991009_{0}_Corrispondente_CodiceSoggetto", idx), codiceCorr.ToString());

                //la PEC presa dal backoffice si presuppone sia più aggiornata di quella presente nel protocollo
                if (!String.IsNullOrEmpty(x.Pec))
                    pecCorr = x.Pec;

                if (!String.IsNullOrEmpty(pecCorr))
                {
                    parametri.Add(String.Format("PRCORE03_99991009_{0}_Corrispondente_Indirizzo_Email_PEC", idx), pecCorr);
                    numPecPresenti++;
                }

                if (x.ModalitaTrasmissione == "S")
                    parametri.Add(String.Format("PRCORE03_99991009_{0}_Corrispondente_PerConoscenza", idx), "S");
            });


            parametri.Add("PRCORE03_99991009_Num_Uffici_Destinatari", Num_Uffici_Dest.ToString());

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
                    _logs.WarnFormat("NON SONO STATE TROVATE TIPOLOGIE PER IL MEZZO CODICE {0}, NON SARA' POSSIBILE INVIARE LA PEC", _datiProtocollo.ProtoIn.TipoSmistamento);
                }
                else
                {
                    _logs.InfoFormat("MEZZO TROVATO CODICE: {0}, DESCRIZIONE: {1}, VALIDO PEC: {2}", mezzo[0].Codice, mezzo[0].TipoMezzo, mezzo[0].ValidoPEC);

                    if (!_vert.InvioPec)
                    {
                        if (mezzo[0].ValidoPEC != "S" && mezzo[0].ValidoECART != "S")
                            parametri.Add("PRCORE03_99991009_TipoMezzo", mezzo[0].TipoMezzo);
                    }
                    else
                    {
                        if ((mezzo[0].ValidoPEC == "S" || mezzo[0].ValidoECART == "S") && numPecPresenti > 0)
                        {
                            parametri.Add("PRCORE03_99991009_TipoMezzo", mezzo[0].TipoMezzo);
                            parametri.Add("PRCORE03_99991009_Invio_Immediato_PEC", "S");
                            parametri.Add("PRCORE03_99991009_Oggetto_PEC", _datiProtocollo.ProtoIn.OggettoMail);
                            parametri.Add("PRCORE03_99991009_Testo_PEC", _datiProtocollo.ProtoIn.CorpoMail);
                            parametri.Add("PRCORE03_99991009_TipoInvio_PEC", _vert.TipoInvioPEC);
                        }
                    }
                }
            }

            return parametri;
        }
    }
}
