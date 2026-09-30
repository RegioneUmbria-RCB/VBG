using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Sit.Ravenna2;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace SIGePro.SIT.Tests.IntegrazioneRavenna2
{
    [TestClass]
    public class Ra147Tests
    {
        // Nell'installazione di ravenna non posso eseguire una select * perchè la vista contiene dei campi spaziali
        // Se vogli oselezionare tutti i campi devo specificare tutti i campi che mi interessano in quella tabella
        [TestMethod]
        public void Ra147Tests_test_all_fields()
        {
            var table = new Ra147Table("");

            var result = table.AllFields().ToString();

            var expected = "RA147.COD_SEZ, RA147.COD_CIRC, RA147.DESCRIZION";

            Assert.AreEqual(expected, result);
        }
    }
}