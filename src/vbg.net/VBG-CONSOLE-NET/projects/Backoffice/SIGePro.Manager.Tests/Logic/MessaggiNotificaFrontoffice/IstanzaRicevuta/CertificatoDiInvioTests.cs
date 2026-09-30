// -----------------------------------------------------------------------
// <copyright file="CertificatoDiInvioTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice.IstanzaRicevuta
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Microsoft.VisualStudio.TestTools.UnitTesting;
	using Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice.IstanzaRicevuta;
	using Init.SIGePro.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	[TestClass]
	public class CertificatoDiInvioTests
	{
		[TestMethod]
		public void PopolaConDati_CompilaTitoloECorpo()
		{
			var titolo = "<xsl:value-of select=\"/Istanze/NUMEROISTANZA\" />";
			var corpo = "<xsl:value-of select=\"/Istanze/IDCOMUNE\" />";
			var dati = new Istanze{
				NUMEROISTANZA = "123",
				IDCOMUNE = "IdComune"
			};
			var certificato = CertificatoDiInvio.FromXsl(titolo, corpo);

			var result = certificato.PopolaConDati(dati);

			Assert.AreEqual<string>(dati.NUMEROISTANZA, result.Titolo);
			Assert.AreEqual<string>(dati.IDCOMUNE, result.Corpo);
		}
	}
}
