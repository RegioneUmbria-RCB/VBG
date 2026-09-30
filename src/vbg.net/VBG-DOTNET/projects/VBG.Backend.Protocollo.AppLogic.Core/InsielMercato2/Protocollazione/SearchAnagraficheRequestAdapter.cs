using ProtocolloInsielMercatoService2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Verticalizzazioni;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Protocollazione
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
