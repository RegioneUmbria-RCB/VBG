using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders.MittentiDestinatari
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
