using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda
{
    public class RedirectFineDomandaService : IRedirectFineDomandaService
    {
        IConfigurazione<ParametriARRedirect> _configurazione;
        IAliasSoftwareResolver _aliasSoftwareResolver;
        IInterventiRepository _interventiRepository;

        public RedirectFineDomandaService(IConfigurazione<ParametriARRedirect> configurazione, IAliasSoftwareResolver aliasSoftwareResolver, IInterventiRepository interventiRepository)
        {
            this._configurazione = configurazione;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._interventiRepository = interventiRepository;
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
            var pathAssoluto = HttpContext.Current.Server.MapPath(nomeFile);

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
