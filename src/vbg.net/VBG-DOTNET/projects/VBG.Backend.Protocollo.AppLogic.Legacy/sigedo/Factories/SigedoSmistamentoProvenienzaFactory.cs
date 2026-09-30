

using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Builders;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Interfacce;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Factories
{
    public class SigedoSmistamentoProvenienzaFactory
    {
        /*public static ISmistamentoProvenienza Create(TipoProvenienza tipoInserimento, string operatore, string operatoreResponsabileProc, DataBase db, string idComune, string software, string codiceComune)
        {
            ISmistamentoProvenienza retVal;

            if (tipoInserimento == TipoProvenienza.ONLINE)
                retVal = new SigedoSmistamentoOnLineBuilder(operatoreResponsabileProc, db, idComune, software, codiceComune);
            else
                retVal = new SigedoSmistamentoBackofficeBuilder(operatore);

            return retVal;
        }*/

        public static ISmistamentoProvenienza Create(TipoProvenienza tipoInserimento, string operatore, ResolveDatiProtocollazioneService datiProtocollazione)
        {
            ISmistamentoProvenienza retVal;

            if (tipoInserimento == TipoProvenienza.ONLINE)
                retVal = new SigedoSmistamentoOnLineBuilder(datiProtocollazione);
            else
                retVal = new SigedoSmistamentoBackofficeBuilder(operatore);

            return retVal;
        }
    }
}
