using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.StradarioComune;
using Init.SIGePro.Manager.Logic.GestioneStradario.RicercaStradario;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneStradario
{
    public class GestioneStradarioService
    {
        private readonly IStradarioRepository _stradarioRepository;

        public GestioneStradarioService(IStradarioRepository stradarioRepository)
        {
            this._stradarioRepository = stradarioRepository;
        }

        public IEnumerable<StradarioDto> FindByMatchParziale(string codiceComune, string comuneLocalizzazione, string indirizzo, bool escludiDisabilitate)
        {
            var partiEspressione = new TokenizerPartiEspressione(indirizzo).GetTokens();
            var stringaIntera = new[] { String.Join(" ", partiEspressione.ToArray()) };

            var condizioniRicerca = new[]
            {
                new CondizioniRicercaStradarioPerDescrizione(codiceComune, comuneLocalizzazione, TipoFiltroWhere.And, stringaIntera, TipoLikeEnum.Equals),
                new CondizioniRicercaStradarioPerDescrizione(codiceComune, comuneLocalizzazione, TipoFiltroWhere.And, stringaIntera, TipoLikeEnum.LikeDestra),
				// new CondizioniRicercaStradarioPerDescrizione(codiceComune, comuneLocalizzazione, TipoFiltroWhere.And, partiEspressione, TipoLikeEnum.LikeDestra),
				new CondizioniRicercaStradarioPerDescrizione(codiceComune, comuneLocalizzazione, TipoFiltroWhere.And, partiEspressione, TipoLikeEnum.LikeCompleta),
                new CondizioniRicercaStradarioPerDescrizione(codiceComune, comuneLocalizzazione, TipoFiltroWhere.Or, partiEspressione, TipoLikeEnum.LikeDestra),
                new CondizioniRicercaStradarioPerDescrizione(codiceComune, comuneLocalizzazione, TipoFiltroWhere.Or, partiEspressione, TipoLikeEnum.LikeCompleta),
            };

            var score = 0;

            var risultati = condizioniRicerca.Select(cond => new
            {
                results = this._stradarioRepository.FindByMatchParziale(cond, escludiDisabilitate),
                score = ++score
            })
                .OrderBy(x => x.score)
                .SelectMany(x => x.results)
                .Distinct();

            if (risultati == null)
            {
                return Enumerable.Empty<StradarioDto>();
            }

            return risultati.Select(x => this.CreateStradarioDto(x));
        }


        public StradarioDto CreateStradarioDto(Stradario stradario)
        {
            var toponimo = stradario.PREFISSO;
            var via = stradario.DESCRIZIONE;

            if (!String.IsNullOrEmpty(stradario.LOCFRAZ))
                via += " (" + stradario.LOCFRAZ + ")";

            return new StradarioDto
            {
                CodiceStradario = Convert.ToInt32(stradario.CODICESTRADARIO),
                NomeVia = String.IsNullOrEmpty(toponimo) ? via : toponimo + " " + via,
                CodViario = stradario.CODVIARIO
            };
        }
    }
}
