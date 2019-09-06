using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP
{
    public class SostituzioniUrlLDP
    {
        public static class Constants
        {
            public const string Alias = "alias";
            public const string Token = "token";
            public const string TokenPartnerApp = "tokenPartnerApp";
            public const string IdDomanda = "idDomanda";
            public const string IdDomandaEsteso = "idDomandaEsteso";
            public const string TipologiaOccupazione = "tipologiaOccupazione";
            public const string TipologiaPeriodo = "tipologiaPeriodo";
            public const string TipologiaGeometria = "tipologiaGeometria";
            public const string CodiceStrada = "codice_strada";
            public const string Civico = "codice_civico";
        }

        Dictionary<string, string> _segnaposto = new Dictionary<string, string>();

        internal SostituzioniUrlLDP()
        {

        }

        public string Alias { set { this._segnaposto[Constants.Alias] = value; } }
        public string Token { set { this._segnaposto[Constants.Token] = value; } }
        public string TokenPartnerApp { set { this._segnaposto[Constants.TokenPartnerApp] = value; } }
        public string IdDomanda { set { this._segnaposto[Constants.IdDomanda] = value; } }
        public string IdDomandaEsteso { set { this._segnaposto[Constants.IdDomandaEsteso] = value; } }
        public string TipologiaOccupazione { set { this._segnaposto[Constants.TipologiaOccupazione] = value; } }
        public string TipologiaPeriodo { set { this._segnaposto[Constants.TipologiaPeriodo] = value; } }
        public string TipologiaGeometria { set { this._segnaposto[Constants.TipologiaGeometria] = value; } }
        public string CodiceStrada { set { this._segnaposto[Constants.CodiceStrada] = value; } }
        public string Civico { set { this._segnaposto[Constants.Civico] = value; } }

        public string ApplicaA(string url)
        {
            foreach(var key in this._segnaposto.Keys)
            {
                var subVal = String.Format("{{{0}}}", key);

                url = url.Replace(subVal, this._segnaposto[key]);
            }

            return url;
        }
    }
}
