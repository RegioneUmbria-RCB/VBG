using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.WsVerificaFirmaDigitale;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{
	public enum StatoVerificaFirma
	{
		FirmaValida,
		FirmaNonValida,
		CertificatoRevocato,
		Errore
	}

	public class EsitoVerificaFirmaDigitale
	{
		public StatoVerificaFirma Stato { get; private set; }
		public string Errore { get; private set; }

		public EsitoVerificaFirmaDigitale(StatoVerificaFirma esito)
		{
			this.Stato = esito;
			this.Errore = EstraiMessaggioDaEsito(esito);
		}

		private static string EstraiMessaggioDaEsito(StatoVerificaFirma esito)
		{
			switch (esito)
			{
				case StatoVerificaFirma.CertificatoRevocato:
					return "Certificato revocato o non valido";
				case StatoVerificaFirma.Errore:
					return "Errore durante la verifica della firma";
				case StatoVerificaFirma.FirmaNonValida:
					return "Il file non sembra essere firmato digitalmente o contiene una firma non valida";
			}

			return String.Empty;
		}
	}

	public interface IVerificaFirmaDigitaleService
	{
		EsitoVerificaFirmaDigitale VerificaFirmaDigitale(BinaryFile file);
		EsitoVerificaFirmaDigitale VerificaFirmaDigitale(int codiceOggetto);
	}

	public interface IFirmaDigitaleMetadataService
	{
		EsitoVerificaFirma EstraiDatiFirma(BinaryFile fileFirmato);
		BinaryFile GetFileInChiaro(BinaryFile fileFirmato);	
	}



	public class VerificaFirmaDigitaleService : IVerificaFirmaDigitaleService, IFirmaDigitaleMetadataService
	{
		ILog _log = LogManager.GetLogger(typeof(VerificaFirmaDigitaleService));
		FirmaDigitaleServiceCreator _serviceCreator;
		IOggettiService _oggettiService;
		
		public VerificaFirmaDigitaleService(FirmaDigitaleServiceCreator serviceCreator, IOggettiService oggettiService)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			this._serviceCreator = serviceCreator;
			this._oggettiService = oggettiService;
		}

		public EsitoVerificaFirma EstraiDatiFirma(BinaryFile fileFirmato)
		{
			try
			{
				using (var ws = _serviceCreator.CreateClient())
				{
					var validationResult = ws.Service.validateDocument(new wsDocument { binary = fileFirmato.FileContent, name = fileFirmato.FileName}, null, false);

					return new EsitoVerificaFirma(validationResult);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("FirmaDigitaleManager.EstraiDatiFirma -> Errore la lettura dei dati del certificato: {0}", ex.ToString());

				return null;
			}
		}

		public BinaryFile GetFileInChiaro(BinaryFile fileFirmato)
		{
			try
			{
				using (var ws = _serviceCreator.CreateClient())
				{
					var validationResult = ws.Service.validateDocument(
						new wsDocument 
						{ 
							binary = fileFirmato.FileContent, 
							name = fileFirmato.FileName
						}, null, true);

					return new BinaryFile( validationResult.content.name, "application/octet-stream", validationResult.content.binary);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("FirmaDigitaleManager.LeggiCertificato -> Errore la lettura dei dati del certificato: {0}", ex.ToString());

				return null;
			}
		}

		public EsitoVerificaFirmaDigitale VerificaFirmaDigitale(BinaryFile file)
		{

			try
			{
				using (var ws = _serviceCreator.CreateClient())
				{
					// solleva un eccezizone se la firma non è valida
					var validationResult = ws.Service.validateDocument( new wsDocument{
						binary = file.FileContent,
						name = file.FileName
					}, null, false);

					if (validationResult == null)
						return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.Errore);

					if (!validationResult.IsFirmaValida())
						return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.FirmaNonValida);

					if (validationResult.IsCertificatoRevocato())
						return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.CertificatoRevocato);

					return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.FirmaValida);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la verifica della firma digitale: {0}", ex.ToString());

				return new EsitoVerificaFirmaDigitale(StatoVerificaFirma.Errore);
			}
		}

		public EsitoVerificaFirmaDigitale VerificaFirmaDigitale(int codiceOggetto)
		{
			var file = this._oggettiService.GetById(codiceOggetto);

			return VerificaFirmaDigitale(file);
		}
	}

	
}
