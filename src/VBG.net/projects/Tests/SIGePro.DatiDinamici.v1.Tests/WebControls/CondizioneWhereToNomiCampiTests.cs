// -----------------------------------------------------------------------
// <copyright file="CondizioneWhereToNomiCampiTests.cs" company="">
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
    public class CondizioneWhereToNomiCampiTests
    {
        [TestMethod]
        public void RecuperonomiCampi()
        {
            var expectedLength = 2;
            var expected1 = "ID_CAMPO_DINAMICO_1";
            var expected2 = "ID_CAMPO_DINAMICO_2";
            var condizione = "CAMPO_DB1={ID_CAMPO_DINAMICO_1} and CAMPO_DB2={ID_CAMPO_DINAMICO_2}";

            var campi = new CondizioneWhereToNomiCampi(condizione).GetNomiCampi();

            Assert.AreEqual<int>(expectedLength, campi.Count());
            Assert.AreEqual<string>(expected1, campi.ElementAt(0));
            Assert.AreEqual<string>(expected2, campi.ElementAt(1));

        }
    }
}
