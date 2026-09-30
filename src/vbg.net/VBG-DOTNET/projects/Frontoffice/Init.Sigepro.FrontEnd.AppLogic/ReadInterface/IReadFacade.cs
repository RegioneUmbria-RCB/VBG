using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;

namespace Init.Sigepro.FrontEnd.AppLogic.ReadInterface
{
#if NET48_OR_GREATER
    public interface IReadFacade : IReadDatiDomanda
    {
        ICittadinanzeService Cittadinanze { get; }
        // IComuniService Comuni { get; }

        ITipiSoggettoService TipiSoggetto { get; }
        // IAtecoRepository Ateco { get; }
        IInterventiRepository Interventi { get; }
        IStradarioRepository Stradario { get; }
    }
#endif
}
