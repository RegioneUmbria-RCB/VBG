using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Anagrafiche
{
    public class PersonaFisicaGiuridicaFactory
    {
        public static IPersonaFisicaGiuridica Create(ProtocolloAnagrafe anagrafica, AnagraficheService wrapper)
        {
            if (anagrafica.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAFISICA)
                return new PersonaFisica(anagrafica, wrapper);
            else if (anagrafica.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAGIURIDICA)
                return new PersonaGiuridica(anagrafica, wrapper);
            else
                throw new Exception("TIPO ANAGRAFE (FISICA O GIURIDICA) NON PRESENTE");
        }
    }
}
