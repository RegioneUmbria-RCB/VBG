// -----------------------------------------------------------------------
// <copyright file="IGestioneSnapshotAttivita.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.ExternalServices
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Manager.EventiAttivitaWebService;
	using Init.SIGePro.Manager.Configuration;
	using System.ServiceModel;
	using System.Threading.Tasks;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IGestioneSnapshotAttivitaService : IDisposable
	{
		void AggiornaCampiSchede(string token, string software, int idAttivita);
		void AggiornaCampiSchedeDaIstanza(string token, string software, int idAttivita, int codiceIstanza, int idScheda);
		void CreaSnapshot(string token, string software, int idAttivita);
	}

	internal class GestioneSnapshotAttivitaService : IGestioneSnapshotAttivitaService
	{
		private static class Constants
		{
			public const string BindingName = "AttivitaService";
		}

		public GestioneSnapshotAttivitaService(string idComune)
		{
		}


		#region IGestioneSnapshotAttivitaService Members

		public void AggiornaCampiSchedeDaIstanza(string token, string software, int idAttivita, int codiceIstanza, int idScheda)
		{
			var task = Task.Factory.StartNew(() =>
			{
				using (var client = CreaClient())
				{
					try
					{
						client.AggiornaCampiSchede(token, software, idAttivita, codiceIstanza, idScheda);
					}
					catch (Exception)
					{
						client.Abort();
						throw;
					}
				}
			});
		}

		public void AggiornaCampiSchede(string token, string software, int idAttivita)
		{
			using (var client = CreaClient())
			{
				try
				{
					client.AggiornaCampiSchede(token, software, idAttivita, null, null);
				}
				catch (Exception)
				{
					client.Abort();
					throw;
				}
			}
		}

		public void CreaSnapshot(string token, string software, int idAttivita)
		{
			var task = Task.Factory.StartNew(() => {

				using (var client = CreaClient())
				{
					try
					{
						client.SnapShot(token, software, idAttivita);
					}
					catch (Exception)
					{
						client.Abort();
						throw;
					}
				}
			});
		}

		#endregion

		private attivitaClient CreaClient()
		{
			var address = ParametriConfigurazione.Get.WsEventiAttivitaServiceUrl;
			//var address = "http://10.10.45.144:8080/backend/services/attivita?wsdl";

			var binding = new BasicHttpBinding(Constants.BindingName);
			var endpoint = new EndpointAddress(address);


			return new attivitaClient(binding, endpoint);
		}


		#region IDisposable Members

		public void Dispose()
		{

		}

		#endregion
	}
}
