using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Allegati
{
    public static class AllegatiExtensions
    {
        private static class Constants
        {
            public const string NomeDocumento = "DOCNAME";
            public const string DescrizioneDocumento = "ABSTRACT";
        }

        public static AllOut ToAllOut(this string idDocumento, GestioneDocumentaleService wrapper)
        {
            var response = wrapper.LeggiDocumento(idDocumento);
            var dic = response.ToDictionary(x => x.key, y => y.value);

            return new AllOut
            {
                Commento = dic[Constants.DescrizioneDocumento],
                IDBase = idDocumento,
                Serial = dic[Constants.NomeDocumento]
            };
        }
    }
}
