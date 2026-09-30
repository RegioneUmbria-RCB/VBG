using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Classificazione
{
    public class ClassificazioneResponseAdapter
    {
        public ClassificazioneResponseAdapter()
        {

        }

        public ListaTipiClassifica Adatta(RootObject response)
        {
            var classifiche = response.results.Select(x =>
            {
                string classifica = "";
                if (x.livello1 != 0)
                {
                    classifica = x.livello1.ToString();
                }

                if (x.livello2 != 0)
                {
                    classifica += String.Concat(".", x.livello2.ToString());
                }

                if (x.livello3 != 0)
                {
                    classifica += String.Concat(".", x.livello3.ToString());
                }

                if (x.livello4 != 0)
                {
                    classifica += String.Concat(".", x.livello4.ToString());
                }

                if (!String.IsNullOrEmpty(classifica))
                {
                    classifica += String.Concat(" - ", x.descrizione);
                }
                else
                {
                    classifica += x.descrizione;
                }

                return new ListaTipiClassificaClassifica { Codice = x.id.ToString(), Descrizione = classifica };

            });

            return new ListaTipiClassifica { Classifica = classifiche.ToArray() };
        }
    }
}
