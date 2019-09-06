using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.Classificazione
{
    public class ClassificazioneResponseAdapter
    {
        public static ListaTipiClassifica Adatta(xapirestTypeTitolario response, string replaceTitolario)
        {
            var retVal = new ListaTipiClassifica
            {
                Classifica = response.getElencoTitolario_Result.SEQ_Titolario
                                        .Where(k => !String.IsNullOrEmpty(k.CodiceRicerca))
                                        .OrderBy(y => y.CodiceRicerca)
                                        .Select(x => 
                                        {
                                            var arrDescrizione = x.Descrizione.Split('/');
                                            var r = new ListaTipiClassificaClassifica 
                                            { 
                                                Codice = x.Codice, 
                                                Descrizione = String.Format("[{0}] {1}", x.CodiceRicerca.Trim(), arrDescrizione[arrDescrizione.Length-1])
                                            };
                                            return r;
                                        }).ToArray()
            };

            return retVal;

        }
    }
}