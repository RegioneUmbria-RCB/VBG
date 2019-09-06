using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Folium.Allegati;
using Init.SIGePro.Protocollo.Folium.LeggiProtocollo;
using Init.SIGePro.Protocollo.Folium.Classifiche;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Folium.Protocollo;
using Init.SIGePro.Protocollo.Folium;
using Init.SIGePro.Protocollo.Folium.ServiceWrapper;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_FOLIUM : ProtocolloBase
    {
        public PROTOCOLLO_FOLIUM()
        {

        }

        public override ListaTipiClassifica GetClassifiche()
        {
            var vert = new VerticalizzazioniWrapper(_protocolloLogs, new VerticalizzazioneProtocolloFolium(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

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

            var srv = new ProtocollazioneServiceWrapper(vert.Url, vert.Binding, auth, _protocolloLogs, _protocolloSerializer);
            var adapter = new ClassificheOutputAdapter(srv);

            var retVal = new ListaTipiClassifica();
            retVal.Classifica = adapter.Adatta().ToArray();
            return retVal;
        }

        public override AllOut LeggiAllegato()
        {
            var vert = new VerticalizzazioniWrapper(_protocolloLogs, new VerticalizzazioneProtocolloFolium(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var auth = new WSAuthentication
            {
                aoo = vert.Aoo,
                applicazione = vert.Applicazione,
                ente = vert.CodiceEnte,
                password = vert.Password,
                username = vert.Username
            };

            var srv = new ProtocollazioneServiceWrapper(vert.Url, vert.Binding, auth, _protocolloLogs, _protocolloSerializer);
            var adapter = new AllegatoOutputAdapter(srv, Convert.ToInt32(IdAllegato));

            var retVal = new AllOut();

            if (IdAllegato == "0")
            {
                retVal = base.GetAllegato();
                var image = srv.LeggiAllegatoPrincipale(Convert.ToInt64(IdProtocollo));
                retVal.Image = image;
            }
            else
            {
                retVal = adapter.Adatta();
            }

            return retVal;
        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(_protocolloLogs, new VerticalizzazioneProtocolloFolium(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            IDatiProtocollo datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            string note = "";

            if (this.DatiProtocollo.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO && this.DatiProtocollo.Movimento != null)
            {
                note = this.DatiProtocollo.Movimento.NOTE;
            }

            var info = new RequestInfo(vert, datiProto, note, base.Anagrafiche, base.Operatore);
            var factory = ProtocollazioneFactory.Create(info, base._protocolloLogs, base._protocolloSerializer);
            var response = factory.Protocolla();

            if (!String.IsNullOrEmpty(response.IdProtocollo))
            {
                var idProtocollo = Convert.ToInt64(response.IdProtocollo);
                factory.InserisciAllegati(idProtocollo);
                factory.Assegna(idProtocollo);
                factory.InviaMail(idProtocollo);
            }

            return response;
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new VerticalizzazioniWrapper(_protocolloLogs, new VerticalizzazioneProtocolloFolium(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var auth = new WSAuthentication
            {
                aoo = vert.Aoo,
                applicazione = vert.Applicazione,
                ente = vert.CodiceEnte,
                password = vert.Password,
                username = vert.Username
            };

            var adapterInput = new LeggiProtocolloInputAdapter(numeroProtocollo, annoProtocollo, vert.Registro);

            var srv = new ProtocollazioneServiceWrapper(vert.Url, vert.Binding, auth, _protocolloLogs, _protocolloSerializer);

            var adapterOutput = new LeggiProtocolloOutputAdapter(srv);

            return adapterOutput.Adatta(adapterInput.Request);
        }
    }
}
