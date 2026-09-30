using SIGePro.Manager.VerticalizzazioniBase;
using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Interfacce;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Builders
{
    public class SigedoSmistamentoBackofficeBuilder : ISmistamentoProvenienza
    {
        string _operatore;
        public SigedoSmistamentoBackofficeBuilder(string operatore)
        {
            _operatore = operatore;
        }

        #region ISmistamentoSigedo Members

        public string GetOperatoreSmistamento(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            if (String.IsNullOrEmpty(_operatore))
                throw new Exception("OPERATORE NON VALORIZZATO, NON E' POSSIBILE RECUPERARE LO SMISTAMENTO");

            return _operatore.Substring(1);
        }

        public bool IsSmistamentoAutomaticoDaOnline
        {
            get { return false; }
        }

        #endregion
    }
}
