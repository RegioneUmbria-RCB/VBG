using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocArea.Interfaces;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.DocArea.Builders.MittentiDestinatari;

namespace Init.SIGePro.Protocollo.DocArea.Factories
{
    public class DocAreaSegnaturaAnagraficaFactory
    {
        public static IDocAreaSegnaturaNominativoPersonaBuilder Create(bool usaDenominazione, ProtocolloAnagrafe anag)
        {
            if (usaDenominazione)
                return new DocAreaSegnaturaDenominazioneAnagraficaBuilder(anag);
            else
                return new DocAreaSegnaturaCognomeAnagraficaBuilder(anag);
        }
    }
}
