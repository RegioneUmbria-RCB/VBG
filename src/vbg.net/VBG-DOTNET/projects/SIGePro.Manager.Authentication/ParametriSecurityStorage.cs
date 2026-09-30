using System;

namespace Init.SIGePro.Manager.Authentication
{
    public static class ParametriSecurityStorage
    {
        private static IParametriSecurity _parametriSecurity;

        public static IParametriSecurity GetParametriSecurity()
        {
            if (_parametriSecurity == null)
            {
                throw new InvalidOperationException("Le credenziali utente non sono state impostate in questa applicazione. Registrare le credenziali dell'applicazione di backend .net all'avvio dell'applicazione");
            }

            return _parametriSecurity;
        }

        public static void RegistraParametriSecurity(IParametriSecurity credenziali)
        {
            if (_parametriSecurity != null)
            {
                throw new InvalidOperationException("Le credenziali utente sono già state registrate per questa appplicazione");
            }

            ParametriSecurityStorage._parametriSecurity = credenziali;
        }
    }
}
