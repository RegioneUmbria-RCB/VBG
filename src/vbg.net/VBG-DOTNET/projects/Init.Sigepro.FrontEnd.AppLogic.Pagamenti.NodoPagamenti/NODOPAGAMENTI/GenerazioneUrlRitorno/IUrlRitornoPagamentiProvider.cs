using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using VBG.Pagamenti.NodoPagamenti;
using VBG.Pagamenti.NodoPagamenti.Shared;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.NODOPAGAMENTI.GenerazioneUrlRitorno
{
    public interface IUrlRitornoPagamentiProvider
    {
        string GeneraUrlRitorno(RiferimentiDomanda domanda, NodoPagamentiSettings settings);
    }

    public class DomandaOnLineUrlRitornoPagamentiProvider : IUrlRitornoPagamentiProvider
    {
        private readonly IResolveUrl _resolveUrl;

        public DomandaOnLineUrlRitornoPagamentiProvider(IResolveUrl resolveUrl)
        {
            this._resolveUrl = resolveUrl;
        }

        public string GeneraUrlRitorno(RiferimentiDomanda domanda, NodoPagamentiSettings settings)
        {
            var relative = $"~/{domanda.IdComune}/{domanda.Software}/pagamento-completato/{domanda.IdDomanda}";

            if ((domanda.StepId ?? -1) != -1)
            {
                relative += $"/{domanda.StepId}";
            }
            return this._resolveUrl.ToAbsoluteUrl(relative);
        }
    }

    public class ArCoreUrlRitornoPagamentiProvider : IUrlRitornoPagamentiProvider
    {
        private readonly IResolveUrl _resolveUrl;

        public ArCoreUrlRitornoPagamentiProvider(IResolveUrl resolveUrl)
        {
            this._resolveUrl = resolveUrl;
        }

        public string GeneraUrlRitorno(RiferimentiDomanda domanda, NodoPagamentiSettings settings)
        {
            var relative = $"~/{domanda.IdComune}/{domanda.Software}/inserimento-istanza/pagamenti/pagamento/{domanda.IdDomanda}/{domanda.StepId}";

            return this._resolveUrl.ToAbsoluteUrl(relative);
        }
    }
#if NET48
    public class ArLegacyUrlRitornoPagamentiProvider : IUrlRitornoPagamentiProvider
    {
        private readonly IResolveUrl _resolveUrl;

        public ArLegacyUrlRitornoPagamentiProvider(IResolveUrl resolveUrl)
        {
            this._resolveUrl = resolveUrl;
        }

        public string GeneraUrlRitorno(RiferimentiDomanda riferimentiDomanda, NodoPagamentiSettings settings)
        {
            return new UrlPagamenti(settings.UrlRitorno, riferimentiDomanda, this._resolveUrl).ToString();
        }
    }
#endif
}
