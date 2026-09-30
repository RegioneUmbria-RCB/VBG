using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Anagrafiche;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione.Anagrafiche
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
