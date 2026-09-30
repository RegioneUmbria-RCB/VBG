using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.GestioneStradario.RicercaStradario
{
    public enum TipoFiltroWhere
    {
        And,
        Or
    }

    public class CondizioniRicercaStradarioPerDescrizione
    {
        private static class Constants
        {
            public const string And = "AND";
            public const string Or = "OR";
        }

        public readonly string SeparatoreCondizioniWhere;
        public readonly IEnumerable<string> MatchParziali;
        public readonly string CodiceComune;
        public readonly string ComuneLocalizzazione;
        public readonly TipoLikeEnum TipoLike;

        internal CondizioniRicercaStradarioPerDescrizione(string codiceComune, string comuneLocalizzazione, TipoFiltroWhere tipoFiltroWhere, IEnumerable<string> matchParziali, TipoLikeEnum tipoLike)
        {
            this.CodiceComune = codiceComune;
            this.ComuneLocalizzazione = String.IsNullOrEmpty(comuneLocalizzazione) ? codiceComune : comuneLocalizzazione;
            this.TipoLike = tipoLike;
            this.SeparatoreCondizioniWhere = tipoFiltroWhere == TipoFiltroWhere.And ? Constants.And : Constants.Or;
            this.MatchParziali = matchParziali;
        }

        internal string ValoreDaTipoLike(string testoRicerca)
        {

            switch (this.TipoLike)
            {
                case TipoLikeEnum.LikeDestra:
                    return testoRicerca + "%";
                case TipoLikeEnum.LikeCompleta:
                    return "%" + testoRicerca + "%";
            }

            return testoRicerca;
        }
    }

}
