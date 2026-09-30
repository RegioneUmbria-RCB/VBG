using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaPubblicazione;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
    internal class VerificaPubblicazioneAreaRiservata : IVerificaaAlbero
    {
        public bool PuoAnalizzare(IIntervento intervento)
        {
            return intervento.Pubblica != FlagPubblicazione.EreditaDalPadre;
        }

        public bool GetRisultato(IIntervento intervento)
        {
            return intervento.Pubblica == FlagPubblicazione.AreaRiservata || intervento.Pubblica == FlagPubblicazione.AreaRiservataEFrontoffice;
        }
    }

}
