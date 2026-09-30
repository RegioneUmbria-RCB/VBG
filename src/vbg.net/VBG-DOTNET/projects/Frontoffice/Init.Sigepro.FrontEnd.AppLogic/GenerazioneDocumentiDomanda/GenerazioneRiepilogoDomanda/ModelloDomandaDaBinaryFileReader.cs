using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda
{
    public class ModelloDomandaDaBinaryFileReader : IModelloDomandaReader
    {
        private readonly BinaryFile _fileModello;
        public ModelloDomandaDaBinaryFileReader(BinaryFile fileModello)
        {
            this._fileModello = fileModello;
        }
        public XslFile Read()
        {
            return new XslFile(this._fileModello.FileContent);
        }
    }
}
