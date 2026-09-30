using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocArea.Interfaces;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.DocArea.Builders.MittentiDestinatari
{
    public class DocAreaSegnaturaCognomeAmministrazioneBuilder : IDocAreaSegnaturaNominativoPersonaBuilder
    {
        Amministrazioni _amm;
        public DocAreaSegnaturaCognomeAmministrazioneBuilder(Amministrazioni amm)
        {
            _amm = amm;
        }

        #region IDocAreaSegnaturaNominativoPersonaBuilder Members

        public string Nome
        {
            get { return ""; }
        }

        public string Cognome
        {
            get { return _amm.AMMINISTRAZIONE; }
        }

        public string Denominazione
        {
            get { return ""; }
        }

        #endregion
    }
}
