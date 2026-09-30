using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.LeggiProtocollo
{
    public class LeggiProtocolloResponse
    {
        public xapirestTypeGetInterrogazioneProtocollo ResponseWs { get; private set; }

        string _xml;

        public LeggiProtocolloResponse(xapirestTypeGetInterrogazioneProtocollo response, string xml)
        {
            ResponseWs = response;
            _xml = xml;
        }

        public List<Corrispondente> GetCorrispondenti()
        {
            var retVal = new List<Corrispondente>();

            for (var i = 1; i <= Convert.ToInt32(ResponseWs.getInterrogazioneProtocollo_Result.SEQ_Protocollo.Protocollo.NumCorrispondenti); i++)
                retVal.Add(new Corrispondente(_xml, i));

            return retVal;
        }

        public List<Allegato> GetAllegati()
        {
            var retVal = new List<Allegato>();

            for (var i = 1; i <= Convert.ToInt32(ResponseWs.getInterrogazioneProtocollo_Result.SEQ_Protocollo.Protocollo.NumDocumenti); i++)
            {
                var allegato = new Allegato(_xml, i);
                if (allegato.IdTestata != "0")
                    retVal.Add(allegato);
            }

            if (retVal.Count == 0)
                return null;

            return retVal;
        }

        public string GetClassifica()
        {
            if (Convert.ToInt32(ResponseWs.getInterrogazioneProtocollo_Result.SEQ_Protocollo.Protocollo.NumDocumenti) > 0)
            {
                var allegato = new Allegato(_xml, 1);
                return allegato.Classificazione;
            }

            return "NON DEFINITA";
        }

        public List<UfficioMittente> GetUfficiMittenti()
        {
            var retVal = new List<UfficioMittente>();

            for (var i = 1; i <= Convert.ToInt32(ResponseWs.getInterrogazioneProtocollo_Result.SEQ_Protocollo.Protocollo.NumUffici_Mittenti); i++)
                retVal.Add(new UfficioMittente(_xml, i));

            return retVal;
        }

        public List<UfficioDestinatario> GetUfficiDestinatari()
        {
            var retVal = new List<UfficioDestinatario>();

            for (var i = 1; i <= Convert.ToInt32(ResponseWs.getInterrogazioneProtocollo_Result.SEQ_Protocollo.Protocollo.NumUffici_Destinatari); i++)
                retVal.Add(new UfficioDestinatario(_xml, i));

            return retVal;
        }
    }
}
