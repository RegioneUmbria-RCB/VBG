using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.SIGePro.Manager.DTO.Visura;
using System.Collections.Concurrent;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
    public interface IStatiIstanzaRepository
    {
        StatoIstanzaDto GetById(string software, string codiceStato);
        StatoIstanzaDto[] GetList(string software);
    }

    internal class WsStatiIstanzaRepository : IStatiIstanzaRepository
    {
        private readonly CampiRicercaPraticheServiceCreator _serviceCreator;
        private readonly IAliasResolver _aliasResolver;
        private static readonly ConcurrentDictionary<string, StatoIstanzaDto> _cacheStati = new ConcurrentDictionary<string, StatoIstanzaDto>();

        public WsStatiIstanzaRepository(CampiRicercaPraticheServiceCreator serviceCreator, IAliasResolver aliasResolver)
        {
            if (serviceCreator == null)
                throw new System.ArgumentNullException(nameof(serviceCreator));
            //Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

            this._serviceCreator = serviceCreator;
            this._aliasResolver = aliasResolver;
        }


        public StatoIstanzaDto[] GetList(string software)
        {
            return this._serviceCreator.Call(ws =>
            {
                return ws.Service.GetStatiIstanza(ws.Token, software);
            });
        }

        public StatoIstanzaDto GetById(string software, string codiceStato)
        {
            var cacheKey = $"{this._aliasResolver.AliasComune}${software}${codiceStato}";

            return _cacheStati.GetOrAdd(cacheKey, (_) => this.GetStatoIstanzaInternal(software, codiceStato));
        }

        private StatoIstanzaDto GetStatoIstanzaInternal(string software, string codiceStato)
        {
            return this._serviceCreator.Call(ws =>
            {
                return ws.Service.GetStatoIstanza(ws.Token, software, codiceStato);
            });
        }
    }
}
