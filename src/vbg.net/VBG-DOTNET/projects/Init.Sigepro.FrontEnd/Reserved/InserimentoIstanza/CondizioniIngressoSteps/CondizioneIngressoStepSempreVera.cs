namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps
{
    public class CondizioneIngressoStepSempreVera : ICondizioneIngressoStep
    {
        #region ICondizioneIngressoStep Members

        public bool Verificata()
        {
            return true;
        }

        #endregion
    }
}