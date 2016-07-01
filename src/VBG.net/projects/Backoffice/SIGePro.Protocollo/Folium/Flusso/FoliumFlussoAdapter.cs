using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Folium.Flusso
{
    public class FoliumFlussoAdapter
    {

        private static class Constants
        {
            public const string COD_ARRIVO_FOLIUM = "I"; /*STA PER INGRESSO*/
            public const string COD_PARTENZA_FOLIUM = "U"; /*STA PER USCITA*/
            public const string COD_INTERNO_FOLIUM = "D"; /*STA PER INTERNA*/
        }

        string _flusso;

        public static string FromVbgToWs(string flusso)
        {
            if (flusso == ProtocolloConstants.COD_ARRIVO)
                return Constants.COD_ARRIVO_FOLIUM;

            if (flusso == ProtocolloConstants.COD_PARTENZA)
                return Constants.COD_PARTENZA_FOLIUM;

            if (flusso == ProtocolloConstants.COD_INTERNO)
                return Constants.COD_INTERNO_FOLIUM;

            throw new Exception(String.Format("FLUSSO {0} NON VALORIZZATO O NON VALIDO", flusso));
        }

        public static string FromWsToVbg(string flusso)
        {
            if (flusso == Constants.COD_ARRIVO_FOLIUM)
                return ProtocolloConstants.COD_ARRIVO;

            if (flusso == Constants.COD_PARTENZA_FOLIUM)
                return ProtocolloConstants.COD_PARTENZA;

            if (flusso == Constants.COD_INTERNO_FOLIUM)
                return ProtocolloConstants.COD_INTERNO;

            throw new Exception(String.Format("FLUSSO {0} NON VALORIZZATO O NON VALIDO", flusso));
        }
    }
}
