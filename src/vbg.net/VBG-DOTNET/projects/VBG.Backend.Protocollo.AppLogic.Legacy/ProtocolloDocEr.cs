using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Autenticazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.Classifiche;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.LeggiDocumento;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.LeggiDocumento.Allegati;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Pec;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione.Registrazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_DOCER : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_DOCER(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            IAuthenticationService loginSrv = null;

            try
            {
                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                ProtocollazioneValidation.Valida(datiProto);

                var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocer>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
                var service = new ProtocollazioneService(vert.UrlProtocollazione, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, this._protocolloLogs, this._protocolloSerializer, base.Provenienza, this._bindingFactory);
                loginSrv.Login();

                var protocollazione = new Protocollazione(service, vert, loginSrv, datiProto, this._protocolloLogs, this._protocolloSerializer, this.DatiProtocollo, this._bindingFactory);
                var response = protocollazione.Protocolla();

                try
                {
                    if (protoIn.Flusso == ProtocolloConstants.COD_PARTENZA && vert.TipoInvioPec == Enumeretors.TipoInvioPec.INVIO_AUTOMATICO)
                    {
                        var gestDocWrapper = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                        var datiPec = new DatiPec(protocollazione.IdUnitaDocumentale.ToString(), gestDocWrapper, this._protocolloSerializer);
                        var pecAdapter = new SegnaturaPecAdapter(datiPec, vert, this._protocolloSerializer);

                        var segnatura = pecAdapter.Adatta();

                        var pecWrapper = new PecService(vert.UrlPec, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory, loginSrv.Token);
                        pecWrapper.InvioPec(protocollazione.IdUnitaDocumentale, segnatura);
                    }
                }
                catch (System.Exception ex)
                {
                    this._protocolloLogs.WarnFormat("ERRORE GENERATO DURANTE L'INVIO DELLA PEC, ERRORE: {0}", ex.Message);
                }

                var responseAdapter = new ProtocollazioneResponseAdapter(response, this._protocolloLogs);
                return responseAdapter.Adatta(protocollazione.IdUnitaDocumentale);
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override ListaFascicoliResponseType GetFascicoli(Fascicolo fascicolo)
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocer>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, this._protocolloLogs, this._protocolloSerializer, base.Provenienza, this._bindingFactory);
                loginSrv.Login();

                var fascMetadataAdapter = new GestioneDocumentaleFascicoloMetadataAdapter(fascicolo, vert.CodiceEnte, vert.CodiceAoo);
                var metadati = fascMetadataAdapter.Adatta();

                var gestDocService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
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

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocer>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, this._protocolloLogs, this._protocolloSerializer, base.Provenienza, this._bindingFactory);
                loginSrv.Login();

                var docService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);

                if (String.IsNullOrEmpty(leggiProtocolloRequest.IdProtocollo))
                {
                    leggiProtocolloRequest.IdProtocollo = docService.CercaProtocollo(leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, vert.CodiceEnte, vert.CodiceAoo, vert.PadNumeroProtocolloLength, vert.PadNumeroProtocolloChar);

                    if (String.IsNullOrEmpty(leggiProtocolloRequest.IdProtocollo))
                        throw new Exception("IL PROTOCOLLO INDICATO NON ESISTE");
                }

                var info = new LeggiDocumentoInfo(docService, leggiProtocolloRequest.IdProtocollo, this._protocolloLogs, this._protocolloSerializer, base.EstraiEml, base.EscludiFileDaEml, base.EstraiZip, base.ZipExtensions);
                var adapter = new LeggiDocumentoResponseMetadataAdapter(info);

                return new List<DatiProtocolloLettoResponseType>() { adapter.Adatta() };
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocer>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, this._protocolloLogs, this._protocolloSerializer, base.Provenienza, this._bindingFactory);
                loginSrv.Login();

                var docService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                var adapter = new DownloadDocumentoResponseAdapter(docService, this.IdAllegato, base.EstraiEml, base.EscludiFileDaEml, base.EstraiZip, base.ZipExtensions);

                return adapter.Adatta();
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }

        }

        public override DatiFascicoloResponseType CambiaFascicolo(Fascicolo fascicolo)
        {
            this._protocolloLogs.InfoFormat("INIZIO FUNZIONALITA' CAMBIA FASCICOLO DI DOCER");

            IAuthenticationService loginSrv = null;

            try
            {
                var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocer>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

                var idProtocollo = this.DatiProtocollo.Istanza.FKIDPROTOCOLLO;

                //var numeroProtocollo = this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO;
                //var dataProtocollo = this.DatiProtocollo.Istanza.DATAPROTOCOLLO;

                if (!this.DatiProtocollo.Istanza.DATAPROTOCOLLO.HasValue)
                {
                    throw new Exception("DATA PROTOCOLLO NON PRESENTE");
                }

                var annoProtocollo = this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.ToString("yyyy");

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, this._protocolloLogs, this._protocolloSerializer, base.Provenienza, this._bindingFactory);
                loginSrv.Login();

                var docService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);

                if (String.IsNullOrEmpty(idProtocollo))
                {
                    idProtocollo = docService.CercaProtocollo(this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, annoProtocollo, vert.CodiceEnte, vert.CodiceAoo, vert.PadNumeroProtocolloLength, vert.PadNumeroProtocolloChar);

                    if (String.IsNullOrEmpty(idProtocollo))
                        throw new Exception("IL PROTOCOLLO INDICATO NON ESISTE");
                }

                var response = docService.LeggiDocumento(idProtocollo);
                var dic = response.ToDictionary(x => x.key, y => y.value);

                fascicolo.Oggetto = dic[LeggiDocumentoConstants.DescrizioneFascicolo];

                var datiFascicolazione = this.Fascicola(fascicolo);

                this._protocolloLogs.InfoFormat("FINE FUNZIONALITA' CAMBIA FASCICOLO DI DOCER");
                return datiFascicolazione;
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }


        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            IAuthenticationService loginSrv = null;
            try
            {

                var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocer>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, this._protocolloLogs, this._protocolloSerializer, base.Provenienza, this._bindingFactory);
                loginSrv.Login();

                var gestDocService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);

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

        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            this._protocolloLogs.InfoFormat("INIZIO FUNZIONALITA' DI FASCICOLAZIONE DI DOCER");

            IAuthenticationService loginSrv = null;
            try
            {
                var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocer>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, this._protocolloLogs, this._protocolloSerializer, base.Provenienza, this._bindingFactory);
                loginSrv.Login();

                var gestDocService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                var fascicolazioneService = new FascicolazioneService(vert.UrlFascicolazione, loginSrv.Token, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);

                IEnumerable<Movimenti> movimentiProtocollati = null;

                if (this.DatiProtocollo.TipoAmbito != AmbitoProtocollazioneEnum.NESSUNO)
                {
                    var movimentiMgr = new MovimentiMgr(this.DatiProtocollo.Db);
                    movimentiProtocollati = movimentiMgr.GetMovimentiProtocollati(this.DatiProtocollo.IdComune, this.DatiProtocollo.CodiceIstanza);
                }

                var factory = FascicolazioneFactory.Create(this.IdProtocollo, this.NumProtocollo, this.AnnoProtocollo, new FascicolazioneConfiguration(loginSrv, vert, gestDocService, fascicolazioneService, fascicolo, this.DatiProtocollo.TipoAmbito, this.DatiProtocollo.Istanza, this.DatiProtocollo.Movimento, movimentiProtocollati));

                var response = factory.Fascicola(new FascicolazioneRequestAdapter(vert, this._protocolloSerializer));

                this._protocolloLogs.InfoFormat("FINE FUNZIONALITA' DI FASCICOLAZIONE DI DOCER");

                return response;
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override DatiProtocolloResponseType Registrazione(string registro, DatiProtocolloIn protoIn)
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocer>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
                var service = new RegistrazioneParticolareService(vert.UrlRegParticolare, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, this._protocolloLogs, this._protocolloSerializer, base.Provenienza, this._bindingFactory);
                loginSrv.Login();

                var registrazione = new Registrazione(service, registro, vert, loginSrv, datiProto, this._protocolloLogs, this._protocolloSerializer, this.DatiProtocollo, this._bindingFactory);
                var response = registrazione.Registra();

                var responseAdapter = new RegistrazioneResponseAdapter(response, this._protocolloLogs);
                return responseAdapter.Adatta(registrazione.IdUnitaDocumentale);

            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA REGISTRAZIONE PARTICOLARE", ex);
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override CreaUnitaDocumentaleResponseType CreaUnitaDocumentale(string tipoDocumento, IEnumerable<ProtocolloAllegati> allegati)
        {
            IAuthenticationService loginSrv = null;

            try
            {
                if (allegati.Count() == 0)
                    throw new Exception("ALLEGATI NON PRESENTI");

                var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocer>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, this._protocolloLogs, this._protocolloSerializer, base.Provenienza, this._bindingFactory);
                loginSrv.Login();

                var docService = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);

                var unitaDocumentale = docService.InserisciDocumentoPrimario(loginSrv, allegati.First(), tipoDocumento, vert.CodiceEnte, vert.CodiceAoo, vert.TipoDocumentoPrincipale, this.DatiProtocollo, vert.DisabilitaMetadati);

                if (allegati.Count() > 1)
                    docService.InserisciDocumentiAllegati(loginSrv, unitaDocumentale.ToString(), allegati.Skip(1), tipoDocumento, vert.CodiceEnte, vert.CodiceAoo, vert.TipoDocumentoAllegato, this.DatiProtocollo, vert.DisabilitaMetadati);

                return new CreaUnitaDocumentaleResponseType { UnitaDocumentale = unitaDocumentale.ToString() };
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA CREAZIONE DELL'UNITA' DOCUMENTALE", ex);
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            IAuthenticationService loginSrv = null;
            try
            {
                var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocer>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, this._protocolloLogs, this._protocolloSerializer, base.Provenienza, this._bindingFactory);
                loginSrv.Login();

                var metadatiAdapter = new LeggiTitolarioMetadataAdapter(vert.CodiceEnte, vert.CodiceAoo);
                var requestTitolario = metadatiAdapter.Adatta();

                var service = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                var response = service.GetClassifiche(requestTitolario);

                var responseAdapter = new LeggiTitolarioResponseAdapter(response);

                var retVal = responseAdapter.Adatta();
                return retVal;
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DEL TITOLARIO", ex);
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
                var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocer>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

                loginSrv = AuthenticationServiceFactory.Create(this.DatiProtocollo, vert, this._protocolloLogs, this._protocolloSerializer, base.Provenienza, this._bindingFactory);
                loginSrv.Login();

                var gestDocWrapper = new GestioneDocumentaleService(vert.UrlGestioneDocumentale, loginSrv.Token, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                var datiPec = new DatiPec(idProtocollo, gestDocWrapper, this._protocolloSerializer);
                var pecAdapter = new SegnaturaPecAdapter(datiPec, vert, this._protocolloSerializer);

                var segnatura = pecAdapter.Adatta();

                var pecWrapper = new PecService(vert.UrlPec, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory, loginSrv.Token);
                pecWrapper.InvioPec(Convert.ToInt32(idProtocollo), segnatura);
            }
            catch (System.Exception ex)
            {
                throw this._protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE L'INVIO PEC", ex);
            }
            finally
            {
                if (loginSrv != null)
                    loginSrv.Logout();
            }
        }
    }
}
