using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Folium.Verticalizzazioni;
using Init.SIGePro.Protocollo.Folium.Services;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Folium.Protocollazione;
using Init.SIGePro.Protocollo.Folium.Allegati;
using Init.SIGePro.Protocollo.Folium.LeggiProtocollo;
using Init.SIGePro.Protocollo.Folium.Classifiche;
using Init.SIGePro.Protocollo.Folium.Assegnazione;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_FOLIUM : ProtocolloBase
    {
        public PROTOCOLLO_FOLIUM()
        {

        }

        public override ListaTipiClassifica GetClassifiche()
        {
            try
            {
                var vert = new FoliumVerticalizzazioniAdapter(_protocolloLogs, new VerticalizzazioneProtocolloFolium(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                if (!vert.UsaWsClassifiche)
                    return base.GetClassifiche();

                var auth = new WSAuthentication
                {
                    aoo = vert.Aoo,
                    applicazione = vert.Applicazione,
                    ente = vert.CodiceEnte,
                    password = vert.Password,
                    username = vert.Username
                };

                var srv = new FoliumProtocollazioneService(vert.Url, vert.Binding, auth, _protocolloLogs, _protocolloSerializer);
                var adapter = new FoliumClassificheOutputAdapter(srv);

                var retVal = new ListaTipiClassifica();
                retVal.Classifica = adapter.Adatta().ToArray();
                return retVal;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA RICERCA DELLE VOCI DI TITOLARIO", ex);
            }
        }

        public override AllOut LeggiAllegato()
        {
            try
            {
                var vert = new FoliumVerticalizzazioniAdapter(_protocolloLogs, new VerticalizzazioneProtocolloFolium(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                var auth = new WSAuthentication
                {
                    aoo = vert.Aoo,
                    applicazione = vert.Applicazione,
                    ente = vert.CodiceEnte,
                    password = vert.Password,
                    username = vert.Username
                };

                var srv = new FoliumProtocollazioneService(vert.Url, vert.Binding, auth, _protocolloLogs, _protocolloSerializer);
                var adapter = new Folium.Allegati.FoliumAllegatoOutputAdapter(srv, Convert.ToInt32(IdAllegato));
                
                var retVal = adapter.Adatta();

                return retVal.ToList()[0];
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DELL'ALLEGATO", ex);
            }
        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {

                var vert = new FoliumVerticalizzazioniAdapter(_protocolloLogs, new VerticalizzazioneProtocolloFolium(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                IDatiProtocollo datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                
                var conf = new FoliumProtocollazioneInputConfiguration(protoIn, vert, datiProto);
                var adapterInput = new FoliumProtocollazioneInputAdapter(conf);

                var auth = new WSAuthentication
                {
                    aoo = vert.Aoo,
                    applicazione = vert.Applicazione,
                    ente = vert.CodiceEnte,
                    password = vert.Password,
                    username = vert.Username
                };

                var srv = new FoliumProtocollazioneService(vert.Url, vert.Binding, auth, _protocolloLogs, _protocolloSerializer);

                var response = srv.Protocolla(adapterInput.Request);

                var allegatiAdapter = new FoliumAllegatiInputAdapter(protoIn.Allegati, response.id);
                srv.InsertAllegati(allegatiAdapter.ListAllegati);

                if (!String.IsNullOrEmpty(protoIn.TipoSmistamento) && protoIn.Flusso != ProtocolloConstants.COD_PARTENZA)
                {
                    var confAssegnazione = new FoliumAssegnazioneInputConfiguration(protoIn.TipoSmistamento, datiProto.Ruolo, response.id.Value, Operatore);
                    var adapterAssInput = new FoliumAssegnazioneInputAdapter(confAssegnazione);
                    srv.Assegna(adapterAssInput.Request);
                }

                var adapterOutput = new FoliumProtocollazioneOutputAdapter(response, _protocolloLogs);

                return adapterOutput.DatiProtocolloResponse;
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
                var vert = new FoliumVerticalizzazioniAdapter(_protocolloLogs, new VerticalizzazioneProtocolloFolium(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var auth = new WSAuthentication
                {
                    aoo = vert.Aoo,
                    applicazione = vert.Applicazione,
                    ente = vert.CodiceEnte,
                    password = vert.Password,
                    username = vert.Username
                };

                var adapterInput = new FoliumLeggiProtocolloInputAdapter(numeroProtocollo, annoProtocollo, vert.Registro);

                var srv = new FoliumProtocollazioneService(vert.Url, vert.Binding, auth, _protocolloLogs, _protocolloSerializer);

                var adapterOutput = new FoliumLeggiProtocolloOutputAdapter(srv);

                return adapterOutput.Adatta(adapterInput.Request);

            }
            catch (Exception ex) 
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO", ex);
            }
        }
    }
}
