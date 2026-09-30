using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda
{
    public class IdDomandaResolver : IIdDomandaResolver
    {
        int _idDomanda;

        public int IdDomanda
        {
            get => this._idDomanda;
            set
            {
                var idDomandaOld = this._idDomanda;

                this._idDomanda = value;

                if (idDomandaOld != value)
                {
                    IdDomandaChanged?.Invoke(this, EventArgs.Empty);
                }
            }
        }

        public event EventHandler? IdDomandaChanged;
    }
}
