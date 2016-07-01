using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione;
using Init.SIGePro.Data;
using System.Text.RegularExpressions;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public class ProtocolloAnagrafeValidator
    {
        public ProtocolloAnagrafeValidator()
        {

        }

        public void Validate(ProtocolloAnagrafe anagrafe)
        {
            if (String.IsNullOrEmpty(anagrafe.GetId()))
                throw new Exception(String.Format("CODICE FISCALE/PARTITA IVA NON VALORIZZATI PER L'ANAGRAFICA CODICE {0} NOME: {1} NOMINATIVO {2}", anagrafe.CODICEANAGRAFE, anagrafe.NOME, anagrafe.NOMINATIVO));

            var cap = anagrafe.GetComune().CAP;
            if (!String.IsNullOrEmpty(cap))
            {
                if (!new Regex(@"^\d{5}$").IsMatch(cap))
                    throw new Exception("IL FORMATO DEL CAP NON E' CORRETTO");
            }
        }
    }
}
