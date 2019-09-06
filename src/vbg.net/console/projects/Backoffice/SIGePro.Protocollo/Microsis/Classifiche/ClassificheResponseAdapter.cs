using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Microsis.Classifiche
{
    public class ClassificheResponseAdapter
    {
        public static ListaTipiClassifica Adatta(Lista_Titolario response)
        {
            return new ListaTipiClassifica
            {
                Classifica = response.Items.Select(x => new ListaTipiClassificaClassifica 
                { 
                    Codice = x.Codice, 
                    Descrizione = x.Classe 
                }).ToArray()
            };
        }
    }
}
