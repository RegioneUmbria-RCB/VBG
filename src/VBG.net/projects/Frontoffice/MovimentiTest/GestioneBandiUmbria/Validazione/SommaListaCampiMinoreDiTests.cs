// -----------------------------------------------------------------------
// <copyright file="SommaListaCampiMinoreDiTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneBandiUmbria.Validazione
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Microsoft.VisualStudio.TestTools.UnitTesting;
    using Init.Sigepro.FrontEnd.AppLogic.ServizioPrecompilazionePDF;
    using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    [TestClass]
    public class SommaListaCampiMinoreDiTests
    {
        private DatiPDFType CreaCampo(string nome, string valore)
        {
            return new DatiPDFType
            {
                codice = nome,
                valore = new[] { valore }
            };
        }

        [TestMethod]
        public void SommaMinoreDiValoreConfronto_restituisceTrue()
        {
            var nomeCampo1 = "campo[n]";
            var confronto = 11.0m;

            var valoriCampi = new[] { 
                CreaCampo("campo1","1"),
                CreaCampo("campo2","2"),
                CreaCampo("campo3","3"),
                CreaCampo("campo4","4")
            };

            var datiPdf = new DatiPdfCompilabile(valoriCampi, "testfile.pdf");

            var spec = new SommaListaCampiMinoreDi(nomeCampo1, valoriCampi.Length, confronto);

            var result = spec.IsSatisfiedBy(datiPdf);

            Assert.IsTrue(result);
        }

        [TestMethod]
        public void SommaMaggioreDiValoreConfronto_restituisceFalse()
        {
            var nomeCampo1 = "campo[n]";
            var confronto = 9.0m;

            var valoriCampi = new[] { 
                CreaCampo("campo1","1"),
                CreaCampo("campo2","2"),
                CreaCampo("campo3","3"),
                CreaCampo("campo4","4")
            };

            var datiPdf = new DatiPdfCompilabile(valoriCampi, "testfile.pdf");

            var spec = new SommaListaCampiMinoreDi(nomeCampo1, valoriCampi.Length, confronto);

            var result = spec.IsSatisfiedBy(datiPdf);

            Assert.IsFalse(result);
        }

        [TestMethod]
        public void SommaUgualeAValoreConfronto_restituisceFalse()
        {
            var nomeCampo1 = "campo[n]";
            var confronto = 10.0m;

            var valoriCampi = new[] { 
                CreaCampo("campo1","1"),
                CreaCampo("campo2","2"),
                CreaCampo("campo3","3"),
                CreaCampo("campo4","4")
            };

            var datiPdf = new DatiPdfCompilabile(valoriCampi, "testfile.pdf");

            var spec = new SommaListaCampiMinoreDi(nomeCampo1, valoriCampi.Length, confronto);

            var result = spec.IsSatisfiedBy(datiPdf);

            Assert.IsFalse(result);
        }
    }
}
