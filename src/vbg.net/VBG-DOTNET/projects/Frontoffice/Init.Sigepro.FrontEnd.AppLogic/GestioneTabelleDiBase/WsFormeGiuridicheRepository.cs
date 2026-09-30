using Init.Sigepro.FrontEnd.AppLogic.Common;
using VBG.Shared.Infrastructure.Caching;
using Init.SIGePro.Manager.DTO.TabelleDiBase;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase
{
    internal class WsFormeGiuridicheRepository : IFormeGiuridicheRepository
    {
        private const string SESSION_KEY = "SESSION_KEY_FORME_GIURIDICHE_";
        private readonly TabelleDiBaseServiceCreator _serviceCreator;
        private readonly IAliasResolver _aliasResolver;
        private readonly IApplicationCache _webCache;

        public WsFormeGiuridicheRepository(TabelleDiBaseServiceCreator serviceCreator, IAliasResolver aliasResolver, IApplicationCache webCache)
        {
            this._serviceCreator = serviceCreator;
            this._aliasResolver = aliasResolver;
            this._webCache = webCache;
        }

        public FormaGiuridicaDto[] GetList(string aliasComune = "")
        {
            if (string.IsNullOrEmpty(aliasComune))
            {
                aliasComune = this._aliasResolver.AliasComune;
            }

            string key = SESSION_KEY + aliasComune;

            return this._webCache.GetOrAdd(key, () =>
            {
                return this._serviceCreator.Call(ws =>
                {
                    return ws.Service.GetListaFormeGiuridiche(ws.Token);
                });
            });
        }


        public FormaGiuridicaDto GetById(string id)
        {
            var alias = this._aliasResolver.AliasComune;

            return this.GetList(alias).Where(x => x.CodiceFormaGiuridica == id).FirstOrDefault();
        }
    }
}
