using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
    public class GeneratoreRiepilogoModelloDinamicoAsync : IGeneratoreRiepilogoModelloDinamicoAsync
    {
        private readonly IRiepilogoModelloInHtmlFactory _riepilogoModelloInHtmlFactory;
        private readonly ISchedeDinamicheDomandaAlRiepilogoService _reader;

        public GeneratoreRiepilogoModelloDinamicoAsync(ISchedeDinamicheDomandaAlRiepilogoService reader,
                                            IRiepilogoModelloInHtmlFactory riepilogoModelloInHtmlFactory)
        {
            this._reader = reader;
            this._riepilogoModelloInHtmlFactory = riepilogoModelloInHtmlFactory;
        }

        public async Task<BinaryFile> GeneraRiepilogoAsync(int idModello, string nomeRiepilogo, int indiceMolteplicita)
        {
            var nomeFile = new NomeFileRiepilogoModello(nomeRiepilogo.Trim(), indiceMolteplicita).ToString();
            var riepilogo = this._riepilogoModelloInHtmlFactory.FromDomanda(this._reader, idModello, indiceMolteplicita);

            return await riepilogo.ConvertiInPdfAsync(nomeFile);
        }
    }
}
