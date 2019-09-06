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
    public class RiferimentiUtenteTests
    {
        [TestMethod]
        public void Popola_correttamente_i_campi()
        {
            var email = "email";
            var identificativoUtente = "identificativoUtente";
            var userId  = "userId";
            var riferimenti = new RiferimentiUtente(email, identificativoUtente, userId);

            Assert.AreEqual<string>(email, riferimenti.Email);
            Assert.AreEqual<string>(identificativoUtente, riferimenti.IdentificativoUtente);
            Assert.AreEqual<string>(userId, riferimenti.UserID);
        }
    }
}
