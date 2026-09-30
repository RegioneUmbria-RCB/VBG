using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.GeProt.Protocollazione
{
    public class ClassificaAdapter
    {
        public ClassificaAdapter()
        {

        }

        public Classifica[] Adatta(string classifica)
        {
            string codiceClassifica = "";
            string descrizioneClassifica = "";

            string[] classifiche = classifica.Split(new Char[] { '#' });
            switch (classifiche.Length)
            {
                case 1:
                    codiceClassifica = classifiche[0];
                    break;
                case 2:
                    codiceClassifica = classifiche[0];
                    descrizioneClassifica = classifiche[1];
                    break;
                default:
                    throw new Exception("Valore della classifica non valido!");
            }

            return new Classifica[] { new Classifica { Livello = new Livello[] { new Livello { Text = new string[] { codiceClassifica }, nome = descrizioneClassifica } } } };
        }
    }
}
