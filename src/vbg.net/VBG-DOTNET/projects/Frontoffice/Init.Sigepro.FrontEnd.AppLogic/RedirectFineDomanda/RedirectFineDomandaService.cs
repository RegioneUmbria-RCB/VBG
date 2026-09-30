using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using System;
using System.IO;

namespace Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda
{
    public class RedirectFineDomandaService : IRedirectFineDomandaService
    {
        private readonly IConfigurazione<ParametriARRedirect> _configurazione;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IInterventiRepository _interventiRepository;
        private readonly IPathMapper _pathMapper;

        public RedirectFineDomandaService(IConfigurazione<ParametriARRedirect> configurazione, IAliasSoftwareResolver aliasSoftwareResolver, IInterventiRepository interventiRepository,
            IPathMapper pathMapper)
        {
            this._configurazione = configurazione;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._interventiRepository = interventiRepository;
            this._pathMapper = pathMapper;
        }


        public string GeneraUrlRedirect(int idDomanda)
        {
            if (!this._configurazione.Parametri.VerticalizzazioneAttiva)
            {
                throw new Exception("Verticalizzazione AREARISERVATA_REDIRECT non attiva");
            }

            var str = this._configurazione.Parametri.UrlRedirect;
            str = str.Replace("{alias}", this._aliasSoftwareResolver.AliasComune);
            str = str.Replace("{software}", this._aliasSoftwareResolver.Software);
            str = str.Replace("{idDomanda}", idDomanda.ToString());

            return str;
        }

        public TestoBoxFineDomanda GetTestiBox()
        {
            if (!this._configurazione.Parametri.VerticalizzazioneAttiva)
            {
                return null;
            }

            var nomeFile = this._configurazione.Parametri.NomeFile;
            var pathAssoluto = this._pathMapper.MapPath(nomeFile);

            if (!File.Exists(pathAssoluto))
            {
                return null;
            }

            return new TestoBoxFineDomandaReader(pathAssoluto).Read();
        }

        public bool RedirectAFineDomandaAttivo(int codiceIntervento)
        {
            return this._configurazione.Parametri.VerticalizzazioneAttiva && this._interventiRepository.InterventoSupportaRedirect(codiceIntervento);
        }
    }
}
