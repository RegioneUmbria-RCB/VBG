using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Protocollo.Civilia;
using Init.SIGePro.Protocollo.Civilia.Protocollazione;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_CIVILIA : ProtocolloBase
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniParametriServiceWrapper(new VerticalizzazioneProtocolloCivilia(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            IDatiProtocollo datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var info = new ProtocolloInfo(vert, datiProto, this.Anagrafiche, this.Operatore);

            var adapter = new ProtocollazioneAdapter();
            var request = adapter.Adatta(info, this._protocolloLogs);

            var wrapper = new ProtocollazioneServiceWrapper(info, _protocolloLogs, _protocolloSerializer);

            var token = wrapper.GetTokenOAuth2();
            var response = wrapper.Protocolla(request, token);

            return new DatiProtocolloRes
            {
                AnnoProtocollo = response.Result.DataRegistrazione.Value.ToString("yyyy"),
                DataProtocollo = response.Result.DataRegistrazione.Value.ToString("dd/MM/yyyy"),
                IdProtocollo = response.Result.Id.ToString(),
                NumeroProtocollo = response.Result.NumeroProtocollo.ToString()
            };

        }
    }
}
