using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public class RequestAdapter20 : RequestAdapterBase, IRequestAdapter
    {
        public RequestAdapter20(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert, ResolveDatiProtocollazioneService datiIstanzaService, TipoProvenienza provenienza) : base(datiProto, vert, datiIstanzaService, provenienza)
        {

        }

        public string Token
        {
            get
            {
                return this._datiProto.Uo;
            }
        }

        public string Service
        {
            get
            {
                return this._datiProto.Ruolo;
            }
        }

        public infoProtocollo Adatta()
        {
            return base.AdattaDatiBase();
        }
    }
}
