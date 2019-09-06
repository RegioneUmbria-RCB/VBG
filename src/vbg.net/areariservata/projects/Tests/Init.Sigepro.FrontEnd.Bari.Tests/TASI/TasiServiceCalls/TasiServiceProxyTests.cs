// -----------------------------------------------------------------------
// <copyright file="TasiServiceProxyTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Tests.TASI.TasiServiceCalls
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.Bari.TASI.wsdl;
	using Init.Sigepro.FrontEnd.Bari.TASI;

	[TestClass]
	public class TasiServiceProxyTests
	{
		TasiServicePortTypeClientMock client;
		datiImmobiliContribuenteResponse rispostaPositiva;
		datiImmobiliResponseType utenzaRisposta;
		string codiceFiscaleDummy = "XXXYYY00Z00Y000X";
		string partitaIvaDummy = "12345678901";
		string pecDummy = "pec@pec.com";

		[TestInitialize]
		public void Initialize()
		{
			utenzaRisposta = new datiImmobiliResponseType
			{
				codContribuente = "123",
				datiResidenzaContribuente = new datiIndirizzoType(),
				elencoImmobili = new datiImmobileResponseType[]{new datiImmobileResponseType()},
				idContribuente = "123",
				Item = new datiAnagraficiPersonaFisicaType()
			};

			rispostaPositiva = new datiImmobiliContribuenteResponse
			{
				datiImmobiliContribuente = utenzaRisposta
			};

		}
		
		[TestMethod]
		public void Chiamata_a_GetDatiImmobile_con_codice_fiscale_utente()
		{
			client = new TasiServicePortTypeClientMock();
			client.DatiImmobiliContribuenteResponse = rispostaPositiva;

			var mock = new TasiServiceProxyMock(client);

			var result = mock.GetDatiContribuenteByCodiceFiscale(codiceFiscaleDummy, pecDummy, codiceFiscaleDummy);

			Assert.AreEqual<string>(codiceFiscaleDummy, client.DatiIdentificativi.Item1);
			Assert.AreEqual<Item1ChoiceType>(Item1ChoiceType.codiceFiscaleContribuente, client.DatiIdentificativi.Item1ElementName);
		}

		[TestMethod]
		public void Chiamata_a_GetDatiImmobile_con_partita_iva_utente()
		{
			client = new TasiServicePortTypeClientMock();
			client.DatiImmobiliContribuenteResponse = rispostaPositiva;

			var mock = new TasiServiceProxyMock(client);

			var result = mock.GetDatiContribuenteByCodiceFiscale(codiceFiscaleDummy, pecDummy, partitaIvaDummy);

			Assert.AreEqual<string>(partitaIvaDummy, client.DatiIdentificativi.Item1);
			Assert.AreEqual<Item1ChoiceType>(Item1ChoiceType.partitaIVAContribuente, client.DatiIdentificativi.Item1ElementName);
		}

		[TestMethod]
		public void Chiamata_a_GetDatiImmobile_con_partita_iva_caf()
		{
			client = new TasiServicePortTypeClientMock();
			client.DatiImmobiliContribuenteResponse = rispostaPositiva;

			var mock = new TasiServiceProxyMock(client);

			var result = mock.GetDatiContribuenteByCodiceFiscale(partitaIvaDummy, pecDummy, codiceFiscaleDummy);

			Assert.AreEqual<string>(partitaIvaDummy, client.DatiIdentificativi.Item);
			Assert.AreEqual<ItemChoiceType>(ItemChoiceType.partitaIVACAF, client.DatiIdentificativi.ItemElementName);
		}

		[TestMethod]
		public void Chiamata_a_GetDatiImmobile_con_codice_fiscale_caf()
		{
			client = new TasiServicePortTypeClientMock();
			client.DatiImmobiliContribuenteResponse = rispostaPositiva;

			var mock = new TasiServiceProxyMock(client);

			var result = mock.GetDatiContribuenteByCodiceFiscale(codiceFiscaleDummy, pecDummy, codiceFiscaleDummy);

			Assert.AreEqual<string>(codiceFiscaleDummy, client.DatiIdentificativi.Item);
			Assert.AreEqual<ItemChoiceType>(ItemChoiceType.codiceFiscaleCAF, client.DatiIdentificativi.ItemElementName);
		}

		[TestMethod]
		[ExpectedException(typeof(TasiServiceInvocationException))]
		public void GetDatiImmobile_restituisce_errore()
		{
			client = new TasiServicePortTypeClientMock();
			client.DatiImmobiliContribuenteResponse = new datiImmobiliContribuenteResponse 
			{ 
				code = 100,
				messaggio = "Errore",
				datiImmobiliContribuente = null
			};

			var mock = new TasiServiceProxyMock(client);

			var result = mock.GetDatiContribuenteByCodiceFiscale(codiceFiscaleDummy, pecDummy, codiceFiscaleDummy);
		}
	}
}
