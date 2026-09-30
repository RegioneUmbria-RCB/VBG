using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.ReadInterface
{
    public interface IReadFacade : IReadDatiDomanda
    {
        ICittadinanzeService Cittadinanze { get; }
        IComuniService Comuni { get; }

        ITipiSoggettoService TipiSoggetto { get; }
        IAtecoRepository Ateco { get; }
        IStradarioRepository Stradario { get; }

    }
}
