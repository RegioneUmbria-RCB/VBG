using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Core.Pal;
using VBG.Backend.Protocollo.AppLogic.Core.Pal.Autenticazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Core.Pal.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Core.Pal.Classificazione;
using VBG.Backend.Protocollo.AppLogic.Core.Pal.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.Pal.Organigramma;
using VBG.Backend.Protocollo.AppLogic.Core.Pal.LeggiAllegati;
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_PAL : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        public PROTOCOLLO_PAL(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPal>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
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

        public override ListaTipiClassificaType GetClassifiche()
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPal>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var auth = new AutenticazioneServiceWrapper(_protocolloLogs, vert.UrlBaseWs);
            var token = auth.GetToken(vert.Username, vert.Password, vert.CodiceIstat, vert.CodiceAoo);

            var wrapper = new ClassificazioneServiceWrapper(_protocolloLogs, vert.UrlBaseWs, token);
            var response = wrapper.GetClassifica();

            var adapter = new ClassificazioneResponseAdapter();
            var retVal = adapter.Adatta(response);
            return retVal;
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPal>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var auth = new AutenticazioneServiceWrapper(_protocolloLogs, vert.UrlBaseWs);
            var token = auth.GetToken(vert.Username, vert.Password, vert.CodiceIstat, vert.CodiceAoo);
            var leggiService = new LeggiProtocolloServiceWrapper(vert.UrlBaseWs, token, base._protocolloLogs, base._protocolloSerializer);
            var responseLeggi = leggiService.LeggiProtocollo(leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo);

            var serviceClassifica = new ClassificazioneServiceWrapper(_protocolloLogs, vert.UrlBaseWs, token);
            var responseClassifiche = serviceClassifica.GetClassifica();

            var serviceOrganigramma = new OrganigrammaServiceWrapper(token, vert.UrlBaseWs, _protocolloLogs);

            var adapter = new LeggiProtocolloAdapter();
            var retVal = adapter.Adatta(responseLeggi, responseClassifiche, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo, base.DataProtocollo, new OrganigrammaServiceWrapper(token, vert.UrlBaseWs, _protocolloLogs));
            return new List<DatiProtocolloLettoResponseType>() { retVal };
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloPal>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var auth = new AutenticazioneServiceWrapper(_protocolloLogs, vert.UrlBaseWs);
            var token = auth.GetToken(vert.Username, vert.Password, vert.CodiceIstat, vert.CodiceAoo);
            var service = new LeggiAllegatiServiceWrapper(token, vert.UrlBaseWs, base._protocolloLogs);
            var retVal = service.GetAllegato(base.IdAllegato);
            return retVal;
        }
    }
}
