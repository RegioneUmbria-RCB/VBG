using System;
using System.IO;
using System.Xml;

namespace Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda
{
    internal class TestoBoxFineDomandaReader
    {
        private string _pathAssoluto;

        public TestoBoxFineDomandaReader(string pathAssoluto)
        {
            this._pathAssoluto = pathAssoluto;
        }

        internal TestoBoxFineDomanda Read()
        {
            using (var istream = File.OpenRead(this._pathAssoluto))
            {
                return Read(istream);
            }
        }

        internal TestoBoxFineDomanda Read(Stream fileStream)
        {
            var doc = new XmlDocument();
            doc.Load(fileStream);
            var root = doc.DocumentElement;

            var titolo = root.SelectSingleNode("/testi-redirect-fine-domanda/titolo").InnerText;
            var messaggio = root.SelectSingleNode("/testi-redirect-fine-domanda/messaggio").InnerText;
            var testoBottone = root.SelectSingleNode("/testi-redirect-fine-domanda/testo-bottone").InnerText;

            return new TestoBoxFineDomanda(titolo, messaggio, testoBottone);
        }
    }
}