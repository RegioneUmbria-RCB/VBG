using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;
using log4net;
using Init.SIGePro.Protocollo.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.WsDataClass;
using System.ServiceModel.Activation;
using Init.SIGePro.Protocollo.Constants;

namespace SIGePro.Net.WebServices.WsSIGePro
{
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class ProtocollazioneService : IProtocollazioneService
    {
        ILog _log = LogManager.GetLogger(typeof(ProtocollazioneService));

        private AuthenticationInfo CheckToken(string token)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);

            return authInfo;
        }

        /// <summary>
        /// Metodo usato per creare una copia di un protocollo
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="codiceAmministrazione"></param>
        /// <returns></returns>
        public DatiProtocolloRes CreaCopie(string token, string codiceIstanza, string codiceAmministrazione)
        {
            _log.DebugFormat("Avvio creazione copia, metodo CreaCopie, token: {0}, codice istanza: {1}, codice amministrazione: {2}", token, codiceIstanza, codiceAmministrazione);

            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA, istanza))
                    return mgr.CreaCopie(codiceAmministrazione);

            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE IL CREA COPIE, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloRes(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per mettere alla firma un documento dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <param name="file"></param>
        /// <returns></returns>
        public DatiProtocolloRes MettiAllaFirmaXml(string token, string codiceMovimento, Dati file)
        {

            _log.DebugFormat("Avvio della messa alla firma, metodo MettiAllaFirmaXml, token: {0}, Codice Movimento: {1}", token, codiceMovimento);

            try
            {
                var authInfo = this.CheckToken(token);
                var db = authInfo.CreateDatabase();
                var movimento = this.GetMovimento(authInfo, db, codiceMovimento);
                var istanza = this.GetIstanzaFromMovimento(authInfo, db, codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento))
                    return mgr.MettiAllaFirma(file);

            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA MESSA ALLA FIRMA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloRes(errore, ex.ToString());


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
        public DatiProtocolloRes ProtocollazioneXml(string token, string software, Dati file, string codiceComune)
        {
            _log.DebugFormat("Avvio protocollazione generica (senza codice istanza e movimento), metodo ProtocollazioneXml, token: {0}, software: {1}, codice comune: {2}", token, software, codiceComune);
            try
            {
                return Protocollazione(token, software, codiceComune, (int)ProtocolloEnum.Source.PROT_IST_MOV_AUT_BO, ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO, null, null, file);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE GENERICA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloRes(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la protocollazione di una istanza senza passare dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="source"></param>
        /// <returns></returns>
        public DatiProtocolloRes ProtocollazioneIstanza(string token, string codiceIstanza, int source, DatiMittenti mittenti)
        {
            _log.DebugFormat("Avvio protocollazione istanza automatica (senza passare dalla maschera), metodo ProtocollazioneIstanza, token: {0}, codice istanza: {1}, source: {2}", token, codiceIstanza, source);
            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                var dati = new Dati { Mittenti = mittenti };

                return this.Protocollazione(token, software, codiceComune, source, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null, dati, authInfo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE ISTANZA AUTOMATICA (SENZA PASSARE DALLA MASCHERA), {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloRes(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la protocollazione di una istanza dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="file"></param>
        /// <returns></returns>
        public DatiProtocolloRes ProtocollazioneIstanzaXml(string token, string codiceIstanza, Dati file)
        {
            _log.DebugFormat("Avvio protocollazione istanza dalla maschera, metodo ProtocollazioneIstanzaXml, token: {0}, codice istanza: {1}", token, codiceIstanza);
            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return this.Protocollazione(token, software, codiceComune, (int)ProtocolloEnum.Source.PROT_IST_MOV_AUT_BO, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null, file, authInfo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE ISTANZA DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());

                return new DatiProtocolloRes(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo utilizzato per la protocollazione da pannello pec
        /// </summary>
        /// <param name="token">Token applicativo</param>
        /// <param name="codicePec">Identificativo della pec sulla tabella PEC_INBOX, campo ID</param>
        /// <param name="file">Dati da valorizzare in base alla compilazione del form dall'interfaccia di protocollazione della pec</param>
        /// <returns></returns>
        /// 
        public DatiProtocolloRes ProtocollazionePecXml(string token, string codicePec, Dati file)
        {
            _log.DebugFormat("Avvio protocollazione pec dalla maschera, metodo ProtocollazionePecXml, token: {0}, codice istanza: {1}", token, codicePec);
            try
            {
                var authInfo = this.CheckToken(token);
                var pecInbox = this.GetPecInbox(authInfo, codicePec);

                return this.Protocollazione(token, pecInbox.SoftwareProt, pecInbox.CodiceComuneProt, (int)ProtocolloEnum.Source.PROT_IST_MOV_AUT_BO, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_PANNELLO_PEC, null, null, file, authInfo, pecInbox);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE PEC DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());

                return new DatiProtocolloRes(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la protocollazione di un movimento senza passare dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <returns></returns>
        public DatiProtocolloRes ProtocollazioneMovimento(string token, string codiceMovimento, DatiMittenti mittenti = null)
        {
            _log.DebugFormat("Avvio protocollazione movimento automatica (senza passare dalla maschera), metodo ProtocollazioneMovimento, token: {0}, codice movimento: {1}", token, codiceMovimento);
            try
            {
                var authInfo = this.CheckToken(token);
                var db = authInfo.CreateDatabase();
                var istanza = this.GetIstanzaFromMovimento(authInfo, db, codiceMovimento);
                var movimento = this.GetMovimento(authInfo, db, codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                var dati = new Dati { Mittenti = mittenti };

                return Protocollazione(token, software, codiceComune, (int)ProtocolloEnum.Source.ON_LINE, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento, dati, authInfo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE DI UN MOVIMENTO SENZA PASSARE DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloRes(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo utilizzato per protocollare le graduatorie tramite un movimento, la protocollazione sarà sempre in partenza.
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <returns></returns>
        public DatiProtocolloRes ProtocollazioneComunicazioneGraduatoria(string token, string codiceMovimento)
        {
            _log.DebugFormat("Avvio protocollazione movimento automatica (senza passare dalla maschera), metodo ProtocollazioneMovimento, token: {0}, codice movimento: {1}", token, codiceMovimento);
            try
            {
                var authInfo = this.CheckToken(token);
                var db = authInfo.CreateDatabase();
                var istanza = this.GetIstanzaFromMovimento(authInfo, db, codiceMovimento);
                var movimento = this.GetMovimento(authInfo, db, codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                var dati = new Dati { Flusso = ProtocolloConstants.COD_PARTENZA };

                var retVal = Protocollazione(token, software, codiceComune, (int)ProtocolloEnum.Source.PROT_IST_MOV_AUT_BO, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento, dati, authInfo);

                try
                {
                    Fascicolazione(token, software, codiceComune, (int)ProtocolloEnum.SourceFascicolazione.FASC_IST_MOV_AUT_BO, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento, null, authInfo);
                }
                catch (Exception exFasc)
                {
                    _log.WarnFormat(exFasc.Message);
                }

                return retVal;
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE DI UNA GRADUATORIA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloRes(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la protocollazione di un movimento dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <param name="file"></param>
        /// <returns></returns>
        public DatiProtocolloRes ProtocollazioneMovimentoXml(string token, string codiceMovimento, Dati file)
        {
            _log.DebugFormat("Avvio protocollazione di un movimento dalla maschera, metodo ProtocollazioneMovimentoXml, Token: {0}, Codice Movimento: {1}", token, codiceMovimento);
            try
            {
                var authInfo = this.CheckToken(token);
                var db = authInfo.CreateDatabase();
                var istanza = this.GetIstanzaFromMovimento(authInfo, db, codiceMovimento);
                var movimento = this.GetMovimento(authInfo, db, codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return Protocollazione(token, software, codiceComune, (int)ProtocolloEnum.Source.PROT_IST_MOV_AUT_BO, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento, file, authInfo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE DI UN MOVIMENTO DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloRes(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per ottenere la lista dei tipi documento 
        /// </summary>
        /// <param name="token"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public ListaTipiDocumento GetTipiDocumento(string token, string software, string codiceComune)
        {
            _log.DebugFormat("Avvio recupero Tipi Documento, metodo GetTipiDocumento Token: {0}, Software: {1}, Codice Comune: {2}", token, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
                    return mgr.ListaTipiDocumento();
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE IL RECUPERO DEI TIPI DOCUMENTO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new ListaTipiDocumento(errore, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per ottenere la lista delle classifiche 
        /// </summary>
        /// <param name="token"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public ListaTipiClassifica GetClassifiche(string token, string software, string codiceComune)
        {
            _log.DebugFormat("Avvio recupero classifiche, metodo GetClassifiche, Token: {0}, Software: {1}, Codice Comune: {2}", token, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
                    return mgr.ListaClassifiche();
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE IL RECUPERO DELLE CLASSIFICHE, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new ListaTipiClassifica(ex.Message, ex.ToString());
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
        public DatiProtocolloLetto LeggiProtocollo(string token, string idProtocollo, string annoProtocollo, string numProtocollo, string software, string codiceComune)
        {
            _log.DebugFormat("Avvio lettura protocollo, metodo LeggiProtocollo, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2}, Numero Protocollo: {3}, Software: {4}, Codice Comune: {5}", token, idProtocollo, annoProtocollo, numProtocollo, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
                    return mgr.LeggiProtocollo(idProtocollo, annoProtocollo, numProtocollo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloLetto(ex.Message, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per leggere i file allegati al protocollo
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idBase"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public AllOut LeggiAllegato(string token, string idBase, string software, string codiceComune)
        {
            _log.DebugFormat("Avvio lettura allegato, metodo LeggiAllegato, token: {0}, Id Base: {1}, Software: {2}, Codice Comune", token, idBase, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);
                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
                {
                    string[] arrDatiProt = idBase.Split('|');

                    string idProtocollo = arrDatiProt[0];
                    string numProtocollo = arrDatiProt[1];
                    string annoProtocollo = arrDatiProt[2];
                    string idAllegato = arrDatiProt[3];

                    return mgr.LeggiAllegato(idProtocollo, numProtocollo, annoProtocollo, idAllegato);
                }
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA LETTURA DI UN ALLEGATO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new AllOut(ex.Message, ex.ToString());
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
        public DatiEtichette StampaEtichette(string token, string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, int numeroCopie, string stampante, string software, string codiceComune)
        {
            _log.DebugFormat("Avvio stampa etichette, metodo StampaEtichette, Token: {0}, Id Protocollo: {1}, Numero Protocollo: {2}, Data Protocollo: {3}, Numero Copie: {4}, Stampante: {5}, Software: {6}, Codice Comune: {7}", token, idProtocollo, numeroProtocollo, dataProtocollo.HasValue ? dataProtocollo.Value.ToString("dd/MM/yyyy") : "", numeroCopie.ToString(), stampante, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
                    return mgr.StampaEtichette(idProtocollo, dataProtocollo, numeroProtocollo, numeroCopie, stampante);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA STAMPA DELLE ETICHETTE, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiEtichette(ex.Message, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per ottenere la lista dei motivi di annullamento
        /// </summary>
        /// <param name="token"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public ListaMotiviAnnullamento GetMotiviAnnullamento(string token, string software, string codiceComune)
        {
            _log.DebugFormat("Avvio recupero motivi annullamento, funzionalità GetMotiviAnnullamento, Token: {0}, Software: {1}, Codice Comune: {2}", token, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
                    return mgr.ListaMotivoAnnullamento();
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE DURANTE IL RECUPERO DEI MOTIVI DI ANNULLAMENTO DI UN PROTOCOLLO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new ListaMotiviAnnullamento(ex.Message, ex.ToString());
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
            _log.DebugFormat("Avvio annullamento protocollo, metodo AnnullaProtocollo, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2}, Numero Protocollo: {3}, Motivo Annullamento: {4}, Note Annullamento: {5}, Software: {6}, Codice Comune: {7}", token, idProtocollo, annoProtocollo, numeroProtocollo, motivoAnnullamento, noteAnnullamento, software, codiceComune);

            try
            {
                var authInfo = CheckToken(token);

                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
                    mgr.AnnullaProtocollo(idProtocollo, annoProtocollo, numeroProtocollo, motivoAnnullamento, noteAnnullamento);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE L'ANNULLAMENTO DEL PROTOCOLLO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
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
        public DatiProtocolloAnnullato IsAnnullato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune)
        {
            _log.DebugFormat("Avvio verifica di annullamento del protocollo, metodo IsAnnullato, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2}, Numero Protocollo: {3}, Software: {4}", token, idProtocollo, annoProtocollo, numeroProtocollo, software);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
                    return mgr.IsAnnullato(idProtocollo, annoProtocollo, numeroProtocollo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA VERIFICA SE UN PROTOCOLLO E' ANNULLATO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloAnnullato(ex.Message, ex.ToString());
            }
        }

        /// <summary>
        /// Non invocare
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIStanza"></param>
        /// <returns></returns>
        public ListaFascicoli GetFascicoli(string token, string codiceIStanza)
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
        public ListaFascicoli SearchFascicoli(string token, string software, string codiceComune, DatiFasc datiFascicolo)
        {
            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
                    return mgr.ListaFascicoli(datiFascicolo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA LETTURA DEI FASCICOLI, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new ListaFascicoli(ex.Message, ex.ToString());

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
        public DatiProtocolloFascicolato IsFascicolato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune)
        {
            _log.DebugFormat("Avvio verifica se un protocollo è fascicolato, metodo IsFascicolato, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2} Numero Protocollo: {3}, Software: {4}, Codice Comune: {5}", token, idProtocollo, annoProtocollo, numeroProtocollo, software, codiceComune);

            try
            {
                var authInfo = this.CheckToken(token);
                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
                    return mgr.IsFascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA VERIFICA SE UN PROTOCOLLO E' FASCICOLATO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloFascicolato(ex.Message, ex.ToString());
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
        public DatiFascicolo FascicolazioneXml(string token, string software, DatiFasc datiFasc, string codiceComune, string idProtocollo, string numeroProtocollo, string annoProtocollo)
        {
            _log.DebugFormat("Avvio fascicolazione generica (senza codice istanza e movimento), metodo FascicolazioneXml, token: {0}, software: {1}, codice comune: {2}", token, software, codiceComune);
            try
            {
                var authInfo = this.CheckToken(token);
                return Fascicolazione(token, software, codiceComune, (int)ProtocolloEnum.SourceFascicolazione.FASC_IST_MOV_AUT_BO, ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO, null, null, datiFasc, authInfo, idProtocollo, numeroProtocollo, annoProtocollo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE UNA FASCICOLAZIONE GENERICA SENZA ISTANZA O MOVIMENTO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicolo(ex.Message, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la fascicolazione di una istanza senza passare dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="source"></param>
        /// <returns></returns>
        public DatiFascicolo FascicolazioneIstanza(string token, string codiceIstanza, int source)
        {
            _log.DebugFormat("Avvio fascicolazione istanza automatica (senza passare dalla maschera), metodo FascicolazioneIstanza, token: {0}, codice istanza: {1}, source: {2}", token, codiceIstanza, source);
            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return this.Fascicolazione(token, software, codiceComune, source, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null, null, authInfo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN'ISTANZA SENZA PASSARE PER LA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicolo(ex.Message, ex.ToString());
            }

        }

        /// <summary>
        /// Metodo usato per effettuare la fascicolazione di una istanza dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceIstanza"></param>
        /// <param name="file"></param>
        /// <returns></returns>
        public DatiFascicolo FascicolazioneIstanzaXml(string token, string codiceIstanza, DatiFasc file)
        {
            _log.DebugFormat("Avvio fascicolazione istanza dalla maschera, metodo FascicolazioneIstanzaXml, token: {0}, codice istanza: {1}, file: {2}", token, codiceIstanza, file);

            try
            {
                var authInfo = this.CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return this.Fascicolazione(token, software, codiceComune, (int)ProtocolloEnum.SourceFascicolazione.FASC_IST_MOV_AUT_BO, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null, file, authInfo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN'ISTANZA DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicolo(ex.Message, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la fascicolazione di un movimento senza passare dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <returns></returns>
        public DatiFascicolo FascicolazioneMovimento(string token, string codiceMovimento)
        {
            _log.DebugFormat("Avvio fascicolazione automatica di un movimento (senza passare dalla maschera) metodo FascicolazioneMovimento, token, {0}, codice movimento: {1}", token, codiceMovimento);
            try
            {
                var authInfo = this.CheckToken(token);

                var istanza = this.GetIstanza(authInfo, "", codiceMovimento);
                var movimento = this.GetMovimento(authInfo, authInfo.CreateDatabase(), codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return Fascicolazione(token, software, codiceComune, (int)ProtocolloEnum.SourceFascicolazione.ON_LINE, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento, null, authInfo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN MOVIMENTO SENZA PASSARE DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicolo(ex.Message, ex.ToString());
            }
        }

        /// <summary>
        /// Metodo usato per effettuare la fascicolazione di un movimento dalla maschera
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceMovimento"></param>
        /// <param name="file"></param>
        /// <returns></returns>
        public DatiFascicolo FascicolazioneMovimentoXml(string token, string codiceMovimento, DatiFasc file)
        {
            _log.DebugFormat("Avvio fascicolazione di un movimento dalla maschera, metodo FascicolazioneMovimentoXml, token: {0}, codice movimento: {1}, file: {2}", token, codiceMovimento, file);
            try
            {
                var authInfo = this.CheckToken(token);

                var istanza = this.GetIstanza(authInfo, "", codiceMovimento);
                var movimento = this.GetMovimento(authInfo, authInfo.CreateDatabase(), codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                return Fascicolazione(token, software, codiceComune, (int)ProtocolloEnum.SourceFascicolazione.FASC_IST_MOV_AUT_BO, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento, file, authInfo);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN MOVIMENTO DALLA MASCHERA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicolo(ex.Message, ex.ToString());
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
        public DatiFascicolo CambiaFascicoloIstanzaXml(string token, string codiceIstanza, DatiFasc datiFasc)
        {
            _log.DebugFormat("Avvio cambio fascicolo dell'istanza, metodo CambiaFascicoloIstanzaXml, Token: {0}, Codice Istanza: {1}", token, codiceIstanza);

            try
            {
                var authInfo = CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);
                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA, istanza);

                return mgr.CambiaFascicolo(datiFasc);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA FUNZIONALITA' CAMBIA FASCICOLO PER ASSEGNARGLI IL PROTOCOLLO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiFascicolo(ex.Message, ex.ToString());
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
            _log.DebugFormat("Invocato il metodo AggiungiAllegati, che serve per allegare files ad un determinato protocollo, numero protocollo: {0}, data protocollo: {1}, id protocollo: {2}, codici allegati: {3} ,software: {4}, token: {5}", numeroProtocollo, dataProtocollo.HasValue ? dataProtocollo.Value.ToString("dd/MM/yyyy") : "", String.Join("|", codiciAllegati), software, token);

            try
            {
                var authInfo = this.CheckToken(token);

                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
                    mgr.AggiungiAllegati(numeroProtocollo, dataProtocollo, idProtocollo, codiciAllegati);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE L'AGGIUNTA DI ALLEGATI AL PROTOCOLLO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                throw new Exception(errore, ex);
            }
        }

        public CreaUnitaDocumentaleResponse CreaUnitaDocumentaleIstanza(string token, string codiceIstanza, CreaUnitaDocumentaleRequest request)
        {
            _log.DebugFormat("Avvio creazione unita documentale da istanza, metodo CreaUnitaDocumentaleIstanza, Token: {0}, Codice Istanza: {1}", token, codiceIstanza);

            try
            {
                var authInfo = CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null);
                return mgr.CreaUnitaDocumentale(request);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA CREAZIONE DELL'UNITA' DOCUMENTALE DA ISTANZA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new CreaUnitaDocumentaleResponse(ex.Message, ex.ToString());
            }
        }

        public CreaUnitaDocumentaleResponse CreaUnitaDocumentaleMovimento(string token, string codiceMovimento, CreaUnitaDocumentaleRequest request)
        {
            _log.DebugFormat("Avvio creazione unita documentale da movimento, metodo CreaUnitaDocumentaleMovimento, Token: {0}, Codice Istanza: {1}", token, codiceMovimento);

            try
            {
                var authInfo = CheckToken(token);

                var istanza = this.GetIstanza(authInfo, "", codiceMovimento);
                var movimento = this.GetMovimento(authInfo, authInfo.CreateDatabase(), codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento);
                return mgr.CreaUnitaDocumentale(request);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA CREAZIONE DELL'UNITA' DOCUMENTALE DA MOVIMENTO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new CreaUnitaDocumentaleResponse(ex.Message, ex.ToString());
            }
        }

        public DatiProtocolloRes RegistrazioneIstanzaXml(string token, string codiceIstanza, string registro, Dati dati)
        {
            try
            {
                var authInfo = CheckToken(token);
                var istanza = this.GetIstanza(authInfo, codiceIstanza);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);
                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA, istanza, null);

                return mgr.Registrazione(registro, dati);
            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA REGISTRAZIONE DA ISTANZA, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloRes(ex.Message, ex.ToString());
            }
        }

        public DatiProtocolloRes RegistrazioneMovimentoXml(string token, string codiceMovimento, string registro, Dati dati)
        {
            try
            {
                var authInfo = CheckToken(token);
                var db = authInfo.CreateDatabase();
                var istanza = this.GetIstanzaFromMovimento(authInfo, db, codiceMovimento);
                var movimento = this.GetMovimento(authInfo, db, codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento);

                return mgr.Registrazione(registro, dati);


            }
            catch (Exception ex)
            {
                var fmtMsg = "ERRORE GENERATO DURANTE LA REGISTRAZIONE DA MOVIMENTO, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                return new DatiProtocolloRes(ex.Message, ex.ToString());
            }
        }

        public void InvioPec(string token, string codiceMovimento)
        {
            try
            {
                var authInfo = CheckToken(token);
                var db = authInfo.CreateDatabase();
                var istanza = this.GetIstanzaFromMovimento(authInfo, db, codiceMovimento);
                var movimento = this.GetMovimento(authInfo, db, codiceMovimento);
                var software = this.GetSoftware(istanza);
                var codiceComune = this.GetCodiceComune(istanza);

                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO, istanza, movimento);

                mgr.InvioPec();
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("ERRORE GENERATO DURANTE LA CREAZIONE DELL'UNITA' DOCUMENTALE, {0}", ex, ToString());

                var fmtMsg = "ERRORE GENERATO DURANTE LA CREAZIONE DELL'UNITA' DOCUMENTALE, {0}";
                var errore = String.Format(fmtMsg, ex.Message);
                _log.ErrorFormat(fmtMsg, ex.ToString());
                throw new Exception(errore, ex);
            }
        }

        private DatiFascicolo Fascicolazione(string token, string software, string codiceComune, int source, ProtocolloEnum.AmbitoProtocollazioneEnum ambito = ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO, Istanze istanza = null, Init.SIGePro.Data.Movimenti movimento = null, DatiFasc file = null, AuthenticationInfo authInfo = null, string idProtocollo = null, string numeroProtocollo = null, string annoProtocollo = null)
        {
            if (authInfo == null)
                authInfo = this.CheckToken(token);

            var mgr = new ProtocolloMgr(authInfo, software, codiceComune, ambito, istanza, movimento);

            var provenienza = authInfo.CodiceResponsabile.HasValue ? ProtocolloEnum.TipoProvenienza.BACKOFFICE : ProtocolloEnum.TipoProvenienza.ONLINE;

            return mgr.Fascicola(file, source, idProtocollo, numeroProtocollo, annoProtocollo, provenienza);
        }

        private DatiProtocolloRes Protocollazione(string token, string software, string codiceComune, int source, ProtocolloEnum.AmbitoProtocollazioneEnum ambito, Istanze istanza = null, Init.SIGePro.Data.Movimenti movimento = null, Dati dati = null, AuthenticationInfo authInfo = null, PecInbox datiPec = null)
        {
            if (authInfo == null)
                authInfo = this.CheckToken(token);

            var mgr = new ProtocolloMgr(authInfo, software, codiceComune, ambito, istanza, movimento, datiPec);

            var provenienza = authInfo.CodiceResponsabile.HasValue ? ProtocolloEnum.TipoProvenienza.BACKOFFICE : ProtocolloEnum.TipoProvenienza.ONLINE;

            return mgr.Protocollazione(provenienza, dati, source);
        }

        private Istanze GetIstanza(AuthenticationInfo authInfo, DataBase db, string codiceIstanza)
        {
            if (String.IsNullOrEmpty(codiceIstanza))
                return null;

            var mgr = new IstanzeMgr(db);
            var istanza = mgr.GetById(authInfo.IdComune, Convert.ToInt32(codiceIstanza), PersonalLib2.Sql.useForeignEnum.Yes);

            return istanza;
        }

        private Istanze GetIstanza(AuthenticationInfo authInfo, string codiceIstanza = "", string codiceMovimento = "")
        {
            using (var db = authInfo.CreateDatabase())
            {
                var istanza = GetIstanza(authInfo, db, codiceIstanza);
                if (istanza == null && !String.IsNullOrEmpty(codiceMovimento))
                    istanza = GetIstanzaFromMovimento(authInfo, db, codiceMovimento);

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
                var pec = mgr.GetById(codicePec, authInfo.IdComune);

                if (pec == null)
                    throw new Exception(String.Format("PEC CODICE {0} NON TROVATA", codicePec));

                return pec;
            }
        }

        private Init.SIGePro.Data.Movimenti GetMovimento(AuthenticationInfo authInfo, DataBase db, string codiceMovimento)
        {
            if (String.IsNullOrEmpty(codiceMovimento))
                return null;

            var mgr = new MovimentiMgr(db);
            var movimento = mgr.GetById(authInfo.IdComune, Convert.ToInt32(codiceMovimento));

            return movimento;
        }

        private Istanze GetIstanzaFromMovimento(AuthenticationInfo authInfo, DataBase db, string codiceMovimento)
        {
            var movimento = GetMovimento(authInfo, db, codiceMovimento);
            if (movimento == null)
                return null;

            return GetIstanza(authInfo, db, movimento.CODICEISTANZA);
        }

        private string GetCodiceComune(AuthenticationInfo authInfo, string codiceIstanza = "", string codiceMovimento = "")
        {
            var istanza = GetIstanza(authInfo, codiceIstanza, codiceMovimento);
            return GetCodiceComune(istanza);
        }

        private string GetCodiceComune(Istanze istanza)
        {
            if (istanza == null)
                throw new Exception("ISTANZA NON VALORIZZATA");

            if (String.IsNullOrEmpty(istanza.CODICECOMUNE))
                throw new Exception("CODICE COMUNE NON VALORIZZATO");

            return istanza.CODICECOMUNE;
        }

        private string GetSoftware(AuthenticationInfo authInfo, string codiceIstanza = "", string codiceMovimento = "")
        {
            var istanza = GetIstanza(authInfo, codiceIstanza, codiceMovimento);
            return GetSoftware(istanza);
        }

        private string GetSoftware(Istanze istanza)
        {
            if (istanza == null)
                throw new Exception("ISTANZA NON VALORIZZATA");

            if (String.IsNullOrEmpty(istanza.SOFTWARE))
                throw new Exception("SOFTWARE NON VALORIZZATO");

            return istanza.SOFTWARE;
        }
    }
}