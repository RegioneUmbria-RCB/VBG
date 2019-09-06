using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneDatiPrivacy
{
    public class PrivacyConsoleService : ServiziLocaliRestClient, IPrivacyConsoleService
    {
        private readonly ConsoleService _consoleService;

        public PrivacyConsoleService(ConsoleService consoleService)
        {
            _consoleService = consoleService;
        }

        public ResponsabiliPrivacyConsoleDto GetDatiPrivacy()
        {
            var parametriSdeProxy = this._consoleService.GetUrlServizi();
            var serviceUrl = parametriSdeProxy.DatiPrivacy;

            return QueryItem<ResponsabiliPrivacyConsoleDtoWrapper>(serviceUrl)?.items;
        }
    }
}
