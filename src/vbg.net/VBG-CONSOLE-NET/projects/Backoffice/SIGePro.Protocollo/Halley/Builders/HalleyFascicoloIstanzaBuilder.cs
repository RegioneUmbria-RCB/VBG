using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Halley.Interfaces;
using Init.SIGePro.Protocollo.ProtocolloHalleyDizionarioServiceProxy;

namespace Init.SIGePro.Protocollo.Halley.Builders
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
