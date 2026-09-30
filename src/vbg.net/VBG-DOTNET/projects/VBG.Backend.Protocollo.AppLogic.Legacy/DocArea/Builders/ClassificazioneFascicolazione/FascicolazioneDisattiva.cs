using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Adapters;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders.ClassificazioneFascicolazione
{
    public class FascicolazioneDisattiva : IClassificazioneFascicolazione
    {
        string _classifica;
        DocAreaVerticalizzazioneParametriAdapter _parametri;

        public FascicolazioneDisattiva(string classifica, DocAreaVerticalizzazioneParametriAdapter parametri)
        {
            this._classifica = classifica;
            this._parametri = parametri;
        }

        public Classifica GetClassificazione()
        {
            if (!String.IsNullOrEmpty(_classifica))
            {
                return new Classifica
                {
                    CodiceTitolario = _classifica,
                    CodiceAmministrazione = _parametri.CodiceAmministrazione,
                    CodiceAOO = _parametri.CodiceAoo
                };
            }

            return null;
        }

        public Fascicolo GetFascicolo()
        {
            return null;
        }
    }
}
