//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Text;
//using Init.SIGePro.Sit.Ravenna2;
//using Microsoft.VisualStudio.TestTools.UnitTesting;

//namespace SIGePro.SIT.Tests.IntegrazioneRavenna2
//{
//    [TestClass]
//    public class ViewVieTests
//    {
//        // Nell'installazione di ravenna non posso eseguire una select * perchè la vista contiene dei campi spaziali
//        // Se vogli oselezionare tutti i campi devo specificare tutti i campi che mi interessano in quella tabella
//        [TestMethod]
//        public void ViewVieTests_test_all_fields()
//        {
//            var table = new ViewVieTable("");

//            var result = table.AllFields().ToString();

//            var expected = "view_vie.cap, view_vie.fraz_nome, view_vie.via_cod";

//            Assert.AreEqual(expected, result);
//        }
//    }
//}
