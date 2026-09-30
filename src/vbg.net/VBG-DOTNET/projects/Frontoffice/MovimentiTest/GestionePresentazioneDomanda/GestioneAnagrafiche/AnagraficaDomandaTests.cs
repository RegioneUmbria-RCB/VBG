using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Xunit;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestionePresentazioneDomanda.GestioneAnagrafiche
{
    public class AnagraficaDomandaTests
    {
        [Fact]
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

        [Fact]
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

        [Fact]
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

            Assert.NotNull(a.DatiIscrizioneInail);
            Assert.NotNull(a.DatiIscrizioneInps);
        }
    }
}
