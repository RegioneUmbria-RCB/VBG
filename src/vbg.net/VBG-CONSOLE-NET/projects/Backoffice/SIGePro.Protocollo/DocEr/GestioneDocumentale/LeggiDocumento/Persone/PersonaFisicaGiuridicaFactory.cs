using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Persone
{
    public class PersonaFisicaGiuridicaFactory
    {
        public static IPersonaFisicaGiuridica Create(object mittDest)
        {
            if (mittDest is PersonaGiuridicaType)
                return new PersonaGiuridica(mittDest);
            else if (mittDest is PersonaType)
                return new PersonaFisica(mittDest);
            else if (mittDest is AmministrazioneType)
                return new PersonaAmministrazione(mittDest);
            else
                throw new Exception("TIPO PERSONA NON GESTITO");
        }
    }
}
