using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders.MittentiDestinatari
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
