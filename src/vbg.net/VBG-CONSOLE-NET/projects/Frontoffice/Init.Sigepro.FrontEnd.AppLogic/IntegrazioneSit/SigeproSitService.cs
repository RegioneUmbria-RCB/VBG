using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.SigeproSitWebService;
using log4net;
using Init.Utils;

namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
	public class SigeproSitService : ISitService
	{
		ILog _log = LogManager.GetLogger(typeof(SigeproSitService));

		SitServiceCreator _serviceCreator;
		IAliasSoftwareResolver _aliasSoftwareResolver;
		CaratteristicheSit _features;

		public SigeproSitService(IAliasSoftwareResolver aliasSoftwareResolver,SitServiceCreator serviceCreator)
		{
			this._serviceCreator = serviceCreator;
			this._aliasSoftwareResolver = aliasSoftwareResolver;
		}

		public CaratteristicheSit GetFeatures(string codiceComune)
		{
			if (this._features != null)
				return this._features;

			using (var ws = this._serviceCreator.CreateClient(codiceComune))
			{
				try
				{
					var features = ws.Service.GetFeatures(ws.Token, this._aliasSoftwareResolver.Software);

					this._features = new CaratteristicheSit(features.CampiGestiti, features.VisualizzazioniFrontoffice);

					return this._features;
				}
				catch (Exception ex)
				{
					ws.Service.Abort();

					_log.ErrorFormat("Errore durante l'interrogazione del sit (GetCampiSupportati): {0}", ex.ToString());

					throw;
				}
			}
		}

		public EsitoValidazioneSit ValidaCampo(string codiceComune, string nomeCampo, IParametriRicercaLocalizzazione parametriRicerca)
		{
			using (var ws = this._serviceCreator.CreateClient(codiceComune))
			{
				try
				{
					var result = ws.Service.ValidateField(ws.Token, nomeCampo, parametriRicerca.ToSit(), this._aliasSoftwareResolver.Software);

					if (String.IsNullOrEmpty(result.Message))
						return EsitoValidazioneSit.FromSitClass(result.DataSit);


					_log.ErrorFormat("Errore durante l'interrogazione del sit (ValidaCampo): {0}, parametri: {1}", result.Message, StreamUtils.SerializeClass(parametriRicerca.ToSit()));
				}
				catch (Exception ex)
				{
					ws.Service.Abort();

					_log.ErrorFormat("Errore durante l'interrogazione del sit (ValidaCampo): {0}, parametri: {1}", ex.ToString(), StreamUtils.SerializeClass(parametriRicerca.ToSit()));
				}

				return null;
			}
		}

		public string[] RicercaValori(string codiceComune, string nomeCampo, IParametriRicercaLocalizzazione parametriRicerca)
		{
			using (var ws = this._serviceCreator.CreateClient(codiceComune))
			{
				try
				{
					var result = ws.Service.GetListField(ws.Token, nomeCampo, parametriRicerca.ToSit(), this._aliasSoftwareResolver.Software);

					if (String.IsNullOrEmpty(result.Message))
						return result.Field;

					_log.ErrorFormat("Errore durante l'interrogazione del sit (GetListaCampi): {0}, parametri: {1}", result.Message, StreamUtils.SerializeClass(parametriRicerca.ToSit()));
				}
				catch (Exception ex)
				{
					ws.Service.Abort();

					_log.ErrorFormat("Errore durante l'interrogazione del sit (GetListaCampi): {0}, parametri: {1}", ex.ToString(), StreamUtils.SerializeClass(parametriRicerca.ToSit()));
				}
				
				return null;				
			}
		}
	}
}
