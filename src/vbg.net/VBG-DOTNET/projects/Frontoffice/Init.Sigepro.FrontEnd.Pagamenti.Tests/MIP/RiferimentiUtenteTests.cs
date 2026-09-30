using Xunit;
using VBG.Pagamenti.Legacy;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.MIP
{
    public class RiferimentiUtenteTests
    {
        [Fact]
        public void Popola_correttamente_i_campi()
        {
            var email = "email";
            var identificativoUtente = "identificativoUtente";
            var userId = "userId";
            var riferimenti = new RiferimentiUtente(email, identificativoUtente, userId);

            Assert.Equal(email, riferimenti.Email);
            Assert.Equal(identificativoUtente, riferimenti.IdentificativoUtente);
            Assert.Equal(userId, riferimenti.UserID);
        }
    }
}
