using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
{
    public enum LivelloAutenticazioneEnum
    {
        Anonimo = 0,
        NonIdentificato = 1,
        Identificato = 2
    }

    public static class DecodeLivelloIntervento
    {
        public static string FromLivelloAutenticazione(LivelloAutenticazioneEnum livello)
        {
            switch (livello)
            {
                case LivelloAutenticazioneEnum.NonIdentificato:
                    return "Identità non verificata";

                case LivelloAutenticazioneEnum.Identificato:
                    return "Identità verificata";
            }

            return "Identità anonima";
        }


        public static string FromIdLivello(int idLivello)
        {
            return FromLivelloAutenticazione((LivelloAutenticazioneEnum)idLivello);
        }
    }
}
