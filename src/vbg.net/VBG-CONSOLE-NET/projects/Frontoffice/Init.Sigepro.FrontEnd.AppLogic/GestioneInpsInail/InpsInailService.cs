using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInpsInail
{
    public class InpsInailService : IInpsInailService
    {
        AreaRiservataServiceCreator _serviceCreator;
        IAliasResolver _resolveAlias;
        ILog _log = LogManager.GetLogger(typeof(InpsInailService));

        internal InpsInailService(AreaRiservataServiceCreator serviceCreator, IAliasResolver resolveAlias)
        {
            this._serviceCreator = serviceCreator;
            this._resolveAlias = resolveAlias;
        }

        public IEnumerable<BaseDtoOfStringString> GetSediInps(string partial)
        {
            using(var svc = this._serviceCreator.CreateClient(this._resolveAlias.AliasComune))
            {
                try
                {
                    return svc.Service.GetElencoSediInps(svc.Token).Where( x => x.Descrizione.ToUpperInvariant().StartsWith(partial.ToUpperInvariant()));
                }
                catch(Exception ex)
                {
                    svc.Service.Abort();

                    _log.ErrorFormat("Errore durante la lettura delle sedi INPS: {0}", ex.ToString());

                    throw;
                }
            }
        }

        public IEnumerable<BaseDtoOfStringString> GetSediInail(string partial)
        {
            using (var svc = this._serviceCreator.CreateClient(this._resolveAlias.AliasComune))
            {
                try
                {
                    return svc.Service.GetElencoSediInail(svc.Token).Where(x => x.Descrizione.ToUpperInvariant().StartsWith(partial.ToUpperInvariant()));
                }
                catch (Exception ex)
                {
                    svc.Service.Abort();

                    _log.ErrorFormat("Errore durante la lettura delle sedi INAIL: {0}", ex.ToString());

                    throw;
                }
            }
        }
    }
}
