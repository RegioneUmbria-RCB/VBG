// -----------------------------------------------------------------------
// <copyright file="BinaryFileTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using System;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Xunit;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneOggetti
{
    public class BinaryFileTests
    {
        [Fact]
        public void SeNomeFileNonContieneEstensioneSollevaeccezione()
        {
            var nomeFile = "nomeFile.";
            var mimeType = "text/plain";
            var contenuto = new byte[0];
            Assert.Throws<ArgumentException>(() => new BinaryFile(nomeFile, mimeType, contenuto));
        }

        [Fact]
        public void SeNomeFileNonContieneEstensioneSollevaeccezione2()
        {
            var nomeFile = "nomeFile";
            var mimeType = "text/plain";
            var contenuto = new byte[0];
            Assert.Throws<ArgumentException>(() => new BinaryFile(nomeFile, mimeType, contenuto));
        }
    }
}
