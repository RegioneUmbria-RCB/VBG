using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneMenu
{
    [TestClass]
    public class MenuUrlParserTests
    {
        [TestMethod]
        public void Sostituisce_Il_Parametro_Token_Dalla_Querystring()
        {
            var urlString = "http://www.miourl.com?idcomune={idComune}&token={token}&software={software}";
            var expected = "http://www.miourl.com?idcomune={idComune}&software={software}";
            var parser = new MenuUrlParser(false);

            var result = parser.Completa(urlString);

            Assert.AreEqual<string>(expected, result);
        }

        [TestMethod]
        public void Sostituisce_Il_Parametro_Token_Dalla_Querystring_Se_Il_Parametro_Si_Trova_In_Fondo_Alla_Stringa()
        {
            var urlString = "http://www.miourl.com?idcomune={idComune}&software={software}&token={token}";
            var expected = "http://www.miourl.com?idcomune={idComune}&software={software}";
            var parser = new MenuUrlParser(false);

            var result = parser.Completa(urlString);

            Assert.AreEqual<string>(expected, result);
        }

        [TestMethod]
        public void Sostituisce_Il_Parametro_Token_Dalla_Querystring_Ignorando_Il_Case()
        {
            var urlString = "HTTP://WWW.MIOURL.COM?IDCOMUNE={IDCOMUNE}&SOFTWARE={SOFTWARE}&TOKEN={TOKEN}";
            var expected = "HTTP://WWW.MIOURL.COM?IDCOMUNE={IDCOMUNE}&SOFTWARE={SOFTWARE}";
            var parser = new MenuUrlParser(false);

            var result = parser.Completa(urlString);

            Assert.AreEqual<string>(expected, result);
        }
    }
}
