using Init.SIGePro.Sit.SilverBrowser.SilverBrowserClasses;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SIGePro.SIT.Tests.SilverBrowser
{
    [TestClass]
    public class CodiceViarioTests
    {
        [TestMethod]
        public void Id_stringa_a_numero()
        {
            var codViario = new CodiceViario("E25600000012");
            var expected = "12";
            var result = codViario.ToString();

            Assert.AreEqual<string>(expected, result);
        }
    }
}
