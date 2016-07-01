using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Globalization;
using System.IO;
using System.Reflection;
using System.Web;
using System.Xml;
using System.Xml.Schema;
using System.Xml.Serialization;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Protocollo;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.Protocollo;
using Init.SIGePro.Verticalizzazioni;
using PersonalLib2.Data;
using log4net;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Validation;
using System.Text;
using Init.SIGePro.Protocollo.Serialize;
using System.Linq;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Manager.Logic.OggettiLogic;
using Init.SIGePro.Protocollo.ProtocolloServices.OperatoreProtocollo;
using System.Collections;
using Init.SIGePro.Authentication;
using System.Runtime.Serialization;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloManagers;

namespace Init.SIGePro.Protocollo.Manager
{
    public partial class ProtocolloMgr : IDisposable
    {
        ResolveDatiProtocollazioneService _datiProtocollazione;
        ProtocolloLogs _protocolloLogs;
        ProtocolloSerializer _protocolloSerializer;
        DatiProtocolloIn _protoIn;
        Fascicolo _fascicolo;
        Dati _dati;
        VerticalizzazioneProtocolloAttivo _verticalizzazioneProtocolloAttivo;
        ProtocolloBase _protocolloBase;

        public ProtocolloMgr(AuthenticationInfo authInfo, string software, string codiceComune = "", ProtocolloEnum.AmbitoProtocollazioneEnum ambito = ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO, Istanze istanza = null, Movimenti movimento = null)
        {
            if (String.IsNullOrEmpty(software))
                throw new ArgumentNullException("software");

            if (authInfo == null)
                throw new ArgumentNullException("authInfo");

            if (String.IsNullOrEmpty(authInfo.IdComune))
                throw new ArgumentNullException("authInfo.IdComune");

            var dataBase = authInfo.CreateDatabase();

            var idComune = authInfo.IdComune;
            var idComuneAlias = authInfo.Alias;
            var codOperatore = authInfo.CodiceResponsabile;
            var token = authInfo.Token;

            this._datiProtocollazione = new ResolveDatiProtocollazioneService(idComune, idComuneAlias, software, dataBase, istanza, movimento, codOperatore, ambito, token, codiceComune);

            this._protocolloLogs = new ProtocolloLogs(this._datiProtocollazione, this.GetType());
            this._protocolloSerializer = new ProtocolloSerializer(_protocolloLogs);

            var attivazioneProtoService = new AttivazioneProtocolloService(this._datiProtocollazione, this._protocolloLogs);
            _verticalizzazioneProtocolloAttivo = attivazioneProtoService.VertProtocolloAttivo;
            _protocolloBase = attivazioneProtoService.AttivaProtocollo();
        }

        private void SetProtocollo(ProtocolloEnum.TipoProvenienza provenienza, int iSource)
        {

            var tipoInserimento = (ProtocolloEnum.Source)iSource;

            _protocolloLogs.Debug("Valorizzazione dei dati di base relativi alla classe ProtocolloBase");

            _protocolloBase.Operatore = _verticalizzazioneProtocolloAttivo.Operatore;
            _protocolloBase.ProxyAddress = _verticalizzazioneProtocolloAttivo.ProxyAddress;
            _protocolloBase.Provenienza = provenienza;
            _protocolloBase.AggiungiAnno = GetParamVertBool(_verticalizzazioneProtocolloAttivo.Aggiungianno, "AGGIUNGIANNO");
            _protocolloBase.GestisciFascicolazione = GetParamVertBool(_verticalizzazioneProtocolloAttivo.GestisciFascicolazione, "GESTISCIFASCICOLAZIONE");
            _protocolloBase.TempPath = ConfigurationManager.AppSettings["TempPath"];
            _protocolloBase.TipoInserimento = tipoInserimento;

            _protocolloLogs.DebugFormat("Valorizzazione dei dati relativi al TipoDocumento, valore TipoDocumento: {0}", _dati.TipoDocumento);

            if (!String.IsNullOrEmpty(_dati.TipoDocumento))
                _protoIn.TipoDocumento = _dati.TipoDocumento;
            else
            {
                string tipoDocumento = "";

                if (_datiProtocollazione.Istanza != null)
                {
                    var mgr = new ProtocolloTipiDocumentoMgr(this._datiProtocollazione.Db);
                    tipoDocumento = mgr.GetCodiceFromAlberoProcProtocollo(_datiProtocollazione.CodiceInterventoProc.Value, _datiProtocollazione.IdComune, _datiProtocollazione.Software, _datiProtocollazione.CodiceComune);
                }

                if (String.IsNullOrEmpty(tipoDocumento))
                {
                    string tipoDocumentoDefault = tipoInserimento == ProtocolloEnum.Source.ON_LINE ? _verticalizzazioneProtocolloAttivo.Tipodocumentodefault : _verticalizzazioneProtocolloAttivo.Tipodocumentodefaultbo;

                    if (!String.IsNullOrEmpty(tipoDocumentoDefault))
                        _protoIn.TipoDocumento = tipoDocumentoDefault;
                    else
                        _protocolloLogs.Warn("TIPO DOCUMENTO DEFAULT BACKOFFICE NON VALORIZZATO");
                }
                else
                    _protoIn.TipoDocumento = tipoDocumento;
            }
            _protocolloLogs.Debug("Fine valorizzazione dei dati relativi al TipoDocumento");

            _protocolloLogs.DebugFormat("Tipo Smistamento: {0}, parametro TipoSmistamentoDefault della verticalizzazione Protocollo_Attivo: {1}", _dati.TipoSmistamento, _verticalizzazioneProtocolloAttivo.Tiposmistamentodefault);
            _protoIn.TipoSmistamento = !String.IsNullOrEmpty(_dati.TipoSmistamento) ? _dati.TipoSmistamento : _verticalizzazioneProtocolloAttivo.Tiposmistamentodefault;
            _protocolloLogs.DebugFormat("Valorizzazione dei dati relativi all'oggetto del protocollo, valore oggetto: {0}", _dati.Oggetto);

            IResolveMailTipoService mailTipoSrv = ResolveMailTipoFactory.Create(this._datiProtocollazione, _protocolloLogs, _protocolloSerializer);
            _protocolloLogs.Debug("Fine recupero dell'oggetto via web service");

            _protoIn.Oggetto = _dati.Oggetto;

            _protoIn.Corpo = String.Empty;

            if (mailTipoSrv != null)
            {
                if (String.IsNullOrEmpty(_dati.Oggetto))
                    _protoIn.Oggetto = mailTipoSrv.Oggetto;

                _protoIn.Corpo = mailTipoSrv.Corpo;
            }

            if (_verticalizzazioneProtocolloAttivo.OggettoUppercase == "1")
                _protoIn.Oggetto = _protoIn.Oggetto.ToUpper();

            if (!String.IsNullOrEmpty(_verticalizzazioneProtocolloAttivo.NumCaratteriOggetto))
            {
                int resultParse = Int32.MinValue;
                bool parseOggetto = Int32.TryParse(_verticalizzazioneProtocolloAttivo.NumCaratteriOggetto, out resultParse);

                if (parseOggetto && _protoIn.Oggetto.Length > resultParse)
                    throw new Exception("LA LUNGHEZZA DELL'OGGETTO SUPERA LA QUOTA MASSIMA CONSENTITA DAL PARAMETRO NUM_CARATTERI_OGGETTO DELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO");

            }

            _protocolloLogs.Debug("Fine valorizzazione dei dati relativi all'oggetto del protocollo");
            _protocolloLogs.DebugFormat("Valorizzazione dei dati relativi al flusso del protocollo");

            if (!String.IsNullOrEmpty(_dati.Flusso))
                _protoIn.Flusso = _dati.Flusso;
            else
            {
                if (!String.IsNullOrEmpty(_verticalizzazioneProtocolloAttivo.Flussodefault))
                    _protoIn.Flusso = _verticalizzazioneProtocolloAttivo.Flussodefault;
                else
                    throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO NON HA SETTATO IL CAMPO FLUSSODEFAULT");
            }
            _protocolloLogs.Debug("Fine valorizzazione dei dati relativi al flusso del protocollo");

            if (_datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA && (_protoIn.Flusso == "P"))
                throw new Exception("NON È POSSIBILE PROTOCOLLARE UN'ISTANZA CON UN FLUSSO IN USCITA!");

            _protocolloLogs.DebugFormat("Recupero dei dati relativi alla classifica: {0}", _dati.Classifica);
            string classifica = _dati.Classifica;

            if (!String.IsNullOrEmpty(classifica))
                _protoIn.Classifica = classifica;
            else
            {
                if (_datiProtocollazione.Istanza != null)
                {
                    var alberoProcMgr = new AlberoProcMgr(this._datiProtocollazione.Db);
                    _protoIn.Classifica = alberoProcMgr.GetClassificaProtocolloFromAlberoProcProtocollo(_datiProtocollazione.CodiceInterventoProc.Value, _datiProtocollazione.IdComune, _datiProtocollazione.Software, _datiProtocollazione.CodiceComune);
                    if (String.IsNullOrEmpty(_protoIn.Classifica))
                        _protoIn.Classifica = _verticalizzazioneProtocolloAttivo.ClassificadefaultBo;
                }
            }

            _protocolloLogs.DebugFormat("Fine recupero dei dati relativi alla classifica: {0}", _protoIn.Classifica);

            if (String.IsNullOrEmpty(_protoIn.Classifica))
                throw new Exception("NON È STATA SETTATA LA CLASSIFICA DI PROTOCOLLAZIONE NELL'ALBERO DEI PROCEDIMENTI");

            if (!String.IsNullOrEmpty(_dati.NumProtMitt))
                _protoIn.NumProtMitt = _dati.NumProtMitt;

            if (!String.IsNullOrEmpty(_dati.DataProtMitt))
                _protoIn.DataProtMitt = _dati.DataProtMitt;

            _protocolloLogs.Debug("Recupero dei dati relativi agli allegati");
            //Setto per l'oggetto Protocollo la proprietà Allegati
            SetAllegati(iSource);
            _protocolloLogs.Debug("Fine recupero dei dati relativi agli allegati");

            _protocolloLogs.Debug("Recupero dei dati relativi ai mittenti");
            //Setto per l'oggetto Protocollo la proprietà Mittenti
            SetMittenti();
            _protocolloLogs.Debug("Fine recupero dei dati relativi ai mittenti");
            _protocolloLogs.Debug("Recupero dei dati relativi ai destinatari");
            //Setto per l'oggetto Protocollo la proprietà Destinatari
            SetDestinatari();
            _protocolloLogs.Debug("Fine recupero dei dati relativi ai destinatari");
            //Setto per l'oggetto Protocollo la proprietà DestinatariPerConoscenza
            //SetDestinatariPerConoscenza();
            _protocolloLogs.Debug("Fine settaggio della classe DatiProtocolloIn");
        }

