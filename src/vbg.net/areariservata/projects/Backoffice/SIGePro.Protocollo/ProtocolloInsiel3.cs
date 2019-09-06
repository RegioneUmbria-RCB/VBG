using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Insiel3.Verticalizzazioni;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Insiel3.Allegati;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.Insiel3.Protocollazione;
using Init.SIGePro.Protocollo.Insiel3.TipiDocumento;
using Init.SIGePro.Protocollo.Insiel3.LeggiProtocollo;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.Insiel3.Fascicolazione;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Insiel3;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_INSIEL3 : ProtocolloBase
    {
        const string SEPARATORE_ID_PROTOCOLLO = ";";

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var allegatiSrv = new AllegatiService(vert.UrlUploadFile, _protocolloLogs, _protocolloSerializer);
            var adapterAllegati = new AllegatiAdapter(allegatiSrv, protoIn.Allegati);
            var docs = adapterAllegati.Adatta();
            var srv = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);
            var adapter = new ProtocollazioneInputAdapter(vert, datiProto, docs, srv, _protocolloLogs);
            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);
            var request = adapter.Adatta(codiceUfficio, this.ValorizzaDataRicezioneSpedizione );
            var response = srv.Protocolla(request);

            var adapterOut = new ProtocollazioneOutputAdapter(response, SEPARATORE_ID_PROTOCOLLO, _protocolloLogs);
            return adapterOut.Adatta();
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);
            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);
            var factory = LeggiProtocolloFactory.Create(idProtocollo, numeroProtocollo, annoProtocollo, codiceUfficio, vert.CodiceRegistro, service, _protocolloLogs);
            var response = factory.Leggi();
            var adapterOutput = new LeggiProtocolloOutputAdapter(response, this._protocolloLogs);
            return adapterOutput.Adatta(vert.UsaLivelliClassifica);
        }

        public override AllOut LeggiAllegato()
        {

            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var serviceProto = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);

            var idProtoAdapter = new IdProtocolloAdapter(IdProtocollo, SEPARATORE_ID_PROTOCOLLO);
            var requestLeggiProto = idProtoAdapter.Adatta();

            var requestDownloadDoc = new DownloadDocumentoRequest
            {
                idDoc = Convert.ToInt64(IdAllegato),
                registrazione = new ProtocolloRequest
                {
                    Item = new IdProtocollo
                    {
                        progDoc = requestLeggiProto.ProgDoc,
                        progMovi = requestLeggiProto.ProgMovi
                    }
                }
            };

            var responseDownloadDoc = serviceProto.DownloadDocumento(requestDownloadDoc);

            var service = new AllegatiService(vert.UrlUploadFile, _protocolloLogs, _protocolloSerializer);
            var response = service.Download(responseDownloadDoc.idFile);

            var retVal = new AllOut { Image = response.binaryData, IDBase = responseDownloadDoc.idFile, Serial = responseDownloadDoc.name, Commento = responseDownloadDoc.name };
            return retVal;
        }

        public override ListaTipiDocumento GetTipiDocumento()
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            if (!vert.TipiDocumentoWs)
                return base.GetTipiDocumento();

            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);
            var adapter = new TipiDocumentoOutputAdapter(service);
            return adapter.Adatta();
        }

        public override DatiProtocolloFascicolato IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);
            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);

            var leggi = this.LeggiProtocollo(idProtocollo, annoProtocollo, numeroProtocollo);

            if (String.IsNullOrEmpty(leggi.AnnoNumeroPratica))
            {
                return new DatiProtocolloFascicolato { Fascicolato = EnumFascicolato.no };
            }

            var annoNumero = leggi.AnnoNumeroPratica.Split('/');
            int annoFascicolo = Convert.ToInt32(annoNumero[0]);
            string numeroFascicolo = annoNumero[1];

            var info = new FascicolazioneInfo(annoFascicolo, numeroFascicolo, leggi.Classifica, "", codiceUfficio, vert, numeroProtocollo, annoProtocollo, leggi.Origine);
            var adapterInterroga = new InterrogaPraticheRequestAdapter();
            var requestInterroga = adapterInterroga.Adatta(info);

            var responseInterroga = service.InterrogaPratiche(requestInterroga);

            var adapter = new DettaglioFascicoloRequestAdapter();
            var request = adapter.Adatta(responseInterroga.progDoc, responseInterroga.progMovi);

            var response = service.GetFascicolo(request);

            var adapterResponse = new DettaglioFascicoloResponseAdapter();
            return adapterResponse.Adatta(response);
        }

        public override DatiFascicolo Fascicola(Fascicolo fascicolo)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);

            var leggi = this.LeggiProtocollo(base.IdProtocollo, base.AnnoProtocollo, base.NumProtocollo);

            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);
            var info = new FascicolazioneInfo(fascicolo.AnnoFascicolo, fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.Oggetto, codiceUfficio, vert, base.NumProtocollo, base.AnnoProtocollo, leggi.Origine);

            var adapterAbil = new AbilitazioneFascicolazioneAdapter();
            var requestAbil = adapterAbil.Adatta(info);

            var abilitato = service.VerificaAbilitazioneFascicolazione(requestAbil);

            if (!abilitato)
            {
                throw new Exception("UTENTE NON ABILITATO ALLA FASCICOLAZIONE");
            }

            string numeroFascicolo = fascicolo.NumeroFascicolo;
            string dataFascicolo = fascicolo.DataFascicolo;
            string annoFascicolo = fascicolo.AnnoFascicolo.GetValueOrDefault(DateTime.Now.Year).ToString();
            long? progDocPratica = null;
            string progMoviPratica = "";

            if (String.IsNullOrEmpty(numeroFascicolo))
            {
                var adapter = new FascicolazioneAdapterRequest();
                var request = adapter.Adatta(info);
                var response = service.CreaFascicolo(request);

                numeroFascicolo = response.numero;
                dataFascicolo = response.dataApertura.ToString("dd/MM/yyyy");
                annoFascicolo = response.anno;
                progDocPratica = response.progDoc;
                progMoviPratica = response.progMovi;
            }
            else
            {
                var adapter = new InterrogaPraticheRequestAdapter();
                var request = adapter.Adatta(info);
                var response = service.InterrogaPratiche(request);

                progDocPratica = response.progDoc;
                progMoviPratica = response.progMovi;
            }

            var adapterAggiornaProtocollo = new AggiornaProtocolloRequestAdapter();
            var requestAggiornaProtocollo = adapterAggiornaProtocollo.Adatta(info, progDocPratica.Value, progMoviPratica);
            service.AggiornaProtocollo(requestAggiornaProtocollo);

            var adapterResponse = new FascicolazioneAdapterResponse();
            return adapterResponse.Adatta(info.AnnoFascicolo, dataFascicolo, numeroFascicolo);
        }

        public override DatiFascicolo CambiaFascicolo(Fascicolo fascicolo)
        {
            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo))
            {
                throw new Exception("NUMERO FASCICOLO NON VALORIZZATO");
            }

            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);

            var leggi = this.LeggiProtocollo(base.DatiProtocollo.Istanza.FKIDPROTOCOLLO, base.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO);

            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);
            var info = new FascicolazioneInfo(fascicolo.AnnoFascicolo, fascicolo.NumeroFascicolo, leggi.Classifica, fascicolo.Oggetto, codiceUfficio, vert, base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, base.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), leggi.Origine);

            var adapterAbil = new AbilitazioneFascicolazioneAdapter();
            var requestAbil = adapterAbil.Adatta(info);

            var abilitato = service.VerificaAbilitazioneFascicolazione(requestAbil);

            if (!abilitato)
            {
                throw new Exception("UTENTE NON ABILITATO AL CAMBIO FASCICOLO");
            }

            string numeroFascicolo = fascicolo.NumeroFascicolo;
            string dataFascicolo = fascicolo.DataFascicolo;
            string annoFascicolo = fascicolo.AnnoFascicolo.GetValueOrDefault(DateTime.Now.Year).ToString();
            long? progDocPratica = null;
            string progMoviPratica = "";

            //if (String.IsNullOrEmpty(numeroFascicolo))
            //{


            //    var adapter = new FascicolazioneAdapterRequest();
            //    var request = adapter.Adatta(info);
            //    var response = service.CreaFascicolo(request);

            //    numeroFascicolo = response.numero;
            //    dataFascicolo = response.dataApertura.ToString("dd/MM/yyyy");
            //    annoFascicolo = response.anno;
            //    progDocPratica = response.progDoc;
            //    progMoviPratica = response.progMovi;
            //}

            var adapterInterroga = new InterrogaPraticheRequestAdapter();
            var requestInterroga = adapterInterroga.Adatta(info);

            var responseInterroga = service.InterrogaPratiche(requestInterroga);
            progDocPratica = responseInterroga.progDoc;
            progMoviPratica = responseInterroga.progMovi;

            var adapterAggiornaProtocollo = new AggiornaProtocolloRequestAdapter();
            var requestAggiornaProtocollo = adapterAggiornaProtocollo.Adatta(info, progDocPratica.Value, progMoviPratica);
            service.AggiornaProtocollo(requestAggiornaProtocollo);

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
                    service.AggiornaProtocollo(requestAggiornaProtocollo);
                }
            }

            return datiFascicolo;
        }

        public override ListaTipiClassifica GetClassifiche()
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            if (!vert.UsaWsClassifiche)
            {
                return base.GetClassifiche();
            }

            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);
            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);

            var request = new RegistriClassPratRequest { filtro = new RegistroDaClassifica { codiceUfficio = codiceUfficio } };
            var response = service.GetClassifiche(request);

            var c = new ClassificaAdapter();

            return new ListaTipiClassifica
            {
                Classifica = response.Items.Select(x => new ListaTipiClassificaClassifica
                {
                    Codice = c.EstraiClassificaDaRegistro(vert.UsaLivelliClassifica, (RegistroDaClassificaView)x),
                    Descrizione = $"{c.EstraiClassificaDaRegistro(vert.UsaLivelliClassifica, (RegistroDaClassificaView)x)} - {((RegistroDaClassificaView)x).descrizioneRegistro}"
                }).ToArray()
            };
        }
    }
}
