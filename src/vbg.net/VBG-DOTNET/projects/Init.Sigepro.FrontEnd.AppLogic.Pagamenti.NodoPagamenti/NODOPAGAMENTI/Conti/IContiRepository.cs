using Init.SIGePro.Manager.DTO.Oneri;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Conti
{
    public interface IContiRepository
    {
        ContoDto GetDatiContoDaCausaleOnere(string codiceComune, int causaleOnereId);
    }
}
