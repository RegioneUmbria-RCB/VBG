using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
    public class GeneratoreRiepilogoModelloDinamico : IGeneratoreRiepilogoModelloDinamico
    {
        private readonly IRiepilogoModelloInHtmlFactory _riepilogoModelloInHtmlFactory;
        private readonly ISchedeDinamicheDomandaAlRiepilogoService _reader;

        public GeneratoreRiepilogoModelloDinamico(ISchedeDinamicheDomandaAlRiepilogoService reader,
                                                    IRiepilogoModelloInHtmlFactory riepilogoModelloInHtmlFactory)
        {
            this._reader = reader;
            this._riepilogoModelloInHtmlFactory = riepilogoModelloInHtmlFactory;
        }

        public BinaryFile GeneraRiepilogo(int idModello, string nomeRiepilogo, int indiceMolteplicita)
        {
            var nomeFile = new NomeFileRiepilogoModello(nomeRiepilogo.Trim(), indiceMolteplicita).ToString();
            var riepilogo = this._riepilogoModelloInHtmlFactory.FromDomanda(this._reader, idModello, indiceMolteplicita);

            return riepilogo.ConvertiInPdf(nomeFile);
        }

    }
}
