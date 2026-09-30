namespace Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore
{
    public class ExitStepEventArgs
    {
        public bool IsCanceled { get; private set; } = false;

        public void Cancel()
        {
            this.IsCanceled = true;
        }
    }
}
