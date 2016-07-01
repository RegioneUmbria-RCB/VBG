// -----------------------------------------------------------------------
// <copyright file="SommatoriaSpesa1MinoreDiPercentualeSommatoriaSpesa2Tests.cs" company="">
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
    using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione;
    using Init.Sigepro.FrontEnd.AppLogic.ServizioPrecompilazionePDF;
    using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    [TestClass]
    public class SommatoriaSpesa1MinoreDiPercentualeSommatoriaSpesa2Tests
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
        public void SommatoriaSpesa1MinoreDiPercentualeSommatoriaSpesa2_restituisceTrue()
        {
            var nomeCampoSpesa1 = "campo1_[n]";
            var nomeCampoSpesa2 = "campo2_[n]";
            var numeroAziende = 3;
            var percentualeAmmessa = 2;
            var percentualeAnticipo = 50;
            var percentualeContributo = 50;

            var valori = new[]{
                CreaCampo("campo1_1", "1"),
                CreaCampo("campo1_2", "1"),
                CreaCampo("campo1_3", "0"),
                CreaCampo("campo2_1", "400"),
                CreaCampo("campo2_2", "200"),
                CreaCampo("campo2_3", "200")
            };

            var datiPdf = new DatiPdfCompilabile(valori, "testfile.pdf");

            var spec = new SommatoriaSpesa1MinoreDiPercentualeSommatoriaSpesa2(nomeCampoSpesa1, nomeCampoSpesa2, numeroAziende, percentualeAmmessa, percentualeAnticipo, percentualeContributo);

            Assert.IsTrue(spec.IsSatisfiedBy(datiPdf));
        }

        [TestMethod]
        public void SommatoriaSpesa1MaggioreDiPercentualeSommatoriaSpesa2_restituisceFalse()
        {
            var nomeCampoSpesa1 = "campo1_[n]";
            var nomeCampoSpesa2 = "campo2_[n]";
            var numeroAziende = 3;
            var percentualeAmmessa = 2;
            var percentualeAnticipo = 50;
            var percentualeContributo = 50;

            var valori = new[]{
                CreaCampo("campo1_1", "1"),
                CreaCampo("campo1_2", "1"),
                CreaCampo("campo1_3", "0"),
                CreaCampo("campo2_1", "100"),
                CreaCampo("campo2_2", "100"),
                CreaCampo("campo2_3", "100")
            };

            var datiPdf = new DatiPdfCompilabile(valori, "testfile.pdf");

            var spec = new SommatoriaSpesa1MinoreDiPercentualeSommatoriaSpesa2(nomeCampoSpesa1, nomeCampoSpesa2, numeroAziende, percentualeAmmessa, percentualeAnticipo, percentualeContributo);

            Assert.IsFalse(spec.IsSatisfiedBy(datiPdf));
        }

        [TestMethod]
        public void SommatoriaSpesa1UgualeDiPercentualeSommatoriaSpesa2_restituisceFalse()
        {
            var nomeCampoSpesa1 = "campo1_[n]";
            var nomeCampoSpesa2 = "campo2_[n]";
            var numeroAziende = 3;
            var percentualeAmmessa = 2;
            var percentualeAnticipo = 50;
            var percentualeContributo = 50;

            var valori = new[]{
                CreaCampo("campo1_1", "1"),
                CreaCampo("campo1_2", "1"),
                CreaCampo("campo1_3", "0"),
                CreaCampo("campo2_1", "200"),
                CreaCampo("campo2_2", "100"),
                CreaCampo("campo2_3", "100")
            };

            var datiPdf = new DatiPdfCompilabile(valori, "testfile.pdf");

            var spec = new SommatoriaSpesa1MinoreDiPercentualeSommatoriaSpesa2(nomeCampoSpesa1, nomeCampoSpesa2, numeroAziende, percentualeAmmessa, percentualeAnticipo, percentualeContributo);

            Assert.IsFalse(spec.IsSatisfiedBy(datiPdf));
        }
    }
}
