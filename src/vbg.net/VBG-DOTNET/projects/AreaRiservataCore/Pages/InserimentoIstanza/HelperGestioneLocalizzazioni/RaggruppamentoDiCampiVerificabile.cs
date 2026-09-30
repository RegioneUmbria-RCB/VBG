namespace AreaRiservataCore.Pages.InserimentoIstanza.HelperGestioneLocalizzazioni
{
    public class RaggruppamentoDiCampiVerificabile : ICompilazioneVerificabile
    {

        public bool VerificaCompilazione()
        {
            //var campiCompilati = this._campi.Where(x => !String.IsNullOrEmpty(x.Valore));
            //var campiNonCompilati = this._campi.Where(x => String.IsNullOrEmpty(x.Valore));

            //return campiCompilati.Count() == 0 || campiCompilati.Count() == this._campi.Count();
            return true;
        }

        IEnumerable<ICampoLocalizzazioni> _campi;

        public RaggruppamentoDiCampiVerificabile(IEnumerable<ICampoLocalizzazioni> campi)
        {
            this._campi = campi;
        }
    }
}
