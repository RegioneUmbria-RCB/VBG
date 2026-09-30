using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders.MittentiDestinatari;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Factories
{
    public class DocAreaSegnaturaAmministrazioneFactory
    {
        public static IDocAreaSegnaturaNominativoPersonaBuilder Create(bool usaDenominazione, ProtocolloAmministrazioni amm)
        {
            if (usaDenominazione)
                return new DocAreaSegnaturaDenominazioneAmministrazioneBuilder(amm);
            else
                return new DocAreaSegnaturaCognomeAmministrazioneBuilder(amm);
        }
    }
}
