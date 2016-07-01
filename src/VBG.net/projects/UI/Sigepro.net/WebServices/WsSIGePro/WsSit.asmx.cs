namespace Sigepro.net.WebServices.WsSIGePro
{
	using System;
	using System.Linq;
	using System.Web.Services;
	using Init.SIGePro.Data;
	using Init.SIGePro.Sit.Manager;
	using Init.SIGePro.Utils;
	using Init.Utils;
	using log4net;
	using Init.SIGePro.Sit.Data;
	using System.Collections.Generic;

    /// <summary>
    /// Summary description for WsSit
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class WsSit : SigeproWebService
    {
		ILog _log = LogManager.GetLogger(typeof(WsSit));

        const int ERR_LIST_FAILED = 58001;
        const int ERR_VALIDATE_FAILED = 58002;
        const int ERR_DETAIL_FAILED = 58003;

        [WebMethod]
        public ListSit GetListField(string token, string field, Sit dataSit, string software)
        {
            var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				try
				{
					var sitMgr = new SitIntegrationService(db, authInfo.IdComune, authInfo.Alias, software);

					return sitMgr.GetList(field, dataSit);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore in GetListField : {0}", ex.ToString());

					if (authInfo != null)
						Logger.LogEvent(authInfo, "LIST", ex.ToString(), ERR_LIST_FAILED.ToString());

					throw;
				}
			}
        }

        [WebMethod]
        public ValidateSit ValidateField(string token, string field, Sit dataSit, string software)
        {
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				try
				{
					var sitMgr = new SitIntegrationService(db, authInfo.IdComune, authInfo.Alias, software);
					
					return sitMgr.Validate(field, dataSit);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore in ValidateField : {0}", ex.ToString());

					if (authInfo != null)
						Logger.LogEvent(authInfo, "VALIDATE", ex.ToString(), ERR_VALIDATE_FAILED.ToString());

					throw;
				}
			}
        }

        [WebMethod]
        public DetailSit GetDetailField(string token, string field, Sit dataSit, string software)
        {
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				try
				{

					var sitMgr = new SitIntegrationService( db , authInfo.IdComune, authInfo.Alias, software );
					
					return sitMgr.GetDetail(field, dataSit);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore in GetDetailField : {0}", ex.ToString());

					if (authInfo != null)
						Logger.LogEvent(authInfo, "DETAIL", ex.ToString(), ERR_DETAIL_FAILED.ToString());

					throw;
				}
			}
        }

		[WebMethod]
		public bool EffettuaValidazioneFormale(string token, string software, Sit sitClass)
		{
			try
			{
				var authInfo = CheckToken(token);
				var idComune = authInfo.IdComune;
                var idComuneAlias = authInfo.Alias;

				using (var db = authInfo.CreateDatabase())
				{
					var mgr = new SitIntegrationService(db, idComune, idComuneAlias, software);

					return mgr.EffettuaValidazioneFormale(sitClass);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la validazione formale della classe sit, token={0}, software={1}, struttura sit={2} \r\n\r\nEccezione: {3}", token, software, StreamUtils.SerializeClass(sitClass), ex.ToString());

				throw;
			}
		}

		[WebMethod]
		public string[] GetCampiGestiti(string token, string software)
		{
			try
			{
				var authInfo = CheckToken(token);
				var idComune = authInfo.IdComune;
                var idComuneAlias = authInfo.Alias;

				using (var db = authInfo.CreateDatabase())
				{
					var mgr = new SitIntegrationService(db, idComune, idComuneAlias, software);

					return mgr.GetListaCampiGestiti().ToArray();
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la chiamata a GetCampiGestiti con token={0} e software={1}: {2}", token, software, ex.ToString());

				throw;
			}
		}

		[WebMethod]
		public SitFeatures GetFeatures(string token, string software)
		{
			try
			{
				var authInfo = CheckToken(token);
				var idComune = authInfo.IdComune;
				var idComuneAlias = authInfo.Alias;

				using (var db = authInfo.CreateDatabase())
				{
					var mgr = new SitIntegrationService(db, idComune, idComuneAlias, software);

					return mgr.GetFeatures();
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la chiamata a GetCampiGestiti con token={0} e software={1}: {2}", token, software, ex.ToString());

				throw;
			}
		}

		[WebMethod]
		public DettagliVia[] GetListaVie(string token, string software, FiltroRicercaListaVie filtro, List<string> codiciComuni)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				try
				{
					var sitMgr = new SitIntegrationService(db, authInfo.IdComune, authInfo.Alias, software);

					return sitMgr.GetListaVie(filtro, codiciComuni.ToArray());
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore in ValidateField : {0}", ex.ToString());

					if (authInfo != null)
						Logger.LogEvent(authInfo, "VALIDATE", ex.ToString(), ERR_VALIDATE_FAILED.ToString());

					throw;
				}
			}
		}
    }
}
