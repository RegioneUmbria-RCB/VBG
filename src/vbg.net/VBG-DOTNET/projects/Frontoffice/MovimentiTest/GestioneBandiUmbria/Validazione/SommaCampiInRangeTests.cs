// -----------------------------------------------------------------------
// <copyright file="SommaCampiInRangeTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneBandiUmbria.Validazione
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Xunit;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione;
    using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
    using Init.Sigepro.FrontEnd.AppLogic.ServizioPrecompilazionePDF;

    public class SommaCampiInRangeTests
    {
        [Fact]
        public void SeValoreCompresoInRange_restituisceTrue()
        {
            var nomeCampo1 = "campo1";
            var nomeCampo2 = "campo2";
            var valoreMin = 20.0m;
            var valoreMax = 21.0m;
            var valoriCampi = new[] {
                new DatiPDFType
                {
                    codice="campo1",
                    valore = new []{"10"}
                },
                new DatiPDFType
                {
                    codice="campo2",
                    valore = new []{"10"}
                }
            };
            var datiPdf = new DatiPdfCompilabile(valoriCampi, "testfile.pdf");
            var spec = new SommaCampiInRange(nomeCampo1, nomeCampo2, valoreMin, valoreMax);
            var result = spec.IsSatisfiedBy(datiPdf);
            Assert.True(result);
        }

        [Fact]
        public void SeValoreMinoreDiValoreMin_restituisceFalse()
        {
            var nomeCampo1 = "campo1";
            var nomeCampo2 = "campo2";
            var valoreMin = 20.0m;
            var valoreMax = 21.0m;
            var valoriCampi = new[] {
                new DatiPDFType
                {
                    codice="campo1",
                    valore = new []{"1"}
                },
                new DatiPDFType
                {
                    codice="campo2",
                    valore = new []{"1"}
                }
            };
            var datiPdf = new DatiPdfCompilabile(valoriCampi, "testfile.pdf");
            var spec = new SommaCampiInRange(nomeCampo1, nomeCampo2, valoreMin, valoreMax);
            var result = spec.IsSatisfiedBy(datiPdf);
            Assert.False(result);
        }

        [Fact]
        public void SeValoreMaggioreDiValoreMin_restituisceFalse()
        {
            var nomeCampo1 = "campo1";
            var nomeCampo2 = "campo2";
            var valoreMin = 1.0m;
            var valoreMax = 2.0m;
            var valoriCampi = new[] {
                new DatiPDFType
                {
                    codice="campo1",
                    valore = new []{"1"}
                },
                new DatiPDFType
                {
                    codice="campo2",
                    valore = new []{"2"}
                }
            };
            var datiPdf = new DatiPdfCompilabile(valoriCampi, "testfile.pdf");
            var spec = new SommaCampiInRange(nomeCampo1, nomeCampo2, valoreMin, valoreMax);
            var result = spec.IsSatisfiedBy(datiPdf);
            Assert.False(result);
        }
    }
}
