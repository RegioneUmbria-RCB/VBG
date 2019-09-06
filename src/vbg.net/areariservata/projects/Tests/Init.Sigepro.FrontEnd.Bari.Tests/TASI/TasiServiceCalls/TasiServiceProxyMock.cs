// -----------------------------------------------------------------------
// <copyright file="TasiServiceProxyMock.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Tests.TASI.TasiServiceCalls
{
	using Init.Sigepro.FrontEnd.Bari.Core;
	using Init.Sigepro.FrontEnd.Bari.Core.Config;
	using Init.Sigepro.FrontEnd.Bari.TASI;
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs.Converters;
	using Init.Sigepro.FrontEnd.Bari.TASI.wsdl;

	internal class MockViaResolver: IResolveViaDaCodviario
	{
		public string GetNomeByCodViario(string codViario)
		{
			return "Nome via";
		}
	}

    internal class MockComuneResolver : IResolveComuneDaCodiceIstat
    {
        public string GetComuneDaCodiceIstat(string codiceIstat)
        {
            return "Nome comune";
        }
    }


	internal class ResponseMapper : DatiImmobiliResponseTypeToDatiContribuenteTasiDto
	{
		public ResponseMapper(): base(
						new DatiIndirizzoTypeToIndirizzoTasiDto(new MockViaResolver()),
						new DatiImmobileMapper(),
                        new MockComuneResolver()
					)
		{

		}
	}

	internal class DatiImmobileMapper : DatiImmobileResponseTypeToImmobileTasiDto
	{
		public DatiImmobileMapper() : base(new IndirizzoImmobileTypeToIndirizzoTasiDto(new MockViaResolver()),
											new DatiCatastaliTypeToDatiCatastaliDto())
		{

		}
	}

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	internal class TasiServiceProxyMock : TasiServiceProxy
	{
		public class TributiConfigServiceMock : ITributiConfigService
		{
			public ParametriServizioTributi Read()
			{
				return new ParametriServizioTributi
				{
					IdentificativoUtente = "Utente",
					Password = "Password",
					UrlServizioTares = "UrlServizioTares",
					UrlServizioTasi = "UrlServizioTasi"
				};
			}
		}

		// DatiImmobiliResponseTypeToDatiContribuenteTasiDto
		// DatiIndirizzoTypeToIndirizzoTasiDto
		// DatiImmobileResponseTypeToImmobileTasiDto
		// IndirizzoImmobileTypeToIndirizzoTasiDto
		// DatiCatastaliTypeToDatiCatastaliDto


		ITasiServicePortTypeClient TasiServicePortTypeClient { get; set; }

		public TasiServiceProxyMock(ITasiServicePortTypeClient tasiServicePortTypeClient)
			: base(new TributiConfigServiceMock(),new ResponseMapper())
		{
			this.TasiServicePortTypeClient = tasiServicePortTypeClient;
		}


		protected override ITasiServicePortTypeClient CreateClient()
		{
			return this.TasiServicePortTypeClient;
		}
	}
}
