using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders.MittentiDestinatari
{
    public class DocAreaSegnaturaDenominazioneAmministrazioneBuilder : IDocAreaSegnaturaNominativoPersonaBuilder
    {
        private readonly ProtocolloAmministrazioni _amm;
        public DocAreaSegnaturaDenominazioneAmministrazioneBuilder(ProtocolloAmministrazioni amm)
        {
            this._amm = amm;
        }

        #region IDocAreaSegnaturaNominativoPersonaBuilder Members

        public string Nome
        {
            get { return String.Empty; }
        }

        public string Cognome
        {
            get { return this._amm.AMMINISTRAZIONE; }
        }

        public string Denominazione
        {
            get { return this._amm.AMMINISTRAZIONE; }
        }

        #endregion
    }
}
