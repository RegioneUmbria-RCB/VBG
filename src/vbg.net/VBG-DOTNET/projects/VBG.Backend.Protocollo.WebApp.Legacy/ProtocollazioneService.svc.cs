using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using log4net;
using Ninject;
using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
//using System.ServiceModel.Activation;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers.Conversioni;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;
using VBG.Backend.Protocollo.WebApp.Legacy.Interfaces;

namespace VBG.Backend.Protocollo.WebApp.Legacy
{
    //[AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class ProtocollazioneService : IProtocollazioneService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ProtocollazioneService));

        [Inject]
        public IVerticalizzazioniFactory _verticalizzazioniFactory { get; set; }

        [Inject]
        public ITransientAuthenticationInfoResolver _transientAuthenticationInfoResolver { get; set; }

        [Inject]
        public IBindingFactory _bindingFactory { get; set; }

        private AuthenticationInfo CheckToken(string token)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);

            this._transientAuthenticationInfoResolver.SetTransientAuthInfo(authInfo);

            return authInfo;
        }

        private IProtocolloMgr CreaProtocolloManager(AuthenticationInfo authInfo, string software, string codiceComune = "", AmbitoProtocollazioneEnum ambito = AmbitoProtocollazioneEnum.NESSUNO, Istanze istanza = null, Movimenti movimento = null, PecInbox datiPec = null)
        {
            var supportoService = new StatoSupportoProtocolliService(this._verticalizzazioniFactory, this._bindingFactory);
            var statoSupporto = supportoService.GetStatoSupportoProtocolli(authInfo.Alias, software, codiceComune);

            IProtocolloStorico protocolloStorico = ProtocolloStoricoNonAttivo.Instance;

            if (statoSupporto.ProtocolloStoricoSupportato == StatoSupportoProtocolliService.StatoSupportoProtocollazioneStoricaEnum.Supportato)
            {
                var storicoDefault = new ProtocolloStoricoDefault(this._verticalizzazioniFactory, this._bindingFactory);
                storicoDefault.Initialize(authInfo, software, codiceComune, ambito, istanza, movimento, datiPec);

                protocolloStorico = storicoDefault;
            }

            var mgr = new ProtocolloMgr(this._verticalizzazioniFactory, protocolloStorico, this._bindingFactory);

            mgr.Initialize(authInfo, software, codiceComune, ambito, istanza, movimento, datiPec);

            return mgr;
        }

        /// <summary>
        /// Metodo usato per creare una copia di un protocollo
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="codiceAmministrazione"></param>
        /// <returns></returns>
        public DatiProtocolloResponseType CreaCopie(string token, string codiceIstanza, string codiceAmministrazione)
        {
            this._log.DebugFormat("Avvio creazione copia, metodo CreaCopie, token: {0}, codice istanza: {1}, codice amministrazione: {2}", token, codiceIstanza, codiceAmministrazione);

            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune, AmbitoProtocollazioneEnum.DA_ISTANZA, istanza))
                {
                    return mgr.CreaCopie(codiceAmministrazione);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE IL CREA COPIE, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per mettere alla firma un documento dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <param name="file"></param>
        /// <returns></returns>
        public DatiProtocolloResponseType MettiAllaFirmaXml(string token, string codiceMovimento, DatiRequestType file)
        {
            this._log.DebugFormat("Avvio della messa alla firma, metodo MettiAllaFirmaXml, token: {0}, Codice Movimento: {1}", token, codiceMovimento);

            try
            {
                var authInfo = this.CheckToken(token);
                var db = authInfo.CreateDatabase();
                var movimento = this.GetMovimento(authInfo, db, codiceMovimento);
                var istanza = this.GetIstanzaFromMovimento(authInfo, db, codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune, AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento))
                {
                    return mgr.MettiAllaFirma(file);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA MESSA ALLA FIRMA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare una protocollazione generica
        /// </summary>
        /// <param name="token"></param>
        /// <param name="software"></param>
        /// <param name="file"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public DatiProtocolloResponseType ProtocollazioneXml(string token, string software, DatiRequestType file, string codiceComune)
        {
            this._log.DebugFormat("Avvio protocollazione generica (senza codice istanza e movimento), metodo ProtocollazioneXml, token: {0}, software: {1}, codice comune: {2}", token, software, codiceComune);
            try
            {
                return this.Protocollazione(token, software, codiceComune, (int)Source.PROT_IST_MOV_AUT_BO, AmbitoProtocollazioneEnum.NESSUNO, null, null, file);
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE GENERICA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la protocollazione di una istanza senza passare dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="source"></param>
        /// <returns></returns>
        public DatiProtocolloResponseType ProtocollazioneIstanza(string token, string codiceIstanza, int source, DatiMittentiType mittenti)
        {
            this._log.DebugFormat("Avvio protocollazione istanza automatica (senza passare dalla maschera), metodo ProtocollazioneIstanza, token: {0}, codice istanza: {1}, source: {2}", token, codiceIstanza, source);

            AuthenticationInfo authInfo = null;
            Istanze istanza = null;
            string software = null;
            string codiceComune = null;

            try
            {
                authInfo = this.CheckToken(token);
                istanza = this.GetIstanza(authInfo, codiceIstanza);
                software = this.GetSoftware(istanza);
                codiceComune = this.GetCodiceComune(istanza);
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE IL RECUPERO DEI DATI PER LA PROTOCOLLAZIONE ISTANZA AUTOMATICA (SENZA PASSARE DALLA MASCHERA), Codice Istanza: {0}, {1}";
                var errore = String.Format(fmtMsg, codiceIstanza, ex.Message);
                this._log.Error(errore, ex);
                return new DatiProtocolloResponseType(errore, ex.ToString());
            }

            try
            {
                if (this.DocumentiIstanzaSenzaCodiceOggetto(authInfo, istanza))
                {
                    throw new Exception("Sono presenti documenti dell'istanza senza avere fisicamente il documento ed è stato impostato nei parametri di non procedere alla protocollazione");
                }

                using (var db = authInfo.CreateDatabase())
                {
                    var ambito = AmbitoProtocollazioneEnum.DA_ISTANZA;

                    var repository = new ProtocollazioneRepository(db, authInfo.IdComune, software, codiceComune, this._log);
                    var converter = new DatiMittentiTypeToDatiMittentiXmlType(repository);
                    var mittentiConvertiti = converter.Converti(ambito, mittenti, istanza, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(authInfo.Alias, software, codiceComune));

                    var dati = new DatiRequestType
                    {
                        Mittenti = mittentiConvertiti
                    }; ;

                    return this.Protocollazione(token, software, codiceComune, source, AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null, dati);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE ISTANZA AUTOMATICA (SENZA PASSARE DALLA MASCHERA), Codice Istanza: {0}, Software: {1}, CodiceComune: {2}, {3}";
                var errore = String.Format(fmtMsg, codiceIstanza, software, codiceComune, ex.Message);
                this._log.Error(errore, ex);
                return new DatiProtocolloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la protocollazione di una istanza dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="file"></param>
        /// <returns></returns>
        public DatiProtocolloResponseType ProtocollazioneIstanzaXml(string token, string codiceIstanza, DatiRequestType file)
        {
            this._log.DebugFormat("Avvio protocollazione istanza dalla maschera, metodo ProtocollazioneIstanzaXml, token: {0}, codice istanza: {1}", token, codiceIstanza);
            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return this.Protocollazione(token, software, codiceComune, (int)Source.PROT_IST_MOV_AUT_BO, AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null, file);
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE ISTANZA DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());

                return new DatiProtocolloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo utilizzato per la protocollazione da pannello pec
        /// </summary>
        /// <param name="token">Token applicativo</param>
        /// <param name="codicePec">Identificativo della pec sulla tabella PEC_INBOX, campo ID</param>
        /// <param name="file">Dati da valorizzare in base alla compilazione del form dall'interfaccia di protocollazione della pec</param>
        /// <returns></returns>
        public DatiProtocolloResponseType ProtocollazionePecXml(string token, string codicePec, DatiRequestType file)
        {
            this._log.DebugFormat("Avvio protocollazione pec dalla maschera, metodo ProtocollazionePecXml, token: {0}, codice istanza: {1}", token, codicePec);
            try
            {
                var authInfo = this.CheckToken(token);
                var pecInbox = this.GetPecInbox(authInfo, codicePec);

                return this.Protocollazione(token, pecInbox.SoftwareProt, pecInbox.CodiceComuneProt, (int)Source.PROT_IST_MOV_AUT_BO, AmbitoProtocollazioneEnum.DA_PANNELLO_PEC, null, null, file, pecInbox);
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE PEC DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());

                return new DatiProtocolloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la protocollazione di un movimento senza passare dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <returns></returns>
        public DatiProtocolloResponseType ProtocollazioneMovimento(string token, string codiceMovimento, DatiMittentiType mittenti = null)
        {
            this._log.DebugFormat("Avvio protocollazione movimento automatica (senza passare dalla maschera), metodo ProtocollazioneMovimento, token: {0}, codice movimento: {1}", token, codiceMovimento);
            try
            {
                var authInfo = this.CheckToken(token);
                using (var db = authInfo.CreateDatabase())
                {
                    var dati = new CaricamentoIstanzaEMovimentoService(db, authInfo.IdComune).GetMovimentoEIstanzaDaIdMovimento(codiceMovimento);

                    var ambito = AmbitoProtocollazioneEnum.DA_MOVIMENTO;

                    var repository = new ProtocollazioneRepository(db, authInfo.IdComune, dati.Software, dati.CodiceComune, this._log);
                    var converter = new DatiMittentiTypeToDatiMittentiXmlType(repository);
                    var mittentiConvertiti = converter.Converti(ambito, mittenti, dati.Istanza, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(authInfo.Alias, dati.Software, dati.CodiceComune));

                    var request = new DatiRequestType { Mittenti = mittentiConvertiti };

                    return this.Protocollazione(token, dati.Software, dati.CodiceComune, (int)Source.ON_LINE, AmbitoProtocollazioneEnum.DA_MOVIMENTO, dati.Istanza, dati.Movimento, request);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE DI UN MOVIMENTO SENZA PASSARE DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo utilizzato per protocollare le graduatorie tramite un movimento, la protocollazione sarà sempre in partenza.
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <returns></returns>
        public DatiProtocolloResponseType ProtocollazioneComunicazioneGraduatoria(string token, string codiceMovimento)
        {
            this._log.DebugFormat("Avvio protocollazione movimento automatica (senza passare dalla maschera), metodo ProtocollazioneMovimento, token: {0}, codice movimento: {1}", token, codiceMovimento);
            try
            {
                var authInfo = this.CheckToken(token);
                var db = authInfo.CreateDatabase();
                var istanza = this.GetIstanzaFromMovimento(authInfo, db, codiceMovimento);
                var movimento = this.GetMovimento(authInfo, db, codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                var dati = new DatiRequestType { Flusso = ProtocolloConstants.COD_PARTENZA };

                var retVal = this.Protocollazione(token, software, codiceComune, (int)Source.PROT_IST_MOV_AUT_BO, AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento, dati);

                try
                {
                    this.Fascicolazione(token, software, codiceComune, (int)SourceFascicolazione.FASC_IST_MOV_AUT_BO, AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento, null);
                }
                catch (Exception exFasc)
                {
                    this._log.WarnFormat(exFasc.Message);
                }

                return retVal;
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE DI UNA COMUNICAZIONE, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la protocollazione di un movimento dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <param name="file"></param>
        /// <returns></returns>
        public DatiProtocolloResponseType ProtocollazioneMovimentoXml(ProtocollazioneMovimentoXmlRequestType request)
        {
            this._log.DebugFormat("Avvio protocollazione di un movimento dalla maschera, metodo ProtocollazioneMovimentoXml, Token: {0}, Codice Movimento: {1}", request.Token, request.CodiceMovimento);
            try
            {
                var authInfo = this.CheckToken(request.Token);
                var db = authInfo.CreateDatabase();
                var istanza = this.GetIstanzaFromMovimento(authInfo, db, request.CodiceMovimento);
                var movimento = this.GetMovimento(authInfo, db, request.CodiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return this.Protocollazione(request.Token, software, codiceComune, request.Source, AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento, request.Dati);
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE DI UN MOVIMENTO DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per ottenere la lista dei tipi documento
        /// </summary>
        /// <param name="token"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public ListaTipiDocumentoResponseType GetTipiDocumento(string token, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio recupero Tipi Documento, metodo GetTipiDocumento Token: {0}, Software: {1}, Codice Comune: {2}", token, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    return mgr.ListaTipiDocumento();
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE IL RECUPERO DEI TIPI DOCUMENTO, {0}";
                //var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new ListaTipiDocumentoResponseType(String.Format(fmtMsg, ex.Message), ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per ottenere la lista delle classifiche
        /// </summary>
        /// <param name="token"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public ListaTipiClassificaType GetClassifiche(string token, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio recupero classifiche, metodo GetClassifiche, Token: {0}, Software: {1}, Codice Comune: {2}", token, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    return mgr.ListaClassifiche();
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE IL RECUPERO DELLE CLASSIFICHE, {0}";
                // var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new ListaTipiClassificaType(String.Format(fmtMsg, ex.Message), ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per rileggere un documento protocollato
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idProtocollo"></param>
        /// <param name="annoProtocollo"></param>
        /// <param name="numProtocollo"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            this._log.DebugFormat("Avvio lettura protocollo, metodo LeggiProtocollo, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2}, Numero Protocollo: {3}, Software: {4}, Codice Comune: {5}",
                leggiProtocolloRequest.Token,
                leggiProtocolloRequest.IdProtocollo,
                leggiProtocolloRequest.AnnoProtocollo,
                leggiProtocolloRequest.NumeroProtocollo,
                leggiProtocolloRequest.Software,
                leggiProtocolloRequest.CodiceComune);

            try
            {
                var authInfo = this.CheckToken(leggiProtocolloRequest.Token);

                using (var mgr = this.CreaProtocolloManager(authInfo, leggiProtocolloRequest.Software, leggiProtocolloRequest.CodiceComune))
                {
                    return mgr.LeggiProtocollo(leggiProtocolloRequest);
                }
            }
            catch (InvalidTokenException tokenEx)
            {
                return new List<DatiProtocolloLettoResponseType>() { new DatiProtocolloLettoResponseType(tokenEx) };
            }
            catch (Exception ex1)
            {
                this._log.Error("Errore in LeggiProtocollo", ex1);
                //verifico se l'anno coincide con l'anno del protocollo storico ( se attivato ), a quel punto tento una prima lettura nel 
                //protocollo storico e se non trovato sollevo l'eccezione
                if (string.IsNullOrEmpty(leggiProtocolloRequest.AnnoProtocollo))
                {
                    return new List<DatiProtocolloLettoResponseType>() { new DatiProtocolloLettoResponseType(ex1) };
                }

                try
                {
                    var dataProtocollo = new DateTime(Convert.ToInt32(leggiProtocolloRequest.AnnoProtocollo), 1, 1);

                    return this.LeggiProtocolloConData(leggiProtocolloRequest.Token, leggiProtocolloRequest.IdProtocollo, dataProtocollo, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.Software, leggiProtocolloRequest.CodiceComune);
                }
                catch (Exception ex2)
                {
                    const string fmtMsg = "ERRORE GENERATO DURANTE LeggiProtocollo, {0}";
                    //var errore = String.Format(fmtMsg, ex2.Message);
                    this._log.ErrorFormat(fmtMsg, ex2.ToString());
                    return new List<DatiProtocolloLettoResponseType>() { new DatiProtocolloLettoResponseType(String.Format(fmtMsg, ex2.Message), ex2.ToString()) };
                }
            }
        }

        /// <summary>
        /// Metodo usato per recuperare le informazioni di un protocollo
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idProtocollo"></param>
        /// <param name="annoProtocollo"></param>
        /// <param name="numProtocollo"></param>
        /// <param name="uo"></param>
        /// <param name="ruolo"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public List<DatiProtocolloLettoResponseType> LeggiProtocolloUORuolo(string token, string idProtocollo, string annoProtocollo, string numProtocollo, string uo, string ruolo, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio lettura protocollo, metodo LeggiProtocolloUORuolo, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2}, Numero Protocollo: {3}, Uo: {4}, Ruolo: {5}, Software: {6}, Codice Comune: {7}", token, idProtocollo, annoProtocollo, numProtocollo, uo, ruolo, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    var protocolloLetto = mgr.LeggiProtocolloUoRuolo(
                        new LeggiProtocolloRequest()
                        {
                            IdProtocollo = idProtocollo,
                            AnnoProtocollo = annoProtocollo,
                            NumeroProtocollo = numProtocollo
                        },
                        uo,
                        ruolo
                    );

                    return protocolloLetto;
                }
            }
            catch (Exception ex1)
            {
                this._log.Error("Errore in LeggiProtocolloUORuolo", ex1);
                //verifico se l'anno coincide con l'anno del protocollo storico ( se attivato ), a quel punto tento una prima lettura nel 
                //protocollo storico e se non trovato sollevo l'eccezione
                try
                {
                    var dataProtocollo = new DateTime(Convert.ToInt32(annoProtocollo), 1, 1);

                    return this.LeggiProtocolloConData(token, idProtocollo, dataProtocollo, numProtocollo, software, codiceComune);
                }
                catch (Exception ex2)
                {
                    const string fmtMsg = "ERRORE GENERATO DURANTE LeggiProtocolloUORuolo, {0}";
                    var errore = String.Format(fmtMsg, ex2.Message);
                    this._log.ErrorFormat(errore, ex2.ToString());
                    return new List<DatiProtocolloLettoResponseType>() { new DatiProtocolloLettoResponseType(errore, ex2.ToString()) };
                }
            }
        }

        /// <summary>
        /// Metodo usato per rileggere un documento protocollato passando la data e non l'anno
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idProtocollo"></param>
        /// <param name="dataProtocollo"></param>
        /// <param name="numProtocollo"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public List<DatiProtocolloLettoResponseType> LeggiProtocolloConData(string token, string idProtocollo, DateTime dataProtocollo, string numProtocollo, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio lettura protocollo, metodo LeggiProtocolloConData, Token: {0}, Id Protocollo: {1}, Data Protocollo: {2}, Numero Protocollo: {3}, Software: {4}, Codice Comune: {5}", token, idProtocollo, dataProtocollo, numProtocollo, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    return mgr.LeggiProtocolloConData(idProtocollo, dataProtocollo, numProtocollo);
                }

            }
            catch (Exception ex)
            {
                this._log.Error("ERRORE GENERATO DURANTE LeggiProtocolloConData", ex);

                const string fmtMsg = "ERRORE GENERATO DURANTE LeggiProtocolloConData, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                return new List<DatiProtocolloLettoResponseType>() { new DatiProtocolloLettoResponseType(errore, ex.ToString()) };
            }
        }

        /// <summary>
        /// Metodo usato per leggere i file allegati al protocollo
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idBase"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        public AllegatoResponseType LeggiAllegato(string token, string idBase, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio lettura allegato, metodo LeggiAllegato, token: {0}, Id Base: {1}, Software: {2}, Codice Comune", token, idBase, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {

                    var arrDatiProt = idBase.Split('|');

                    var idProtocollo = arrDatiProt[0];
                    var numProtocollo = arrDatiProt[1];
                    var annoProtocollo = arrDatiProt[2];
                    var idAllegato = arrDatiProt[3];

                    var allegato = mgr.LeggiAllegato(idProtocollo, numProtocollo, annoProtocollo, idAllegato) ?? throw new Exception("Allegato non trovato");
                    return allegato;
                }
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA LETTURA DI UN ALLEGATO, {0}; VERRA' TENTATA UNA LETTURA DAL PROTOCOLLO STORICO SE PRESENTE";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(errore, ex.ToString());

                try
                {
                    return this.LeggiAllegatoStorico(token, idBase, software, codiceComune);
                }
                catch (Exception ex2)
                {
                    fmtMsg = "ERRORE GENERATO DURANTE LA LETTURA DI UN ALLEGATO DAL PROTOCOLLO STORICO, {0}";
                    errore = String.Format(fmtMsg, ex2.Message);
                    this._log.ErrorFormat(fmtMsg, ex2.ToString());
                    return new AllegatoResponseType(errore, ex2.ToString());
                }
            }
        }

        /// <summary>
        /// Metodo usato per leggere i file allegati al protocollo passando anche UO e Ruolo per la lettura
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idBase"></param>
        /// <param name="uo"></param>
        /// <param name="ruolo"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        public AllegatoResponseType LeggiAllegatoUORuolo(string token, string idBase, string uo, string ruolo, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio lettura allegato, metodo LeggiAllegato, token: {0}, Id Base: {1}, Uo: {2}, Ruolo: {3}, Software: {4}, Codice Comune: {5}", token, idBase, uo, ruolo, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    var arrDatiProt = idBase.Split('|');

                    var idProtocollo = arrDatiProt[0];
                    var numProtocollo = arrDatiProt[1];
                    var annoProtocollo = arrDatiProt[2];
                    var idAllegato = arrDatiProt[3];

                    var allegato = mgr.LeggiAllegato(idProtocollo, numProtocollo, annoProtocollo, idAllegato, uo, ruolo) ?? throw new Exception("Allegato non trovato");
                    return allegato;
                }
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA LETTURA DI UN ALLEGATO, {0}; VERRA' TENTATA UNA LETTURA DAL PROTOCOLLO STORICO SE PRESENTE";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(errore, ex.ToString());

                try
                {
                    return this.LeggiAllegatoStorico(token, idBase, software, codiceComune);
                }
                catch (Exception ex2)
                {
                    fmtMsg = "ERRORE GENERATO DURANTE LA LETTURA DI UN ALLEGATO DAL PROTOCOLLO STORICO, {0}";
                    errore = String.Format(fmtMsg, ex2.Message);
                    this._log.ErrorFormat(fmtMsg, ex2.ToString());
                    return new AllegatoResponseType(errore, ex2.ToString());
                }
            }
        }

        public AllegatoResponseType LeggiAllegatoStorico(string token, string idBase, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio lettura allegato, metodo LeggiAllegatoStorico, token: {0}, Id Base: {1}, Software: {2}, Codice Comune", token, idBase, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    var arrDatiProt = idBase.Split('|');

                    var idProtocollo = arrDatiProt[0];
                    var numProtocollo = arrDatiProt[1];
                    var annoProtocollo = arrDatiProt[2];
                    var idAllegato = arrDatiProt[3];

                    var allegato = mgr.LeggiAllegatoStorico(idProtocollo, numProtocollo, annoProtocollo, idAllegato);
                    return allegato;
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA LETTURA DI UN ALLEGATO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new AllegatoResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la stampa di etichette (Protocollo SIDOP)
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idProtocollo"></param>
        /// <param name="numeroProtocollo"></param>
        /// <param name="dataProtocollo"></param>
        /// <param name="numeroCopie"></param>
        /// <param name="stampante"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public EtichetteResponseType StampaEtichette(string token, string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, int numeroCopie, string stampante, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio stampa etichette, metodo StampaEtichette, Token: {0}, Id Protocollo: {1}, Numero Protocollo: {2}, Data Protocollo: {3}, Numero Copie: {4}, Stampante: {5}, Software: {6}, Codice Comune: {7}", token, idProtocollo, numeroProtocollo, dataProtocollo.HasValue ? dataProtocollo.Value.ToString("dd/MM/yyyy") : "", numeroCopie.ToString(), stampante, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    return mgr.StampaEtichette(idProtocollo, dataProtocollo, numeroProtocollo, numeroCopie, stampante);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA STAMPA DELLE ETICHETTE, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new EtichetteResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per ottenere la lista dei motivi di annullamento
        /// </summary>
        /// <param name="token"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public ListaMotiviAnnullamentoResponseType GetMotiviAnnullamento(string token, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio recupero motivi annullamento, funzionalità GetMotiviAnnullamento, Token: {0}, Software: {1}, Codice Comune: {2}", token, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    return mgr.ListaMotivoAnnullamento();
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE DURANTE IL RECUPERO DEI MOTIVI DI ANNULLAMENTO DI UN PROTOCOLLO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new ListaMotiviAnnullamentoResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per annullare un protocollo
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idProtocollo"></param>
        /// <param name="annoProtocollo"></param>
        /// <param name="numeroProtocollo"></param>
        /// <param name="motivoAnnullamento"></param>
        /// <param name="noteAnnullamento"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        public void AnnullaProtocollo(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio annullamento protocollo, metodo AnnullaProtocollo, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2}, Numero Protocollo: {3}, Motivo Annullamento: {4}, Note Annullamento: {5}, Software: {6}, Codice Comune: {7}", token, idProtocollo, annoProtocollo, numeroProtocollo, motivoAnnullamento, noteAnnullamento, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    mgr.AnnullaProtocollo(idProtocollo, annoProtocollo, numeroProtocollo, motivoAnnullamento, noteAnnullamento);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE L'ANNULLAMENTO DEL PROTOCOLLO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(errore, ex.ToString());
                throw;
            }
        }

        /// <summary>
        /// Metodo usato per stabilire se un protocollo è annullato
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idProtocollo"></param>
        /// <param name="annoProtocollo"></param>
        /// <param name="numeroProtocollo"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public DatiProtocolloAnnullatoResponseType IsAnnullato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio verifica di annullamento del protocollo, metodo IsAnnullato, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2}, Numero Protocollo: {3}, Software: {4}", token, idProtocollo, annoProtocollo, numeroProtocollo, software);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    return mgr.IsAnnullato(idProtocollo, annoProtocollo, numeroProtocollo);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA VERIFICA SE UN PROTOCOLLO E' ANNULLATO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(errore, ex.ToString());
                return new DatiProtocolloAnnullatoResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Non invocare
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <returns></returns>
        public ListaFascicoliResponseType GetFascicoli(string token, string codiceIstanza)
        {
            return null;
        }

        /// <summary>
        /// Metodo usato per ottenere una lista di fascicoli in base ai parametri di ricerca indicati
        /// </summary>
        /// <param name="token"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <param name="datiFascicolo">Classe dove indicare i paraemtri di ricerca</param>
        /// <returns></returns>
        public ListaFascicoliResponseType SearchFascicoli(string token, string software, string codiceComune, DatiFascType datiFascicolo)
        {
            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    return mgr.ListaFascicoli(datiFascicolo);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA LETTURA DEI FASCICOLI, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new ListaFascicoliResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per stabilire se un protocollo è fascicolato
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idProtocollo"></param>
        /// <param name="annoProtocollo"></param>
        /// <param name="numeroProtocollo"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public DatiProtocolloFascicolatoResponseType IsFascicolato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio verifica se un protocollo è fascicolato, metodo IsFascicolato, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2} Numero Protocollo: {3}, Software: {4}, Codice Comune: {5}", token, idProtocollo, annoProtocollo, numeroProtocollo, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    return mgr.IsFascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA VERIFICA SE UN PROTOCOLLO E' FASCICOLATO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloFascicolatoResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare una fascicolazione generica senza aggancio a movimento / istanza.
        /// </summary>
        /// <param name="token"></param>
        /// <param name="software"></param>
        /// <param name="datiFasc"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public DatiFascicoloResponseType FascicolazioneXml(string token, string software, DatiFascType datiFasc, string codiceComune, string idProtocollo, string numeroProtocollo, string annoProtocollo)
        {
            this._log.DebugFormat("Avvio fascicolazione generica (senza codice istanza e movimento), metodo FascicolazioneXml, token: {0}, software: {1}, codice comune: {2}", token, software, codiceComune);
            try
            {
                var authInfo = this.CheckToken(token);
                return this.Fascicolazione(token, software, codiceComune, (int)SourceFascicolazione.FASC_IST_MOV_AUT_BO, AmbitoProtocollazioneEnum.NESSUNO, null, null, datiFasc, idProtocollo, numeroProtocollo, annoProtocollo);
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE UNA FASCICOLAZIONE GENERICA SENZA ISTANZA O MOVIMENTO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicoloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la fascicolazione di una istanza senza passare dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="source"></param>
        /// <returns></returns>
        public DatiFascicoloResponseType FascicolazioneIstanza(string token, string codiceIstanza, int source)
        {
            this._log.DebugFormat("Avvio fascicolazione istanza automatica (senza passare dalla maschera), metodo FascicolazioneIstanza, token: {0}, codice istanza: {1}, source: {2}", token, codiceIstanza, source);
            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return this.Fascicolazione(token, software, codiceComune, source, AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null, null);
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN'ISTANZA SENZA PASSARE PER LA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicoloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la fascicolazione di una istanza dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="datiFasc"></param>
        /// <returns></returns>
        public DatiFascicoloResponseType FascicolazioneIstanzaXml(string token, string codiceIstanza, DatiFascType datiFasc)
        {
            this._log.DebugFormat("Avvio fascicolazione istanza dalla maschera, metodo FascicolazioneIstanzaXml, token: {0}, codice istanza: {1}, file: {2}", token, codiceIstanza, datiFasc);

            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return this.Fascicolazione(token, software, codiceComune, (int)SourceFascicolazione.FASC_IST_MOV_AUT_BO, AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null, datiFasc);
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN'ISTANZA DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicoloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la fascicolazione di un movimento senza passare dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <returns></returns>
        public DatiFascicoloResponseType FascicolazioneMovimento(string token, string codiceMovimento)
        {
            this._log.DebugFormat("Avvio fascicolazione automatica di un movimento (senza passare dalla maschera) metodo FascicolazioneMovimento, token, {0}, codice movimento: {1}", token, codiceMovimento);
            try
            {
                var authInfo = this.CheckToken(token);

                var istanza = this.GetIstanza(authInfo, "", codiceMovimento);
                var movimento = this.GetMovimento(authInfo, authInfo.CreateDatabase(), codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return this.Fascicolazione(token, software, codiceComune, (int)SourceFascicolazione.ON_LINE, AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento, null);
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN MOVIMENTO SENZA PASSARE DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicoloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la fascicolazione di un movimento dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <param name="file"></param>
        /// <returns></returns>
        public DatiFascicoloResponseType FascicolazioneMovimentoXml(string token, string codiceMovimento, DatiFascType datiFasc)
        {
            this._log.DebugFormat("Avvio fascicolazione di un movimento dalla maschera, metodo FascicolazioneMovimentoXml, token: {0}, codice movimento: {1}, file: {2}", token, codiceMovimento, datiFasc);
            try
            {
                var authInfo = this.CheckToken(token);

                var istanza = this.GetIstanza(authInfo, "", codiceMovimento);
                var movimento = this.GetMovimento(authInfo, authInfo.CreateDatabase(), codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return this.Fascicolazione(token, software, codiceComune, (int)SourceFascicolazione.FASC_IST_MOV_AUT_BO, AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento, datiFasc);
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN MOVIMENTO DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicoloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per cambiare fascicolo dell'istanza ed assegnarvi il protocollo
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="datiFasc"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public DatiFascicoloResponseType CambiaFascicoloIstanzaXml(string token, string codiceIstanza, DatiFascType datiFasc)
        {
            this._log.DebugFormat("Avvio cambio fascicolo dell'istanza, metodo CambiaFascicoloIstanzaXml, Token: {0}, Codice Istanza: {1}", token, codiceIstanza);

            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune, AmbitoProtocollazioneEnum.DA_ISTANZA, istanza))
                {
                    return mgr.CambiaFascicolo(datiFasc);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA FUNZIONALITA' CAMBIA FASCICOLO PER ASSEGNARGLI IL PROTOCOLLO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicoloResponseType(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo utilizzato per aggiungere allegati ad un determinato protocollo
        /// </summary>
        /// <param name="token"></param>
        /// <param name="numeroProtocollo"></param>
        /// <param name="dataProtocollo"></param>
        /// <param name="idProtocollo"></param>
        /// <param name="codiciAllegati"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        public void AggiungiAllegati(string token, string numeroProtocollo, DateTime? dataProtocollo, string idProtocollo, int[] codiciAllegati, string software, string codiceComune)
        {
            this._log.DebugFormat("Invocato il metodo AggiungiAllegati, che serve per allegare files ad un determinato protocollo, numero protocollo: {0}, data protocollo: {1}, id protocollo: {2}, codici allegati: {3} ,software: {4}, token: {5}", numeroProtocollo, dataProtocollo.HasValue ? dataProtocollo.Value.ToString("dd/MM/yyyy") : "", String.Join("|", codiciAllegati), software, token);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    mgr.AggiungiAllegati(numeroProtocollo, dataProtocollo, idProtocollo, codiciAllegati);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE L'AGGIUNTA DI ALLEGATI AL PROTOCOLLO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                throw new Exception(errore, ex);
            }
        }

        public List<MetadatoType> RecuperaMetadati(string token, string software, string codiceComune)
        {
            this._log.DebugFormat("Invocato il metodo RecuperaMetadati, token: {0}, software: {1}", token, software);
            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    return mgr.RecuperaMetadati();
                }
            }
            catch (Exception ex)
            {
                var msg = $"ERRORE GENERATO DURANTE IL RECUPERO DEI METADATI DEL PROTOCOLLO PROTOCOLLO: {ex.Message}";

                this._log.ErrorFormat(msg, ex.ToString());
                throw new Exception(msg, ex);
            }
        }

        public CreaUnitaDocumentaleResponseType CreaUnitaDocumentaleIstanza(string token, string codiceIstanza, CreaUnitaDocumentaleRequestType request)
        {
            this._log.DebugFormat("Avvio creazione unita documentale da istanza, metodo CreaUnitaDocumentaleIstanza, Token: {0}, Codice Istanza: {1}", token, codiceIstanza);

            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune, AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null))
                {
                    return mgr.CreaUnitaDocumentale(request);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA CREAZIONE DELL'UNITA' DOCUMENTALE DA ISTANZA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new CreaUnitaDocumentaleResponseType(errore, ex.ToString());
            }
        }

        public CreaUnitaDocumentaleResponseType CreaUnitaDocumentaleMovimento(string token, string codiceMovimento, CreaUnitaDocumentaleRequestType request)
        {
            this._log.DebugFormat("Avvio creazione unita documentale da movimento, metodo CreaUnitaDocumentaleMovimento, Token: {0}, Codice Istanza: {1}", token, codiceMovimento);

            try
            {
                var authInfo = this.CheckToken(token);

                var istanza = this.GetIstanza(authInfo, "", codiceMovimento);
                var movimento = this.GetMovimento(authInfo, authInfo.CreateDatabase(), codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune, AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento))
                {
                    return mgr.CreaUnitaDocumentale(request);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA CREAZIONE DELL'UNITA' DOCUMENTALE DA MOVIMENTO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new CreaUnitaDocumentaleResponseType(errore, ex.ToString());
            }
        }

        public DatiProtocolloResponseType RegistrazioneIstanzaXml(string token, string codiceIstanza, string registro, DatiRequestType dati)
        {
            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune, AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null))
                {
                    return mgr.Registrazione(registro, dati);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA REGISTRAZIONE DA ISTANZA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloResponseType(errore, ex.ToString());
            }
        }

        public DatiProtocolloResponseType RegistrazioneMovimentoXml(string token, string codiceMovimento, string registro, DatiRequestType dati)
        {
            try
            {
                var authInfo = this.CheckToken(token);
                var db = authInfo.CreateDatabase();
                var istanza = this.GetIstanzaFromMovimento(authInfo, db, codiceMovimento);
                var movimento = this.GetMovimento(authInfo, db, codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune, AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento))
                {
                    return mgr.Registrazione(registro, dati);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA REGISTRAZIONE DA MOVIMENTO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloResponseType(errore, ex.ToString());
            }
        }

        public void InvioPec(string token, string codiceMovimento)
        {
            try
            {
                var authInfo = this.CheckToken(token);
                var db = authInfo.CreateDatabase();
                var istanza = this.GetIstanzaFromMovimento(authInfo, db, codiceMovimento);
                var movimento = this.GetMovimento(authInfo, db, codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune, AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento))
                {
                    mgr.InvioPec();
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("ERRORE GENERATO DURANTE LA CREAZIONE DELL'UNITA' DOCUMENTALE, {0}", ex, this.ToString());

                const string fmtMsg = "ERRORE GENERATO DURANTE LA CREAZIONE DELL'UNITA' DOCUMENTALE, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                throw new Exception(errore, ex);
            }
        }

        public DatiFascicoloResponseType Fascicolazione(string token, string software, string codiceComune, int source, AmbitoProtocollazioneEnum ambito = AmbitoProtocollazioneEnum.NESSUNO, Istanze istanza = null, Init.SIGePro.Data.Movimenti movimento = null, DatiFascType file = null, string idProtocollo = null, string numeroProtocollo = null, string annoProtocollo = null)
        {
            var authInfo = this.CheckToken(token);

            using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune, ambito, istanza, movimento))
            {

                var provenienza = authInfo.CodiceResponsabile.HasValue ? TipoProvenienza.BACKOFFICE : TipoProvenienza.ONLINE;

                return mgr.Fascicola(file, source, idProtocollo, numeroProtocollo, annoProtocollo, provenienza);
            }
        }

        public DatiProtocolloResponseType Protocollazione(string token, string software, string codiceComune, int source, AmbitoProtocollazioneEnum ambito, Istanze istanza = null, Init.SIGePro.Data.Movimenti movimento = null, DatiRequestType dati = null, PecInbox datiPec = null)
        {
            var authInfo = this.CheckToken(token);

            using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune, ambito, istanza, movimento, datiPec))
            {
                var provenienza = authInfo.CodiceResponsabile.HasValue ? TipoProvenienza.BACKOFFICE : TipoProvenienza.ONLINE;

                return mgr.Protocollazione(provenienza, dati, (Source)source);
            }
        }

        private Istanze GetIstanza(AuthenticationInfo authInfo, DataBase db, string codiceIstanza)
        {
            if (String.IsNullOrEmpty(codiceIstanza))
                return null;

            var mgr = new IstanzeMgr(db);
            return mgr.GetById(authInfo.IdComune, Convert.ToInt32(codiceIstanza), PersonalLib2.Sql.useForeignEnum.Yes);
        }

        private Istanze GetIstanza(AuthenticationInfo authInfo, string codiceIstanza = "", string codiceMovimento = "")
        {
            using (var db = authInfo.CreateDatabase())
            {
                var istanza = this.GetIstanza(authInfo, db, codiceIstanza);
                if (istanza == null && !String.IsNullOrEmpty(codiceMovimento))
                    istanza = this.GetIstanzaFromMovimento(authInfo, db, codiceMovimento);

                return istanza;
            }
        }

        private PecInbox GetPecInbox(AuthenticationInfo authInfo, string codicePec)
        {
            if (String.IsNullOrEmpty(codicePec))
                throw new Exception("CODICE PEC NON VALORIZZATO");

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new PecInboxMgr(db);
                var pec = mgr.GetById(codicePec, authInfo.IdComune) ?? throw new Exception(String.Format("PEC CODICE {0} NON TROVATA", codicePec));
                return pec;
            }
        }

        private Init.SIGePro.Data.Movimenti GetMovimento(AuthenticationInfo authInfo, DataBase db, string codiceMovimento)
        {
            if (String.IsNullOrEmpty(codiceMovimento))
                return null;

            var mgr = new MovimentiMgr(db);
            return mgr.GetById(authInfo.IdComune, Convert.ToInt32(codiceMovimento));
        }

        private Istanze GetIstanzaFromMovimento(AuthenticationInfo authInfo, DataBase db, string codiceMovimento)
        {
            var movimento = this.GetMovimento(authInfo, db, codiceMovimento);
            if (movimento == null)
                return null;

            return this.GetIstanza(authInfo, db, movimento.CODICEISTANZA);
        }

        private string GetCodiceComune(Istanze istanza)
        {
            if (istanza == null)
                throw new Exception("ISTANZA NON VALORIZZATA");

            if (String.IsNullOrEmpty(istanza.CODICECOMUNE))
                throw new Exception("CODICE COMUNE NON VALORIZZATO");

            return istanza.CODICECOMUNE;
        }

        private string GetSoftware(Istanze istanza)
        {
            if (istanza == null)
                throw new Exception("ISTANZA NON VALORIZZATA");

            if (String.IsNullOrEmpty(istanza.SOFTWARE))
                throw new Exception("SOFTWARE NON VALORIZZATO");

            return istanza.SOFTWARE;
        }

        private bool DocumentiIstanzaSenzaCodiceOggetto(AuthenticationInfo authInfo, Istanze istanza)
        {
            var software = this.GetSoftware(istanza);
            var codiceComune = this.GetCodiceComune(istanza);

            var vert = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(authInfo.Alias, software, codiceComune);

            if (vert.VerificaDocumentiIstanza)
            {
                return new DocumentiIstanzaMgr(authInfo.CreateDatabase()).VerificaPresenzaDocumentiSenzaCodiceOggetto(istanza.IDCOMUNE, istanza.CODICEISTANZA);
            }

            return false;
        }

        public ListaFirmatari GetFirmatari(string token, string software, string codiceComune)
        {
            this._log.DebugFormat($"Inizio recupero firmatari, metodo GetFirmatari, Token: {token}, Software: {software}, Codice Comune: {codiceComune}");

            var authInfo = this.CheckToken(token);

            using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
            {
                return mgr.GetFirmatari();
            }
        }

        public EseguiAccettazioneResponseType EseguiAccettazione(string token, string idProtocollo, string annoProtocollo, string numProtocollo, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio accettazione protocollo, metodo EseguiAccettazione, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2}, Numero Protocollo: {3}, Software: {4}, Codice Comune: {5}", token, idProtocollo, annoProtocollo, numProtocollo, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    return mgr.EseguiAccettazione(idProtocollo, annoProtocollo, numProtocollo);
                }
            }
            catch (Exception ex)
            {
                const string msg = "ERRORE GENERATO DURANTE L'ACCETTAZIONE DEL PROTOCOLLO, {0}";
                this._log.ErrorFormat(msg, ex.ToString());
                return new EseguiAccettazioneResponseType(String.Format(msg, ex.Message), ex);
            }
        }

        /// <summary>
        /// Metodo usato per stabilire se un protocollo è stato accettato
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idProtocollo"></param>
        /// <param name="annoProtocollo"></param>
        /// <param name="numeroProtocollo"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public DatiProtocolloEsitatoResponseType IsEsitato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune)
        {
            this._log.DebugFormat("Avvio verifica se un protocollo è stato accettato, metodo IsEsitato, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2} Numero Protocollo: {3}, Software: {4}, Codice Comune: {5}", token, idProtocollo, annoProtocollo, numeroProtocollo, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = this.CreaProtocolloManager(authInfo, software, codiceComune))
                {
                    return mgr.IsEsitato(idProtocollo, annoProtocollo, numeroProtocollo);
                }
            }
            catch (Exception ex)
            {
                const string fmtMsg = "ERRORE GENERATO DURANTE LA VERIFICA SE UN PROTOCOLLO E' STATO ACCETTATO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                this._log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloEsitatoResponseType(errore, ex.ToString());
            }
        }
    }
}