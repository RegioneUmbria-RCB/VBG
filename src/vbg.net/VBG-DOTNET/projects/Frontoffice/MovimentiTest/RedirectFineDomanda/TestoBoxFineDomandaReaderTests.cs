using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda;
using System;
using System.IO;
using System.Text;
using Xunit;

namespace MovimentiTest.RedirectFineDomanda
{
    public class TestoBoxFineDomandaReaderTests
    {
        [Fact]
        public void Lettura_testi_domanda_da_file_xml()
        {
            var xml = @"<?xml version=""1.0"" encoding=""utf-8"" ?>
                        <testi-redirect-fine-domanda>
                          <titolo>Denuncia ai fini TARI</titolo>
                          <messaggio>La pratica che hai presentato prevede che venga effettuata la denuncia ai fini TARI. Clicca su PROCEDI per proseguire.</messaggio>
                          <testo-bottone>Procedi</testo-bottone>
                        </testi-redirect-fine-domanda>";

            var reader = new TestoBoxFineDomandaReader(String.Empty);

            var res = reader.Read(new MemoryStream(Encoding.Default.GetBytes(xml)));

            Assert.Equal("Denuncia ai fini TARI", res.Titolo);
            Assert.Equal("La pratica che hai presentato prevede che venga effettuata la denuncia ai fini TARI. Clicca su PROCEDI per proseguire.", res.Messaggio);
            Assert.Equal("Procedi", res.TestoBottone);
        }
    }
}
