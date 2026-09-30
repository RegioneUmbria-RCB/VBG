namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;

    using Init.SIGePro.Manager.DTO.Common;
    using Init.SIGePro.Manager.DTO.Endoprocedimenti;
    using log4net;
    using System.Collections.Generic;
    using System.Linq;
    using VBG.Shared.Infrastructure.Caching;

    internal class WsEndoprocedimentiRepository : IEndoprocedimentiRepository, IEndoprocedimentiIncompatibiliRepository
    {
        private static class Constants
        {
            public const string TipoTitoloCacheKey = "TipoTitoloCacheKey:{0}:{1}";
        }

        private readonly EndoprocedimentiServiceCreator _serviceCreator;
        private readonly IAliasResolver _aliasResolver;
        private readonly IApplicationCache _webCache;
        private readonly ILog _log = LogManager.GetLogger(typeof(WsEndoprocedimentiRepository));

        public WsEndoprocedimentiRepository(EndoprocedimentiServiceCreator serviceCreator, IAliasResolver aliasResolver, IApplicationCache webCache)
        {
            this._aliasResolver = aliasResolver;
            this._webCache = webCache;
            this._serviceCreator = serviceCreator;
        }

        public EndoprocedimentoMappatoDto GetByIdEndoMappato(string idEndoMappato)
        {
            return this._serviceCreator.Call(ws => ws.Service.GetEndoprocedimentoDaIdMappatura(ws.Token, idEndoMappato));
        }

        public EndoprocedimentoDto GetById(int id, AmbitoRicerca ambitoRicercaDocumenti)
        {
            return this._serviceCreator.Call(ws =>
            {
                var endo = ws.Service.GetEndoprocedimentoById(ws.Token, id, ambitoRicercaDocumenti);

                endo.Adempimenti = string.IsNullOrEmpty(endo.Adempimenti) ? string.Empty : endo.Adempimenti.Replace("\n", "<br />");
                endo.Amministrazione = string.IsNullOrEmpty(endo.Amministrazione) ? string.Empty : endo.Amministrazione.Replace("\n", "<br />");
                endo.Applicazione = string.IsNullOrEmpty(endo.Applicazione) ? string.Empty : endo.Applicazione.Replace("\n", "<br />");
                endo.DatiGenerali = string.IsNullOrEmpty(endo.DatiGenerali) ? string.Empty : endo.DatiGenerali.Replace("\n", "<br />");
                endo.Descrizione = string.IsNullOrEmpty(endo.Descrizione) ? string.Empty : endo.Descrizione.Replace("\n", "<br />");
                endo.NormativaNazionale = string.IsNullOrEmpty(endo.NormativaNazionale) ? string.Empty : endo.NormativaNazionale.Replace("\n", "<br />");
                endo.NormativaRegionale = string.IsNullOrEmpty(endo.NormativaRegionale) ? string.Empty : endo.NormativaRegionale.Replace("\n", "<br />");
                endo.NormativaUE = string.IsNullOrEmpty(endo.NormativaUE) ? string.Empty : endo.NormativaUE.Replace("\n", "<br />");
                endo.Note = string.IsNullOrEmpty(endo.Note) ? string.Empty : endo.Note.Replace("\n", "<br />");
                endo.Regolamenti = string.IsNullOrEmpty(endo.Regolamenti) ? string.Empty : endo.Regolamenti.Replace("\n", "<br />");
                endo.Tempificazione = string.IsNullOrEmpty(endo.Tempificazione) ? string.Empty : endo.Tempificazione.Replace("\n", "<br />");
                endo.Tipologia = string.IsNullOrEmpty(endo.Tipologia) ? string.Empty : endo.Tipologia.Replace("\n", "<br />");

                return endo;
            });
        }

        public EndoprocedimentiAreaRiservataDto GetListaEndoDaIdIntervento(string codiceComune, int codIntervento, bool utenteTeste)
        {
            return this._serviceCreator.Call(ws => ws.Service.GetListaEndoDaIdIntervento(ws.Token, codiceComune, codIntervento, utenteTeste));
        }

        public Dictionary<int, IEnumerable<TipiTitoloDto>> TipiTitoloWhereCodiceInventarioIn(List<int> codiciInventario)
        {
            return this._serviceCreator.Call(ws =>
            {
                var tipiTitolo = ws.Service.GetTipiTitoloEndoDaListaCodiciInventario(ws.Token, codiciInventario.ToArray());

                return tipiTitolo.ToDictionary(x => x.CodiceInventario, x => x.TipiTitolo.AsEnumerable());
            });
        }

        public TipiTitoloDto GetTipoTitoloById(int codiceTipoTitolo)
        {
            var cacheKey = $"TipoTitoloCacheKey:{this._aliasResolver.AliasComune}:{codiceTipoTitolo}";

            return this._webCache.GetOrAdd(cacheKey, () =>
                this._serviceCreator.Call(ws => ws.Service.GetTipoTitoloById(ws.Token, codiceTipoTitolo))
            );
        }


        public IEnumerable<EndoprocedimentoIncompatibileDto> GetEndoprocedimentiIncompatibili(int[] listaIdEndoAttivati)
        {
            return this._serviceCreator.Call(ws => ws.Service.GetEndoprocedimentiIncompatibili(ws.Token, listaIdEndoAttivati));
        }

        public string GetNaturaBaseDaidEndoprocedimento(int codiceInventario)
        {
            return this._serviceCreator.Call(ws => ws.Service.GetNaturaBaseDaidEndoprocedimento(ws.Token, codiceInventario)?.Natura);
        }

    }
}
