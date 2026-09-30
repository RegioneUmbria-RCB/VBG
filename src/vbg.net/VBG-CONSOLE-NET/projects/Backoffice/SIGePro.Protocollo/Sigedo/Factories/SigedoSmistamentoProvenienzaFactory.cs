using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sigedo.Interfacce;
using Init.SIGePro.Protocollo.Manager;
using Init.SIGePro.Protocollo.Sigedo.Builders;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;

namespace Init.SIGePro.Protocollo.Sigedo.Factories
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

        public static ISmistamentoProvenienza Create(ProtocolloEnum.TipoProvenienza tipoInserimento, string operatore, ResolveDatiProtocollazioneService datiProtocollazione)
        {
            ISmistamentoProvenienza retVal;

            if (tipoInserimento == ProtocolloEnum.TipoProvenienza.ONLINE)
                retVal = new SigedoSmistamentoOnLineBuilder(datiProtocollazione);
            else
                retVal = new SigedoSmistamentoBackofficeBuilder(operatore);

            return retVal;
        }
    }
}
