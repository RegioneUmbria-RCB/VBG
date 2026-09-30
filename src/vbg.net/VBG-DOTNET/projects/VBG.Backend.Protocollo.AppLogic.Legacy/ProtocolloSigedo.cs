using Init.SIGePro.Manager.Authentication;
using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Adapters;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.AggiungiDocumenti;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Builders;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Configurations;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.PresaInCarico;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Services;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Smistamenti;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers;
using VBG.Backend.Protocollo.AppLogic.Shared.Metadati;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_SIGEDO : ProtocolloBase
    {
        public static class Constants
        {
            public const string AREA_CLASSIFICA = "SEGRETERIA";
            public const string MODELLO_CLASSIFICA = "DIZ_CLASSIFICAZIONE";
            public const string STATO_CLASSIFICA = "BO";
            public const string MODELLO_SMISTAMENTI = "M_SMISTAMENTO";
        }


        private SigedoVerticalizzazioneParametriAdapter _params;
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_SIGEDO(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            this._params = new SigedoVerticalizzazioneParametriAdapter(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSigedo>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            if (!this._params.UsaWsClassifiche)
                return base.GetClassifiche();

            this._params = new SigedoVerticalizzazioneParametriAdapter(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSigedo>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var querySrv = new SigedoQueryService(this._protocolloLogs, this._protocolloSerializer, this._params.UrlQueryService, this._params.UsernameQueryService, this._params.PasswordQueryService);
            var adapter = new SigedoClassificheResponseAdapter(querySrv);
            return adapter.Adatta(Constants.AREA_CLASSIFICA, Constants.MODELLO_CLASSIFICA, Constants.STATO_CLASSIFICA, this._params.OperatoreRicercheClassifiche, this.Operatore);
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            this._protocolloLogs.Debug("Protocollo SIGEDO - Protocollazione");
            this._params = new SigedoVerticalizzazioneParametriAdapter(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSigedo>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            var protoSrv = new SigedoProtocollazioneService(this._params.UrlProto, this._protocolloLogs, this._protocolloSerializer);
            var token = protoSrv.Login(this._params.CodiceEnte, this._params.UsernameWs, this._params.PasswordWs);

            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var uoSmistamento = this._params.Uo;

            var smistamentoConf = new SmistamentoConfiguration(this._params.UrlCgiUo, this.Provenienza, this.Operatore, this.DatiProtocollo, this._protocolloLogs, this._protocolloSerializer, datiProto);
            var smistamentoFlusso = SmistamentoFlussoFactory.Create(smistamentoConf, protoIn.Flusso);
            uoSmistamento = smistamentoFlusso.GetUoSmistamento(this._verticalizzazioniFactory);

            this._protocolloLogs.InfoFormat("UO SMISTAMENTO: {0}", uoSmistamento);

            var conf = new SigedoSegnaturaParamConfiguration(this._params, protoIn.TipoSmistamento, this.Operatore, protoIn.Classifica, protoIn.Oggetto, protoIn.Flusso, uoSmistamento, datiProto.AltriDestinatariInterni);
            var segnaturaBuilder = new SigedoSegnaturaBuilder(datiProto, conf, this._protocolloLogs, this._protocolloSerializer);

            if (this._params.InviaSegnatura && !protoIn.HaAllegati())
            {
                protoIn.AggiungiAllegato(segnaturaBuilder.CreaSegnaturaFittizia());
            }

            protoSrv.InserisciAllegati(protoIn.RecuperaAllegati(), token, this._params.UsernameWs);

            var segn = segnaturaBuilder.GetSegnatura(protoIn.RecuperaAllegati());

            var response = protoSrv.Protocollazione(this._params.UsernameWs, token);

            var adapter = new SigedoProtocolloInsertOutputAdapter(response);
            var retVal = adapter.Adatta();

            if (this._params.UsaSmistamentoAction && this.DatiProtocollo.TipoAmbito != AmbitoProtocollazioneEnum.DA_PANNELLO_PEC)
            {
                if (protoIn.Flusso != ProtocolloConstants.COD_ARRIVO)
                    return retVal;

                var vertAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);
                var codiceOperatore = smistamentoConf.Provenienza == TipoProvenienza.ONLINE ? this.DatiProtocollo.CodiceResponsabileProcedimentoIstanza.Value : this.DatiProtocollo.CodiceResponsabileUtenteLoggato;
                var operatore = OperatoreProtocolloFactory.Create(this.DatiProtocollo.Db, vertAttivo.Operatore, codiceOperatore, this.DatiProtocollo.IdComune);
                var adapterSmistamento = new SmistamentoActionSegnaturaAdapter(retVal.NumeroProtocollo, retVal.AnnoProtocollo, this._params.CodiceAoo, this._params.CodiceAmministrazione, this._params.TipoRegistro, uoSmistamento, operatore.CodiceOperatore, this._params.ApplicativoProtocollo);

                try
                {
                    var presaInCaricoSegnatura = adapterSmistamento.AdattaCarico();
                    protoSrv.PresaInCarico(this._params.UsernameWs, token, presaInCaricoSegnatura);
                }
                catch (Exception ex)
                {
                    this._protocolloLogs.WarnFormat("Errore generato durante la presa in carico, dettaglio errore: {0}", ex.Message);
                    retVal.Warning = this._protocolloLogs.Warnings.WarningMessage;
                }
                finally { }

                try
                {
                    var eseguitoSegnatura = adapterSmistamento.AdattaEseguito();
                    protoSrv.Eseguito(this._params.UsernameWs, token, eseguitoSegnatura);
                }
                catch (Exception ex)
                {
                    this._protocolloLogs.WarnFormat("Errore generato durante l'eseguito, dettaglio errore: {0}", ex.Message);
                    retVal.Warning = this._protocolloLogs.Warnings.WarningMessage;
                }
                finally { }

            }

            return retVal;
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            this._protocolloLogs.DebugFormat("Protocollo SIGEDO - LeggiProtocollo. IdProtocollo {0}", leggiProtocolloRequest.IdProtocollo);
            if (!String.IsNullOrEmpty(leggiProtocolloRequest.IdProtocollo))
            {
                var sigedoToSigeproAdapter = new SigedoToSigeproAdapter(this.DatiProtocollo);
                var protoSigepro = sigedoToSigeproAdapter.Adatta();
                var protoLetto = protoSigepro.LeggiProtocollo(leggiProtocolloRequest);
                return protoLetto;
            }
            else
            {
                this._params = new SigedoVerticalizzazioneParametriAdapter(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSigedo>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

                this._protocolloLogs.DebugFormat("UrlQueryService: {0}, UsernameQueryService: {1}, PasswordQueryService: {2}", this._params.UrlQueryService, this._params.UsernameQueryService, this._params.PasswordQueryService);
                var querySrv = new SigedoQueryService(this._protocolloLogs, this._protocolloSerializer, this._params.UrlQueryService, this._params.UsernameQueryService, this._params.PasswordQueryService);
                this._protocolloLogs.DebugFormat("AreaLeggiProto: {0}, ModelloLeggiProto: {1}, Stato: {2}, Operatore: {3}, OperatoreRicerche: {4}", this._params.AreaLeggiProto, this._params.ModelloLeggiProto, this._params.Stato, this.Operatore, this._params.OperatoreRicerche);
                var responseProtocollo = querySrv.LeggiProtocollo(this._params.AreaLeggiProto, this._params.ModelloLeggiProto, this._params.Stato, this.Operatore, this._params.OperatoreRicerche, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo);


                if (responseProtocollo.listaDocumenti?.Length == 0)
                    throw new Exception(String.Format("IL PROTOCOLLO NUMERO {0} ANNO {1} NON E' STATO TROVATO", leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo));

                var adapter = new SigedoProtocolloLettoAdapter(responseProtocollo, this.DatiProtocollo.Db);
                var idRif = adapter.GetIdRiferimento();

                this._protocolloLogs.DebugFormat("idRiferimento: {0}", idRif);

                var dati = new DatiProtocolloLettoResponseType();
                adapter.CreaDatiProtocollo(responseProtocollo, dati);
                this._protocolloLogs.Debug("CreaDatiProtocollo: DONE");

                var responseInCaricoA = querySrv.LeggiSmistamenti(this._params.AreaLeggiProto, Constants.MODELLO_SMISTAMENTI, this._params.Stato, this.Operatore, this._params.OperatoreRicerche, idRif);
                adapter.CreaInCaricoA(responseInCaricoA, dati, this._params.DescrizioneEnte);
                this._protocolloLogs.Debug("CreaInCaricoA: DONE");

                var responseAllegati = querySrv.LeggiAllegati(this._params.AreaAllegati, this._params.ModelloAllegati, this._params.Stato, this.Operatore, this._params.OperatoreRicerche, idRif);
                adapter.CreaDatiAllegatiSecondari(responseAllegati, dati);
                this._protocolloLogs.Debug("CreaDatiAllegatiSecondari: DONE");

                var responseMittentiDestinatari = querySrv.LeggiMittentiDestinatari(this._params.AreaSoggetti, this._params.ModelloSoggetti, this._params.Stato, this.Operatore, this._params.OperatoreRicerche, idRif);
                adapter.CreaDatiMittentiDestinatari(responseMittentiDestinatari, dati, this._params.AreaSoggetti, this._params.DescrizioneEnte);
                this._protocolloLogs.Debug("CreaDatiMittentiDestinatari: DONE");

                var responseClassifica = querySrv.LeggiClassifica(Constants.AREA_CLASSIFICA, Constants.MODELLO_CLASSIFICA, Constants.STATO_CLASSIFICA, this._params.OperatoreRicerche, dati.Classifica, this.Operatore);
                adapter.SetDescrizioneClassifica(responseClassifica, dati);
                this._protocolloLogs.Debug("SetDescrizioneClassifica: DONE");

                return new List<DatiProtocolloLettoResponseType>() { dati };
            }
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            if (!String.IsNullOrEmpty(this.IdProtocollo) && this.IdProtocollo != "0")
            {
                var adapter = new SigedoToSigeproAdapter(this.DatiProtocollo, this.IdAllegato);
                return adapter.Adatta().LeggiAllegato();
            }
            else
            {
                this._params = new SigedoVerticalizzazioneParametriAdapter(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSigedo>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
                var all = new SigedoAllegatiService(this._protocolloLogs, this._protocolloSerializer, this._params.UrlWsAllegati, this._params.UsernameWsAllegati, this._params.PasswordWsAllegati);
                var response = all.GetAllegato(Convert.ToInt32(this.IdAllegato), this.Operatore);

                var allOut = new AllegatoResponseType
                {
                    Image = response.allegato,
                    Commento = response.descrizione,
                    IDBase = response.idAllegato.ToString(),
                    TipoFile = String.Concat(".", response.tipoAllegato),
                    Serial = response.descrizione
                };

                return allOut;
            }
        }

        public override void AggiungiAllegati(string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, IEnumerable<ProtocolloAllegati> allegati)
        {
            this._params = new SigedoVerticalizzazioneParametriAdapter(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSigedo>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var protoSrv = new SigedoProtocollazioneService(this._params.UrlProto, this._protocolloLogs, this._protocolloSerializer);
            var token = protoSrv.Login(this._params.CodiceEnte, this._params.UsernameWs, this._params.PasswordWs);

            protoSrv.InserisciAllegati(allegati.ToList(), token, this._params.UsernameWs);

            if (!dataProtocollo.HasValue)
                throw new Exception("DATA PROTOCOLLO NON VALORIZZATA");

            var segnatura = AggiungiDocumentiAdapter.Adatta(allegati, this._params, numeroProtocollo, dataProtocollo.Value.Year.ToString(), this.Operatore);

            protoSrv.AggiungiAllegatiaProtocollo(token, this._params.UsernameWs, segnatura);
        }

        public override EseguiAccettazioneResponseType EseguiAccettazione(AuthenticationInfo auth, string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            this._protocolloLogs.Debug("Protocollo SIGEDO - EseguiAccettazione");
            this._params = new SigedoVerticalizzazioneParametriAdapter(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSigedo>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            this._protocolloLogs.DebugFormat("UrlProto: {0}, CodiceEnte: {1}, UsernameWs: {2}, PasswordWs: {3}", this._params.UrlProto, this._params.CodiceEnte, this._params.UsernameWs, this._params.PasswordWs);

            var protoSrv = new SigedoProtocollazioneService(this._params.UrlProto, this._protocolloLogs, this._protocolloSerializer);
            var token = protoSrv.Login(this._params.CodiceEnte, this._params.UsernameWs, this._params.PasswordWs);

            var request = new LeggiProtocolloRequest()
            {
                IdProtocollo = idProtocollo,
                AnnoProtocollo = null,
                NumeroProtocollo = null,
            };

            if (string.IsNullOrEmpty(idProtocollo))
            {
                request.IdProtocollo = null;
                request.AnnoProtocollo = annoProtocollo;
                request.NumeroProtocollo = numeroProtocollo;
            }

            var protocolli = this.LeggiProtocollo(request);

            this._protocolloLogs.DebugFormat("Protocolli recuperati: {0}", protocolli.Count);

            var protocollo = protocolli.FirstOrDefault();
            var flusso = "";

            switch (protocollo.Origine)
            {
                case "E":
                case "A":
                    flusso = ProtocolloConstants.COD_ARRIVO;
                    break;
                case "U":
                case "P":
                    flusso = ProtocolloConstants.COD_PARTENZA;
                    break;
                default:
                    flusso = ProtocolloConstants.COD_INTERNO;
                    break;
            }

            this._protocolloLogs.DebugFormat("UrlCgiUo: {0}, Provenienza: {1}, Operatore: {2}", this._params.UrlCgiUo, this.Provenienza, this.Operatore);
            var smistamentoConf = new SmistamentoConfiguration(this._params.UrlCgiUo, this.Provenienza, this.Operatore, this.DatiProtocollo, this._protocolloLogs, this._protocolloSerializer, null /*datiProto*/);
            //var smistamentoConf = new SmistamentoConfiguration(_params.UrlCgiUo, Provenienza, Operatore, DatiProtocollo, _protocolloLogs, _protocolloSerializer, datiProto);
            var smistamentoFlusso = SmistamentoFlussoFactory.Create(smistamentoConf, flusso);

            var uoSmistamento = smistamentoFlusso.GetUoSmistamento(this._verticalizzazioniFactory);

            this._protocolloLogs.DebugFormat("flusso: {0}, uoSmistamento: {1}", flusso, uoSmistamento);

            var vertAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);
            var codiceOperatore = smistamentoConf.Provenienza == TipoProvenienza.ONLINE ? this.DatiProtocollo.CodiceResponsabileProcedimentoIstanza.Value : this.DatiProtocollo.CodiceResponsabileUtenteLoggato;
            var operatore = OperatoreProtocolloFactory.Create(this.DatiProtocollo.Db, vertAttivo.Operatore, codiceOperatore, this.DatiProtocollo.IdComune);
            var adapterSmistamento = new SmistamentoActionSegnaturaAdapter(numeroProtocollo, annoProtocollo, this._params.CodiceAoo, this._params.CodiceAmministrazione, this._params.TipoRegistro, uoSmistamento, operatore.CodiceOperatore, this._params.ApplicativoProtocollo);

            try
            {
                var presaInCaricoSegnatura = adapterSmistamento.AdattaCarico();
                protoSrv.PresaInCarico(this._params.UsernameWs, token, presaInCaricoSegnatura);
            }
            catch (Exception ex)
            {
                this._protocolloLogs.WarnFormat("Errore generato durante la presa in carico, dettaglio errore: {0}", ex.Message);
                return new EseguiAccettazioneResponseType(this._protocolloLogs.Warnings.WarningMessage, ex);
            }

            try
            {
                var eseguitoSegnatura = adapterSmistamento.AdattaEseguito();
                protoSrv.Eseguito(this._params.UsernameWs, token, eseguitoSegnatura);
            }
            catch (Exception ex)
            {
                this._protocolloLogs.WarnFormat("Errore generato durante l'eseguito, dettaglio errore: {0}", ex.Message);
                return new EseguiAccettazioneResponseType(this._protocolloLogs.Warnings.WarningMessage, ex);
            }

            this._protocolloLogs.Debug("Ricerca ambito accettazione");
            using (var db = auth.CreateDatabase())
            {
                var idProtocolloVuoto = string.IsNullOrEmpty(idProtocollo);

                var ambito = idProtocolloVuoto
                    ? new AmbitoMgr(db).GetAmbitoProtocollazione(auth.IdComune, numeroProtocollo, annoProtocollo)
                    : new AmbitoMgr(db).GetAmbitoProtocollazione(auth.IdComune, idProtocollo);

                this._protocolloLogs.DebugFormat(
                    "Salvataggio accettazione {0} per {1}: {2}",
                    ambito.Item1,
                    idProtocolloVuoto ? "numeroProtocollo" : "idProtocollo",
                    idProtocolloVuoto ? numeroProtocollo : idProtocollo);

                MetadatiManagerFactory.Create(ambito.Item1, db).SetProtocolloEsitato(auth.IdComune, ambito.Item2);
            }

            return new EseguiAccettazioneResponseType()
            {
                Status = EnumStatusType.OK
            };
        }

        public override DatiProtocolloEsitatoResponseType IsEsitato(AuthenticationInfo auth, string idProtocollo)
        {
            this._protocolloLogs.DebugFormat("Protocollo SIGEDO metodo IsEsitato - idComune {0}, idProtocollo {1}", auth.IdComune, idProtocollo);
            var isEsitato = false;

            using (var db = auth.CreateDatabase())
            {
                var ambito = new AmbitoMgr(db).GetAmbitoProtocollazione(auth.IdComune, idProtocollo);

                isEsitato = this.IsEsitatoInternal(db, auth.IdComune, ambito.Item1, ambito.Item2);
            }

            return new DatiProtocolloEsitatoResponseType()
            {
                Esitato = isEsitato ? EnumEsitatoType.si : EnumEsitatoType.no
            };
        }

        public override DatiProtocolloEsitatoResponseType IsEsitato(AuthenticationInfo auth, string annoProtocollo, string numeroProtocollo)
        {
            this._protocolloLogs.DebugFormat("Protocollo SIGEDO metodo IsEsitato - idComune {0}, numeroProtocollo {1}, annoProtocollo {2}", auth.IdComune, numeroProtocollo, annoProtocollo);
            var isEsitato = false;

            using (var db = auth.CreateDatabase())
            {
                var ambito = new AmbitoMgr(db).GetAmbitoProtocollazione(auth.IdComune, numeroProtocollo, annoProtocollo);

                isEsitato = this.IsEsitatoInternal(db, auth.IdComune, ambito.Item1, ambito.Item2);
            }

            return new DatiProtocolloEsitatoResponseType()
            {
                Esitato = isEsitato ? EnumEsitatoType.si : EnumEsitatoType.no
            };
        }

        private bool IsEsitatoInternal(DataBase db, string idComune, AmbitoProtocollazioneEnum ambito, int codice)
        {
            this._protocolloLogs.DebugFormat("ambito {0} - codice {1}", ambito, codice);

            switch (ambito)
            {
                case AmbitoProtocollazioneEnum.DA_ISTANZA:
                    {
                        return new IstanzaMetadatiManager(db).IsProtocolloEsitato(idComune, codice);
                    }
                case AmbitoProtocollazioneEnum.DA_MOVIMENTO:
                    {
                        return new MovimentoMetadatiManager(db).IsProtocolloEsitato(idComune, codice);
                    }
                case AmbitoProtocollazioneEnum.DA_AUTORIZZAZIONE:
                    {
                        return new AutorizzazioneMetadatiManager(db).IsProtocolloEsitato(idComune, codice);
                    }
                default:
                    throw new Exception("IL PROTOCOLLO NON E' ASSOCIATO NÉ AD UN'ISTANZA NÉ AD UN MOVIMENTO NE AD UN'AUTORIZZAZIONE");
            }
        }
    }
}
