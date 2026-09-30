using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;

namespace Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo
{
    public static class LeggiMittentiDestinatariExtensions
    {
        public static MittDestOut ToMittDestOutFromMittente(this sender mittente)
        {
            var res = new MittDestOut { IdSoggetto = mittente.code, CognomeNome = mittente.description };
            if (!String.IsNullOrEmpty(mittente.transmissionMode))
                res.CognomeNome = String.Format("{0} Trasmesso per: {1}", mittente.description, mittente.transmissionMode);

            return res;

        }

        public static MittDestOut ToMittDestOutFromDestinatario(this recipient destinatario)
        {
            var res = new MittDestOut { IdSoggetto = destinatario.code, CognomeNome = destinatario.description };
            if (!String.IsNullOrEmpty(destinatario.transmissionMode))
                res.CognomeNome = String.Format("{0} Trasmesso per: {1}", destinatario.description, destinatario.transmissionMode);

            return res;

        }

    }
}