        private void SetMettiAllaFirma()
        {
            _protocolloBase.Operatore = _verticalizzazioneProtocolloAttivo.Operatore;

            if (!string.IsNullOrEmpty(_dati.TipoDocumento))
                _protoIn.TipoDocumento = _dati.TipoDocumento;
            else
            {
                var mgr = new ProtocolloTipiDocumentoMgr(this._datiProtocollazione.Db);
                var tipoDocumentoBo = mgr.GetCodiceFromAlberoProcProtocollo(_datiProtocollazione.CodiceInterventoProc.Value, _datiProtocollazione.IdComune, _datiProtocollazione.Software, _datiProtocollazione.CodiceComune);

                if (String.IsNullOrEmpty(tipoDocumentoBo))
                {
                    if (!String.IsNullOrEmpty(_verticalizzazioneProtocolloAttivo.Tipodocumentodefaultbo))
                        _protoIn.TipoDocumento = _verticalizzazioneProtocolloAttivo.Tipodocumentodefaultbo;
                    else
                        throw new ProtocolloException("LA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO NON HA SETTATO IL CAMPO TIPODOCUMENTODEFAULTBO");
                }
                else
                    _protoIn.TipoDocumento = tipoDocumentoBo;
            }

            if (!string.IsNullOrEmpty(_dati.Oggetto))
                _protoIn.Oggetto = _dati.Oggetto;
            else
            {
                IResolveMailTipoService mailTipoSrv = ResolveMailTipoFactory.Create(this._datiProtocollazione, _protocolloLogs, _protocolloSerializer);

                _protoIn.Oggetto = mailTipoSrv.Oggetto;
                _protoIn.Corpo = mailTipoSrv.Corpo;
            }

            if (!string.IsNullOrEmpty(_dati.Flusso))
                _protoIn.Flusso = _dati.Flusso;
            else
            {
                if (!string.IsNullOrEmpty(_verticalizzazioneProtocolloAttivo.Flussodefault))
                    _protoIn.Flusso = _verticalizzazioneProtocolloAttivo.Flussodefault;
                else
                    throw new ProtocolloException("La verticalizzazione Protocollo_Attivo non ha settato il campo FlussoDefault");
            }

            if (!String.IsNullOrEmpty(_dati.Classifica))
                _protoIn.Classifica = _dati.Classifica;
            else
            {
                var alberoMgr = new AlberoProcMgr(this._datiProtocollazione.Db);
                _protoIn.Classifica = alberoMgr.GetClassificaProtocolloFromAlberoProcProtocollo(_datiProtocollazione.CodiceInterventoProc.Value, _datiProtocollazione.IdComune, _datiProtocollazione.Software, _datiProtocollazione.CodiceComune);
            }

            if (String.IsNullOrEmpty(_protoIn.Classifica))
                throw new Exception("NON È STATA SETTATA LA CLASSIFICA DI PROTOCOLLAZIONE NELL'ALBERO DEI PROCEDIMENTI");

            if (!String.IsNullOrEmpty(_dati.NumProtMitt))
                _protoIn.NumProtMitt = _dati.NumProtMitt;

            if (!String.IsNullOrEmpty(_dati.DataProtMitt))
                _protoIn.DataProtMitt = _dati.DataProtMitt;

            SetAllegati();

            SetMittenti();

            SetDestinatari();
        }

