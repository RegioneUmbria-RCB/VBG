using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using Init.Sigepro.FrontEnd.Bari.Core.Config;
using Init.Sigepro.FrontEnd.Bari.JSignPdfWebService;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.Bari.FirmaCidPin
{
	public class FirmaCidPinService : IFirmaCidPinService
	{
		ILog _log = LogManager.GetLogger(typeof(FirmaCidPinService));
		ParametriServizioTributi _configurazione;

		public FirmaCidPinService(TributiConfigService configReader)
		{
			this._configurazione = configReader.Read();
		}

		public byte[] Firma(CidPinSignRequest request)
		{
			using (var service = CreateService())
			{
				try
				{
                    var document = new wsDocument
                    {
                        binary = request.FileContent,
                        name = request.FileName
                    };

					this._log.DebugFormat("Firma del documento {0} ({1} bytes) con cid={2}. Parametri: signatureFormat.PAdES_BES, signaturePackaging.ENVELOPING, hash={3}",
											request.FileName, request.FileContent.Length, request.Cid, request.GetCidPinHash());

					var result = service.signDocument2( document, signatureFormat.PAdES_BES, signaturePackaging.ENVELOPING, request.GetCidPinHash());

					return result.binary;
				}
				catch (Exception ex)
				{
					this._log.ErrorFormat("Errore durante la firma del documento {0} con cid {1}: {2}", request.FileName, request.Cid, ex.ToString());

					service.Abort();

					throw;
				}
			}
		}


        private SignatureServiceClient CreateService()
		{
			var binding = new BasicHttpBinding("oggettiServiceBinding");
			var endpoint = new EndpointAddress(this._configurazione.UrlServizioFirmaCidPin);

			this._log.DebugFormat("Creazione del servizio di firma CID/PIN binding={0} url={1}", binding.Name, this._configurazione.UrlServizioFirmaCidPin);

            return new SignatureServiceClient(binding, endpoint);
		}

	}
}
