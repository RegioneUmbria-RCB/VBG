using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.Common;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;

namespace VBG.AppLogic.SSU.GestioneRiepilogoDomanda
{
    public class SsuModelloDomandaReader : IModelloDomandaReader
    {
        private readonly string _templateXsl;

        public SsuModelloDomandaReader(string templateXslInBase64)
        {
            var data = Convert.FromBase64String(templateXslInBase64);
            this._templateXsl = System.Text.Encoding.UTF8.GetString(data);
        }

        public XslFile Read() => new(this._templateXsl);
    }
}
