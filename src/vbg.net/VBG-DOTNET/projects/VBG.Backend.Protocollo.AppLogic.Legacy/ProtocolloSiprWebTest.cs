using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.Allegati;
using VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.TipiDocumento;
using VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.Classificazione;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_SIPRWEBTEST : ProtocolloBase
    {
        const string FILTRO_TUTTI = "TUTTI";
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_SIPRWEBTEST(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            _verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var verticalizzazione = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSiprwebtest>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                _protocolloLogs.Debug("CREAZIONE FACTORY");
                IDatiProtocollo datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                _protocolloLogs.Debug("CREAZIONE ADAPTER");
                var adapterInput = new ProtocollazioneInputAdapter(_protocolloLogs, verticalizzazione, protoIn, datiProto, Operatore);

                var request = adapterInput.Adatta();
                var srv = new ProtocollazioneService(verticalizzazione.UrlProtocolla, _protocolloLogs, _protocolloSerializer);
                var response = srv.Protocolla(request);

                if (protoIn.HaAllegati())
                {
                    var adapterAllegati = new AllegatiInputAdapter(_protocolloLogs, response.NumeroProtocollo, Operatore, verticalizzazione, protoIn.RecuperaAllegati().ToList());
                    var requestAllegati = adapterAllegati.Adatta();

                    if (!adapterAllegati.isAdapterError && requestAllegati != null)
                    {
                        var srvAllegati = new AllegatiService(verticalizzazione.UrlAllegati, _protocolloLogs, _protocolloSerializer);
                        srvAllegati.Inserisci(requestAllegati);
                    }
                }

                var adapterOutput = new ProtocollazioneOutputAdapter(response, _protocolloLogs);
                return adapterOutput.Adatta(ModificaNumero, AggiungiAnno);
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            try
            {
                var vert = new SiprWeb.Verticalizzazioni.VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSiprweb>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var srv = new LeggiProtocolloService(vert.UrlLeggi, _protocolloLogs, _protocolloSerializer);

                var srvTipiDoc = new TipiDocumentoService(vert.UrlListaTipiDocumento, _protocolloLogs, _protocolloSerializer);
                var adapter = new LeggiProtocolloAdapter(srv, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, srvTipiDoc, vert.UsaWsTipiDocumento, _protocolloLogs, DatiProtocollo, this.DatiProtocollo.Db);

                return new List<DatiProtocolloLettoResponseType>() { adapter.Adatta() };

            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO", ex);
            }

        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            try
            {
                var verticalizzazione = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSiprwebtest>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                if (!verticalizzazione.UsaWsClassifiche)
                    return base.GetClassifiche();

                var adapter = new ClassificheAdapter();
                var srv = new ClassificheService(verticalizzazione.UrlListaClassifica, _protocolloLogs, _protocolloSerializer);
                var reader = new ClassificheReader(srv, _protocolloLogs, _protocolloSerializer);

                return adapter.Adatta(reader);
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DELLE CLASSIFICHE", ex);
            }
        }

        public override ListaTipiDocumentoResponseType GetTipiDocumento()
        {
            try
            {
                var verticalizzazione = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSiprwebtest>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                if (!verticalizzazione.UsaWsTipiDocumento)
                    return base.GetTipiDocumento();

                var service = new TipiDocumentoService(verticalizzazione.UrlListaTipiDocumento, _protocolloLogs, _protocolloSerializer);
                var adapter = new TipiDocumentoAdapter(service, FILTRO_TUTTI);
                return adapter.Adatta();
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DELLE TIPOLOGIE DI DOCUMENTO", ex);
            }
        }

    }
}
