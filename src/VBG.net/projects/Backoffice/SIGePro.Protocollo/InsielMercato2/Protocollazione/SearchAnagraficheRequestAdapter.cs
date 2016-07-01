using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.InsielMercato2.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.InsielMercato2.Protocollazione
{
    public class SearchAnagraficheRequestAdapter
    {
        public static anagraficRequest Adatta(VerticalizzazioniConfiguration vert, string nominativo, string pec)
        {
            var res = new anagraficRequest
            {
                user = new user
                {
                    code = vert.Username,
                    password = vert.Password
                },
                anagrafic = new anagrafic
                {
                    anagraficDescription = nominativo.ToUpper(),
                    emailPec = pec.ToUpper()
                }
            };

            return res;
        }
    }
}
