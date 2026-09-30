using VisuraVbg;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class SegnapostoDatoDinamico : ISegnapostoRiepilogo
    {
        #region ISegnapostoRiepilogo Members

        public string NomeTag => "campoDinamico";

        public string NomeArgomento => "id";

        public Task<string> ElaboraAsync(Istanze istanza, string argomento, string espressione)
        {
            if (!int.TryParse(argomento, out var idCampoDinamico))
                throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Campo, espressione);

            var valore = this.GetValoreDecodificatoCampoDinamico(istanza, idCampoDinamico);

            return Task.FromResult(valore);
        }

        public string GetValoreDecodificatoCampoDinamico(Istanze istanza, int idCampoDinamico, int indiceMolteplicita = 0)
        {
            return istanza.IstanzeDyn2Dati.Where(x =>
                                        x.FkD2cId.GetValueOrDefault(-1) == idCampoDinamico &&
                                        x.IndiceMolteplicita == indiceMolteplicita)
                                .Select(x => x.Valoredecodificato)
                                .FirstOrDefault() ?? "";
        }

        #endregion
    }
}
