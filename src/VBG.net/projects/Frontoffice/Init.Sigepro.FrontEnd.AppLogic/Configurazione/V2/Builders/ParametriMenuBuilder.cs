using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Utils;
using log4net;
using Init.Utils.Xml;
using System.Web;
using System.IO;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;


namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriMenuBuilder : AreaRiservataWsConfigBuilder , IConfigurazioneBuilder<ParametriMenu>
	{
		ILog _log = LogManager.GetLogger(typeof(ParametriMenuBuilder));
		IOggettiService _oggettiService;
		IAliasSoftwareResolver _aliasResolver;

		public ParametriMenuBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository, IOggettiService oggettiService)
			: base(aliasResolver, configurazioneAreaRiservataRepository)
		{
			if (oggettiService == null)
				throw new ArgumentNullException("oggettiRepository");

			this._oggettiService = oggettiService;
			this._aliasResolver = aliasResolver;
		}


		#region IBuilder<ParametriMenu> Members

		public ParametriMenu Build()
		{
			var cfg = GetConfig();

			if (cfg.CodiceOggettoMenuXml.HasValue)
				return BuildMenuFromOggetto(cfg.CodiceOggettoMenuXml.Value);

			return BuildMenuFromFilesystem();
		}

		private ParametriMenu BuildMenuFromFilesystem()
		{
			var menu = CaricaMenuDaFilesystem();

			return new ParametriMenu(menu.TitoloPagina, menu.DescrizionePagina, menu.VociMenu);
		}

		private ParametriMenu BuildMenuFromOggetto(int codiceOggetto)
		{
			var obj = _oggettiService.GetById( codiceOggetto );
			var aliasComune = _aliasResolver.AliasComune;

			var menu = MenuNavigazioneFlyweight.LoadFrom(obj.FileContent, aliasComune);

			return new ParametriMenu(menu.TitoloPagina , menu.DescrizionePagina, menu.VociMenu);
		}

		private MenuNavigazioneFlyweight CaricaMenuDaFilesystem()
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

				return MenuNavigazioneFlyweight.LoadFrom(path, idComune);
			}

			return null;
		}

		#endregion
	}
}