        private void SetAllegati(int iSource = (int)ProtocolloEnum.Source.NONPROTOCOLLARE)
        {
            try
            {
                var protoAllegatiMgr = new ProtocolloAllegatiMgr(this._datiProtocollazione.Db);
                _protoIn.Allegati = new List<ProtocolloAllegati>();

                if (_dati.Allegati == null)
                {
                    bool noAllegatiFO = GetParamVertBool(_verticalizzazioneProtocolloAttivo.Noallegatifo, "NOALLEGATIFO");
                    bool noAllegati = GetParamVertBool(_verticalizzazioneProtocolloAttivo.Noallegati, "NOALLEGATI");

                    _protocolloLogs.DebugFormat("SOURCE: {0}, NOALLEGATIFO: {1}, NOALLEGATI: {2}", iSource, noAllegatiFO, noAllegati);
                    _protocolloLogs.DebugFormat("VERIFICA RECUPERO ALLEGATI: {0}", ((((iSource == (int)ProtocolloEnum.Source.INSERIMENTO_NORMALE) || (iSource == (int)ProtocolloEnum.Source.INSERIMENTO_RAPIDO) || (iSource == (int)ProtocolloEnum.Source.PROT_IST_MOV_AUT_BO) || (iSource == (int)ProtocolloEnum.Source.NONPROTOCOLLARE)) && !noAllegati) || (((iSource == (int)ProtocolloEnum.Source.ON_LINE) || (iSource == (int)ProtocolloEnum.Source.PROT_MOV_ONLINE)) && !noAllegatiFO)));

                    if ((((iSource == (int)ProtocolloEnum.Source.INSERIMENTO_NORMALE) || (iSource == (int)ProtocolloEnum.Source.INSERIMENTO_RAPIDO) || (iSource == (int)ProtocolloEnum.Source.PROT_IST_MOV_AUT_BO) || (iSource == (int)ProtocolloEnum.Source.NONPROTOCOLLARE)) && !noAllegati) || (((iSource == (int)ProtocolloEnum.Source.ON_LINE) || (iSource == (int)ProtocolloEnum.Source.PROT_MOV_ONLINE)) && !noAllegatiFO))
                    {
                        if (this._datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                        {
                            //Protocollo un'istanza
                            var docIstanzaMgr = new DocumentiIstanzaMgr(this._datiProtocollazione.Db);
                            var docIstanza = new DocumentiIstanza();
                            docIstanza.CODICEISTANZA = this._datiProtocollazione.CodiceIstanza;
                            docIstanza.IDCOMUNE = this._datiProtocollazione.IdComune;
                            docIstanza.OthersWhereClause.Add("DOCUMENTIISTANZA.CODICEOGGETTO is not null");

                            List<DocumentiIstanza> pListDocIstanza = docIstanzaMgr.GetList(docIstanza);
                            int iCountDocIstanza = pListDocIstanza.Count;

                            _protocolloLogs.DebugFormat("NUMERO ALLEGATI TROVATI SU DOCUMENTIISTANZA: {0}", iCountDocIstanza);

                            var istanzeAllMgr = new IstanzeAllegatiMgr(this._datiProtocollazione.Db);
                            var istanzeAll = new IstanzeAllegati();
                            istanzeAll.CODICEISTANZA = this._datiProtocollazione.CodiceIstanza;
                            istanzeAll.IDCOMUNE = this._datiProtocollazione.IdComune;
                            istanzeAll.OthersWhereClause.Add("ISTANZEALLEGATI.CODICEOGGETTO is not null");
                            List<IstanzeAllegati> pListIstanzeAll = istanzeAllMgr.GetList(istanzeAll);
                            int iCountIstanzeAll = pListIstanzeAll.Count;

                            _protocolloLogs.DebugFormat("NUMERO ALLEGATI TROVATI SU ISTANZEALLEGATI: {0}", iCountIstanzeAll);


                            var istanzeProcureMgr = new IstanzeProcureMgr(this._datiProtocollazione.Db);
                            var istanzeProcure = new IstanzeProcure();
                            istanzeProcure.CodiceIstanza = Convert.ToInt32(this._datiProtocollazione.CodiceIstanza);
                            istanzeProcure.IdComune = this._datiProtocollazione.IdComune;
                            istanzeProcure.OthersWhereClause.Add("ISTANZEPROCURE.CODICEOGGETTOPROCURA is not null");

                            var listIstanzeProcure = istanzeProcureMgr.GetList(istanzeProcure);
                            int iCountIstanzeProcure = listIstanzeProcure.Count;

                            _protocolloLogs.DebugFormat("NUMERO ALLEGATI TROVATI SU ISTANZEPROCURE: {0}", iCountIstanzeProcure);

                            var listaProcureSoggettiCollegati = new IstanzeRichiedentiMgr(this._datiProtocollazione.Db).GetList(new IstanzeRichiedenti
                            {
                                IDCOMUNE = this._datiProtocollazione.IdComune,
                                CODICEISTANZA = this._datiProtocollazione.CodiceIstanza,
                                OthersWhereClause = new ArrayList { "codiceoggetto_procura is not null" }
                            }).ToList<IstanzeRichiedenti>();

                            int numeroProcureSoggettiCollegati = listaProcureSoggettiCollegati.Count;

                            _protocolloLogs.DebugFormat("NUMERO ALLEGATI TROVATI SU ISTANZERICHIEDENTI: {0}", numeroProcureSoggettiCollegati);


                            int iCount = iCountDocIstanza + iCountIstanzeAll + iCountIstanzeProcure + numeroProcureSoggettiCollegati;

                            _protocolloLogs.DebugFormat("NUMERO ALLEGATI TOTALI: {0}", iCount);

                            var listaAllegati = new List<Allegato>();

                            listaAllegati.AddRange(pListDocIstanza.Select(x => new Allegato
                            {
                                Cod = x.CODICEOGGETTO,
                                Descrizione = x.DOCUMENTO
                            }));

                            _protocolloLogs.DebugFormat("INSERITI GLI ALLEGATI SU _dati.Allegati dopo il ciclo su DocumentiIstanza, numero Allegati: {0}", listaAllegati.Count);

                            listaAllegati.AddRange(pListIstanzeAll.Select(x => new Allegato
                            {
                                Cod = x.CODICEOGGETTO,
                                Descrizione = x.ALLEGATOEXTRA
                            }));

                            _protocolloLogs.DebugFormat("INSERITI GLI ALLEGATI SU _dati.Allegati dopo il ciclo su IstanzeAllegati, numero Allegati: {0}", listaAllegati.Count);

                            var oggMgr = new OggettiMgr(_datiProtocollazione.Db);

                            listaAllegati.AddRange(listIstanzeProcure.Where(y => y.CodiceOggettoProcura.HasValue).Select(x => new Allegato
                            {
                                Cod = x.CodiceOggettoProcura.Value.ToString(),
                                Descrizione = oggMgr.GetNomeFile(_datiProtocollazione.IdComune, x.CodiceOggettoProcura.Value)
                            }));

                            _protocolloLogs.DebugFormat("INSERITI GLI ALLEGATI SU _dati.Allegati dopo il ciclo su IstanzeProcure, numero Allegati: {0}", listaAllegati.Count);

                            listaAllegati.AddRange(listaProcureSoggettiCollegati.Where(y => y.CodiceoggettoProcura.HasValue).Select(x => new Allegato
                            {
                                Cod = x.CodiceoggettoProcura.Value.ToString(),
                                Descrizione = oggMgr.GetNomeFile(_datiProtocollazione.IdComune, x.CodiceoggettoProcura.Value)
                            }));

                            _protocolloLogs.DebugFormat("INSERITI GLI ALLEGATI SU _dati.Allegati dopo il ciclo su IstanzeRichiedenti, numero Allegati: {0}", listaAllegati.Count);


                            _dati.Allegati = listaAllegati.ToArray();

                        }
                        else if (this._datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                        {
                            //Protocollo un movimento
                            MovimentiAllegatiMgr pMovAllMgr = new MovimentiAllegatiMgr(this._datiProtocollazione.Db);
                            MovimentiAllegati pMovAll = new MovimentiAllegati();
                            pMovAll.CODICEMOVIMENTO = this._datiProtocollazione.CodiceMovimento;
                            pMovAll.IDCOMUNE = this._datiProtocollazione.IdComune;
                            pMovAll.OthersWhereClause.Add("MOVIMENTIALLEGATI.CODICEOGGETTO is not null");
                            List<MovimentiAllegati> pListMovAll = pMovAllMgr.GetList(pMovAll);
                            int iCount = pListMovAll.Count;

                            _dati.Allegati = new Allegato[iCount];
                            int iIndex = 0;
                            foreach (MovimentiAllegati obj in pListMovAll)
                            {
                                _dati.Allegati[iIndex] = new Allegato();
                                _dati.Allegati[iIndex].Cod = obj.CODICEOGGETTO;
                                _dati.Allegati[iIndex].Descrizione = obj.DESCRIZIONE;

                                iIndex++;
                            }

                            _protocolloLogs.DebugFormat("INSERITI GLI ALLEGATI SU _dati.Allegati dopo il ciclo su MovimentiAllegati, numero Allegati: {0}", iIndex);
                        }
                    }
                    else
                    {
                        _dati.Allegati = new Allegato[0];
                    }
                }

                _protocolloLogs.DebugFormat("NUMERO ALLEGATI INSERITI: {0}", _dati.Allegati.Length);

                foreach (Allegato allegato in _dati.Allegati)
                {

                    _protocolloLogs.DebugFormat("CHIAMATA AL WEB SERVICE CHE RESTITUISCE GLI OGGETTI, CODICE ALLEGATO: {0}", allegato.Cod);

                    //var recAnag = _datiProto.AnagraficheProtocollo.Select(x => x.Pec).Distinct().Select(x => new pecRecipientsRecipient { type = "to", Value = x });

                    //if (_protoIn.Allegati.Where(x => x.CODICEOGGETTO == allegato.Cod).Count() == 0)
                    //{
                    var oggetto = protoAllegatiMgr.GetById(this._datiProtocollazione.IdComune, Convert.ToInt32(allegato.Cod));

                    if (oggetto == null)
                        throw new Exception(String.Format("IL CODICEOGGETTO {0} PER L'IDCOMUNE {1} NON È PRESENTE NELLA TABELLA OGGETTI", allegato.Cod, this._datiProtocollazione.IdComune));

                    var nomeAllegato = new NomeFileAllegato(this._datiProtocollazione.IdComune, this._datiProtocollazione.CodiceComune, oggetto, allegato.Descrizione, _verticalizzazioneProtocolloAttivo.NomeFileMaxLength);

                    var protoAllegati = new ProtocolloAllegati
                    {
                        MimeType = protoAllegatiMgr.GetContentType(oggetto),
                        Extension = nomeAllegato.GetEstensione(),
                        NOMEFILE = nomeAllegato.GetNomeCompleto(_verticalizzazioneProtocolloAttivo.NomefileOrigine),
                        Descrizione = allegato.Descrizione,
                        CODICEOGGETTO = oggetto.CODICEOGGETTO,
                        IDCOMUNE = oggetto.IDCOMUNE,
                        OGGETTO = oggetto.OGGETTO,
                    };

                    /*var mgr = new OggettiMgr(_datiProtocollazione.Db);
                    protoAllegati.Percorso = mgr.GetPercorsoOggetto(oggetto.CODICEOGGETTO, _datiProtocollazione.IdComune);*/

                    protoAllegati.RimuoviCaratteriNonValidiDaNomeFile(_verticalizzazioneProtocolloAttivo.ListaCaratteriDaEliminare);

                    _protoIn.Allegati.Add(protoAllegati);
                    //}
                    //else
                    //    _protocolloLogs.WarnFormat("LA LISTA DEGLI ALLEGATI PRESENTA DUE OGGETTI CON LO STESSO CODICE, INDICE CHE ERANO PRESENTI DUE FILE IDENTICI, MOTIVO PER IL QUALE NE E' STATO PRESO IN CONSIDERAZIONE SOLAMENTE UNO");
                }

                SpostaAllegatoPrincipale(_protoIn.Allegati);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEGLI ALLEGATI NELL'OGGETTO PROTOCOLLO, {0}", ex.Message), ex);
            }
        }

        /// <summary>
        /// Questa funzionalità cerca nella tabella oggetti_metadati se è presente il file riepilogo domanda, in caso positivo lo sposta come primo elemento 
        /// facendolo diventare principale.
        /// </summary>
        /// <param name="allegati"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="db"></param>
        /// <param name="idComune"></param>
        protected void SpostaAllegatoPrincipale(List<ProtocolloAllegati> allegati)
        {
            if (allegati != null && allegati.Count > 0 && this._datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                var metadatoRiepilogoDomanda = String.IsNullOrEmpty(_verticalizzazioneProtocolloAttivo.MetadatoRiepilogoDomanda) ? Constants.RIEPILOGO_DOMANDA : _verticalizzazioneProtocolloAttivo.MetadatoRiepilogoDomanda;

                int? codiceOggettoRiepilogoDomanda = new OggettiMetadatiMgr(this._datiProtocollazione.Db).TrovaRiepilogoDomanda(Convert.ToInt32(this._datiProtocollazione.CodiceIstanza), this._datiProtocollazione.IdComune, Constants.CHIAVE_TIPODOCUMENTO, metadatoRiepilogoDomanda);

                if (codiceOggettoRiepilogoDomanda.HasValue)
                {
                    var allegatoPrincipale = allegati.Where(x => x.CODICEOGGETTO == codiceOggettoRiepilogoDomanda.Value.ToString()).FirstOrDefault();

                    if (allegatoPrincipale != null)
                    {
                        allegati.Remove(allegatoPrincipale);
                        allegati.Insert(0, allegatoPrincipale);
                    }
                }
            }
        }

        /// <summary>
        /// Metodo usato per settare la proprietà Mittenti
        /// </summary>
        private void SetMittenti()
        {
            try
            {

                var tipoMittente = ProtocolloEnum.TipoMittenteEnum.AZIENDA_RICHIEDENTE;

                if (_verticalizzazioneProtocolloAttivo.TipoMittDestAuto == "1")
                    tipoMittente = ProtocolloEnum.TipoMittenteEnum.RICHIEDENTE;
                
                if(_verticalizzazioneProtocolloAttivo.TipoMittDestAuto == "2")
                    tipoMittente = ProtocolloEnum.TipoMittenteEnum.AZIENDA;

                _protoIn.Mittenti = new ListaMittDest();
                _protoIn.Mittenti.Amministrazione = new List<Amministrazioni>();
                _protoIn.Mittenti.Anagrafe = new List<ProtocolloAnagrafe>();

                var listCodice = new List<string>();

                switch (_protoIn.Flusso)
                {
                    case "P":
                    case "I":
                        if (_dati.Mittenti == null)
                        {
                            var ammMgr = new AmministrazioniMgr(_datiProtocollazione.Db);
                            string codiceAmministrazione = "";

                            if (_datiProtocollazione.CodiceInterventoProc.HasValue)
                                codiceAmministrazione = ammMgr.GetCodiceFromAlberoProcProtocollo(_datiProtocollazione.CodiceInterventoProc.Value, _datiProtocollazione.IdComune, _datiProtocollazione.Software, _datiProtocollazione.CodiceComune);

                            if (String.IsNullOrEmpty(codiceAmministrazione))
                                codiceAmministrazione = _verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault;

                            _dati.Mittenti = new DatiMittenti
                            {
                                Amministrazione = new DatiAnagrafici[] 
                                { 
                                    new DatiAnagrafici 
                                    { 
                                        Cod = codiceAmministrazione, 
                                        Mezzo = _verticalizzazioneProtocolloAttivo.MezzoDefault, 
                                        ModalitaTrasmissione = _verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault 
                                    } 
                                }.ToList()
                            };
                        }

                        if (_dati.Mittenti.Amministrazione == null || _dati.Mittenti.Amministrazione.Count == 0)
                            throw new Exception("UN PROTOCOLLO IN PARTENZA OPPURE INTERNO DEVE AVERE COME MITTENTE UN'AMMINISTRAZIONE CON UNITÀ ORGANIZZATIVA O RUOLO SETTATI!");


                        foreach (var datiAnagAmm in _dati.Mittenti.Amministrazione)
                        {
                            var ammMgr = new AmministrazioniMgr(_datiProtocollazione.Db);
                            var amm = ammMgr.GetByIdProtocollo(this._datiProtocollazione.IdComune, Convert.ToInt32(datiAnagAmm.Cod), this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                            if (amm == null)
                                throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE  {0}, CODICE COMUNE: {1} NON È PRESENTE NELL'ANAGRAFICA DELL'AMMINISTRAZIONE", datiAnagAmm.Cod, this._datiProtocollazione.CodiceComune));

                            if ((!String.IsNullOrEmpty(amm.PROT_UO)) || (!String.IsNullOrEmpty(amm.PROT_RUOLO)))
                            {
                                if (!listCodice.Contains(amm.CODICEAMMINISTRAZIONE))
                                {
                                    amm.Mezzo = datiAnagAmm.Mezzo;
                                    amm.ModalitaTrasmissione = datiAnagAmm.ModalitaTrasmissione;
                                    _protoIn.Mittenti.Amministrazione.Add(amm);

                                    listCodice.Add(amm.CODICEAMMINISTRAZIONE);
                                }

                                if (_protoIn.Mittenti.Amministrazione.Count > 1)
                                    throw new Exception("UN PROTOCOLLO IN PARTENZA OPPURE INTERNO NON PUÒ AVERE COME MITTENTE PIÙ DI UNA AMMINISTRAZIONE CON UNITÀ ORGANIZZATIVA O RUOLO SETTATI!");
                            }

                            if ((String.IsNullOrEmpty(amm.PROT_UO)) && (String.IsNullOrEmpty(amm.PROT_RUOLO)))
                                throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER L'IDCOMUNE {1} NON HA VALORIZZATO NE' L'UNITÀ ORGANIZZATIVA NE' IL RUOLO", datiAnagAmm.Cod, this._datiProtocollazione.IdComune));
                        }

                        break;
                    case "A":
                        if (_dati.Mittenti == null)
                        {
                            var datiMittenti = new DatiMittenti();
                            var datiAnagraficiList = new List<DatiAnagrafici>();

                            if (_datiProtocollazione.Istanza == null)
                                throw new Exception("ISTANZA NON VALORIZZATA");

                            var datiAnagraficiRichiedente = new DatiAnagrafici
                                {
                                    Cod = _datiProtocollazione.Istanza.CODICERICHIEDENTE,
                                    Mezzo = _verticalizzazioneProtocolloAttivo.MezzoDefault,
                                    ModalitaTrasmissione = _verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault
                                };

                            DatiAnagrafici datiAnagraficiAzienda = null;
                            if (!String.IsNullOrEmpty(_datiProtocollazione.Istanza.CODICETITOLARELEGALE))
                            {
                                datiAnagraficiAzienda = (new DatiAnagrafici
                                {
                                    Cod = _datiProtocollazione.Istanza.CODICETITOLARELEGALE,
                                    Mezzo = _verticalizzazioneProtocolloAttivo.MezzoDefault,
                                    ModalitaTrasmissione = _verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault
                                });
                            }

                            if(tipoMittente == ProtocolloEnum.TipoMittenteEnum.AZIENDA_RICHIEDENTE)
                            {
                                _protocolloLogs.Info("VALORIZZAZIONE MITTENTI CON AZIENDA E RICHIEDENTE");
                                datiAnagraficiList.Add(datiAnagraficiRichiedente);
                                if (datiAnagraficiAzienda != null)
                                    datiAnagraficiList.Add(datiAnagraficiAzienda);
                                else
                                    _protocolloLogs.Info("VALORIZZAZIONE MITTENTI CON AZIENDA E RICHIEDENTE, AZIENDA NON PRESENTE");
                            }
                            else if(tipoMittente == ProtocolloEnum.TipoMittenteEnum.RICHIEDENTE)
                            {
                                datiAnagraficiList.Add(datiAnagraficiRichiedente);
                                _protocolloLogs.Info("VALORIZZAZIONE MITTENTI CON RICHIEDENTE");
                            }
                            else if (tipoMittente == ProtocolloEnum.TipoMittenteEnum.AZIENDA)
                            {
                                _protocolloLogs.Info("VALORIZZAZIONE MITTENTI CON AZIENDA");
                                if(datiAnagraficiAzienda != null)
                                    datiAnagraficiList.Add(datiAnagraficiAzienda);
                                else
                                {
                                    datiAnagraficiList.Add(datiAnagraficiRichiedente);
                                    _protocolloLogs.Info("VALORIZZAZIONE MITTENTI CON AZIENDA, AZIENDA NON PRESENTE INSERIMENTO RICHIEDENTE COME MITTENTE");
                                }
                            }

                            datiMittenti.Anagrafe = datiAnagraficiList;
                            _dati.Mittenti = datiMittenti;
                        }

                        if (_dati.Mittenti.Anagrafe != null)
                        {
                            listCodice.Clear();

                            foreach (var datiAnagrafici in _dati.Mittenti.Anagrafe)
                            {
                                var anagMgr = new ProtocolloAnagrafeMgr(_datiProtocollazione.Db);
                                var anag = anagMgr.GetById(this._datiProtocollazione.IdComune, Convert.ToInt32(datiAnagrafici.Cod));

                                if (anag == null)
                                    throw new Exception(String.Format("IL CODICEANAGRAFE {0} PER L'IDCOMUNE {1} NON E' PRESENTE NELL'ANAGRAFICA", datiAnagrafici.Cod, this._datiProtocollazione.IdComune));

                                var protoAnag = new ProtocolloAnagrafe();
                                anagMgr.SetProtocolloAnagrafe(anag, protoAnag);

                                if (this._datiProtocollazione.TipoAmbito != ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO)
                                    protoAnag.SetPec(this._datiProtocollazione.Istanza);

                                if (!String.IsNullOrEmpty(anag.CODCOMNASCITA))
                                {
                                    protoAnag.CodiceIstatComNasc = anagMgr.GetCodiceIstat(anag.CODCOMNASCITA);
                                    protoAnag.CodiceStatoEsteroNasc = anagMgr.GetCodiceStatoEstero(anag.CODCOMNASCITA);
                                    protoAnag.ComuneNascita = new ComuniMgr(this._datiProtocollazione.Db).GetById(anag.CODCOMNASCITA);

                                }

                                if (!String.IsNullOrEmpty(anag.COMUNERESIDENZA))
                                {
                                    protoAnag.CodiceIstatComRes = anagMgr.GetCodiceIstat(anag.COMUNERESIDENZA);
                                    protoAnag.CodiceStatoEsteroRes = anagMgr.GetCodiceStatoEstero(anag.COMUNERESIDENZA);
                                    protoAnag.ComuneResidenza = new ComuniMgr(this._datiProtocollazione.Db).GetById(anag.COMUNERESIDENZA);
                                }

                                if (!listCodice.Contains(protoAnag.CODICEANAGRAFE))
                                {
                                    protoAnag.Mezzo = datiAnagrafici.Mezzo;
                                    protoAnag.ModalitaTrasmissione = datiAnagrafici.ModalitaTrasmissione;
                                    _protoIn.Mittenti.Anagrafe.Add(protoAnag);

                                    listCodice.Add(protoAnag.CODICEANAGRAFE);
                                }

                                if (_protocolloBase.Anagrafiche == null)
                                    _protocolloBase.Anagrafiche = new List<IAnagraficaAmministrazione>();

                                _protocolloBase.Anagrafiche.Add(new AnagraficaService(protoAnag));

                            }
                        }

                        if (_dati.Mittenti.Amministrazione != null)
                        {
                            listCodice.Clear();

                            foreach (var datiAmministrazione in _dati.Mittenti.Amministrazione)
                            {
                                var ammMgr = new AmministrazioniMgr(_datiProtocollazione.Db);
                                var amm = ammMgr.GetByIdProtocollo(this._datiProtocollazione.IdComune, Convert.ToInt32(datiAmministrazione.Cod), this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                                if (amm == null)
                                    throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER IL CODICE COMUNE {1} NON E' PRESENTE NELL'ANAGRAFICA DELL'AMMINISTRAZIONE", datiAmministrazione.Cod, this._datiProtocollazione.CodiceComune));

                                if ((!String.IsNullOrEmpty(amm.PROT_UO)) || (!String.IsNullOrEmpty(amm.PROT_RUOLO)))
                                    throw new Exception("UN PROTOCOLLO IN ARRIVO NON PUÒ AVERE COME MITTENTE UNA AMMINISTRAZIONE CON UNITÀ ORGANIZZATIVA O RUOLO SETTATI!USARE IL FLUSSO INTERNO!");

                                if ((String.IsNullOrEmpty(amm.PROT_UO)) && (String.IsNullOrEmpty(amm.PROT_RUOLO)) && !listCodice.Contains(amm.CODICEAMMINISTRAZIONE))
                                {
                                    amm.Mezzo = datiAmministrazione.Mezzo;
                                    amm.ModalitaTrasmissione = datiAmministrazione.ModalitaTrasmissione;
                                    _protoIn.Mittenti.Amministrazione.Add(amm);

                                    listCodice.Add(amm.CODICEAMMINISTRAZIONE);
                                }

                                if (_protocolloBase.Anagrafiche == null)
                                    _protocolloBase.Anagrafiche = new List<IAnagraficaAmministrazione>();

                                _protocolloBase.Anagrafiche.Add(new AmministrazioneService(amm));

                            }
                        }

                        if ((_protoIn.Mittenti.Amministrazione.Count == 0) && (_protoIn.Mittenti.Anagrafe.Count == 0))
                            throw new Exception("UN PROTOCOLLO IN ARRIVO DEVE AVERE COME MITTENTE ALMENO UN'AMMINISTRAZIONE CON UNITÀ ORGANIZZATIVA O RUOLO NON SETTATI O ALMENO UN'ANAGRAFICA!");

                        break;
                    default:
                        break;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEI MITTENTI NELL'OGGETTO, {0}", ex.Message), ex);
            }
        }

        /// <summary>
        /// Metodo usato per settare la proprietà Destinatari
        /// </summary>
        private void SetDestinatari()
        {
            try
            {
                _protoIn.Destinatari = new ListaMittDest();
                _protoIn.Destinatari.Amministrazione = new List<Amministrazioni>();
                _protoIn.Destinatari.Anagrafe = new List<ProtocolloAnagrafe>();

                var tipoDestinatario = ProtocolloEnum.TipoMittenteEnum.AZIENDA_RICHIEDENTE;

                if (_verticalizzazioneProtocolloAttivo.TipoMittDestAuto == "1")
                    tipoDestinatario = ProtocolloEnum.TipoMittenteEnum.RICHIEDENTE;

                if (_verticalizzazioneProtocolloAttivo.TipoMittDestAuto == "2")
                    tipoDestinatario = ProtocolloEnum.TipoMittenteEnum.AZIENDA;

                //Usati per verificare se l'anagrafica o l'amministrazione siano già stati caricati 
                //nelle rispettive collection
                List<string> listCodice = new List<string>();

                switch (_protoIn.Flusso)
                {
                    case "A":
                    case "I":
                        if (_dati.Destinatari == null)
                        {
                            var ammMgr = new AmministrazioniMgr(_datiProtocollazione.Db);
                            string codiceAmministrazione = "";

                            if (_datiProtocollazione.CodiceInterventoProc.HasValue)
                                codiceAmministrazione = ammMgr.GetCodiceFromAlberoProcProtocollo(_datiProtocollazione.CodiceInterventoProc.Value, _datiProtocollazione.IdComune, _datiProtocollazione.Software, _datiProtocollazione.CodiceComune);

                            if (String.IsNullOrEmpty(codiceAmministrazione))
                                codiceAmministrazione = _verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault;

                            _dati.Destinatari = new DatiDestinatari
                            {
                                Amministrazione = new DatiAnagrafici[] 
                                { 
                                    new DatiAnagrafici 
                                    { 
                                        Cod = codiceAmministrazione, 
                                        Mezzo = _verticalizzazioneProtocolloAttivo.MezzoDefault, 
                                        ModalitaTrasmissione = _verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault 
                                    } 
                                }.ToList()
                            };
                        }

                        if (_dati.Destinatari.Amministrazione == null || _dati.Destinatari.Amministrazione.Count == 0)
                            throw new Exception("AMMINISTRAZIONE DESTINATARIO NON PRESENTE");

                        foreach (var datiAmministrazione in _dati.Destinatari.Amministrazione)
                        {
                            var ammMgr = new AmministrazioniMgr(_datiProtocollazione.Db);
                            var amm = ammMgr.GetByIdProtocollo(this._datiProtocollazione.IdComune, Convert.ToInt32(datiAmministrazione.Cod), this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                            if (amm == null)
                                throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER IL CODICE COMUNE {1} NON È PRESENTE NELLA TABELLA AMMINISTRAZIONI!", datiAmministrazione.Cod, this._datiProtocollazione.CodiceComune));

                            if ((String.IsNullOrEmpty(amm.PROT_UO)) && (String.IsNullOrEmpty(amm.PROT_RUOLO)))
                                throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER IL CODICE COMUNE {1} NON HA SETTATO NE' L'UNITÀ ORGANIZZATIVA NE' IL RUOLO", datiAmministrazione.Cod, this._datiProtocollazione.CodiceComune));

                            if (((!String.IsNullOrEmpty(amm.PROT_UO)) || (!String.IsNullOrEmpty(amm.PROT_RUOLO))) && !listCodice.Contains(amm.CODICEAMMINISTRAZIONE))
                            {
                                amm.Mezzo = datiAmministrazione.Mezzo;
                                amm.ModalitaTrasmissione = datiAmministrazione.ModalitaTrasmissione;
                                _protoIn.Destinatari.Amministrazione.Add(amm);

                                listCodice.Add(amm.CODICEAMMINISTRAZIONE);
                            }
                        }

                        break;
                    case "P":
                        //Verifico se i mittenti sono settati dal file xml

                        if (_dati.Destinatari == null)
                        {
                            //_dati.Destinatari = new DatiDestinatari();
                            var datiDestinatari = new DatiDestinatari();
                            var datiAnagraficiList = new List<DatiAnagrafici>();

                            if (_datiProtocollazione.Istanza == null)
                                throw new Exception("ISTANZA NON VALORIZZATA");

                            var datiAnagraficiRichiedente = new DatiAnagrafici
                            {
                                Cod = _datiProtocollazione.Istanza.CODICERICHIEDENTE,
                                Mezzo = _verticalizzazioneProtocolloAttivo.MezzoDefault,
                                ModalitaTrasmissione = _verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault
                            };

                            DatiAnagrafici datiAnagraficiAzienda = null;
                            if (!String.IsNullOrEmpty(_datiProtocollazione.Istanza.CODICETITOLARELEGALE))
                            {
                                datiAnagraficiAzienda = (new DatiAnagrafici
                                {
                                    Cod = _datiProtocollazione.Istanza.CODICETITOLARELEGALE,
                                    Mezzo = _verticalizzazioneProtocolloAttivo.MezzoDefault,
                                    ModalitaTrasmissione = _verticalizzazioneProtocolloAttivo.ModalitaTrasmissioneDefault
                                });
                            }

                            if (tipoDestinatario == ProtocolloEnum.TipoMittenteEnum.AZIENDA_RICHIEDENTE)
                            {
                                _protocolloLogs.Info("VALORIZZAZIONE MITTENTI CON AZIENDA E RICHIEDENTE");
                                datiAnagraficiList.Add(datiAnagraficiRichiedente);
                                if (datiAnagraficiAzienda != null)
                                    datiAnagraficiList.Add(datiAnagraficiAzienda);
                                else
                                    _protocolloLogs.Info("VALORIZZAZIONE MITTENTI CON AZIENDA E RICHIEDENTE, AZIENDA NON PRESENTE");
                            }
                            else if (tipoDestinatario == ProtocolloEnum.TipoMittenteEnum.RICHIEDENTE)
                            {
                                datiAnagraficiList.Add(datiAnagraficiRichiedente);
                                _protocolloLogs.Info("VALORIZZAZIONE MITTENTI CON RICHIEDENTE");
                            }
                            else if (tipoDestinatario == ProtocolloEnum.TipoMittenteEnum.AZIENDA)
                            {
                                _protocolloLogs.Info("VALORIZZAZIONE MITTENTI CON AZIENDA");
                                if (datiAnagraficiAzienda != null)
                                    datiAnagraficiList.Add(datiAnagraficiAzienda);
                                else
                                {
                                    datiAnagraficiList.Add(datiAnagraficiRichiedente);
                                    _protocolloLogs.Info("VALORIZZAZIONE MITTENTI CON AZIENDA, AZIENDA NON PRESENTE INSERIMENTO RICHIEDENTE COME MITTENTE");
                                }
                            }


                            datiDestinatari.Anagrafe = datiAnagraficiList;
                            _dati.Destinatari = datiDestinatari;
                        }

                        //Verifico le anagrafiche per i destinatari
                        if (_dati.Destinatari.Anagrafe != null)
                        {
                            listCodice.Clear();

                            foreach (var datiAnagrafe in _dati.Destinatari.Anagrafe)
                            {
                                var anagMgr = new ProtocolloAnagrafeMgr(_datiProtocollazione.Db);
                                var a = anagMgr.GetById(this._datiProtocollazione.IdComune, Convert.ToInt32(datiAnagrafe.Cod));

                                if (a == null)
                                    throw new Exception(String.Format("IL CODICEANAGRAFE {0} PER L'IDCOMUNE {1} NON È PRESENTE IN ANAGRAFICA", datiAnagrafe.Cod, this._datiProtocollazione.IdComune));

                                var protoAnag = new ProtocolloAnagrafe();
                                anagMgr.SetProtocolloAnagrafe(a, protoAnag);

                                if (this._datiProtocollazione.TipoAmbito != ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO)
                                    protoAnag.SetPec(this._datiProtocollazione.Istanza);

                                if (!String.IsNullOrEmpty(a.CODCOMNASCITA))
                                {
                                    protoAnag.CodiceIstatComNasc = anagMgr.GetCodiceIstat(a.CODCOMNASCITA);
                                    protoAnag.CodiceStatoEsteroNasc = anagMgr.GetCodiceStatoEstero(a.CODCOMNASCITA);
                                    protoAnag.ComuneNascita = new ComuniMgr(this._datiProtocollazione.Db).GetById(a.CODCOMNASCITA);

                                }
                                if (!String.IsNullOrEmpty(a.COMUNERESIDENZA))
                                {
                                    protoAnag.CodiceIstatComRes = anagMgr.GetCodiceIstat(a.COMUNERESIDENZA);
                                    protoAnag.CodiceStatoEsteroRes = anagMgr.GetCodiceStatoEstero(a.COMUNERESIDENZA);
                                    protoAnag.ComuneResidenza = new ComuniMgr(this._datiProtocollazione.Db).GetById(a.COMUNERESIDENZA);
                                }

                                if (!listCodice.Contains(protoAnag.CODICEANAGRAFE))
                                {
                                    protoAnag.Mezzo = datiAnagrafe.Mezzo;
                                    protoAnag.ModalitaTrasmissione = datiAnagrafe.ModalitaTrasmissione;

                                    _protoIn.Destinatari.Anagrafe.Add(protoAnag);

                                    listCodice.Add(protoAnag.CODICEANAGRAFE);
                                }
                                
                                if (_protocolloBase.Anagrafiche == null)
                                    _protocolloBase.Anagrafiche = new List<IAnagraficaAmministrazione>();

                                _protocolloBase.Anagrafiche.Add(new AnagraficaService(protoAnag));


                            }
                        }
                        if (_dati.Destinatari.Amministrazione != null)
                        {
                            listCodice.Clear();

                            foreach (var datiAmministrazione in _dati.Destinatari.Amministrazione)
                            {
                                var ammMgr = new AmministrazioniMgr(_datiProtocollazione.Db);
                                var amm = ammMgr.GetByIdProtocollo(this._datiProtocollazione.IdComune, Convert.ToInt32(datiAmministrazione.Cod), this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);
                                if (amm == null)
                                    throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER IL CODICE COMUNE {1} NON E' PRESENTE NELLA TABELLA DELLE AMMINISTRAZIONI", datiAmministrazione.Cod, this._datiProtocollazione.CodiceComune));

                                if (!listCodice.Contains(amm.CODICEAMMINISTRAZIONE))
                                {
                                    amm.Mezzo = datiAmministrazione.Mezzo;
                                    amm.ModalitaTrasmissione = datiAmministrazione.ModalitaTrasmissione;
                                    _protoIn.Destinatari.Amministrazione.Add(amm);

                                    listCodice.Add(amm.CODICEAMMINISTRAZIONE);
                                }

                                if (_protocolloBase.Anagrafiche == null)
                                    _protocolloBase.Anagrafiche = new List<IAnagraficaAmministrazione>();

                                _protocolloBase.Anagrafiche.Add(new AmministrazioneService(amm));

                            }
                        }

                        if ((_protoIn.Destinatari.Amministrazione.Count == 0) && (_protoIn.Destinatari.Anagrafe.Count == 0))
                            throw new Exception("UN PROTOCOLLO IN PARTENZA DEVE AVERE COME DESTINATARIO ALMENO UN'AMMINISTRAZIONE O UN'ANAGRAFICA!");
                        break;
                    default:
                        break;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEI DESTINATARI, {0}", ex.Message), ex);
            }
        }

        public DatiEtichette StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            if (String.IsNullOrEmpty(idProtocollo) && !dataProtocollo.HasValue && String.IsNullOrEmpty(numeroProtocollo))
                throw new Exception("L'ID, IL NUMERO E LA DATA DEL PROTOCOLLO SONO VUOTI!");

            if (String.IsNullOrEmpty(idProtocollo))
            {
                if (!dataProtocollo.HasValue)
                    throw new ProtocolloException("LA DATA DEL PROTOCOLLO È VUOTA");

                if (String.IsNullOrEmpty(numeroProtocollo))
                    throw new ProtocolloException("IL NUMERO DEL PROTOCOLLO È VUOTO!");
            }

            SetStampaEtichette();
            var datiEtichette = _protocolloBase.StampaEtichette(idProtocollo, dataProtocollo, numeroProtocollo, numeroCopie, stampante);

            if (datiEtichette != null)
                _protocolloLogs.InfoFormat("ID ETICHETTA RESTITUITO: {0}", datiEtichette.IdEtichetta);

            return datiEtichette;
        }

        private void SetStampaEtichette()
        {
            _protocolloBase.Operatore = _verticalizzazioneProtocolloAttivo.Operatore;

            var mgr = new AmministrazioniMgr(this._datiProtocollazione.Db);
            var amm = mgr.GetByIdProtocollo(this._datiProtocollazione.IdComune, Convert.ToInt32(_verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault), this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

            if (amm == null)
                throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER IL CODICE COMUNE {1} NON È PRESENTE NELLA TABELLA AMMINISTRAZIONI OPPURE NON È STATO SETTATO NELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO!", _verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault, this._datiProtocollazione.CodiceComune));

            _protocolloBase.Ruolo = string.IsNullOrEmpty(amm.PROT_RUOLO) ? amm.PROT_UO : amm.PROT_RUOLO;

        }

        public ListaFascicoli ListaFascicoli(DatiFasc datiFascicolo)
        {
            _fascicolo = new Fascicolo
            {
                Classifica = datiFascicolo.ClassificaFascicolo,
                NumeroFascicolo = datiFascicolo.NumeroFascicolo,
                Oggetto = datiFascicolo.OggettoFascicolo,
                DataFascicolo = datiFascicolo.DataFascicolo,
            };

            if (!String.IsNullOrEmpty(datiFascicolo.AnnoFascicolo))
                _fascicolo.AnnoFascicolo = Convert.ToInt32(datiFascicolo.AnnoFascicolo);

            var listaFascicoli = _protocolloBase.GetFascicoli(_fascicolo);

            if (listaFascicoli != null)
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.ListaFascicoliResponseFileName, listaFascicoli);

            return listaFascicoli;
        }

        public DatiProtocolloFascicolato IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            DatiProtocolloFascicolato protoFascicolato = null;

            try
            {
                if (GetParamVertBool(_verticalizzazioneProtocolloAttivo.GestisciFascicolazione, "GESTISCIFASCICOLAZIONE"))
                {
                    if (string.IsNullOrEmpty(idProtocollo) && string.IsNullOrEmpty(annoProtocollo) && string.IsNullOrEmpty(numeroProtocollo))
                    {
                        protoFascicolato = new DatiProtocolloFascicolato();
                        protoFascicolato.Fascicolato = EnumFascicolato.warning;
                        protoFascicolato.NoteFascicolo = "L'id, il numero e la data del protocollo sono vuoti!";

                        if (_protocolloLogs.IsDebugEnabled)
                            _protocolloSerializer.SerializeAndValidateStream(protoFascicolato);

                        return protoFascicolato;
                    }
                    else
                    {
                        if (string.IsNullOrEmpty(idProtocollo))
                        {
                            if (string.IsNullOrEmpty(annoProtocollo))
                            {
                                protoFascicolato = new DatiProtocolloFascicolato();
                                protoFascicolato.Fascicolato = EnumFascicolato.warning;
                                protoFascicolato.NoteFascicolo = "La data del protocollo è vuota!";

                                if (_protocolloLogs.IsDebugEnabled)
                                    _protocolloSerializer.SerializeAndValidateStream(protoFascicolato);

                                return protoFascicolato;
                            }
                            if (string.IsNullOrEmpty(numeroProtocollo))
                            {
                                protoFascicolato = new DatiProtocolloFascicolato();
                                protoFascicolato.Fascicolato = EnumFascicolato.warning;
                                protoFascicolato.NoteFascicolo = "Il numero del protocollo è vuoto!";

                                if (_protocolloLogs.IsDebugEnabled)
                                    _protocolloSerializer.SerializeAndValidateStream(protoFascicolato);

                                return protoFascicolato;
                            }
                        }
                    }

                    //Per ricavare informazioni utili per la fascicolazione del protocollo
                    SetEFascicolato();
                    protoFascicolato = _protocolloBase.IsFascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
                }
                else
                {
                    protoFascicolato = new DatiProtocolloFascicolato();
                    protoFascicolato.Fascicolato = EnumFascicolato.nondefinito;
                    protoFascicolato.NoteFascicolo = "Con il sistema di protocollazione attivo non è possibile fascicolare il protocollo";

                    if (_protocolloLogs.IsDebugEnabled)
                        _protocolloSerializer.SerializeAndValidateStream(protoFascicolato);

                    return protoFascicolato;
                }

                if (protoFascicolato != null)
                {
                    if (_protocolloLogs.IsDebugEnabled)
                        _protocolloSerializer.SerializeAndValidateStream(protoFascicolato);
                }

                return protoFascicolato;
            }
            catch (Exception ex)
            {
                protoFascicolato = new DatiProtocolloFascicolato();
                protoFascicolato.Fascicolato = EnumFascicolato.warning;
                protoFascicolato.NoteFascicolo = "Verifica fascicolazione terminata con errore: " + ex.Message;

                if (_protocolloLogs.IsDebugEnabled)
                    _protocolloSerializer.SerializeAndValidateStream(protoFascicolato);

                return protoFascicolato;
            }
        }

        public DatiFascicolo Fascicola(DatiFasc dati, int source = (int)ProtocolloEnum.SourceFascicolazione.FASC_IST_MOV_AUT_BO, string idProtocollo = null, string numeroProtocollo = null, string annoProtocollo = null)
        {

            DatiFascicolo rVal = null;
            bool gestisciFascicolazione = GetParamVertBool(_verticalizzazioneProtocolloAttivo.GestisciFascicolazione, "GESTISCIFASCICOLAZIONE");

            if (!gestisciFascicolazione)
                return new DatiFascicolo();

            if ((source != (int)ProtocolloEnum.SourceFascicolazione.FASC_IST_MOV_AUT_BO) && (source != (int)ProtocolloEnum.SourceFascicolazione.FASC_MOV_ONLINE))
            {
                var alberoMgr = new AlberoProcMgr(_datiProtocollazione.Db);
                bool fascicolazioneAutomatica = alberoMgr.IsFascicolazioneAutomatica(_datiProtocollazione.CodiceInterventoProc.Value, source, _datiProtocollazione.IdComune, _datiProtocollazione.Software, _datiProtocollazione.CodiceComune);

                if (!fascicolazioneAutomatica)
                    return new DatiFascicolo();
            }

            if (_datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                if (String.IsNullOrEmpty(_datiProtocollazione.Istanza.FKIDPROTOCOLLO) && (String.IsNullOrEmpty(_datiProtocollazione.Istanza.NUMEROPROTOCOLLO) || !_datiProtocollazione.Istanza.DATAPROTOCOLLO.HasValue))
                    return new DatiFascicolo();
                else
                {

                    string sNumProtocollo = "";
                    if (!String.IsNullOrEmpty(_datiProtocollazione.Istanza.NUMEROPROTOCOLLO))
                    {
                        string[] sNumProtSplit = _datiProtocollazione.Istanza.NUMEROPROTOCOLLO.Split(new Char[] { '/' });
                        sNumProtocollo = sNumProtSplit[0];
                    }
                }
            }

            if (_datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if (String.IsNullOrEmpty(_datiProtocollazione.Movimento.FKIDPROTOCOLLO) && (String.IsNullOrEmpty(_datiProtocollazione.Movimento.NUMEROPROTOCOLLO) || !_datiProtocollazione.Movimento.DATAPROTOCOLLO.HasValue))
                    return new DatiFascicolo();
                else
                {
                    string sNumProtocollo = "";
                    if (!String.IsNullOrEmpty(_datiProtocollazione.Movimento.NUMEROPROTOCOLLO))
                    {
                        string[] sNumProtSplit = _datiProtocollazione.Movimento.NUMEROPROTOCOLLO.Split(new Char[] { '/' });
                        sNumProtocollo = sNumProtSplit[0];
                    }
                }
            }

            if (_datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO)
            {
                if (String.IsNullOrEmpty(idProtocollo) && (String.IsNullOrEmpty(numeroProtocollo) || String.IsNullOrEmpty(annoProtocollo)))
                    throw new Exception("NON E' POSSIBILE FASCICOLARE, NON SONO STATI SPECIFICATI I DATI RELATIVI AL PROTOCOLLO DA FASCICOLARE");

                _protocolloBase.IdProtocollo = idProtocollo;
                _protocolloBase.NumProtocollo = numeroProtocollo;
                _protocolloBase.AnnoProtocollo = annoProtocollo;
            }

            if (dati == null)
                dati = new DatiFasc();
            else
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.FascicolazioneSoapRequestFileName, dati);

            _fascicolo = new Fascicolo();

            bool isRichiestaDiFascicolazionePronta = PreparaLaRichiestaPerCreazioneOCambiamentoFascicolo(dati, ProtocolloEnum.TipiFascicolazione.CREA);

            if (isRichiestaDiFascicolazionePronta)
            {
                rVal = _protocolloBase.Fascicola(_fascicolo);

                if (rVal != null)
                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.FascicolazioneSoapResponseFileName, rVal);
            }
            else
            {
                rVal = new DatiFascicolo { Warning = "LA PRATICA NON È STATA FASCICOLATA!!" };
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.ResponseFileName, rVal);
            }

            return rVal;
        }

        public DatiFascicolo CambiaFascicolo(DatiFasc datiFascicolo)
        {
            var datiFascicoloRes = new DatiFascicolo();

            if (datiFascicolo == null)
                throw new ArgumentNullException("datiFascicolo");

            _protocolloSerializer.Serialize(ProtocolloLogsConstants.FascicolazioneSoapRequestFileName, datiFascicolo);

            _fascicolo = new Fascicolo();

            if (PreparaLaRichiestaPerCreazioneOCambiamentoFascicolo(datiFascicolo))
                datiFascicoloRes = _protocolloBase.CambiaFascicolo(_fascicolo);
            else
                datiFascicoloRes.Warning = "La pratica non è stata fascicolata!!";

            _protocolloSerializer.Serialize(ProtocolloLogsConstants.FascicolazioneSoapResponseFileName, datiFascicoloRes);

            return datiFascicoloRes;
        }

        public void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento)
        {
            if (string.IsNullOrEmpty(idProtocollo) && string.IsNullOrEmpty(annoProtocollo) && string.IsNullOrEmpty(numeroProtocollo))
                throw new ProtocolloException("L'id, il numero e la data del protocollo sono vuoti!");
            else
            {
                if (string.IsNullOrEmpty(idProtocollo))
                {
                    if (string.IsNullOrEmpty(annoProtocollo))
                        throw new ProtocolloException("La data del protocollo è vuota!");
                    if (string.IsNullOrEmpty(numeroProtocollo))
                        throw new ProtocolloException("Il numero del protocollo è vuoto!");
                }
            }

            //Per ricavare informazioni utili per la stampa delle etichette
            SetAnnullaProtocollo();

            _protocolloBase.AnnullaProtocollo(idProtocollo, annoProtocollo, numeroProtocollo, motivoAnnullamento, noteAnnullamento);
        }

        public DatiProtocolloAnnullato IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            DatiProtocolloAnnullato pProtAnnullato = null;

            try
            {
                if (string.IsNullOrEmpty(idProtocollo) && string.IsNullOrEmpty(annoProtocollo) && string.IsNullOrEmpty(numeroProtocollo))
                {
                    pProtAnnullato = new DatiProtocolloAnnullato();
                    pProtAnnullato.Annullato = EnumAnnullato.warning;
                    pProtAnnullato.NoteAnnullamento = "L'id, il numero e la data del protocollo sono vuoti!";

                    if (_protocolloLogs.IsDebugEnabled)
                        _protocolloSerializer.SerializeAndValidateStream(pProtAnnullato);

                    return pProtAnnullato;
                }
                else
                {
                    if (string.IsNullOrEmpty(idProtocollo))
                    {
                        if (string.IsNullOrEmpty(annoProtocollo))
                        {
                            pProtAnnullato = new DatiProtocolloAnnullato();
                            pProtAnnullato.Annullato = EnumAnnullato.warning;
                            pProtAnnullato.NoteAnnullamento = "La data del protocollo è vuota!";

                            if (_protocolloLogs.IsDebugEnabled)
                                _protocolloSerializer.SerializeAndValidateStream(pProtAnnullato);

                            return pProtAnnullato;
                        }
                        if (string.IsNullOrEmpty(numeroProtocollo))
                        {
                            pProtAnnullato = new DatiProtocolloAnnullato();
                            pProtAnnullato.Annullato = EnumAnnullato.warning;
                            pProtAnnullato.NoteAnnullamento = "Il numero del protocollo è vuoto!";

                            if (_protocolloLogs.IsDebugEnabled)
                                _protocolloSerializer.SerializeAndValidateStream(pProtAnnullato);

                            return pProtAnnullato;
                        }
                    }
                }
                //Per ricavare informazioni utili per l'annullamento del protocollo
                SetAnnullaProtocollo();
                pProtAnnullato = _protocolloBase.IsAnnullato(idProtocollo, annoProtocollo, numeroProtocollo);

                if (pProtAnnullato != null)
                {
                    if (_protocolloLogs.IsDebugEnabled)
                        _protocolloSerializer.SerializeAndValidateStream(pProtAnnullato);
                }
            }
            catch (Exception ex)
            {
                pProtAnnullato = new DatiProtocolloAnnullato();
                pProtAnnullato.Annullato = EnumAnnullato.warning;
                pProtAnnullato.NoteAnnullamento = String.Format("Verifica nullabilità terminata con errore: {0}", ex.Message);

                if (_protocolloLogs.IsDebugEnabled)
                    _protocolloSerializer.SerializeAndValidateStream(pProtAnnullato);

                return pProtAnnullato;
            }

            return pProtAnnullato;
        }

