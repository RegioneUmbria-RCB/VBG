using Init.SIGePro.Protocollo.ApSystems.Allegati;
using Init.SIGePro.Protocollo.ApSystems.Protocollazione.Corrispondenti.Get;
using Init.SIGePro.Protocollo.ApSystems.Protocollazione.Corrispondenti.Insert;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.Protocollazione
{
    public class ProtocollazioneArrivo : IProtocollazione
    {
        IEnumerable<IAnagraficaAmministrazione> _anagrafiche;
        CorrispondentiGetServiceWrapper _corrispondentGetSrv;
        CorrispondentiInsertServiceWrapper _corrispondentiInsertSrv;
        string _operatore;
        string _uo;

        ILog logs = LogManager.GetLogger(typeof(ProtocollazioneArrivo));

        public ProtocollazioneArrivo(IEnumerable<IAnagraficaAmministrazione> anagrafiche, CorrispondentiGetServiceWrapper corrispondentGetSrv, CorrispondentiInsertServiceWrapper corrispondentiInsertSrv, string operatore, string uo)
        {
            _anagrafiche = anagrafiche;
            _corrispondentGetSrv = corrispondentGetSrv;
            _corrispondentiInsertSrv = corrispondentiInsertSrv;
            _operatore = operatore;
            _uo = uo;
        }

        public DatiProtocolloRes Protocolla(DataSet request, ProtocollazioneServiceWrapper wrapper)
        {
            return wrapper.ProtocollaArrivo(request);
        }

        public void InserisciAllegato(string codiceProtocollo, string numeroProtocollo, string dataProtocollo, byte[] oggetto, string nomeFile, string codiceAllegato, AllegatiServiceWrapper allegatiSrv)
        {
            allegatiSrv.InserisciAllegatoaProtocolloGenerale(codiceProtocollo, numeroProtocollo, dataProtocollo, oggetto, nomeFile, codiceAllegato);
        }

        public string Flusso
        {
            get { return "A"; }
        }

        public void SetMittenti(DatiProtocollo.protocolli.mittenteDataTable dt)
        {
            try
            {
                //var dt = new DatiProtocollo.protocolli.mittenteDataTable();
                foreach (var a in _anagrafiche)
                {
                    if (String.IsNullOrEmpty(a.CodiceFiscalePartitaIva))
                        throw new Exception(String.Format("CODICE FISCALE / PARTITA IVA DEL MITTENTE {0} NON VALORIZZATO", a.Denominazione));

                    var corrDt = _corrispondentGetSrv.GetCorrispondenteByCodiceFiscale(a.CodiceFiscalePartitaIva);

                    logs.Info("PREPARAZIONE DELLA NUOVA RIGA MITTENTEROW");
                    var r = dt.NewmittenteRow();

                    logs.InfoFormat("NUMERO RIGHE TROVATE DALLA RICERCA, {0}", corrDt.Rows.Count);
                    if (corrDt.Rows.Count == 0)
                    {
                        var rcInsert = _corrispondentiInsertSrv.InsertCorrispondente(a, _operatore);
                        var rc = _corrispondentGetSrv.GetCorrispondenteByCodice(rcInsert.codice);

                        r.codice = rc.codice;
                        r.descrizione = rc.descrizione;
                        r.indirizzo = rc.indirizzo;
                        r.cap = rc.cap;
                        r.codice_comune = rc.codice_comune;
                        r.descrizione_comune = rc.descrizione_comune;
                    }
                    else
                    {
                        logs.Info("VALORIZZAZIONE DATI DA CORRISPONDENTE TROVATO IN ANAGRAFICA PROTCOLLO");
                        var rc = corrDt[0];
                        logs.InfoFormat("CODICE: {0}", rc.codice);
                        r.codice = rc.codice;
                        logs.InfoFormat("DESCRIZIONE: {0}", rc.descrizione);
                        r.descrizione = rc.descrizione;
                        logs.InfoFormat("INDIRIZZO: {0}", rc.indirizzo);
                        r.indirizzo = rc.indirizzo;
                        logs.InfoFormat("CAP: {0}", rc.cap);
                        r.cap = rc.cap;
                        logs.InfoFormat("CODICE COMUNE: {0}", rc.codice_comune);
                        r.codice_comune = rc.codice_comune;
                        logs.InfoFormat("DESCRIZIONE COMUNE: {0}", rc.descrizione_comune);
                        r.descrizione_comune = rc.descrizione_comune;
                    }
                    logs.Info("AGGIUNTA ALLA NUOVA RIGA");
                    dt.AddmittenteRow(r);
                    logs.Info("AGGIUNTA ALLA NUOVA RIGA AVVENUTO CON SUCCESSO");
                }

                //return dt;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA VALORIZZAZIONE DEI MITTENTI DI UN PROTOCOLLO IN ARRIVO, {0}", ex.Message), ex);
            }
        }

        public void SetDestinatari(DatiProtocollo.protocolli.destinatarioDataTable dt)
        {
            try
            {
                var rc = _corrispondentGetSrv.GetCorrispondenteByCodiceUfficio(_uo);

                var r = dt.NewdestinatarioRow();

                r.codice = rc.codice;
                r.descrizione = rc.descrizione;
                r.indirizzo = rc.indirizzo;
                r.cap = rc.cap;
                r.codice_comune = rc.codice_comune;
                r.descrizione_comune = rc.descrizione_comune;
                r.codice_ufficio = rc.codice_ufficio;

                dt.AdddestinatarioRow(r);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA VALORIZZAZIONE DEL DESTINATARIO DI UN PROTOCOLLO IN ARRIVO, {0}", ex.Message), ex);
            }
        }
    }
}
