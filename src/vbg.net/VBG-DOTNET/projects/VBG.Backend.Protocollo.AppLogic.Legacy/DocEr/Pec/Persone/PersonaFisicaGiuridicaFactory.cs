
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Pec.Persone
{
    public class PersonaFisicaGiuridicaFactory
    {
        public static IPersonaFisicaGiuridica Create(ProtocollazioneRegistrazione.MittDestType mittDest)
        {
            var obj = mittDest.Items[0];

            if (obj is ProtocollazioneRegistrazione.PersonaGiuridicaType)
                return new PersonaGiuridica((ProtocollazioneRegistrazione.PersonaGiuridicaType)obj);
            else if (obj is ProtocollazioneRegistrazione.PersonaType)
                return new PersonaFisica((ProtocollazioneRegistrazione.PersonaType)obj);
            else
                throw new Exception("TIPO PERSONA NON GESTITO");
        }
    }
}
