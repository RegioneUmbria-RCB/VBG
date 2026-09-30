using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public class RequestAdapterFactory
    {
        private class Constants
        {
            public const string Versione_40 = "4.0";
        }

        public static IRequestAdapter Create(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert, ResolveDatiProtocollazioneService datiIstanzaService, TipoProvenienza provenienza)
        {
            if (vert.Versione == Constants.Versione_40)
            {
                return new RequestAdapter40(datiProto, vert, datiIstanzaService, provenienza);
            }

            return new RequestAdapter20(datiProto, vert, datiIstanzaService, provenienza);
        }
    }
}
