using Init.SIGePro.Manager;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Allegati;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.TipiDocumento;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4;
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_INSIELREST : ProtocolloBase
    {
        const string SEPARATORE_ID_PROTOCOLLO = ";";
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_INSIELREST(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var par = RecuperaParametri();
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var allegatiSrv = new AllegatiService(par, _protocolloLogs, _protocolloSerializer);
            var adapterAllegati = new AllegatiAdapter(allegatiSrv, protoIn.RecuperaAllegati().ToList());
            var docs = adapterAllegati.Adatta();
            var srv = new ProtocolloService(par, _protocolloLogs, _protocolloSerializer);
            var codiceUfficio = GetUfficioRegistro(par.CodiceRegistro);
            var verticalizzazione = new InsielVerticalizzazioniConfiguration(_protocolloLogs, par);
            var requestProtocollazione = new ProtocollazioneInputAdapter(verticalizzazione, datiProto, srv, _protocolloLogs).CreaRequest(codiceUfficio, this.ValorizzaDataRicezioneSpedizione, docs);
            var response = srv.Protocolla(requestProtocollazione);

            var adapterOut = new ProtocollazioneOutputAdapter(response, SEPARATORE_ID_PROTOCOLLO, _protocolloLogs);
            return adapterOut.Adatta();
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var par = RecuperaParametri();
            var service = new ProtocolloService(par, _protocolloLogs, _protocolloSerializer);
            var codiceUfficio = GetUfficioRegistro(par.CodiceRegistro);
            var factory = LeggiProtocolloFactory.Create(leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, codiceUfficio, par.CodiceRegistro, service, _protocolloLogs);
            var response = factory.Leggi();
            var adapterOutput = new LeggiProtocolloOutputAdapter(response, this._protocolloLogs);
            return new List<DatiProtocolloLettoResponseType>() { adapterOutput.Adatta(par.UsaLivelliClassifica == "1") };
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var par = RecuperaParametri();
            var serviceProto = new ProtocolloService(par, _protocolloLogs, _protocolloSerializer);

            var idProtoAdapter = new IdProtocolloAdapter(IdProtocollo, SEPARATORE_ID_PROTOCOLLO);

            var requestDownloadDoc = new DownloadDocumentoRequest
            {
                IdDoc = Convert.ToInt64(IdAllegato),
                Registrazione = new RegistrazioneID
                {
                    Id = idProtoAdapter.Adatta()
                }
            };

            var responseDownloadDoc = serviceProto.DownloadDocumento(requestDownloadDoc);

            var retVal = new AllegatoResponseType { Image = responseDownloadDoc.File, IDBase = IdAllegato, Serial = responseDownloadDoc.NomeFile, Commento = responseDownloadDoc.NomeFile };

            return retVal;
        }

        public override ListaTipiDocumentoResponseType GetTipiDocumento()
        {
            var par = RecuperaParametri();

            if (par.TipiDocumentoWs != "1")
                return base.GetTipiDocumento();

            var service = new ProtocolloService(par, _protocolloLogs, _protocolloSerializer);
            var adapter = new TipiDocumentoOutputAdapter(service);
            return adapter.Adatta();
        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var par = RecuperaParametri();

            var service = new ProtocolloService(par, _protocolloLogs, _protocolloSerializer);
            var codiceUfficio = GetUfficioRegistro(par.CodiceRegistro);

            var protocolli = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = idProtocollo, AnnoProtocollo = annoProtocollo, NumeroProtocollo = numeroProtocollo });
            var leggi = protocolli.FirstOrDefault();

            if (String.IsNullOrEmpty(leggi.AnnoNumeroPratica))
            {
                return new DatiProtocolloFascicolatoResponseType { Fascicolato = EnumFascicolatoType.no };
            }

            var annoNumero = leggi.AnnoNumeroPratica.Split('/');
            int annoFascicolo = Convert.ToInt32(annoNumero[1]);
            string numeroFascicolo = annoNumero[2];

            var info = new FascicolazioneInfo(annoFascicolo, numeroFascicolo, leggi.Classifica, "", codiceUfficio, par, numeroProtocollo, annoProtocollo, leggi.Origine);
            var adapterInterroga = new InterrogaPraticheRequestAdapter();
            var requestInterroga = adapterInterroga.Adatta(info);

            var responseInterroga = service.InterrogaFascicoli(requestInterroga);

            var adapter = new DettaglioFascicoloRequestAdapter();
            var request = adapter.Adatta(responseInterroga.ProgDoc, responseInterroga.ProgMovi.ToString());

            var response = service.GetFascicolo(request);

            var adapterResponse = new DettaglioFascicoloResponseAdapter();
            return adapterResponse.Adatta(response);
        }

        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            var par = RecuperaParametri();
            var service = new ProtocolloService(par, _protocolloLogs, _protocolloSerializer);

            var protocolli = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = base.IdProtocollo, AnnoProtocollo = base.AnnoProtocollo, NumeroProtocollo = base.NumProtocollo });
            var leggi = protocolli.FirstOrDefault();
            var codiceUfficio = GetUfficioRegistro(par.CodiceRegistro);
            var info = new FascicolazioneInfo(fascicolo.AnnoFascicolo, fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.Oggetto, codiceUfficio, par, base.NumProtocollo, base.AnnoProtocollo, leggi.Origine);

            var adapterAbil = new AbilitazioneFascicolazioneAdapter();
            var requestAbil = adapterAbil.Adatta(info);

            var abilitato = service.VerificaAbilitazioneFascicolazione(requestAbil);

            if (!abilitato)
            {
                throw new Exception("UTENTE NON ABILITATO ALLA FASCICOLAZIONE");
            }

            string numeroFascicolo = fascicolo.NumeroFascicolo;
            string dataFascicolo = fascicolo.DataFascicolo;
            string annoFascicolo = info.AnnoFascicolo;
            long? progDocPratica = null;
            string progMoviPratica = "";

            if (String.IsNullOrEmpty(numeroFascicolo))
            {
                var adapter = new FascicolazioneAdapterRequest();
                var request = adapter.Adatta(info);
                var response = service.CreaFascicolo(request);

                numeroFascicolo = response.Numero.ToString();
                dataFascicolo = response.DataApertura?.ToString("dd/MM/yyyy");
                annoFascicolo = response.Anno.ToString();
                progDocPratica = response.ProgDoc;
                progMoviPratica = response.ProgMovi.ToString();
            }
            else
            {
                var adapter = new InterrogaPraticheRequestAdapter();
                var request = adapter.Adatta(info);
                var response = service.InterrogaFascicoli(request);

                progDocPratica = response.ProgDoc;
                progMoviPratica = response.ProgMovi.ToString();
            }

            var adapterAggiornaProtocollo = new AggiornaProtocolloRequestAdapter();
            var requestAggiornaProtocollo = adapterAggiornaProtocollo.Adatta(info, progDocPratica.Value, progMoviPratica);
            service.AggiornaProtocollo(requestAggiornaProtocollo, true);

            var adapterResponse = new FascicolazioneAdapterResponse();
            return adapterResponse.Adatta(annoFascicolo, dataFascicolo, numeroFascicolo);
        }

        public override DatiFascicoloResponseType CambiaFascicolo(Fascicolo fascicolo)
        {
            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo))
            {
                throw new Exception("NUMERO FASCICOLO NON VALORIZZATO");
            }

            var par = RecuperaParametri();
            var service = new ProtocolloService(par, _protocolloLogs, _protocolloSerializer);

            var protocolli = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = base.DatiProtocollo.Istanza.FKIDPROTOCOLLO, AnnoProtocollo = base.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), NumeroProtocollo = base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO });
            var leggi = protocolli.FirstOrDefault();
            var codiceUfficio = GetUfficioRegistro(par.CodiceRegistro);
            var info = new FascicolazioneInfo(fascicolo.AnnoFascicolo, fascicolo.NumeroFascicolo, leggi.Classifica, fascicolo.Oggetto, codiceUfficio, par, base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, base.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), leggi.Origine);

            var adapterAbil = new AbilitazioneFascicolazioneAdapter();
            var requestAbil = adapterAbil.Adatta(info);

            var abilitato = service.VerificaAbilitazioneFascicolazione(requestAbil);

            if (!abilitato)
            {
                throw new Exception("UTENTE NON ABILITATO AL CAMBIO FASCICOLO");
            }

            string numeroFascicolo = fascicolo.NumeroFascicolo;
            string dataFascicolo = fascicolo.DataFascicolo;

            var adapterInterroga = new InterrogaPraticheRequestAdapter();
            var requestInterroga = adapterInterroga.Adatta(info);

            var responseInterroga = service.InterrogaFascicoli(requestInterroga);
            long? progDocPratica = responseInterroga.ProgDoc;
            string progMoviPratica = responseInterroga.ProgMovi.ToString();

            var adapterAggiornaProtocollo = new AggiornaProtocolloRequestAdapter();
            var requestAggiornaProtocollo = adapterAggiornaProtocollo.Adatta(info, progDocPratica.Value, progMoviPratica);
            service.AggiornaProtocollo(requestAggiornaProtocollo, true);

            var adapterResponse = new FascicolazioneAdapterResponse();
            var datiFascicolo = adapterResponse.Adatta(info.AnnoFascicolo, dataFascicolo, numeroFascicolo);

            var movimenti = new MovimentiMgr(base.DatiProtocollo.Db);
            var movimentiProtocollati = movimenti.GetMovimentiProtocollati(base.DatiProtocollo.IdComune, base.DatiProtocollo.CodiceIstanza);

            foreach (var movimento in movimentiProtocollati)
            {
                if (!(movimento.NUMEROPROTOCOLLO == this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO && movimento.DATAPROTOCOLLO.Value.Year.ToString() == this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString()))
                {
                    info.NumeroProtocollo = movimento.NUMEROPROTOCOLLO;
                    info.AnnoProtocollo = movimento.DATAPROTOCOLLO.Value.Year.ToString();
                    requestAggiornaProtocollo = adapterAggiornaProtocollo.Adatta(info, progDocPratica.Value, progMoviPratica);

                    if (!service.AggiornaProtocollo(requestAggiornaProtocollo, false)) // se non trova nulla con il verso corrente prova con il verso opposto
                    {
                        info.Flusso = info.Flusso == "A" ? "P" : "A";
                        requestAggiornaProtocollo = adapterAggiornaProtocollo.Adatta(info, progDocPratica.Value, progMoviPratica);

                        service.AggiornaProtocollo(requestAggiornaProtocollo, true);
                    }
                }
            }

            return datiFascicolo;
        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            var par = RecuperaParametri();

            if (par.UsaWsClassifiche != "1")
            {
                return base.GetClassifiche();
            }

            var service = new ProtocolloService(par, _protocolloLogs, _protocolloSerializer);
            var codiceUfficio = GetUfficioRegistro(par.CodiceRegistro);

            //var requestClassifiche = new InfoClassificaRequest();
            //var responseClassifiche = service.GetClassifiche(requestClassifiche);
            var requestRegistri = new RegistriDaClassificaFascicoliRequest() { Filtro = new FiltroRegistri() { CodiceUfficio = codiceUfficio } };
            var responseRegistri = service.GetRegistri(requestRegistri);

            var c = new ClassificaAdapter();

            return new ListaTipiClassificaType
            {
                Classifica = responseRegistri.Registri.Select(x => new ListaTipiClassificaClassifica
                {
                    Codice = c.EstraiClassificaDaRegistro(par.UsaLivelliClassifica == "1", x),
                    Descrizione = $"{c.EstraiClassificaDaRegistro(par.UsaLivelliClassifica == "1", x)} - {x.DescrizioneRegistro}"
                }).ToArray()
            };
        }

        private ParametriRegoleInfo RecuperaParametri()
        {
            return new ParametriRegoleInfoAdapter(_protocolloLogs, _protocolloSerializer, base.DatiProtocollo.Token, base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune, base.Operatore, this._verticalizzazioniFactory).Adatta();
        }
    }
}
