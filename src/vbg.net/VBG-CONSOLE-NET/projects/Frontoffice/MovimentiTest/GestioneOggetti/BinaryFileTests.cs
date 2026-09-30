// -----------------------------------------------------------------------
// <copyright file="BinaryFileTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneOggetti
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Microsoft.VisualStudio.TestTools.UnitTesting;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    /// 
    [TestClass]
    public class BinaryFileTests
    {
        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void SeNomeFileNonContieneEstensioneSollevaeccezione()
        {
            var nomeFile = "nomeFile.";
            var mimeType = "text/plain";
            var contenuto = new byte[0];

            var file = new BinaryFile(nomeFile, mimeType, contenuto);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void SeNomeFileNonContieneEstensioneSollevaeccezione2()
        {
            var nomeFile = "nomeFile";
            var mimeType = "text/plain";
            var contenuto = new byte[0];

            var file = new BinaryFile(nomeFile, mimeType, contenuto);
        }
    }
}
