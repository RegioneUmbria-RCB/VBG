using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using System.Text.RegularExpressions;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public class AmministrazioneEsternaValidator
    {
        public AmministrazioneEsternaValidator()
        {

        }

        public void Validate(Amministrazioni amm)
        {
            if (!String.IsNullOrEmpty(amm.PROT_UO))
                throw new Exception("E' PRESENTE UN'AMMINISTRAZIONE INTERNA");

            if (!String.IsNullOrEmpty(amm.CAP))
            {
                if (!new Regex(@"^\d{5}$").IsMatch(amm.CAP))
                    throw new Exception("IL FORMATO DEL CAP NON E' CORRETTO");
            }
        }
    }
}
