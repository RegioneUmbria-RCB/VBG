using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Core.Civilia;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Core.Civilia.Protocollazione;
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_CIVILIA : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_CIVILIA(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            IDatiProtocollo datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var vert = new VerticalizzazioniParametriServiceWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloCivilia>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var info = new ProtocolloInfo(vert, datiProto, this.Anagrafiche, this.Operatore);

            var adapter = new ProtocollazioneAdapter();
            var request = adapter.Adatta(info, this._protocolloLogs);

            var wrapper = new ProtocollazioneServiceWrapper(info, _protocolloLogs, _protocolloSerializer);

            var token = wrapper.GetTokenOAuth2();
            var response = wrapper.Protocolla(request, token, base.EncodingCharset);

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = response.Result.DataRegistrazione.Value.ToString("yyyy"),
                DataProtocollo = response.Result.DataRegistrazione.Value.ToString("dd/MM/yyyy"),
                IdProtocollo = response.Result.Id.ToString(),
                NumeroProtocollo = response.Result.NumeroProtocollo.ToString()
            };

        }
    }
}
