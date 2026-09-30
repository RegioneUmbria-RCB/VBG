using Init.SIGePro.Manager;
using ProtocolloInsielService3;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Allegati;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Storico;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.TipiDocumento;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_INSIEL3 : ProtocolloBase, IProtocolloStorico
    {
        const string SEPARATORE_ID_PROTOCOLLO = ";";
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_INSIEL3(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var allegatiSrv = new AllegatiService(vert.UrlUploadFile, _protocolloLogs, _protocolloSerializer, this._bindingFactory);
            var adapterAllegati = new AllegatiAdapter(allegatiSrv, protoIn.RecuperaAllegati().ToList());
            var docs = adapterAllegati.Adatta();
            var srv = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente);
            var adapter = new ProtocollazioneInputAdapter(vert, datiProto, docs, srv, _protocolloLogs);
            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);
            var request = adapter.Adatta(codiceUfficio, this.ValorizzaDataRicezioneSpedizione);
            var response = srv.Protocolla(request);

            var adapterOut = new ProtocollazioneOutputAdapter(response, SEPARATORE_ID_PROTOCOLLO, _protocolloLogs);
            return adapterOut.Adatta();
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente);
            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);
            var factory = LeggiProtocolloFactory.Create(leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, codiceUfficio, vert.CodiceRegistro, service, _protocolloLogs);
            var response = factory.Leggi();
            var adapterOutput = new LeggiProtocolloOutputAdapter(response, this._protocolloLogs);
            return new List<DatiProtocolloLettoResponseType>() { adapterOutput.Adatta(vert.UsaLivelliClassifica) };
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var serviceProto = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente);

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

            var service = new AllegatiService(vert.UrlUploadFile, _protocolloLogs, _protocolloSerializer, this._bindingFactory);
            var response = service.Download(responseDownloadDoc.idFile);

            var retVal = new AllegatoResponseType { Image = response.binaryData, IDBase = responseDownloadDoc.idFile, Serial = responseDownloadDoc.name, Commento = responseDownloadDoc.name };
            return retVal;
        }

        public override ListaTipiDocumentoResponseType GetTipiDocumento()
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            if (!vert.TipiDocumentoWs)
                return base.GetTipiDocumento();

            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente);
            var adapter = new TipiDocumentoOutputAdapter(service);
            return adapter.Adatta();
        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente);
            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);

            var protocolli = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = idProtocollo, AnnoProtocollo = annoProtocollo, NumeroProtocollo = numeroProtocollo });
            var leggi = protocolli.FirstOrDefault();

            if (String.IsNullOrEmpty(leggi.AnnoNumeroPratica))
            {
                return new DatiProtocolloFascicolatoResponseType { Fascicolato = EnumFascicolatoType.no };
            }

            var annoNumero = leggi.AnnoNumeroPratica.Split('/');
            int annoFascicolo = Convert.ToInt32(annoNumero[1]);
            string numeroFascicolo = annoNumero[2];

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

        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente);

            var protocolli = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = base.IdProtocollo, AnnoProtocollo = base.AnnoProtocollo, NumeroProtocollo = base.NumProtocollo });
            var leggi = protocolli.FirstOrDefault();
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

        public override DatiFascicoloResponseType CambiaFascicolo(Fascicolo fascicolo)
        {
            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo))
            {
                throw new Exception("NUMERO FASCICOLO NON VALORIZZATO");
            }

            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente);

            var leggi = this.LeggiProtocollo(new LeggiProtocolloRequest()
            {
                IdProtocollo = base.DatiProtocollo.Istanza.FKIDPROTOCOLLO,
                AnnoProtocollo = base.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(),
                NumeroProtocollo = base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO
            });

            var singoloProtocollo = leggi.FirstOrDefault();

            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);
            var info = new FascicolazioneInfo(fascicolo.AnnoFascicolo, fascicolo.NumeroFascicolo, singoloProtocollo.Classifica, fascicolo.Oggetto, codiceUfficio, vert, base.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, base.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), singoloProtocollo.Origine);

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

        public override ListaTipiClassificaType GetClassifiche()
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            if (!vert.UsaWsClassifiche)
            {
                return base.GetClassifiche();
            }

            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente);
            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);

            var request = new RegistriClassPratRequest { filtro = new RegistroDaClassifica { codiceUfficio = codiceUfficio } };
            var response = service.GetClassifiche(request);

            var c = new ClassificaAdapter();

            return new ListaTipiClassificaType
            {
                Classifica = response.Items.Select(x => new ListaTipiClassificaClassifica
                {
                    Codice = c.EstraiClassificaDaRegistro(vert.UsaLivelliClassifica, (RegistroDaClassificaView)x),
                    Descrizione = $"{c.EstraiClassificaDaRegistro(vert.UsaLivelliClassifica, (RegistroDaClassificaView)x)} - {((RegistroDaClassificaView)x).descrizioneRegistro}"
                }).ToArray()
            };
        }

        public override DatiProtocolloLettoResponseType LeggiProtocolloStorico(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {

            var par = new ParametriStoriciAdapter(base.DatiProtocollo.Token, base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();

            var codiceUfficio = par.CodiceUfficio;
            var separatore = SEPARATORE_ID_PROTOCOLLO;

            return new ServiceWrapperStorico(par, base._protocolloSerializer, base._protocolloLogs, _bindingFactory).LeggiProtocollo(idProtocollo, separatore, annoProtocollo, numeroProtocollo, codiceUfficio);
        }

        public override AllegatoResponseType LeggiAllegatoStorico()
        {
            var par = new ParametriStoriciAdapter(base.DatiProtocollo.Token, base.DatiProtocollo.IdComuneAlias, base.DatiProtocollo.Software, base.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();
            var idAllegato = base.IdAllegato;
            var idProtocollo = base.IdProtocollo;
            var separatore = SEPARATORE_ID_PROTOCOLLO;

            return new ServiceWrapperStorico(par, base._protocolloSerializer, base._protocolloLogs, _bindingFactory).LeggiAllegatoStorico(idAllegato, idProtocollo, separatore);
        }
    }
}
