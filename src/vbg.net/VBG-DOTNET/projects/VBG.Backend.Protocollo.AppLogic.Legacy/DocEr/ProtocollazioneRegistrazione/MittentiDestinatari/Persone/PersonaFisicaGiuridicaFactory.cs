using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone
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
