using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaPubblicazione;
using log4net;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
    internal class VerificaPubblicazioneDomandaOnLine : IVerificaaAlbero
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(VerificaPubblicazioneDomandaOnLine));

        public bool PuoAnalizzare(IIntervento intervento)
        {
            return intervento.Pubblica != FlagPubblicazione.EreditaDalPadre;
        }

        public bool GetRisultato(IIntervento intervento)
        {
            return intervento.Pubblica == FlagPubblicazione.SoloDomandaOnLine || intervento.Pubblica == FlagPubblicazione.AreaRiservataEFrontoffice;
        }
    }
}