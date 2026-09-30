using Init.Sigepro.FrontEnd.AppLogic.WsOneri;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Conti
{
    public interface IContiRepository
    {
        ContoDto GetDatiContoDaCausaleOnere(string codiceComune, int causaleOnereId);
    }
}