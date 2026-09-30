using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.SiprWebTest.Verticalizzazioni;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.SiprWebTest.Protocollazione;
using Init.SIGePro.Protocollo.SiprWebTest.Allegati;
using Init.SIGePro.Protocollo.SiprWebTest.Classificazione;
using Init.SIGePro.Protocollo.SiprWebTest.TipiDocumento;
using Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_SIPRWEBTEST : ProtocolloBase
    {
        const string FILTRO_TUTTI = "TUTTI";

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var verticalizzazione = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloSiprwebtest(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                _protocolloLogs.Debug("CREAZIONE FACTORY");
                IDatiProtocollo datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                _protocolloLogs.Debug("CREAZIONE ADAPTER");
                var adapterInput = new ProtocollazioneInputAdapter(_protocolloLogs, verticalizzazione, protoIn, datiProto, Operatore);

                var request = adapterInput.Adatta();
                var srv = new ProtocollazioneService(verticalizzazione.UrlProtocolla, _protocolloLogs, _protocolloSerializer);
                var response = srv.Protocolla(request);

                if (protoIn.Allegati.Count > 0)
                {
                    var adapterAllegati = new AllegatiInputAdapter(_protocolloLogs, response.NumeroProtocollo, Operatore, verticalizzazione, protoIn.Allegati);
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

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                var vert = new SiprWeb.Verticalizzazioni.VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloSiprweb(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var srv = new LeggiProtocolloService(vert.UrlLeggi, _protocolloLogs, _protocolloSerializer);

                var srvTipiDoc = new TipiDocumentoService(vert.UrlListaTipiDocumento, _protocolloLogs, _protocolloSerializer);
                var adapter = new LeggiProtocolloAdapter(srv, numeroProtocollo, annoProtocollo, srvTipiDoc, vert.UsaWsTipiDocumento, _protocolloLogs, DatiProtocollo, this.DatiProtocollo.Db);

                return adapter.Adatta();

            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO", ex);
            }

        }

        public override ListaTipiClassifica GetClassifiche()
        {
            try
            {
                var verticalizzazione = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloSiprwebtest(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
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

        public override ListaTipiDocumento GetTipiDocumento()
        {
            try
            {
                var verticalizzazione = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloSiprwebtest(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

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
