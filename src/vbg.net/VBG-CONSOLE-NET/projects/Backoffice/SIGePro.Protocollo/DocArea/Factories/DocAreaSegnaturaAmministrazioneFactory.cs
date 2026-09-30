using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocArea.Interfaces;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.DocArea.Builders.MittentiDestinatari;

namespace Init.SIGePro.Protocollo.DocArea.Factories
{
    public class DocAreaSegnaturaAmministrazioneFactory
    {
        public static IDocAreaSegnaturaNominativoPersonaBuilder Create(bool usaDenominazione, Amministrazioni amm)
        {
            if (usaDenominazione)
                return new DocAreaSegnaturaDenominazioneAmministrazioneBuilder(amm);
            else
                return new DocAreaSegnaturaCognomeAmministrazioneBuilder(amm);
        }
    }
}
