// -----------------------------------------------------------------------
// <copyright file="ControlSafeNomeCampoTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace SIGePro.DatiDinamici.v1.Tests.WebControls
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Microsoft.VisualStudio.TestTools.UnitTesting;
    using Init.SIGePro.DatiDinamici.WebControls;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    [TestClass]
    public class ControlSafeNomeCampoTests
    {
        [TestMethod]
        public void I_caratteri_non_testuali_vengono_sostituiti_con_underscore()
        {
            var test     = "Nome-campo/con caratteri strani'";
            var expected = "NOME_CAMPO_CON_CARATTERI_STRANI_";

            var nomeCampo = new ControlSafeNomeCampo(test);

            Assert.AreEqual<string>(expected, nomeCampo.ToString());


        }
    }
}
