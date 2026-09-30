using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione;
using Init.SIGePro.Protocollo.Sicraweb.Verticalizzazioni;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Sicraweb.Services;
using Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Data;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_SICRAWEB : ProtocolloBase
    {
        public PROTOCOLLO_SICRAWEB()
        {
            
        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloSicraweb(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                var srv = new ProtocolloService(vert.Url, vert.ConnectionString, _protocolloLogs, _protocolloSerializer);
                var segnaturaAdapter = new SegnaturaAdapter(_protocolloLogs, _protocolloSerializer, protoIn, datiProto, vert, srv);
                var segnatura = segnaturaAdapter.Adatta();

                var response = srv.Protocolla(segnatura, vert.ConnectionString);

                return new ProtocollazioneOutputAdapter(response).Adatta();
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

        public override AllOut LeggiAllegato()
        {
            try
            {
                var rVal = GetAllegato();

                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloSicraweb(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var srv = new LeggiAllegatiService(vert.UrlWsAllegati, String.Empty, _protocolloLogs, _protocolloSerializer);
                var credenziali = String.Format("<logon_credentials alias=\"{0}\" />", vert.ConnectionString);
                var response = srv.LeggiAllegato(credenziali, IdAllegato);

                rVal.Image = Convert.FromBase64String(response);
                return rVal;
            }
            catch(Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA LETTURA DELL'ALLEGATO", ex);
            }
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                long numProto = long.Parse(numeroProtocollo);
                long annoProto = long.Parse(annoProtocollo);

                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloSicraweb(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var srv = new ProtocolloService(vert.Url, vert.ConnectionString, _protocolloLogs, _protocolloSerializer);
                var response = srv.LeggiProtocollo(numProto, annoProto, vert.CodiceAoo);
                var adapter = new LeggiProtocolloAdapter(response);
                return adapter.Adatta(new OggettiMgr(this.DatiProtocollo.Db));
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO", ex);
            }
        }
    }
}
