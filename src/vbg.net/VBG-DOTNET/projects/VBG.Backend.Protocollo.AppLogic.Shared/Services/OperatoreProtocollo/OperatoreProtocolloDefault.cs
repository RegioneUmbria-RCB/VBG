using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services.OperatoreProtocollo
{
    public class OperatoreProtocolloDefault : IOperatoreProtocollo
    {
        string _segnalibro = "";
        public OperatoreProtocolloDefault(string segnalibro)
        {
            _segnalibro = segnalibro;
        }

        #region IOperatoreProtocollo Members

        public string CodiceOperatore
        {
            get { return _segnalibro; }
        }

        public bool IsOperatoreDefault
        {
            get { return true; }
        }

        #endregion
    }
}
