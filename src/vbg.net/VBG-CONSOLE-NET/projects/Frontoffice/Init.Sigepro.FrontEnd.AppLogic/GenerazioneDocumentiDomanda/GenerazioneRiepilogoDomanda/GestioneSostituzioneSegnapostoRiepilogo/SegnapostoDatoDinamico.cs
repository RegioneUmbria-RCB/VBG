using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

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

        public string Elabora(IDatiDinamiciRiepilogoReader reader, string argomento, string espressione)
        {
            Condition.Requires(reader, "domanda").IsNotNull();

            int idCampoDinamico = -1;

            if (!int.TryParse(argomento, out idCampoDinamico))
                throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Campo, espressione);

            //var campo = reader.ReadInterface
            //					.DatiDinamici
            //					.DatiDinamici
            //					.Where(x => x.IdCampo == idCampoDinamico && x.IndiceMolteplicita == 0)
            //					.FirstOrDefault();

            var campo = reader.GetCampoDinamico(idCampoDinamico);

            if (campo == null)
                return string.Empty;

            return campo.ValoreDecodificato;
        }

        #endregion
    }
}
