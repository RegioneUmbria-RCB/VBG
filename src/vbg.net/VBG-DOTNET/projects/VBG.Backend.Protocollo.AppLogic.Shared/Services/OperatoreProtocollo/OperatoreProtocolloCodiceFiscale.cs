using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using PersonalLib2.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services.OperatoreProtocollo
{
    public class OperatoreProtocolloCodiceFiscale : BaseOperatoreProtocollo, IOperatoreProtocollo
    {
        public OperatoreProtocolloCodiceFiscale(int codiceOperatore, string idComune, DataBase db) : base(db, codiceOperatore, idComune)
        {

        }

        #region IOperatoreProtocollo Members

        public string CodiceOperatore
        {
            get { return Responsabile.CODICEFISCALE; }
        }

        public bool IsOperatoreDefault
        {
            get { return false; }
        }

        #endregion
    }
}
