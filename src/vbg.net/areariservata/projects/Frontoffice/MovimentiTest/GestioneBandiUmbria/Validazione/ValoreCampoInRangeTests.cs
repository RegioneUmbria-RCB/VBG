// -----------------------------------------------------------------------
// <copyright file="ValoreCampoInRangeTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneBandiUmbria.Validazione
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Init.Sigepro.FrontEnd.AppLogic.ServizioPrecompilazionePDF;
    using Microsoft.VisualStudio.TestTools.UnitTesting;
    using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    [TestClass]
    public class ValoreCampoInRangeTests
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
        public void ValoreCampoInRange_restituisceTrue()
        {
            var nomeCampo = "campo1";
            var valoreMin = 20.0m;
            var valoreMax = 22.0m;

            var valoriCampi = new[] { 
                new DatiPDFType
                {
                    codice="campo1",
                    valore = new []{"21"}
                }                
            };

            var datiPdf = new DatiPdfCompilabile(valoriCampi, "testfile.pdf");

            var spec = new ValoreCampoInRange(nomeCampo, valoreMin, valoreMax);

            var result = spec.IsSatisfiedBy(datiPdf);

            Assert.IsTrue(result);
        }

        [TestMethod]
        public void ValoreMinoreDiRange_restituisceFalse()
        {
            var nomeCampo = "campo1";
            var valoreMin = 20.0m;
            var valoreMax = 22.0m;

            var valoriCampi = new[] { 
                new DatiPDFType
                {
                    codice="campo1",
                    valore = new []{"10"}
                }                
            };

            var datiPdf = new DatiPdfCompilabile(valoriCampi, "testfile.pdf");

            var spec = new ValoreCampoInRange(nomeCampo, valoreMin, valoreMax);

            var result = spec.IsSatisfiedBy(datiPdf);

            Assert.IsFalse(result);
        }

        [TestMethod]
        public void ValoreMaggioreDiRange_restituisceFalse()
        {
            var nomeCampo = "campo1";
            var valoreMin = 20.0m;
            var valoreMax = 22.0m;

            var valoriCampi = new[] { 
                new DatiPDFType
                {
                    codice="campo1",
                    valore = new []{"23"}
                }                
            };

            var datiPdf = new DatiPdfCompilabile(valoriCampi, "testfile.pdf");

            var spec = new ValoreCampoInRange(nomeCampo, valoreMin, valoreMax);

            var result = spec.IsSatisfiedBy(datiPdf);

            Assert.IsFalse(result);
        }
    }
}
