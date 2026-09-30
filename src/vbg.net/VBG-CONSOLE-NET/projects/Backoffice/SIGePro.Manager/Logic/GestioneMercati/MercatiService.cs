using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using Init.SIGePro.Manager.WsMercatiService;
using System.ServiceModel;
using Init.SIGePro.Manager.Configuration;

namespace Init.SIGePro.Manager.Logic.GestioneMercati
{
	internal class MercatiService
	{
		private static class Constants
		{
			public const string BindingName = "mercatiHttpBinding";
		}

		string _token;
		ILog _log = LogManager.GetLogger(typeof(MercatiService));


		internal MercatiService(string token)
		{
			if (string.IsNullOrEmpty(token))
				throw new Exception("Impossibile utilizzare MercatiService senza aver impostato il token");

			this._token = token;
		}

		private MercatiClient CreateClient()
		{
			_log.DebugFormat("Inizializzazione del servizio di gestione mercati all'indirizzo {0} utilizzando il binding {1}", ParametriConfigurazione.Get.WsMercatiServiceUrl, Constants.BindingName);

			var endpoint = new EndpointAddress(ParametriConfigurazione.Get.WsMercatiServiceUrl);
			var binding = new BasicHttpBinding(Constants.BindingName);

			return new MercatiClient(binding, endpoint);
		}

		internal int GetPresenze( int codiceistanza, string autoriznumero, string autorizdata, string autorizcomune, int fkidregistro, string catMerc, bool inserisciAutSeNonTrovata)
		{
			try
			{
				using (var client = CreateClient())
				{
					var estremi = new EstremiAut
					{
						autoriznumero = autoriznumero,
						autorizdata = autorizdata,
						codiceAutorizcomune = autorizcomune,
						codiceAutorizregistro = fkidregistro
					};

					var req = new PresenzeRequest
					{
						codiceIstanza = codiceistanza,
						token = this._token,
						inserisciAutSeNonTrovata = inserisciAutSeNonTrovata,
						estremiAut = estremi,
						catMerc = catMerc
					};

					var response = client.Presenze(req);

					return response.numeroPresenze;
				}
			}catch(Exception ex)
			{
				_log.ErrorFormat( "Errore durante l'invocazione al servizio di gestione mercati: {0}", ex.ToString());

				throw;
			}
		}

		internal int GetPresenzeTitolare( int codiceistanza, string autoriznumero, string autorizdata, string autorizcomune, int fkidregistro, string catMerc, bool inserisciAutSeNonTrovata)
		{
			try
			{
				using (var client = CreateClient())
				{
					var estremi = new EstremiAut
					{
						autoriznumero = autoriznumero,
						autorizdata = autorizdata,
						codiceAutorizcomune = autorizcomune,
						codiceAutorizregistro = fkidregistro
					};

					var req = new PresenzeProprietarioRequest
					{
						codIstanza = codiceistanza,
						token = this._token,
						inserisciAutSeNonTrovata = inserisciAutSeNonTrovata,
						estremiAut = estremi,
						catMerc = catMerc
					};

					var response = client.PresenzeProprietario(req);

					return response.numeroPresenzeProp;

				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante l'invocazione del metodo GetPresenzeTitolare del servizio di gestione mercati: {0}", ex.ToString());

				throw;
			}


		}

        internal int presenzeManifestazione(string autoriznumero, string autorizdata, string autorizcomune, int fkidregistro, string catMerc, int codiceManifestazione, int? codiceUso, bool inserisciAutSeNonTrovata)
        {
            try
            {
                using (var client = CreateClient())
                {
                    var estremi = new EstremiAut
                    {
                        autoriznumero = autoriznumero,
                        autorizdata = autorizdata,
                        codiceAutorizcomune = autorizcomune,
                        codiceAutorizregistro = fkidregistro
                    };

                    var req = new PresenzeManifestazioneRequest
                    {
                        catMerc = catMerc,
                        codiceManifestazione = codiceManifestazione,
                        estremiAut = estremi,
                        inserisciAutSeNonTrovata = inserisciAutSeNonTrovata,
                        token = this._token,
                    };

                    if (codiceUso != null)
                    {
                        req.codiceUso = codiceUso.Value;
                        req.codiceUsoSpecified = true;
                    }
                    else
                    {
                        req.codiceUsoSpecified = false;
                    }


                    var response = client.PresenzeManifestazione(req);


                    return response.numeroPresenze;

                }
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("Errore durante l'invocazione del metodo presenzeManifestazione del servizio di gestione mercati: {0}", ex.ToString());

                throw;
            }
        }
	}
}
