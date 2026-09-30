using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using Init.SIGePro.Protocollo.AIDAProtService;
using Init.SIGePro.Protocollo.Aida;
using System.Xml.Serialization;
using System.IO;
using Init.SIGePro.Data;
using System.Text.RegularExpressions;
using Microsoft.Web.Services2.Attachments;
using Init.SIGePro.Exceptions.Protocollo;
using Init.SIGePro.Verticalizzazioni;
using log4net;
using System.Web;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_AIDA : ProtocolloBase
    {
        #region Proprietà

        string _utente = String.Empty;
        string _password = String.Empty;
        string _url = String.Empty;
        string _possesso = String.Empty;
        string _tipoSegnaturaAllegati = String.Empty;
        string _errorMessage = String.Empty;

        private string BaseUrlAllegati
        {
            get { return SetBaseUrlAllegati(); }
        }

        #endregion

        public PROTOCOLLO_AIDA()
        {
            
        }

        private string SetBaseUrlAllegati()
        {
            var req = HttpContext.Current.Request;
            var downloadUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

            if (!String.IsNullOrEmpty(req.ApplicationPath))
                downloadUrl += req.ApplicationPath;

            if (!downloadUrl.EndsWith("/"))
                downloadUrl += "/";

            downloadUrl += TempPath.Replace("~\\", "");
            
            _protocolloLogs.DebugFormat("BaseUrl File Virtual Path: {0}", downloadUrl);

            return downloadUrl;
        }

        private AIDAProtSoapPortClient CreaWebService()
        {
            _protocolloLogs.Debug("Creazione del web service AIDA");
            try
            {
                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_AIDA NON È STATO VALORIZZATO.");

                var ws = new AIDAProtSoapPortClient(binding, endPointAddress);

                _protocolloLogs.Debug("Fine creazione del web service AIDA");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE", ex);
            }
        }

        #region Protocollazione

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn pProt)
        {
            try
            {
                SetParametriVertProtocolloAIDA();

                using (var ws = CreaWebService())
                {
                    string segnatura = GetSegnatura(pProt);
                    string wsResponse = EseguiProtocollazione(ws, segnatura);

                    var datiResponse = (SegnaturaResponse)DeserializeFromString(wsResponse, typeof(SegnaturaResponse));
                    
                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, (object)datiResponse);
                    
                    string xmlAllegati = InserimentoAllegati(pProt.Allegati, datiResponse);
                    EseguiInsertAllegati(ws, xmlAllegati);

                    DatiProtocolloRes dp = CreaDatiProtocollo(datiResponse);
                    return dp;
                }
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE ESEGUITO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

        private string EseguiProtocollazione(AIDAProtSoapPortClient ws, string segnatura)
        {
            try
            {
                //Esegue la protocollazione e ritorna i dati del protocollo.
                _protocolloLogs.InfoFormat("Chiamata a GETNPROT (Protocollazione), segnatura: {0}", segnatura);
                string wsResponse = ws.GETNPROT(segnatura);
                _protocolloLogs.Info("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");
                return wsResponse;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE L'ESECUZIONE DELLA LA PROTOCOLLAZIONE", ex);
            }
        }

        private DatiProtocolloRes CreaDatiProtocollo(SegnaturaResponse datiResponse)
        {
            try
            {
                DatiProtocolloRes datiProtocollo = new DatiProtocolloRes();

                datiProtocollo.AnnoProtocollo = datiResponse.anno;
                datiProtocollo.DataProtocollo = datiResponse.data;
                datiProtocollo.NumeroProtocollo = datiResponse.numero;

                _protocolloLogs.InfoFormat("DATI PROTOCOLLAZIONE, Numero: {0}, Data: {1}, Anno: {2}", datiProtocollo.NumeroProtocollo, datiProtocollo.DataProtocollo, datiProtocollo.AnnoProtocollo);

                return datiProtocollo;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DEI DATI DI PROTOCOLLO", ex);
            }
        }

        #endregion

        #region Segnatura

        /// <summary>
        /// Serializza la Classe Segnatura del Namespace Protocollo.Aida in quanto al web service deve essere passata una stringa.
        /// </summary>
        /// <returns></returns>
        private string GetSegnatura(Data.DatiProtocolloIn pProt)
        {
            try
            {
                string rVal = String.Empty;
                var corrispondenti = new List<Segnatura.ProtocolloCorrispondentiCorrispondente>();
                var assegnatari = new List<Segnatura.ProtocolloAssegnatariAssegnatario>();
                var segnatura = new Segnatura();

                if (String.IsNullOrEmpty(_utente))
                    throw new Exception("E' NECESSARIO INSERIRE L'UTENTE");

                segnatura.utente = _utente;

                if (String.IsNullOrEmpty(_utente))
                    throw new Exception("E' NECESSARIO INSERIRE LA PASSWORD");

                segnatura.password = _password;

                if (pProt.Flusso == ProtocolloConstants.COD_ARRIVO)
                {
                    segnatura.tipo = "1";
                    corrispondenti = GetCorrispondenti(pProt.Mittenti);
                    assegnatari = GetAssegnatari(pProt.Destinatari);
                }

                if (pProt.Flusso == ProtocolloConstants.COD_PARTENZA)
                {
                    segnatura.tipo = "2";
                    corrispondenti = GetCorrispondenti(pProt.Destinatari);
                    assegnatari = GetAssegnatari(pProt.Mittenti);
                }

                if (pProt.Flusso == ProtocolloConstants.COD_INTERNO)
                {
                    segnatura.tipo = "3";
                    corrispondenti = GetCorrispondenti(pProt.Mittenti);
                    assegnatari = GetAssegnatari(pProt.Destinatari);
                }

                if (corrispondenti.Count == 0)
                    throw new Exception("E' NECESSARIO INSERIRE ALMENO UN CORRISPONDENTE");

                segnatura.corrispondenti = new Segnatura.ProtocolloCorrispondenti();
                segnatura.corrispondenti.corrispondente = new List<Segnatura.ProtocolloCorrispondentiCorrispondente>();
                segnatura.corrispondenti.corrispondente = corrispondenti;

                if (String.IsNullOrEmpty(_utente))
                    throw new Exception("E' NECESSARIO INSERIRE L'OGGETTO");

                segnatura.oggetto = pProt.Oggetto;

                segnatura.assegnatari = new Segnatura.ProtocolloAssegnatari();
                segnatura.assegnatari.assegnatario = new List<Segnatura.ProtocolloAssegnatariAssegnatario>();
                segnatura.assegnatari.assegnatario = assegnatari;

                _protocolloSerializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, (object)segnatura);

                rVal = SerializeToString((object)segnatura, segnatura.GetType());

                return rVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE LA CREAZIONE DELLA SEGNATURA", ex);
            }
        }

        private List<Segnatura.ProtocolloCorrispondentiCorrispondente> GetCorrispondenti(Data.ListaMittDest corr)
        {
            var corrispondenti = new List<Segnatura.ProtocolloCorrispondentiCorrispondente>();

            if (corr.Amministrazione.Count > 0)
            {
                foreach (var amm in corr.Amministrazione)
                {
                    var corrispondente = new Segnatura.ProtocolloCorrispondentiCorrispondente();

                    corrispondente.nome = amm.AMMINISTRAZIONE;
                    corrispondenti.Add(corrispondente);
                }
            }

            if (corr.Anagrafe.Count > 0)
            {
                foreach (var amm in corr.Anagrafe)
                {
                    var corrispondente = new Segnatura.ProtocolloCorrispondentiCorrispondente();

                    string nominativo = amm.NOMINATIVO + " " + amm.NOME;
                    corrispondente.nome = nominativo.Trim();
                    corrispondenti.Add(corrispondente);
                }
            }

            return corrispondenti;
        }

        private List<Segnatura.ProtocolloAssegnatariAssegnatario> GetAssegnatari(Data.ListaMittDest ass)
        {
            var assegnatari = new List<Segnatura.ProtocolloAssegnatariAssegnatario>();
            var assegnatario = new Segnatura.ProtocolloAssegnatariAssegnatario();

            if (ass.Amministrazione.Count > 0)
            {
                foreach (var amm in ass.Amministrazione)
                {
                    assegnatario.livello = amm.PROT_UO;
                    assegnatario.possesso = _possesso;
                    assegnatari.Add(assegnatario);
                }
            }

            return assegnatari;
        }

        #endregion

        #region Serializzazione / Deserializzazione

        private string SerializeToString(object segnatura, Type t)
        {
            XmlSerializer serializer = new XmlSerializer(t);

            using (StringWriter writer = new StringWriter())
            {
                serializer.Serialize(writer, segnatura);
                return writer.ToString();
            }
        }

        private object DeserializeFromString(string xml, Type type)
        {
            object response;

            MemoryStream pMemStream = null;
            MemoryStream pMemStreamOut = null;

            try
            {
                pMemStreamOut = new MemoryStream();
                pMemStream = Init.Utils.StreamUtils.StringToStream(xml);
                Init.Utils.StreamUtils.BulkTransfer(pMemStream, pMemStreamOut);
                pMemStream.Seek(0, SeekOrigin.Begin);

                XmlSerializer pXmlSerializer;
                pXmlSerializer = new XmlSerializer(type);
                response = pXmlSerializer.Deserialize(pMemStreamOut);

                return response;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA DESERIALIZZAZIONE DELLA RISPOSTA DELLA PROTOCOLLAZIONE DEL WEB SERVICE ", ex);
            }
        }

        #endregion

        #region Allegati

        private string InserimentoAllegati(List<ProtocolloAllegati> pProtAll, SegnaturaResponse response)
        {
            try
            {
                SegnaturaAllegati sa = new SegnaturaAllegati();

                sa.utente = _utente;
                sa.password = _password;
                sa.numero = response.numero;
                sa.anno = response.anno;
                sa.tipo = _tipoSegnaturaAllegati;

                sa.documenti = new SegnaturaAllegati.ProtocolloDocumenti();

                var docs = GetDocs(pProtAll);
                sa.documenti.doc = docs;

                _protocolloSerializer.Serialize(ProtocolloLogsConstants.AllegatoRequestFileName, (object)sa);

                string rVal = SerializeToString((object)sa, sa.GetType());

                return rVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE L'INSERIMENTO DEGLI ALLEGATI", ex);
            }
        }

        /// <summary>
        /// Esegue l'upload dell'allegato.
        /// </summary>
        /// <param name="response"></param>
        /// <returns>Ritorna una lista di ProtocolloDocumentiDoc</returns>
        private List<SegnaturaAllegati.ProtocolloDocumentiDoc> GetDocs(List<ProtocolloAllegati> pProtAll)
        {
            string percorso = String.Empty;
            FileStream pFs = null;

            var docs = new List<SegnaturaAllegati.ProtocolloDocumentiDoc>();

            foreach (var allegato in pProtAll)
            {

                byte[] bytes = allegato.OGGETTO;

                if (bytes != null)
                {
                    /*string pattern = @"[\\/:*?<>|]";
                    allegato.NOMEFILE = Regex.Replace(allegato.NOMEFILE, pattern, "");
                    allegato.NOMEFILE = allegato.NOMEFILE.Replace("\"", "");
                    allegato.NOMEFILE = allegato.NOMEFILE.Replace("€", "Euro");*/

                    percorso = Path.Combine(_protocolloLogs.Folder, allegato.NOMEFILE);
                    pFs = new FileStream(percorso, FileMode.Create);

                    pFs.Write(bytes, 0, bytes.Length);

                    pFs.Close();

                    var doc = new SegnaturaAllegati.ProtocolloDocumentiDoc();
                    doc.url = GetUrlAlletato(percorso);
                    _protocolloLogs.DebugFormat("Url File Allegato: {0}", doc.url);
                    docs.Add(doc);
                }
                else
                    throw new ProtocolloException("L'ALLEGATO " + allegato.NOMEFILE + ", HA L'OGGETTO NULL");
            }

            return docs;
        }

        private string GetUrlAlletato(string percorso)
        {
            try
            {
                string folder = new FileInfo(percorso).Directory.Name;
                string file = Path.GetFileName(percorso);

                string folderFile = Path.Combine(folder, file);

                return BaseUrlAllegati + "/" + folderFile.Replace("\\","/");
            }
            catch (Exception ex)
            {
                throw new Exception("URL NON TROVATO: " + percorso + ".", ex);
            }
        }

        private void EseguiInsertAllegati(AIDAProtSoapPortClient ws, string xmlAllegati)
        {
            try
            {
                _protocolloLogs.DebugFormat("Chiamata a SETDOCNPROT (Inserimento allegati), segnatura allegati: {0}", xmlAllegati);
                string xmlResponse = ws.SETDOCNPROT(xmlAllegati);
                _protocolloLogs.Info("INSERIMENTO DEGLI ALLEGATI AVVENUTO CON SUCCESSO");

                var response = (SegnaturaAllegatiResponse)DeserializeFromString(xmlResponse, typeof(SegnaturaAllegatiResponse));
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.AllegatoResponseFileName, (object)response);

                if (response.errnum != "0")
                    throw new Exception(String.Format("CODICE ERRORE: {0}", response.errnum));


            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE L'INSERIMENTO DEGLI ALLEGATI", ex);
            }
        }

        #endregion

        private void SetParametriVertProtocolloAIDA()
        {
            try
            {
                var protocolloAida = new VerticalizzazioneProtocolloAida(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

                if (protocolloAida.Attiva)
                {
                    _protocolloLogs.DebugFormat("Valori parametri verticalizzazioni: url: {0}, utente: {1}, password: {2}, tipoallegati: {3}, possesso: {4}", 
                                                protocolloAida.Url,
                                                protocolloAida.Utente,
                                                protocolloAida.Password,
                                                protocolloAida.TipoAllegati,
                                                protocolloAida.Possesso);

                    if (String.IsNullOrEmpty(protocolloAida.Url))
                        throw new Exception("L'URL DEL WEB SERVICE NON E' VALORIZZATO");

                    if (String.IsNullOrEmpty(protocolloAida.Utente))
                        throw new Exception("L'UTENTE NON E' VALORIZZATO");

                    if (String.IsNullOrEmpty(protocolloAida.Password))
                        throw new Exception("LA PASSWORD NON E' VALORIZZATA");

                    if (String.IsNullOrEmpty(protocolloAida.TipoAllegati))
                        throw new Exception("IL TIPO DEGLI ALLEGATI NON E' VALORIZZATO");

                    _password = protocolloAida.Password;
                    _utente = protocolloAida.Utente;
                    _url = protocolloAida.Url;
                    _possesso = protocolloAida.Possesso;
                    _tipoSegnaturaAllegati = protocolloAida.TipoAllegati;

                    _protocolloLogs.Debug("Fine recupero valori da verticalizzazioni");
                }
                else
                    throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_AIDA NON È ATTIVA");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE IL RECUPERO DELLE INFORMAZIONI DALLA VERTICALIZZAZIONE PROTOCOLLO_AIDA", ex);
            }
        }

    }
}

