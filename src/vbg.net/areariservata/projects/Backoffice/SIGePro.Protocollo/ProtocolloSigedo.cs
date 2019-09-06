using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Sigedo.Services;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Sigedo.Builders;
using Init.SIGePro.Protocollo.Sigedo;
using Init.SIGePro.Protocollo.Sigedo.Proxies;
using Init.SIGePro.Protocollo.Sigedo.Configurations;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Sigedo.Adapters;
using Init.SIGePro.Protocollo.Validation;
using Init.SIGePro.Data;
using System.IO;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Sigedo.Factories;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Sigedo.Smistamenti;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Sigedo.PresaInCarico;
using Init.SIGePro.Protocollo.Sigedo.AggiungiDocumenti;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo
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


        SigedoVerticalizzazioneParametriAdapter _params;

        public PROTOCOLLO_SIGEDO()
        {

        }

        public override ListaTipiClassifica GetClassifiche()
        {
            _params = new SigedoVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloSigedo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            if (!_params.UsaWsClassifiche)
                return base.GetClassifiche();

            _params = new SigedoVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloSigedo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var querySrv = new SigedoQueryService(_protocolloLogs, _protocolloSerializer, _params.UrlQueryService, _params.UsernameQueryService, _params.PasswordQueryService);
            var adapter = new SigedoClassificheResponseAdapter(querySrv);
            return adapter.Adatta(Constants.AREA_CLASSIFICA, Constants.MODELLO_CLASSIFICA, Constants.STATO_CLASSIFICA, _params.OperatoreRicercheClassifiche, Operatore);
        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {

            _params = new SigedoVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloSigedo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var protoSrv = new SigedoProtocollazioneService(_params.UrlProto, _protocolloLogs, _protocolloSerializer);
            string token = protoSrv.Login(_params.CodiceEnte, _params.UsernameWs, _params.PasswordWs);

            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            string uoSmistamento = _params.Uo;

            var smistamentoConf = new SmistamentoConfiguration(_params.UrlCgiUo, Provenienza, Operatore, DatiProtocollo, _protocolloLogs, _protocolloSerializer, datiProto);
            var smistamentoFlusso = SmistamentoFlussoFactory.Create(smistamentoConf, protoIn.Flusso);
            uoSmistamento = smistamentoFlusso.GetUoSmistamento();

            _protocolloLogs.InfoFormat("UO SMISTAMENTO: {0}", uoSmistamento);

            var conf = new SigedoSegnaturaParamConfiguration(_params, protoIn.TipoSmistamento, Operatore, protoIn.Classifica, protoIn.Oggetto, protoIn.Flusso, uoSmistamento, datiProto.AltriDestinatariInterni);
            var segnaturaBuilder = new SigedoSegnaturaBuilder(datiProto, conf, _protocolloLogs, _protocolloSerializer, protoIn.Allegati);

            if (_params.InviaSegnatura && protoIn.Allegati.Count == 0)
                segnaturaBuilder.CreaSegnaturaFittizia();

            protoSrv.InserisciAllegati(protoIn.Allegati, token, _params.UsernameWs);

            var segn = segnaturaBuilder.GetSegnatura();

            var response = protoSrv.Protocollazione(_params.UsernameWs, token);

            var adapter = new SigedoProtocolloInsertOutputAdapter(response);
            var retVal = adapter.Adatta();

            if (_params.UsaSmistamentoAction && DatiProtocollo.TipoAmbito != ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_PANNELLO_PEC)
            {
                if (protoIn.Flusso != ProtocolloConstants.COD_ARRIVO)
                    return retVal;

                var vertAttivo = new VerticalizzazioneProtocolloAttivo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
                var codiceOperatore = smistamentoConf.Provenienza == ProtocolloEnumerators.ProtocolloEnum.TipoProvenienza.ONLINE ? DatiProtocollo.CodiceResponsabileProcedimentoIstanza.Value : DatiProtocollo.CodiceResponsabile;
                var operatore = OperatoreProtocolloFactory.Create(DatiProtocollo.Db, vertAttivo.Operatore, codiceOperatore, DatiProtocollo.IdComune);
                var adapterSmistamento = new SmistamentoActionSegnaturaAdapter(retVal.NumeroProtocollo, retVal.AnnoProtocollo, _params.CodiceAoo, _params.CodiceAmministrazione, _params.TipoRegistro, uoSmistamento, operatore.CodiceOperatore, _params.ApplicativoProtocollo);

                try
                {
                    var presaInCaricoSegnatura = adapterSmistamento.AdattaCarico();
                    protoSrv.PresaInCarico(_params.UsernameWs, token, presaInCaricoSegnatura);
                }
                catch (Exception ex)
                {
                    _protocolloLogs.WarnFormat("Errore generato durante la presa in carico, dettaglio errore: {0}", ex.Message);
                    retVal.Warning = _protocolloLogs.Warnings.WarningMessage;
                }
                finally { }

                try
                {
                    var eseguitoSegnatura = adapterSmistamento.AdattaEseguito();
                    protoSrv.Eseguito(_params.UsernameWs, token, eseguitoSegnatura);
                }
                catch (Exception ex)
                {
                    _protocolloLogs.WarnFormat("Errore generato durante l'eseguito, dettaglio errore: {0}", ex.Message);
                    retVal.Warning = _protocolloLogs.Warnings.WarningMessage;
                }
                finally { }

            }

            return retVal;
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            if (!String.IsNullOrEmpty(idProtocollo))
            {
                var sigedoToSigeproAdapter = new SigedoToSigeproAdapter(DatiProtocollo);
                var protoSigepro = sigedoToSigeproAdapter.Adatta();
                var protoLetto = protoSigepro.LeggiProtocollo(idProtocollo, annoProtocollo, numeroProtocollo);
                return protoLetto;
            }
            else
            {
                _params = new SigedoVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloSigedo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                var querySrv = new SigedoQueryService(_protocolloLogs, _protocolloSerializer, _params.UrlQueryService, _params.UsernameQueryService, _params.PasswordQueryService);
                var responseProtocollo = querySrv.LeggiProtocollo(_params.AreaLeggiProto, _params.ModelloLeggiProto, _params.Stato, Operatore, _params.OperatoreRicerche, numeroProtocollo, annoProtocollo);

                if (responseProtocollo.numeroDocumentiTotali == 0)
                    throw new Exception(String.Format("IL PROTOCOLLO NUMERO {0} ANNO {1} NON E' STATO TROVATO", numeroProtocollo, annoProtocollo));

                var adapter = new SigedoProtocolloLettoAdapter(responseProtocollo, DatiProtocollo.Db);
                string idRif = adapter.GetIdRiferimento();

                var dati = new DatiProtocolloLetto();
                adapter.CreaDatiProtocollo(responseProtocollo, dati);

                var responseInCaricoA = querySrv.LeggiSmistamenti(_params.AreaLeggiProto, Constants.MODELLO_SMISTAMENTI, _params.Stato, Operatore, _params.OperatoreRicerche, idRif);
                adapter.CreaInCaricoA(responseInCaricoA, dati, _params.DescrizioneEnte);

                var responseAllegati = querySrv.LeggiAllegati(_params.AreaAllegati, _params.ModelloAllegati, _params.Stato, Operatore, _params.OperatoreRicerche, idRif);
                adapter.CreaDatiAllegatiSecondari(responseAllegati, dati);

                var responseMittentiDestinatari = querySrv.LeggiMittentiDestinatari(_params.AreaSoggetti, _params.ModelloSoggetti, _params.Stato, Operatore, _params.OperatoreRicerche, idRif);
                adapter.CreaDatiMittentiDestinatari(responseMittentiDestinatari, dati, _params.AreaSoggetti, _params.DescrizioneEnte);

                var responseClassifica = querySrv.LeggiClassifica(Constants.AREA_CLASSIFICA, Constants.MODELLO_CLASSIFICA, Constants.STATO_CLASSIFICA, _params.OperatoreRicerche, dati.Classifica, Operatore);
                adapter.SetDescrizioneClassifica(responseClassifica, dati);

                return dati;
            }
        }

        public override AllOut LeggiAllegato()
        {
            if (!String.IsNullOrEmpty(IdProtocollo) && IdProtocollo != "0")
            {
                var adapter = new SigedoToSigeproAdapter(DatiProtocollo, IdAllegato);
                return adapter.Adatta().LeggiAllegato();
            }
            else
            {
                _params = new SigedoVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloSigedo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var all = new SigedoAllegatiService(_protocolloLogs, _protocolloSerializer, _params.UrlWsAllegati, _params.UsernameWsAllegati, _params.PasswordWsAllegati);
                var response = all.GetAllegato(Convert.ToInt32(IdAllegato), Operatore);

                var allOut = new AllOut
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
            _params = new SigedoVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloSigedo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var protoSrv = new SigedoProtocollazioneService(_params.UrlProto, _protocolloLogs, _protocolloSerializer);
            string token = protoSrv.Login(_params.CodiceEnte, _params.UsernameWs, _params.PasswordWs);

            protoSrv.InserisciAllegati(allegati.ToList(), token, _params.UsernameWs);

            if (!dataProtocollo.HasValue)
                throw new Exception("DATA PROTOCOLLO NON VALORIZZATA");

            var segnatura = AggiungiDocumentiAdapter.Adatta(allegati, _params, numeroProtocollo, dataProtocollo.Value.Year.ToString(), Operatore);

            protoSrv.AggiungiAllegatiaProtocollo(token, _params.UsernameWs, segnatura);
        }
    }
}
