using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Protocollo.DocEr.Autenticazione;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.DocEr.Fascicolazione;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Fascicolazione;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Allegati;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiTipiDocumento;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Classifiche;
using Init.SIGePro.Protocollo.DocEr.Pec;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.Registrazione;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.Protocollazione;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_DOCER : ProtocolloBase
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            IAuthenticationService loginSrv = null;

            try
            {
                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                ProtocollazioneValidation.Valida(datiProto);

                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloDocer(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var service = new ProtocollazioneService(vert.UrlProtocollazione, _protocolloLogs, _protocolloSerializer);

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, _protocolloLogs, _protocolloSerializer);
                loginSrv.Login();

                var protocollazione = new Protocollazione(service, vert, loginSrv, datiProto, _protocolloLogs, _protocolloSerializer, this.DatiProtocollo);
                var response = protocollazione.Protocolla();

                try
                {
                    if (protoIn.Flusso == ProtocolloConstants.COD_PARTENZA && vert.TipoInvioPec == Enumeretors.TipoInvioPec.INVIO_AUTOMATICO)
                    {
                        var datiPec = new PecAutomatica(datiProto);
                        var pecAdapter = new SegnaturaPecAdapter(datiPec, _protocolloSerializer);

                        var segnatura = pecAdapter.Adatta();

                        var pecWrapper = new PecService(vert.UrlPec, _protocolloLogs, _protocolloSerializer, loginSrv.Token);
                        pecWrapper.InvioPec(protocollazione.IdUnitaDocumentale, segnatura);
                    }
                }
                catch (System.Exception ex)
                {
                    _protocolloLogs.WarnFormat("ERRORE GENERATO DURANTE L'INVIO DELLA PEC, ERRORE: {0}", ex.Message);
                }

                var responseAdapter = new ProtocollazioneResponseAdapter(response, _protocolloLogs);
                return responseAdapter.Adatta(protocollazione.IdUnitaDocumentale);
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override ListaFascicoli GetFascicoli(Fascicolo fascicolo)
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloDocer(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, _protocolloLogs, _protocolloSerializer);
                loginSrv.Login();

                var fascMetadataAdapter = new GestioneDocumentaleFascicoloMetadataAdapter(fascicolo, vert.CodiceEnte, vert.CodiceAoo);
                var metadati = fascMetadataAdapter.Adatta();

                var gestDocService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, _protocolloLogs, _protocolloSerializer);
                var response = gestDocService.SearchFascicoli(metadati);

                var adapterResponse = new GestioneDocumentaleCercaFascicoliResponseAdapter(response);

                return adapterResponse.Adatta();
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloDocer(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, _protocolloLogs, _protocolloSerializer);
                loginSrv.Login();

                var docService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, _protocolloLogs, _protocolloSerializer);

                if (String.IsNullOrEmpty(idProtocollo))
                {
                    idProtocollo = docService.CercaProtocollo(numeroProtocollo, annoProtocollo, vert.CodiceEnte, vert.CodiceAoo, vert.PadNumeroProtocolloLength, vert.PadNumeroProtocolloChar);
                    
                    if (String.IsNullOrEmpty(idProtocollo))
                        throw new Exception("IL PROTOCOLLO INDICATO NON ESISTE");
                }

                var adapter = new LeggiDocumentoResponseMetadataAdapter(docService, idProtocollo, _protocolloLogs, _protocolloSerializer);

                return adapter.Adatta();
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override AllOut LeggiAllegato()
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloDocer(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, _protocolloLogs, _protocolloSerializer);
                loginSrv.Login();

                var docService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, _protocolloLogs, _protocolloSerializer);
                var adapter = new DownloadDocumentoResponseAdapter(docService, IdAllegato);

                return adapter.Adatta();
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }

        }

        public override DatiFascicolo CambiaFascicolo(Fascicolo fascicolo)
        {
            return Fascicola(fascicolo);
        }

        public override DatiProtocolloFascicolato IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            IAuthenticationService loginSrv = null;
            try
            {

                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloDocer(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, _protocolloLogs, _protocolloSerializer);
                loginSrv.Login();

                var gestDocService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, _protocolloLogs, _protocolloSerializer);

                if (String.IsNullOrEmpty(idProtocollo))
                    idProtocollo = gestDocService.CercaProtocollo(numeroProtocollo, annoProtocollo, vert.CodiceEnte, vert.CodiceAoo, vert.PadNumeroProtocolloLength.Value, vert.PadNumeroProtocolloChar);

                var adapter = new IsFascicolatoResponseAdapter(gestDocService, idProtocollo);

                return adapter.Adatta();
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override DatiFascicolo Fascicola(Fascicolo fascicolo)
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloDocer(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, _protocolloLogs, _protocolloSerializer);
                loginSrv.Login();

                var gestDocService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, _protocolloLogs, _protocolloSerializer);
                var fascicolazioneService = new FascicolazioneService(vert.UrlFascicolazione, loginSrv.Token, _protocolloLogs, _protocolloSerializer);

                IEnumerable<Movimenti> movimentiProtocollati = null;

                if(this.DatiProtocollo.TipoAmbito != ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO)
                {
                    var movimentiMgr = new MovimentiMgr(DatiProtocollo.Db);
                    movimentiProtocollati = movimentiMgr.GetMovimentiProtocollati(this.DatiProtocollo.IdComune, this.DatiProtocollo.CodiceIstanza);
                }

                var factory = FascicolazioneFactory.Create(this.IdProtocollo, this.NumProtocollo, this.AnnoProtocollo, new FascicolazioneConfiguration(loginSrv, vert, gestDocService, fascicolazioneService, fascicolo, this.DatiProtocollo.TipoAmbito, this.DatiProtocollo.Istanza, this.DatiProtocollo.Movimento, movimentiProtocollati));

                var response = factory.Fascicola(new FascicolazioneRequestAdapter(vert, _protocolloSerializer));
                return response;
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override DatiProtocolloRes Registrazione(string registro, DatiProtocolloIn protoIn)
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloDocer(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var service = new RegistrazioneParticolareService(vert.UrlRegParticolare, _protocolloLogs, _protocolloSerializer);

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, _protocolloLogs, _protocolloSerializer);
                loginSrv.Login();

                var registrazione = new Registrazione(service, registro, vert, loginSrv, datiProto, _protocolloLogs, _protocolloSerializer, this.DatiProtocollo);
                var response = registrazione.Registra();

                var responseAdapter = new RegistrazioneResponseAdapter(response, _protocolloLogs);
                return responseAdapter.Adatta(registrazione.IdUnitaDocumentale);

            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA REGISTRAZIONE PARTICOLARE", ex);
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override CreaUnitaDocumentaleResponse CreaUnitaDocumentale(string tipoDocumento, IEnumerable<ProtocolloAllegati> allegati)
        {
            IAuthenticationService loginSrv = null;

            try
            {
                if (allegati.Count() == 0)
                    throw new Exception("ALLEGATI NON PRESENTI");

                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloDocer(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, _protocolloLogs, _protocolloSerializer);
                loginSrv.Login();

                var docService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, _protocolloLogs, _protocolloSerializer);

                long unitaDocumentale = docService.InserisciDocumentoPrimario(loginSrv, allegati.First(), tipoDocumento, vert.CodiceEnte, vert.CodiceAoo, vert.TipoDocumentoPrincipale, DatiProtocollo);

                if (allegati.Count() > 1)
                    docService.InserisciDocumentiAllegati(loginSrv, unitaDocumentale.ToString(), allegati.Skip(1), tipoDocumento, vert.CodiceEnte, vert.CodiceAoo, vert.TipoDocumentoAllegato, DatiProtocollo);

                return new CreaUnitaDocumentaleResponse { UnitaDocumentale = unitaDocumentale.ToString() };
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA CREAZIONE DELL'UNITA' DOCUMENTALE", ex);
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override ListaTipiClassifica GetClassifiche()
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloDocer(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, _protocolloLogs, _protocolloSerializer);
                loginSrv.Login();

                var metadatiAdapter = new LeggiTitolarioMetadataAdapter(vert.CodiceEnte, vert.CodiceAoo);
                var requestTitolario = metadatiAdapter.Adatta();

                var service = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, _protocolloLogs, _protocolloSerializer);
                var response = service.GetClassifiche(requestTitolario);

                var responseAdapter = new LeggiTitolarioResponseAdapter(response);

                var retVal = responseAdapter.Adatta();
                return retVal;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DEL TITOLARIO", ex);
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override void InvioPec(string idProtocollo, string numeroProtocollo, string annoProtocollo)
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloDocer(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, _protocolloLogs, _protocolloSerializer);
                loginSrv.Login();

                var gestDocWrapper = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, _protocolloLogs, _protocolloSerializer);
                var datiPec = new PecManuale(idProtocollo, gestDocWrapper, _protocolloSerializer);
                var pecAdapter = new SegnaturaPecAdapter(datiPec, _protocolloSerializer);

                var segnatura = pecAdapter.Adatta();

                var pecWrapper = new PecService(vert.UrlPec, _protocolloLogs, _protocolloSerializer, loginSrv.Token);
                pecWrapper.InvioPec(Convert.ToInt32(idProtocollo), segnatura);
            }
            catch (System.Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE L'INVIO PEC", ex);
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }
    }
}
