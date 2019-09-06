// -----------------------------------------------------------------------
// <copyright file="EndoprocedimentoIncompatibileTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace MovimentiTest.GestioneEndoprocedimenti.IncompatibilitaEndo
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Microsoft.VisualStudio.TestTools.UnitTesting;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;

	[TestClass]
	public class EndoprocedimentoIncompatibileTests
	{
		[TestMethod]
		public void Verifica_della_stringa_restituita_da_ToString_con_un_solo_endo_incompatibile()
		{
			var nomeEndo = "Endo1";
			var nomeEndoIncomp = "Endo2";
			var expected = "L'endoprocedimento \"" + nomeEndo + "\" non è compatibile con l'endoprocedimento \"" + nomeEndoIncomp + "\"";
			var arrEndoIncomp = new string[] { nomeEndoIncomp };

			var ei = new EndoprocedimentoIncompatibile(nomeEndo, arrEndoIncomp);

			Assert.AreEqual<string>(expected, ei.ToString());
		}

		[TestMethod]
		public void Verifica_della_stringa_restituita_da_ToString_con_piu_di_un_endo_incompatibile()
		{
			var nomeEndo = "Endo1";
			var nomeEndoIncomp1 = "Endo2";
			var nomeEndoIncomp2 = "Endo2";
			var expected = "L'endoprocedimento \"" + nomeEndo + "\" non è compatibile con i seguenti endoprocedeimenti: \"" + nomeEndoIncomp1 + "\", \"" + nomeEndoIncomp2 + "\"";
			var arrEndoIncomp = new string[] { nomeEndoIncomp1, nomeEndoIncomp2 };

			var ei = new EndoprocedimentoIncompatibile(nomeEndo, arrEndoIncomp);

			Assert.AreEqual<string>(expected, ei.ToString());
		}
	}
}
