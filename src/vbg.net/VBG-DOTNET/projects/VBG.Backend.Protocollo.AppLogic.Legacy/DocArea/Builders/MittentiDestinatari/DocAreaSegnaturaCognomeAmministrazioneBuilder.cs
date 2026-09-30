using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders.MittentiDestinatari
{
    public class DocAreaSegnaturaCognomeAmministrazioneBuilder : IDocAreaSegnaturaNominativoPersonaBuilder
    {
        private readonly ProtocolloAmministrazioni _amm;
        public DocAreaSegnaturaCognomeAmministrazioneBuilder(ProtocolloAmministrazioni amm)
        {
            this._amm = amm;
        }

        #region IDocAreaSegnaturaNominativoPersonaBuilder Members

        public string Nome
        {
            get { return ""; }
        }

        public string Cognome
        {
            get { return this._amm.AMMINISTRAZIONE; }
        }

        public string Denominazione
        {
            get { return ""; }
        }

        #endregion
    }
}
