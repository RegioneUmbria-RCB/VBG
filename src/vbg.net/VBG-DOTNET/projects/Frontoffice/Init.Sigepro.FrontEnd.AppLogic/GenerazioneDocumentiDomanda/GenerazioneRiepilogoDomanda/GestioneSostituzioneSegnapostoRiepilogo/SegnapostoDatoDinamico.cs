
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    internal class SegnapostoDatoDinamico : ISegnapostoRiepilogo
    {
        #region ISegnapostoRiepilogo Members

        public string NomeTag
        {
            get { return "campoDinamico"; }
        }

        public string NomeArgomento
        {
            get { return "id"; }
        }

        public bool SupportaOperazioniAsincrone => false;

        public string Elabora(ISchedeDinamicheDomandaAlRiepilogoService reader, string argomento, string espressione)
        {
            if (reader == null)
                throw new System.ArgumentNullException(nameof(reader));

            if (!int.TryParse(argomento, out var idCampoDinamico))
                throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Campo, espressione);

            var campo = reader.GetCampoDinamico(idCampoDinamico);

            if (campo == null)
                return string.Empty;

            return campo.ValoreDecodificato;
        }

        public Task<string> ElaboraAsync(ISchedeDinamicheDomandaAlRiepilogoService reader, string argomento, string espressione)
        {
            throw new System.NotImplementedException();
        }

        #endregion
    }
}