        public ListaMotiviAnnullamento ListaMotivoAnnullamento()
        {
            SetAnnullaProtocollo();

            var listMotivoAnn = _protocolloBase.GetMotivoAnnullamento();

            if (listMotivoAnn != null && _protocolloLogs.IsDebugEnabled)
                _protocolloSerializer.SerializeAndValidateStream(listMotivoAnn, "ListaMotiviAnnullamento.xml");

            return listMotivoAnn;
        }

        private void SetEFascicolato()
        {
            _protocolloBase.Operatore = _verticalizzazioneProtocolloAttivo.Operatore;

            var ammMgr = new AmministrazioniMgr(this._datiProtocollazione.Db);
            var amm = ammMgr.GetByIdProtocollo(this._datiProtocollazione.IdComune, Convert.ToInt32(_verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault), this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

            if (amm == null)
                throw new ProtocolloException(String.Format("IL CODICEAMMINISTRAZIONE {0} PER L'IDCOMUNE {1} NON È PRESENTE NELLA TABELLA AMMINISTRAZIONI OPPURE NON È STATO SETTATO NELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO", _verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault, this._datiProtocollazione.IdComune));

            _protocolloBase.Ruolo = string.IsNullOrEmpty(amm.PROT_RUOLO) ? amm.PROT_UO : amm.PROT_RUOLO;
        }

        private bool PreparaLaRichiestaPerCreazioneOCambiamentoFascicolo(DatiFasc datiFasc, ProtocolloEnum.TipiFascicolazione tipoFasc = ProtocolloEnum.TipiFascicolazione.CAMBIA)
        {
            _protocolloBase.Operatore = _verticalizzazioneProtocolloAttivo.Operatore;

            if (datiFasc != null)
            {
                switch (tipoFasc)
                {
                    case ProtocolloEnum.TipiFascicolazione.CREA:
                        if (_datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO)
                        {
                            if (!String.IsNullOrEmpty(datiFasc.DataFascicolo))
                                _fascicolo.DataFascicolo = datiFasc.DataFascicolo;
                            else
                                _fascicolo.DataFascicolo = DateTime.Now.ToString("dd/MM/yyyy");

                            _fascicolo.AnnoFascicolo = DateTime.ParseExact(_fascicolo.DataFascicolo, "dd/MM/yyyy", null).Year;

                            if (!String.IsNullOrEmpty(datiFasc.NumeroFascicolo))
                                _fascicolo.NumeroFascicolo = datiFasc.NumeroFascicolo;

                            if (String.IsNullOrEmpty(datiFasc.OggettoFascicolo))
                                throw new Exception("OGGETTO NON VALORIZZATO");

                            _fascicolo.Oggetto = datiFasc.OggettoFascicolo;
                            _fascicolo.Classifica = datiFasc.ClassificaFascicolo;

                            if (String.IsNullOrEmpty(datiFasc.ClassificaFascicolo))
                                _fascicolo.Classifica = _verticalizzazioneProtocolloAttivo.ClassificaFascDefaultBo;

                            if (String.IsNullOrEmpty(datiFasc.NumeroFascicolo) && String.IsNullOrEmpty(_fascicolo.Classifica))
                                throw new Exception("NON E' STATA VALORIZZATA LA CLASSIFICA DEL FASCICOLO");
                        }

                        if (_datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                        {
                            if (!String.IsNullOrEmpty(datiFasc.DataFascicolo))
                                _fascicolo.DataFascicolo = datiFasc.DataFascicolo;
                            else
                                _fascicolo.DataFascicolo = DateTime.Now.ToString("dd/MM/yyyy");

                            _fascicolo.AnnoFascicolo = DateTime.ParseExact(_fascicolo.DataFascicolo, "dd/MM/yyyy", null).Year;

                            if (!String.IsNullOrEmpty(datiFasc.NumeroFascicolo))
                                _fascicolo.NumeroFascicolo = datiFasc.NumeroFascicolo;

                            if (String.IsNullOrEmpty(datiFasc.OggettoFascicolo))
                            {
                                IResolveMailTipoService mailTipoSrv = ResolveMailTipoFactory.Create(this._datiProtocollazione, _protocolloLogs, _protocolloSerializer);
                                _fascicolo.Oggetto = mailTipoSrv.Oggetto;

                                _protocolloLogs.DebugFormat("Oggetto restituito: {0}", _fascicolo.Oggetto);
                            }
                            else
                                _fascicolo.Oggetto = datiFasc.OggettoFascicolo;

                            if (!String.IsNullOrEmpty(datiFasc.ClassificaFascicolo))
                                _fascicolo.Classifica = datiFasc.ClassificaFascicolo;
                            else
                            {
                                var alberoMgr = new AlberoProcMgr(this._datiProtocollazione.Db);
                                _fascicolo.Classifica = alberoMgr.GetClassificaFascicoloFromAlberoProcProtocollo(_datiProtocollazione.CodiceInterventoProc.Value, _datiProtocollazione.IdComune, _datiProtocollazione.Software, _datiProtocollazione.CodiceComune);

                                if (String.IsNullOrEmpty(_fascicolo.Classifica))
                                    _fascicolo.Classifica = _verticalizzazioneProtocolloAttivo.ClassificaFascDefaultBo;
                            }

                            if (String.IsNullOrEmpty(datiFasc.NumeroFascicolo) && String.IsNullOrEmpty(_fascicolo.Classifica))
                                throw new ProtocolloException("NON È STATA SETTATA LA CLASSIFICA NELL'ALBERO DEI PROCEDIMENTI");
                        }

                        if (_datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                        {
                            if (!String.IsNullOrEmpty(datiFasc.NumeroFascicolo))
                            {
                                _fascicolo.NumeroFascicolo = datiFasc.NumeroFascicolo;

                                if (!String.IsNullOrEmpty(datiFasc.DataFascicolo))
                                    _fascicolo.DataFascicolo = datiFasc.DataFascicolo;
                                else
                                    _fascicolo.DataFascicolo = DateTime.Now.ToString("dd/MM/yyyy");

                                _fascicolo.AnnoFascicolo = !String.IsNullOrEmpty(datiFasc.AnnoFascicolo) ? Convert.ToInt32(datiFasc.AnnoFascicolo) : DateTime.ParseExact(_fascicolo.DataFascicolo, "dd/MM/yyyy", null).Year;
                                _fascicolo.Classifica = datiFasc.ClassificaFascicolo;
                                _fascicolo.Oggetto = datiFasc.OggettoFascicolo;

                            }
                            else
                            {
                                var datiProtFasc = IsFascicolato(_datiProtocollazione.Istanza.FKIDPROTOCOLLO, _datiProtocollazione.Istanza.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), _datiProtocollazione.Istanza.NUMEROPROTOCOLLO);
                                _fascicolo.NumeroFascicolo = datiProtFasc.NumeroFascicolo;
                                _fascicolo.DataFascicolo = datiProtFasc.DataFascicolo;
                                _fascicolo.AnnoFascicolo = string.IsNullOrEmpty(datiProtFasc.AnnoFascicolo) ? 0 : Convert.ToInt32(datiProtFasc.AnnoFascicolo);
                                _fascicolo.Classifica = datiProtFasc.Classifica;
                                _fascicolo.Oggetto = datiProtFasc.Oggetto;
                            }

                            if (String.IsNullOrEmpty(_fascicolo.NumeroFascicolo) || (_fascicolo.AnnoFascicolo == 0))
                            {
                                //throw new ProtocolloException("La pratica non è stata fascicolata!!");
                                //Per evitare di sollevare un'eccezione in corrispondenza di questo "finto" problema
                                return false;
                            }
                        }
                        break;
                    case ProtocolloEnum.TipiFascicolazione.CAMBIA:
                        if (!string.IsNullOrEmpty(datiFasc.DataFascicolo))
                            _fascicolo.DataFascicolo = datiFasc.DataFascicolo;
                        else
                            _fascicolo.DataFascicolo = DateTime.Now.ToString("dd/MM/yyyy");

                        _fascicolo.AnnoFascicolo = DateTime.ParseExact(_fascicolo.DataFascicolo, "dd/MM/yyyy", null).Year;

                        if (String.IsNullOrEmpty(datiFasc.NumeroFascicolo))
                            throw new ProtocolloException("Non è stata settato il numero di fascicolo");

                        _fascicolo.NumeroFascicolo = datiFasc.NumeroFascicolo;
                        break;
                }


                var ammMgr = new AmministrazioniMgr(this._datiProtocollazione.Db);
                Amministrazioni amm = null;

                if(_datiProtocollazione.TipoAmbito != ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO)
                    amm = ammMgr.GetFromAlberoProcProtocollo(this._datiProtocollazione.CodiceInterventoProc.Value, this._datiProtocollazione.IdComune, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);
            
                if (amm == null)
                    amm = ammMgr.GetByIdProtocollo(this._datiProtocollazione.IdComune, Convert.ToInt32(_verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault), this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                if (amm == null)
                    throw new Exception("AMMINISTRAZIONE NON TROVATA");

                _protocolloBase.Ruolo = String.IsNullOrEmpty(amm.PROT_RUOLO) ? amm.PROT_UO : amm.PROT_RUOLO;
            }

            return true;
        }

        private void SetAnnullaProtocollo()
        {
            _protocolloBase.Operatore = _verticalizzazioneProtocolloAttivo.Operatore;
        }

        public void InvioPec()
        {
            _protocolloBase.InvioPec(_datiProtocollazione.Movimento.FKIDPROTOCOLLO, _datiProtocollazione.Movimento.NUMEROPROTOCOLLO, _datiProtocollazione.Movimento.DATAPROTOCOLLO.Value.Year.ToString());
        }

        public DatiProtocolloRes CreaCopie(string codiceAmministrazione)
        {
            DatiProtocolloRes datiProtocollo = null;
            if (String.IsNullOrEmpty(_datiProtocollazione.Istanza.NUMEROPROTOCOLLO) || !_datiProtocollazione.Istanza.DATAPROTOCOLLO.HasValue)
                throw new Exception("IL NUMERO O LA DATA PROTOCOLLO NON E' VALORIZZATO");

            _protocolloLogs.DebugFormat("Valorizzazione dati tramite la chiamata a SetCreaCopie");
            SetCreaCopie(codiceAmministrazione);
            _protocolloLogs.DebugFormat("Fine valorizzazione dati tramite la chiamata a SetCreaCopie");

            _protocolloLogs.DebugFormat("Chiamata a CreaCopie da ProtocolloMgr");
            datiProtocollo = _protocolloBase.CreaCopie();
            _protocolloLogs.DebugFormat("Fine chiamata a CreaCopie da ProtocolloMgr");

            if (datiProtocollo != null)
            {
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.CreaCopieReturnFileName, datiProtocollo);
                var istanzeMgr = new IstanzeMgr(this._datiProtocollazione.Db);
                _datiProtocollazione.Istanza.FKIDPROTOCOLLO = String.IsNullOrEmpty(datiProtocollo.IdProtocollo) ? null : datiProtocollo.IdProtocollo;

                if (!String.IsNullOrEmpty(datiProtocollo.NumeroProtocollo) && datiProtocollo.NumeroProtocollo != "0")
                {
                    _datiProtocollazione.Istanza.NUMEROPROTOCOLLO = datiProtocollo.NumeroProtocollo;
                    _datiProtocollazione.Istanza.DATAPROTOCOLLO = DateTime.ParseExact(datiProtocollo.DataProtocollo, "dd/MM/yyyy", null);
                }

                istanzeMgr.UpdateDatiProtocollo(_datiProtocollazione.Istanza.FKIDPROTOCOLLO, _datiProtocollazione.Istanza.NUMEROPROTOCOLLO, _datiProtocollazione.Istanza.DATAPROTOCOLLO.Value, _datiProtocollazione.IdComune, Convert.ToInt32(_datiProtocollazione.CodiceIstanza));

                var movMgr = new MovimentiMgr(this._datiProtocollazione.Db);
                var mov = new Movimenti();
                mov.IDCOMUNE = this._datiProtocollazione.IdComune;
                mov.TIPOMOVIMENTO = _datiProtocollazione.Istanza.TIPOMOVAVVIO;
                mov.CODICEISTANZA = _datiProtocollazione.CodiceIstanza;
                mov = movMgr.GetByClass(mov);

                if (mov != null)
                {
                    //Prima con il protocollo Sigepro non veniva aggiornato il campo FKIDPROTOCOLLO per il movimento di avvio
                    mov.FKIDPROTOCOLLO = string.IsNullOrEmpty(datiProtocollo.IdProtocollo) ? null : datiProtocollo.IdProtocollo;
                    if (!String.IsNullOrEmpty(datiProtocollo.NumeroProtocollo) && datiProtocollo.NumeroProtocollo != "0")
                    {
                        mov.NUMEROPROTOCOLLO = datiProtocollo.NumeroProtocollo;
                        mov.DATAPROTOCOLLO = _datiProtocollazione.Istanza.DATAPROTOCOLLO;
                    }
                    movMgr.UpdateDatiProtocollo(mov.FKIDPROTOCOLLO, mov.NUMEROPROTOCOLLO, mov.DATAPROTOCOLLO.Value, mov.IDCOMUNE, Convert.ToInt32(mov.CODICEMOVIMENTO));
                }
            }

            return datiProtocollo;
        }

        private void SetCreaCopie(string codiceAmministrazione)
        {
            var ammMgr = new AmministrazioniMgr(this._datiProtocollazione.Db);
            Amministrazioni amm = null;

            if (String.IsNullOrEmpty(codiceAmministrazione))
            {
                amm = ammMgr.GetFromAlberoProcProtocollo(this._datiProtocollazione.CodiceInterventoProc.Value, this._datiProtocollazione.IdComune, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                if (amm == null)
                    codiceAmministrazione = _verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault;
            }

            if (amm == null)
                amm = ammMgr.GetByIdProtocollo(this._datiProtocollazione.IdComune, Convert.ToInt32(codiceAmministrazione), this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

            if (amm == null)
                throw new Exception("AMMINISTRAZIONE NON TROVATA");

            _protocolloBase.Operatore = _verticalizzazioneProtocolloAttivo.Operatore;
            _protocolloBase.Ruolo = amm.PROT_RUOLO;
            _protocolloBase.CodAmministrazione = amm.CODICEAMMINISTRAZIONE;

        }

        public DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            if (String.IsNullOrEmpty(idProtocollo) && String.IsNullOrEmpty(annoProtocollo) && String.IsNullOrEmpty(numeroProtocollo))
                throw new Exception("L'ID, IL NUMERO E LA DATA DEL PROTOCOLLO SONO VUOTI!");
            else
            {
                if (string.IsNullOrEmpty(idProtocollo))
                {
                    if (String.IsNullOrEmpty(annoProtocollo))
                        throw new Exception("LA DATA DEL PROTOCOLLO È VUOTA!");
                    if (String.IsNullOrEmpty(numeroProtocollo))
                        throw new Exception("IL NUMERO DEL PROTOCOLLO È VUOTO!");
                }
            }

            SetLettura();
            var protoLetto = _protocolloBase.LeggiProtocollo(idProtocollo, annoProtocollo, numeroProtocollo);
            if (protoLetto != null)
            {
                _protocolloBase.CheckProtocolloLetto(annoProtocollo, numeroProtocollo, idProtocollo, protoLetto);
                SetAllegatiWsReturn(protoLetto);
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.ResponseFileName, protoLetto);
            }

            return protoLetto;
        }

        /// <summary>
        /// Setta l'id dell'allegato con il formato IDPROTOCOLLO|NUMERO|ANNO|IDALLEGATO e elimina dal tag Image dell'Xml il file in formato binario, vengono letti successivamente con il metodo LeggiAllegato
        /// </summary>
        /// <param name="allout"></param>
        private void SetAllegatiWsReturn(DatiProtocolloLetto datiProtLetto)
        {
            AllOut[] allout = datiProtLetto.Allegati;
            if (allout != null)
            {
                for (int i = 0; i < allout.Length; i++)
                {

                    string idprotocollo = String.IsNullOrEmpty(datiProtLetto.IdProtocollo) ? "0" : datiProtLetto.IdProtocollo;
                    string numeroProtocollo = datiProtLetto.NumeroProtocollo;
                    string annoProtocollo = datiProtLetto.AnnoProtocollo;
                    string idAllegato = allout[i].IDBase;
                    allout[i].IDBase = idprotocollo + "|" + numeroProtocollo + "|" + annoProtocollo + "|" + idAllegato + "|" + _datiProtocollazione.Software;
                    allout[i].Image = null;
                }
            }
        }

        public AllOut LeggiAllegato(string IdProtocollo, string numProtocollo, string annoProtocollo, string idAllegato)
        {
            _protocolloBase.IdProtocollo = IdProtocollo;
            _protocolloBase.NumProtocollo = numProtocollo;
            _protocolloBase.AnnoProtocollo = annoProtocollo;
            _protocolloBase.IdAllegato = idAllegato;

            SetLettura();

            _protocolloLogs.DebugFormat("Lettura dell'allegato, ID Protocollo: {0}, Numero Protocollo: {1}, Anno Protocollo: {2}, Id Allegato: {3}", IdProtocollo, numProtocollo, annoProtocollo, idAllegato);
            AllOut res = _protocolloBase.LeggiAllegato();

            return res;
        }

        private void SetLettura()
        {
            _protocolloBase.Operatore = _verticalizzazioneProtocolloAttivo.Operatore;

            var ammMgr = new AmministrazioniMgr(this._datiProtocollazione.Db);
            var amm = ammMgr.GetByIdProtocollo(this._datiProtocollazione.IdComune, Convert.ToInt32(_verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault), this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

            if (amm == null)
                throw new Exception(String.Format("IL CODICEAMMINISTRAZIONE {0} PER L'IDCOMUNE {1} NON È PRESENTE NELLA TABELLA AMMINISTRAZIONI OPPURE NON È STATO SETTATO NELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO", _verticalizzazioneProtocolloAttivo.Codiceamministrazionedefault, this._datiProtocollazione.IdComune));

            _protocolloBase.Ruolo = String.IsNullOrEmpty(amm.PROT_RUOLO) ? amm.PROT_UO : amm.PROT_RUOLO;
        }

        public ListaTipiDocumento ListaTipiDocumento()
        {
            var listaTipiDocumento = _protocolloBase.GetTipiDocumento();

            if (listaTipiDocumento != null && _protocolloLogs.IsDebugEnabled)
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.TipiDocumentoSoapResponseFileName, listaTipiDocumento);

            return listaTipiDocumento;
        }

        #region Metodo usato per ottenere la lista delle classifiche
        public ListaTipiClassifica ListaClassifiche()
        {
            try
            {
                SetListaClassifiche();

                var response = _protocolloBase.GetClassifiche();

                if (response != null)
                {
                    if (_protocolloLogs.IsDebugEnabled)
                        _protocolloSerializer.Serialize(ProtocolloLogsConstants.ResponseFileName, response);
                }

                return response;
            }
            catch (Exception)
            {
                throw;
            }
        }

        private void SetListaClassifiche()
        {
            _protocolloBase.Operatore = _verticalizzazioneProtocolloAttivo != null ? _verticalizzazioneProtocolloAttivo.Operatore : String.Empty;
        }
        #endregion

        public DatiProtocolloRes MettiAllaFirma(Dati dati)
        {
            _dati = dati;

            if (!String.IsNullOrEmpty(_datiProtocollazione.Movimento.FKIDPROTOCOLLO))
                throw new Exception("ID PROTOCOLLO VALORIZZATO");

            //Deserializzo il file xml che ricevo in ingresso all'interno dell'oggetto Dati
            if (_dati != null)
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.RequestFileName, _dati);
            else
                _dati = new Dati();

            _protoIn = new DatiProtocolloIn();
            SetMettiAllaFirma();
            var response = _protocolloBase.MettiAllaFirma(_protoIn);

            if (response != null)
            {
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.ResponseFileName, response);

                var movimentiMgr = new MovimentiMgr(this._datiProtocollazione.Db);
                _datiProtocollazione.Movimento.FKIDPROTOCOLLO = string.IsNullOrEmpty(response.IdProtocollo) ? null : response.IdProtocollo;
                movimentiMgr.Update(_datiProtocollazione.Movimento, ComportamentoElaborazioneEnum.NonElaborare);
            }

            return response;

        }

