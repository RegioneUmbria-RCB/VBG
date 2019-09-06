using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using log4net;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class MenuReader
    {
        IAliasSoftwareResolver _aliasResolver;
        IOggettiService _oggettiService;
        IConfigurazione<ParametriMenuV2> _configurazione;
        ILog _log = LogManager.GetLogger(typeof(MenuReader));

        public MenuReader(IAliasSoftwareResolver aliasResolver, IOggettiService oggettiService, IConfigurazione<ParametriMenuV2> configurazione)
        {
            this._aliasResolver = aliasResolver;
            this._oggettiService = oggettiService;
            this._configurazione = configurazione;
        }

        public byte[] Read()
        {
            if (!this._configurazione.Parametri.CodiceOggettoMenu.HasValue)
            {
                return CaricaMenuDaFilesystem();
            }

            return BuildMenuFromOggetto(this._configurazione.Parametri.CodiceOggettoMenu.Value);
        }

        private byte[] BuildMenuFromOggetto(int codiceOggetto)
        {
            var obj = _oggettiService.GetById(codiceOggetto);

            return obj.FileContent;
        }

        private byte[] CaricaMenuDaFilesystem()
        {
            string basePath = HttpContext.Current.Server.MapPath("~/Menu");
            string path = "";
            string idComune = _aliasResolver.AliasComune;
            string software = _aliasResolver.Software;

            // provo con menu_idcomune_software_T per tecnico oppure menu_idcomune_software per non tecnico
            var listaPathDaProvare = new string[]{
					"menu_" + idComune + "_" + software + ".xml",
					"menu_" + idComune + ".xml",
					"menu.xml"
				};

            for (int i = 0; i < listaPathDaProvare.Length; i++)
            {
                path = Path.Combine(basePath, listaPathDaProvare[i]);

                _log.DebugFormat("Tentativo di lettura del file di menu dal path {0}", path);

                if (!File.Exists(path))
                {
                    _log.Debug("Il file non esiste");
                    continue;
                }

                _log.DebugFormat("File di menu trovato, inizio caricamento");

                return File.ReadAllBytes(path);
            }

            return null;
        }

    }
}
