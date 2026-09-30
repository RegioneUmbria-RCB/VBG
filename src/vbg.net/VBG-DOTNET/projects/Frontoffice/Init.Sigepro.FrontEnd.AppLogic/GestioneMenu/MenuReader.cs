using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using log4net;
using System.IO;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class MenuReader : IMenuReader
    {
        private readonly IAliasSoftwareResolver _aliasResolver;
        private readonly IOggettiService _oggettiService;
        private readonly IConfigurazione<ParametriMenuV2> _configurazione;
        private readonly IPathMapper _pathMapper;
        private readonly ILog _log = LogManager.GetLogger(typeof(MenuReader));

        public MenuReader(IAliasSoftwareResolver aliasResolver, IOggettiService oggettiService, IConfigurazione<ParametriMenuV2> configurazione, IPathMapper pathMapper)
        {
            this._aliasResolver = aliasResolver;
            this._oggettiService = oggettiService;
            this._configurazione = configurazione;
            this._pathMapper = pathMapper;
        }

        public MenuFile Read()
        {
            if (!this._configurazione.Parametri.CodiceOggettoMenu.HasValue)
            {
                return new ByteArrayMenuFile(this.CaricaMenuDaFilesystem());
            }

            return new ByteArrayMenuFile(this.BuildMenuFromOggetto(this._configurazione.Parametri.CodiceOggettoMenu.Value));
        }

        private byte[] BuildMenuFromOggetto(int codiceOggetto)
        {
            var obj = this._oggettiService.GetById(codiceOggetto);

            return obj.FileContent;
        }

        private byte[] CaricaMenuDaFilesystem()
        {
            string basePath = this._pathMapper.MapPath("~/Menu");
            string path = "";
            string idComune = this._aliasResolver.AliasComune;
            string software = this._aliasResolver.Software;

            // provo con menu_idcomune_software_T per tecnico oppure menu_idcomune_software per non tecnico
            var listaPathDaProvare = new string[]{
                    "menu_" + idComune + "_" + software + ".xml",
                    "menu_" + idComune + ".xml",
                    "menu.xml"
                };

            for (int i = 0; i < listaPathDaProvare.Length; i++)
            {
                path = Path.Combine(basePath, listaPathDaProvare[i]);

                this._log.DebugFormat("Tentativo di lettura del file di menu dal path {0}", path);

                if (!File.Exists(path))
                {
                    this._log.Debug("Il file non esiste");
                    continue;
                }

                this._log.DebugFormat("File di menu trovato, inizio caricamento");

                return File.ReadAllBytes(path);
            }

            return null;
        }
    }
}
