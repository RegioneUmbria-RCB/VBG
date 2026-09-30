using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.SiprWebTest.Classificazione
{
    public class LivelliClassificaConfiguration
    {
        public readonly string Livello1;
        public readonly string Livello2;
        public readonly string Livello3;
        public readonly string Livello4;

        public LivelliClassificaConfiguration(string classifica)
        {
            var livelliClassifiche = FormattaClassifica(classifica);

            if (livelliClassifiche.Length > 0)
                Livello1 = livelliClassifiche[0];

            if (livelliClassifiche.Length > 1)
                Livello2 = livelliClassifiche[1];

            if (livelliClassifiche.Length > 2)
                Livello3 = livelliClassifiche[2];

            if (livelliClassifiche.Length > 3)
                Livello4 = livelliClassifiche[3];

        }

        private string[] FormattaClassifica(string classifica)
        {
            string[] aClassifica = classifica.Split(new Char[] { '.' });
            if (aClassifica.Length == 0 || aClassifica.Length > 4)
                throw new Exception("FORMATO DELLA CLASSIFICA NON VALIDO, INSERIRE AL MASSIMO QUATTRO LIVELLI DIVISI DA UN PUNTO");

            return aClassifica;
        }
    }
}
