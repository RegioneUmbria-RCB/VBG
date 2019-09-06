// -----------------------------------------------------------------------
// <copyright file="NomeFileRiepilogoDatiDinamiciTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace MovimentiTest.TestSegnapostoSchedeDinamiche
{
	using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli;
	using Microsoft.VisualStudio.TestTools.UnitTesting;

	[TestClass]
	public class NomeFileRiepilogoDatiDinamiciTests
	{
		[TestMethod]
		public void Se_il_nome_file_contiene_caratteri_non_validi_i_questi_vengono_sostituiti_con_un_underscore()
		{
			var nomeFile = "\\";
			var expected = "_.pdf";

			var result = new NomeFileRiepilogoModello(nomeFile, -1).ToString();

			Assert.AreEqual(expected, result);

			nomeFile = ":";
			expected = "_.pdf";

			result = new NomeFileRiepilogoModello(nomeFile, -1).ToString();

			Assert.AreEqual(expected, result);
		}
	}
}
