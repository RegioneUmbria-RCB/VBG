using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda;
using System.IO;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.RedirectFineDomanda
{
    [TestClass]
    public class TestoBoxFineDomandaReaderTests
    {
        [TestMethod]
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

            Assert.AreEqual<string>("Denuncia ai fini TARI", res.Titolo);
            Assert.AreEqual<string>("La pratica che hai presentato prevede che venga effettuata la denuncia ai fini TARI. Clicca su PROCEDI per proseguire.", res.Messaggio );
            Assert.AreEqual<string>("Procedi", res.TestoBottone);
        }
    }
}
