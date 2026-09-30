// -----------------------------------------------------------------------
// <copyright file="ValoreCampoMaggioreDiPercentualeAltroCampoTests.cs" company="">
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
    using Init.Sigepro.FrontEnd.AppLogic.ServizioPrecompilazionePDF;
    using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;

    public class ValoreCampoMaggioreDiPercentualeAltroCampoTests
    {
        private DatiPDFType CreaCampo(string nome, string valore)
        {
            return new DatiPDFType
            {
                codice = nome,
                valore = new[] { valore }
            };
        }

        [Fact]
        public void ValoreCampoMaggioreDiPercentualeAltroCampo_restituisceTrue()
        {
            var nomeCampo = "campo1";
            var nomeCampoConfronto = "campo2";
            var percentuale = 50;

            var valori = new[]{
                CreaCampo("campo1", "51"),
                CreaCampo("campo2", "100"),
            };

            var datiPdf = new DatiPdfCompilabile(valori, "testfile.pdf");

            var spec = new ValoreCampoMaggioreDiPercentualeAltroCampo(nomeCampo, nomeCampoConfronto, percentuale);

            Assert.True(spec.IsSatisfiedBy(datiPdf));
        }

        [Fact]
        public void ValoreCampoMinoreDiPercentualeAltroCampo_restituisceFalse()
        {
            var nomeCampo = "campo1";
            var nomeCampoConfronto = "campo2";
            var percentuale = 50;

            var valori = new[]{
                CreaCampo("campo1", "49"),
                CreaCampo("campo2", "100"),
            };

            var datiPdf = new DatiPdfCompilabile(valori, "testfile.pdf");

            var spec = new ValoreCampoMaggioreDiPercentualeAltroCampo(nomeCampo, nomeCampoConfronto, percentuale);

            Assert.False(spec.IsSatisfiedBy(datiPdf));
        }

        [Fact]
        public void ValoreCampoUgualeAPercentualeAltroCampo_restituisceTrue()
        {
            var nomeCampo = "campo1";
            var nomeCampoConfronto = "campo2";
            var percentuale = 50;

            var valori = new[]{
                CreaCampo("campo1", "50"),
                CreaCampo("campo2", "100"),
            };

            var datiPdf = new DatiPdfCompilabile(valori, "testfile.pdf");

            var spec = new ValoreCampoMaggioreDiPercentualeAltroCampo(nomeCampo, nomeCampoConfronto, percentuale);

            Assert.True(spec.IsSatisfiedBy(datiPdf));
        }
    }
}
