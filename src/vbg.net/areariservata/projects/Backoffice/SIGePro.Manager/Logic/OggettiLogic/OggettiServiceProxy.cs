using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Authentication;
using System.Web;
using Init.SIGePro.Manager.Logic.Caching;
using Init.SIGePro.Manager.WsSigeproSecurity;
using PersonalLib2.Data;
using Init.SIGePro.Manager.WsOggettiService;
using System.ServiceModel;
using log4net;
using Init.Utils;
using Init.SIGePro.Manager.Configuration;

namespace Init.SIGePro.Manager.Logic.OggettiLogic
{
	public class OggettiServiceProxy
	{
		private static class Constants
		{
			public const string BindingName = "OggettiMtomBinding";
		}

		ILog _log = LogManager.GetLogger(typeof(OggettiServiceProxy));
		DataBase _db;

		public OggettiServiceProxy(DataBase db)
		{
			_db = db;
		}


		private string GetToken()
		{
			return _db.ConnectionDetails.Token;
		}


		private OggettiClient CreateClient()
		{
			_log.DebugFormat("Creazione del client per il web service di gestione Oggetti sull'endpoint {0} utilizzando il binding {1}", ParametriConfigurazione.Get.WsOggettiServiceUrl, Constants.BindingName);

			var binding  = new BasicHttpBinding( Constants.BindingName );
			var endpoint = new EndpointAddress( ParametriConfigurazione.Get.WsOggettiServiceUrl);

			var ws = new OggettiClient(binding, endpoint );

			return ws;
		}

		public int InsertOggetto(string fileName, string mimeType, byte[] fileData)
		{

			try
			{
				using (var ws = CreateClient())
				{
					_log.DebugFormat("Invocazione di InsertOggetto all'indirizzo: {0}", ws.Endpoint.Address);

					var req = new OggettiInsertRequest
					{
						binaryData = fileData,
						fileName = fileName,
						mimeType = mimeType,
						token = GetToken()
					};

					var res = ws.OggettiInsert(req);

					return Convert.ToInt32(res.id);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore nella chiamata a InsertOggetto: {0}", ex.ToString());

				throw;
			}
		}

		public void UpdateOggetto(int id, string fileName,byte[] fileData)
		{
			try
			{
				using (var ws = CreateClient())
				{
					_log.DebugFormat("Invocazione di UpdateOggetto all'indirizzo: {0}", ws.Endpoint.Address);

					var req = new OggettiUpdateRequest
					{
						binaryData = fileData,
						id = id.ToString(),
						fileName = fileName,
						token = GetToken()
					};

					var res = ws.OggettiUpdate(req);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore nella chiamata a UpdateOggetto: {0}", ex.ToString());

				throw;
			}
		}

		public void DeleteOggetto(int id)
		{
			try
			{
				using (var ws = CreateClient())
				{
					_log.DebugFormat("Invocazione di DeleteOggetto all'indirizzo: {0}", ws.Endpoint.Address);

					var req = new OggettiDeleteRequest
					{
						id = id.ToString(),
						token = GetToken()						
					};

					var res = ws.OggettiDelete(req);

				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore nella chiamata a DeleteOggetto: {0}", ex.ToString());

				throw;
			}
		}

		public string GetFileName(int id)
		{
			try
			{
				using (var ws = CreateClient())
				{
					_log.DebugFormat("Invocazione di GetFileName all'indirizzo: {0}", ws.Endpoint.Address);

					var req = new OggettiFindNomeRequest
					{
						id = id.ToString(),
						token = GetToken()
					};

					var res = ws.OggettiFindNome(req);

					return res.fileName;
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore nella chiamata a GetFileName: {0}", ex.ToString());

				throw;
			}
		}

		public Init.SIGePro.Data.Oggetti GetById(int id)
		{
			var res = GetByIdNativo(id);
			
			if (res == null || String.IsNullOrEmpty(res.fileName))
				return null;

			return new Init.SIGePro.Data.Oggetti
			{
				CODICEOGGETTO = id.ToString(),
				NOMEFILE = res.fileName,
				OGGETTO = res.binaryData
			};
		}

		public OggettiFindResponse GetByIdNativo(int id)
		{
			try
			{
				using (var ws = CreateClient())
				{
					_log.DebugFormat("Invocazione di GetFileName all'indirizzo: {0}", ws.Endpoint.Address);

					var req = new OggettiFindRequest
					{
						id = id.ToString(),
						token = GetToken()
					};

					var res = ws.OggettiFind(req);
                    
					return res;
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore nella chiamata a OggettiFind: {0}", ex.ToString());

				throw;
			}
		}
	}
}
