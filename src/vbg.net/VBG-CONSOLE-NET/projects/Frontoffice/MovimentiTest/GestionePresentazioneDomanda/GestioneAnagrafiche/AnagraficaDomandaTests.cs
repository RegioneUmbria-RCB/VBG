using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestionePresentazioneDomanda.GestioneAnagrafiche
{
    [TestClass]
    public class AnagraficaDomandaTests
    {
        [TestMethod]
        public void CreazioneDiUnAnagraficaDomandaPersonaFisica()
        {
            var t = new PresentazioneIstanzaDbV2.ANAGRAFEDataTable();
            var row = t.NewANAGRAFERow();

            row.TIPOSOGGETTO = 1;
            row.DescrSoggetto = "Tipo soggetto";

            row.TIPOANAGRAFE = "F";
            row.NOMINATIVO = "Nominativo";
            row.NOME = "Nome";
            row.SESSO = "M";
            row.PROVINCIANASCITA = "PG";
            row.CODICEFISCALE = "GRGNCL79C19G478O";
            row.DATANASCITA = DateTime.Now;
            row.CODCOMNASCITA = "G478";

            var a = AnagraficaDomanda.FromAnagrafeRow(row);
        }

        [TestMethod]
        public void CreazioneDiUnAnagraficaDomandaPersonaGiuridica()
        {
            var t = new PresentazioneIstanzaDbV2.ANAGRAFEDataTable();
            var row = t.NewANAGRAFERow();

            row.TIPOSOGGETTO = 1;
            row.DescrSoggetto = "Tipo soggetto";

            row.TIPOANAGRAFE = "G";
            row.NOMINATIVO = "Ragione sociale";
            row.FORMAGIURIDICA = 1;
            
            var a = AnagraficaDomanda.FromAnagrafeRow(row);
        }

        [TestMethod]
        public void UnaPersonaGiuridicaHaSempreDatiInpsEInail()
        {
            var t = new PresentazioneIstanzaDbV2.ANAGRAFEDataTable();
            var row = t.NewANAGRAFERow();

            row.TIPOSOGGETTO = 1;
            row.DescrSoggetto = "Tipo soggetto";

            row.TIPOANAGRAFE = "G";
            row.NOMINATIVO = "Ragione sociale";
            row.FORMAGIURIDICA = 1;

            var a = AnagraficaDomanda.FromAnagrafeRow(row);

            Assert.IsNotNull(a.DatiIscrizioneInail);
            Assert.IsNotNull(a.DatiIscrizioneInps);
        }
    }
}
