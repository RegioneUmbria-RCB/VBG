using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders.MittentiDestinatari;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Factories
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
