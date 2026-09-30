using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone
{
    public class PersonaFisicaGiuridicaFactory
    {
        public static IPersonaFisicaGiuridica Create(ProtocolloAnagrafe anagrafica)
        {
            if (anagrafica.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAFISICA)
                return new PersonaFisica(anagrafica);
            else if (anagrafica.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAGIURIDICA)
                return new PersonaGiuridica(anagrafica);
            else
                throw new Exception(String.Format("TIPOLOGIA SOGGETTO {0} NON GESTITA", anagrafica.TIPOANAGRAFE));
        }
    }
}
