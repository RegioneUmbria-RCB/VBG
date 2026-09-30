using ProtocolloHalleyDizionarioServiceProxy;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Interfaces;


namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders
{
    public class HalleyFascicoloIstanzaBuilder : IFascicoloHalleyBuilder
    {
        string _descrizioneFascicolo;

        public HalleyFascicoloIstanzaBuilder(string numeroIstanza, string idComune, string software)
        {
            _descrizioneFascicolo = String.Concat(numeroIstanza, ".", idComune, ".", software);
        }

        #region IFascicoloHalleyBuilder Members

        public FascicoliFascicolo GetDatiFascicolo()
        {
            return new FascicoliFascicolo { Nome = _descrizioneFascicolo };

            //return new Fascicolo { Text = new string[] { _descrizioneFascicolo } };
        }

        #endregion
    }
}
