using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.SiprWeb.Classificazione
{
    public class ClassificheAdapter
    {
        public ClassificheAdapter()
        {
            
        }

        public ListaTipiClassifica Adatta(ClassificheReader reader)
        {
            var classifiche = reader.Read();

            return new ListaTipiClassifica
            {
                
                Classifica = classifiche.Select(x => new ListaTipiClassificaClassifica
                {
                    Codice = x.CodiceClassificazione,
                    Descrizione = String.Format("[{0}] {1}", x.CodiceClassificazione, x.Classificazione)
                }).ToArray()
            };
        }
    }
}
