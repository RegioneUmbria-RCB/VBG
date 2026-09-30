using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.SIGePro.Manager.DTO.TabelleDiBase;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInpsInail
{
    public class InpsInailService : IInpsInailService
    {
        private readonly TabelleDiBaseServiceCreator _serviceCreator;
        private readonly IAliasResolver _resolveAlias;
        private readonly ILog _log = LogManager.GetLogger(typeof(InpsInailService));

        public InpsInailService(TabelleDiBaseServiceCreator serviceCreator, IAliasResolver resolveAlias)
        {
            this._serviceCreator = serviceCreator;
            this._resolveAlias = resolveAlias;
        }

        public IEnumerable<SedeInpsDto> GetSediInps(string partial)
        {
            return this._serviceCreator.Call(svc =>
            {
                try
                {
                    return svc.Service.GetElencoSediInps(svc.Token).Where(x => x.Descrizione.ToUpperInvariant().StartsWith(partial.ToUpperInvariant()));
                }
                catch (Exception ex)
                {
                    svc.Service.Abort();

                    this._log.ErrorFormat("Errore durante la lettura delle sedi INPS: {0}", ex.ToString());

                    throw;
                }
            });
        }

        public SedeInpsDto GetSedeInpsByCodice(string codice)
        {
            return this._serviceCreator.Call(svc =>
            {
                try
                {
                    return Array.Find(svc.Service.GetElencoSediInps(svc.Token), x => x.Codice == codice);
                }
                catch (Exception ex)
                {
                    svc.Service.Abort();

                    this._log.ErrorFormat("Errore durante la lettura delle sedi INPS: {0}", ex.ToString());

                    throw;
                }
            });
        }

        public IEnumerable<SedeInailDto> GetSediInail(string partial)
        {
            return this._serviceCreator.Call(svc =>
            {
                try
                {
                    return svc.Service.GetElencoSediInail(svc.Token).Where(x => x.Descrizione.ToUpperInvariant().StartsWith(partial.ToUpperInvariant()));
                }
                catch (Exception ex)
                {
                    svc.Service.Abort();

                    this._log.ErrorFormat("Errore durante la lettura delle sedi INAIL: {0}", ex.ToString());

                    throw;
                }
            });
        }

        public SedeInailDto GetSedeInailByCodice(string codice)
        {
            return this._serviceCreator.Call(svc =>
            {
                try
                {
                    return Array.Find(svc.Service.GetElencoSediInail(svc.Token), x => x.Codice == codice);
                }
                catch (Exception ex)
                {
                    svc.Service.Abort();

                    this._log.ErrorFormat("Errore durante la lettura delle sedi INAIL: {0}", ex.ToString());

                    throw;
                }
            });
        }
    }
}
