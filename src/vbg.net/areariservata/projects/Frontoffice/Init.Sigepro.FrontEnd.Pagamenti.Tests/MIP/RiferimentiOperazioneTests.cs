using Init.Sigepro.FrontEnd.Pagamenti.MIP;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.MIP
{
    [TestClass]
    public class RiferimentiOperazioneTests
    {
        [TestMethod]
        public void Popola_correttamente_i_parametri_di_inizializzazione()
        {
            var num = "numero_operaionze";
            var importo = 12345;
            var r = new RiferimentiOperazione(num, importo);

            Assert.AreEqual<string>(num, r.NumeroOperazione);
            Assert.AreEqual<string>(importo.ToString(), r.Importo);
            Assert.AreEqual<string>("EUR", r.Valuta);
        }
    }
}
