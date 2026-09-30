using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali
{
    public class RisorseTestualiService : IRisorseTestualiService
    {
        protected static class Constants
        {
            public const string Prefix = "AREA_RISERVATA.";
        }

        AreaRiservataServiceCreator _serviceCreator;
        ISoftwareResolver _softwareResolver;

        internal RisorseTestualiService(AreaRiservataServiceCreator serviceCreator, ISoftwareResolver softwareResolver)
        {
            this._serviceCreator = serviceCreator;
            this._softwareResolver = softwareResolver;
        }

        public string GetRisorsa(string id, string valoreDefault = "")
        {
            var risorse = GetListaRisorse();
            var idCompleto = Constants.Prefix + id;

            if (risorse.TryGetValue(idCompleto, out string valore))
            {
                return valore;
            }

            return valoreDefault;
        }

        public virtual void AggiornaRisorsa(string id, string valore)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    var idCompleto = Constants.Prefix + id;
                    ws.Service.AggiornaRisorsaTestuale(ws.Token, this._softwareResolver.Software, idCompleto, valore);
                }
                catch (Exception)
                {
                    ws.Service.Abort();

                    throw;
                }
            }
        }

        public virtual Dictionary<string, string> GetListaRisorse()
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    var risorse = ws.Service.GetRisorseTestuali(ws.Token, this._softwareResolver.Software);

                    return risorse.ToDictionary(x => x.Chiave, x => x.Valore);
                }
                catch (Exception)
                {
                    ws.Service.Abort();

                    throw;
                }
            }
        }
    }
}
