using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocArea.Interfaces;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.DocArea.Builders.MittentiDestinatari
{
    public class DocAreaSegnaturaDenominazioneAnagraficaBuilder : IDocAreaSegnaturaNominativoPersonaBuilder
    {
        ProtocolloAnagrafe _anag;

        public DocAreaSegnaturaDenominazioneAnagraficaBuilder(ProtocolloAnagrafe anag)
        {
            _anag = anag;
        }

        #region IDocAreaSegnaturaNominativoPersonaBuilder Members

        public string Nome
        {
            get { return _anag.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAFISICA ? _anag.NOME ?? "" : String.Empty; }
        }

        public string Cognome
        {
            get { return _anag.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAFISICA ? _anag.NOMINATIVO ?? "" : String.Empty; }
        }

        public string Denominazione
        {
            get { return _anag.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAFISICA ? String.Empty : _anag.NOMINATIVO ?? ""; }
        }

        #endregion
    }
}
