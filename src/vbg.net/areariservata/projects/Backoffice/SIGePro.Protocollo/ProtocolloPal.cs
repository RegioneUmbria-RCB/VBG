using System;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Pal.Autenticazione;
using Init.SIGePro.Protocollo.Pal;
using Init.SIGePro.Protocollo.Pal.Classificazione;
using Init.SIGePro.Protocollo.Pal.Protocollazione;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Pal.LeggiProtocollo;
using Init.SIGePro.Protocollo.Pal.Organigramma;
using Init.SIGePro.Protocollo.Pal.LeggiAllegati;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_PAL : ProtocolloBase
    {
        public PROTOCOLLO_PAL()
        {

        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloPal(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var auth = new AutenticazioneServiceWrapper(_protocolloLogs, vert.UrlBaseWs);
            var token = auth.GetToken(vert.Username, vert.Password, vert.CodiceIstat, vert.CodiceAoo);

            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
            var timeStamp = DateTime.Now.ToString("yyyyMMddHHmmss");

            var adapter = new ProtocollazioneAdapter(datiProto, base.Anagrafiche, vert, timeStamp);
            var request = adapter.Adatta();

            var wrapper = new ProtocollazioneServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.UrlBaseWs, token);

            var response = wrapper.Protocolla(request);

            var responseAdapter = new ProtocollazioneResponseAdapter();
            var retVal = responseAdapter.Adatta(response);

            _protocolloLogs.InfoFormat("REGOLA INVIO PEC: {0}, FLUSSO: {1}", vert.InvioPec, protoIn.Flusso);

            if (vert.InvioPec && protoIn.Flusso == ProtocolloConstants.COD_PARTENZA)
            {
                wrapper.InviaPec(response.id.ToString());
            }

            return retVal;
        }

        public override ListaTipiClassifica GetClassifiche()
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloPal(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            var auth = new AutenticazioneServiceWrapper(_protocolloLogs, vert.UrlBaseWs);
            var token = auth.GetToken(vert.Username, vert.Password, vert.CodiceIstat, vert.CodiceAoo);

            var wrapper = new ClassificazioneServiceWrapper(_protocolloLogs, vert.UrlBaseWs, token);
            var response = wrapper.GetClassifica();

            var adapter = new ClassificazioneResponseAdapter();
            var retVal = adapter.Adatta(response);
            return retVal;
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloPal(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var auth = new AutenticazioneServiceWrapper(_protocolloLogs, vert.UrlBaseWs);
            var token = auth.GetToken(vert.Username, vert.Password, vert.CodiceIstat, vert.CodiceAoo);
            var leggiService = new LeggiProtocolloServiceWrapper(vert.UrlBaseWs, token, base._protocolloLogs, base._protocolloSerializer);
            var responseLeggi = leggiService.LeggiProtocollo(annoProtocollo, numeroProtocollo);

            var serviceClassifica = new ClassificazioneServiceWrapper(_protocolloLogs, vert.UrlBaseWs, token);
            var responseClassifiche = serviceClassifica.GetClassifica();

            var serviceOrganigramma = new OrganigrammaServiceWrapper(token, vert.UrlBaseWs, _protocolloLogs);

            var adapter = new LeggiProtocolloAdapter();
            var retVal = adapter.Adatta(responseLeggi, responseClassifiche, annoProtocollo, numeroProtocollo, base.DataProtocollo, new OrganigrammaServiceWrapper(token, vert.UrlBaseWs, _protocolloLogs));
            return retVal;
        }

        public override AllOut LeggiAllegato()
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloPal(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var auth = new AutenticazioneServiceWrapper(_protocolloLogs, vert.UrlBaseWs);
            var token = auth.GetToken(vert.Username, vert.Password, vert.CodiceIstat, vert.CodiceAoo);
            var service = new LeggiAllegatiServiceWrapper(token, vert.UrlBaseWs, base._protocolloLogs);
            var retVal = service.GetAllegato(base.IdAllegato);
            return retVal;
        }
    }
}
