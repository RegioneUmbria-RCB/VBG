using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocArea.Interfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.DocArea.Builders.MittentiDestinatari
{
    public class DocAreaSegnaturaCognomeAnagraficaBuilder : IDocAreaSegnaturaNominativoPersonaBuilder
    {
        ProtocolloAnagrafe _anag;

        public DocAreaSegnaturaCognomeAnagraficaBuilder(ProtocolloAnagrafe anag)
        {
            _anag = anag;
        }

        #region IDocAreaSegnaturaNominativoPersonaBuilder Members

        public string Nome
        {
            get { return _anag.NOME ?? ""; }
        }

        public string Cognome
        {
            get { return _anag.NOMINATIVO ?? ""; }
        }

        public string Denominazione
        {
            get { return ""; }
        }

        #endregion
    }
}
