using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Adapters.IstanzaSigeproAdapterTests.Mocks
{
    public class UserCredentialsStorageFake : IUserCredentialsStorage
    {
        public UserAuthenticationResult Get()
        {
            return new UserAuthenticationResult("123456789", "E256", "E256", new AppLogic.GestioneAnagrafiche.AnagraficaUtente
            {
                Nominativo = "Gargagli",
                Nome = "Nicola",
                Codicefiscale = "GRGNCL79C19G478O"
            }, LivelloAutenticazioneEnum.Identificato);
        }

        public void Set(UserAuthenticationResult uar)
        {
            throw new NotImplementedException();
        }
    }
}