        public DatiProtocolloRes Protocollazione(ProtocolloEnum.TipoProvenienza provenienza, Dati dati, int iSource)
        {
            DatiProtocolloRes datiProtocollo = null;
            _dati = dati;

            if ((iSource != (int)ProtocolloEnum.Source.PROT_IST_MOV_AUT_BO) && (iSource != (int)ProtocolloEnum.Source.PROT_MOV_ONLINE))
            {
                var alberoMgr = new AlberoProcMgr(_datiProtocollazione.Db);
                bool isProtocollazioneAutomatica = alberoMgr.IsProtocollazioneAutomatica(_datiProtocollazione.CodiceInterventoProc.Value, iSource, _datiProtocollazione.IdComune, _datiProtocollazione.Software, _datiProtocollazione.CodiceComune);
                if (!isProtocollazioneAutomatica)
                {
                    _protocolloLogs.Info("LA PROTOCOLLAZIONE AUTOMATICA NON E' CONFIGURATA, VERIFICARE LA CONFIGURAZIONE DEI PARAMETRI DEL PROTOCOLLO SULLA GESTIONE DELL'ALBERO DEGLI INTERVENTI");
                    return new DatiProtocolloRes();
                }
            }

            if (_datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                if (!String.IsNullOrEmpty(_datiProtocollazione.Istanza.FKIDPROTOCOLLO) || !String.IsNullOrEmpty(_datiProtocollazione.Istanza.NUMEROPROTOCOLLO) || _datiProtocollazione.Istanza.DATAPROTOCOLLO.HasValue)
                    throw new Exception(String.Format("L'ISTANZA CODICE {0} RISULTA AVERE ALCUNI O TUTTI I DATI PROTOCOLLATI, FKIDPROTOCOLLO: {1}, NUMEROPROTOCOLLO: {2}, DATAPROTOCOLLO: {3}", _datiProtocollazione.CodiceIstanza, _datiProtocollazione.Istanza.FKIDPROTOCOLLO, _datiProtocollazione.Istanza.NUMEROPROTOCOLLO, _datiProtocollazione.Istanza.DATAPROTOCOLLO.HasValue ? _datiProtocollazione.Istanza.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy") : String.Empty));
            }

            if (_datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if (!String.IsNullOrEmpty(_datiProtocollazione.Movimento.FKIDPROTOCOLLO) || !String.IsNullOrEmpty(_datiProtocollazione.Movimento.NUMEROPROTOCOLLO) || _datiProtocollazione.Movimento.DATAPROTOCOLLO.HasValue)
                    throw new Exception(String.Format("IL MOVIMENTO CODICE {0} RISULTA AVERE ALCUNI O TUTTI I DATI PROTOCOLLATI, FKIDPROTOCOLLO: {1}, NUMEROPROTOCOLLO: {2}, DATAPROTOCOLLO: {3}", _datiProtocollazione.CodiceMovimento, _datiProtocollazione.Movimento.FKIDPROTOCOLLO, _datiProtocollazione.Movimento.NUMEROPROTOCOLLO, _datiProtocollazione.Movimento.DATAPROTOCOLLO.HasValue ? _datiProtocollazione.Movimento.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy") : String.Empty));
            }

            if (_dati == null)
                _dati = new Dati();

            _protocolloSerializer.Serialize(ProtocolloLogsConstants.RequestFileName, _dati);

            _protoIn = new DatiProtocolloIn();

            SetProtocollo(provenienza, iSource);

            if (_protocolloLogs.IsDebugEnabled)
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.DatiProtocolloInFileName, _protoIn);

            datiProtocollo = _protocolloBase.Protocollazione(_protoIn);
            _protocolloLogs.DebugFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO: Numero Protocollo: {0} | DataProtocollo: {1} | AnnoProtocollo: {2}", datiProtocollo.NumeroProtocollo, datiProtocollo.DataProtocollo, datiProtocollo.AnnoProtocollo);

            if (datiProtocollo != null)
            {
                _protocolloSerializer.SerializeAndValidateStream(datiProtocollo, ProtocolloLogsConstants.ResponseFileName);

                if (String.IsNullOrEmpty(datiProtocollo.DataProtocollo))
                    throw new Exception(String.Format("DATA PROTOCOLLO NON VALORIZZATA DOPO AVER EFFETTUATO LA PROTOCOLLAZIONE DELL'ISTANZA CODICE: {0}", _datiProtocollazione.CodiceIstanza));

                if (String.IsNullOrEmpty(datiProtocollo.NumeroProtocollo))
                    throw new Exception(String.Format("NUMERO PROTOCOLLO NON VALORIZZATO DOPO AVER EFFETTUATO LA PROTOCOLLAZIONE DELL'ISTANZA CODICE: {0}", _datiProtocollazione.CodiceIstanza));

                var numeroProtocollo = datiProtocollo.NumeroProtocollo;
                var dataProtocollo = DateTime.ParseExact(datiProtocollo.DataProtocollo, "dd/MM/yyyy", null);
                var idProtocollo = datiProtocollo.IdProtocollo;

                if (_datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                {
                    var istanzaMgr = new IstanzeMgr(this._datiProtocollazione.Db);
                    _protocolloLogs.DebugFormat("Prima dell'Update dell'istanza {0} con i dati del protocollo, id protocollo: {1}, data protocollo: {2}, numero protocollo: {3}", this._datiProtocollazione.CodiceIstanza, idProtocollo, dataProtocollo, numeroProtocollo);
                    istanzaMgr.UpdateDatiProtocollo(idProtocollo, numeroProtocollo, dataProtocollo, this._datiProtocollazione.IdComune, Convert.ToInt32(this._datiProtocollazione.CodiceIstanza));
                    _protocolloLogs.DebugFormat("Update dell'istanza {0} con i dati del protocollo, id protocollo: {1}, data protocollo: {2}, numero protocollo: {3}", this._datiProtocollazione.CodiceIstanza, idProtocollo, dataProtocollo, numeroProtocollo);
                    var mov = new MovimentiMgr(this._datiProtocollazione.Db).GetByClass(new Movimenti
                        {
                            IDCOMUNE = this._datiProtocollazione.IdComune,
                            TIPOMOVIMENTO = this._datiProtocollazione.Istanza.TIPOMOVAVVIO,
                            CODICEISTANZA = this._datiProtocollazione.CodiceIstanza
                        });

                    if (mov != null)
                    {
                        var movMgr = new MovimentiMgr(this._datiProtocollazione.Db);
                        _protocolloLogs.DebugFormat("Prima dell'update del movimento di avvio, codice movimento {0}", mov.CODICEMOVIMENTO);
                        movMgr.UpdateDatiProtocollo(idProtocollo, numeroProtocollo, dataProtocollo, mov.IDCOMUNE, Convert.ToInt32(mov.CODICEMOVIMENTO));
                        _protocolloLogs.DebugFormat("Update del movimento di avvio, codice movimento {0}", mov.CODICEMOVIMENTO);
                    }
                }
                else if (_datiProtocollazione.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                {
                    var movMgr = new MovimentiMgr(this._datiProtocollazione.Db);
                    _protocolloLogs.DebugFormat("Prima dell'update del movimento {0} con i dati del protocollo, idprotocollo: {1}, data protocollo: {2}, numero protocollo: {3}", this._datiProtocollazione.CodiceMovimento, idProtocollo, dataProtocollo, numeroProtocollo);
                    movMgr.UpdateDatiProtocollo(idProtocollo, numeroProtocollo, dataProtocollo, this._datiProtocollazione.IdComune, Convert.ToInt32(this._datiProtocollazione.CodiceMovimento));
                    _protocolloLogs.DebugFormat("Update del movimento {0} con i dati del protocollo, idprotocollo: {1}, data protocollo: {2}, numero protocollo: {3}", this._datiProtocollazione.CodiceMovimento, idProtocollo, dataProtocollo, numeroProtocollo);
                }
            }

            return datiProtocollo;
        }

        public DatiProtocolloRes Registrazione(string registro, Dati dati, ProtocolloEnum.TipoProvenienza provenienza = ProtocolloEnum.TipoProvenienza.BACKOFFICE, int iSource = (int)ProtocolloEnum.Source.PROT_IST_MOV_AUT_BO)
        {
            _dati = dati;

            if (_dati == null)
                _dati = new Dati();

            _protocolloSerializer.Serialize(ProtocolloLogsConstants.RequestFileName, _dati);

            _protoIn = new DatiProtocolloIn();
            SetProtocollo(provenienza, iSource);

            if (_protocolloLogs.IsDebugEnabled)
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.DatiProtocolloInFileName, _protoIn);

            return _protocolloBase.Registrazione(registro, _protoIn);
        }

        public CreaUnitaDocumentaleResponse CreaUnitaDocumentale(CreaUnitaDocumentaleRequest request)
        {
            var protoAllegatiMgr = new ProtocolloAllegatiMgr(this._datiProtocollazione.Db);
            var protocolloAllegati = request.Allegati.Select(x => x.ToProtocolloAllegati(protoAllegatiMgr, _datiProtocollazione.IdComune, _datiProtocollazione.CodiceComune, _verticalizzazioneProtocolloAttivo));

            return _protocolloBase.CreaUnitaDocumentale(request.TipoDocumento, protocolloAllegati);
        }

        private bool GetParamVertBool(string sParamVertValue, string sParamVertName)
        {
            switch (sParamVertValue)
            {
                case "":
                case "0":
                    return false;
                case "1":
                    return true;
                default:
                    throw new Exception("Il valore del parametro " + sParamVertName + " non è corretto! Valori ammissibili 0 ed 1, valore settato: " + sParamVertValue);
            }
        }

        public void Dispose()
        {
            if (this._datiProtocollazione.Db == null)
                throw new Exception("ERRORE DURANTE IL DISPOSE DEL MANAGER, IL DATABASE NON E' STATO GENERATO");

            this._datiProtocollazione.Db.Dispose();
            this._datiProtocollazione.Db = null;
        }
    }
}