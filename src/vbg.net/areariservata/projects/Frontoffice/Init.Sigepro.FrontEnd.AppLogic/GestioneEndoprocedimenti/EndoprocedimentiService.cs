namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione;
	using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

	public interface IEndoprocedimentiService
	{
		void ImpostaEndoSelezionati(int idDomanda, List<int> idEndoprocedimentiSelezionati);

		void ImpostaEndoPresenti(int idDomanda, IEnumerable<DatiEndoprocedimentoPresente> listaEndoPresenti);

		IEnumerable<string> VerificaCorrettezzaListaEndoPresenti(IEnumerable<DatiEndoprocedimentoPresente> listaEndoPresenti);

		Dictionary<int, List<TipiTitoloDto>> TipiTitoloWhereCodiceInventarioIn(string alias, IEnumerable<int> codiciInventario);

		IEnumerable<EndoprocedimentoDto> WhereCodiceEndoIn(string alias, int idIntervento, IEnumerable<int> listaId);

		EndoprocedimentiDellaDomandaOnline LeggiEndoprocedimentiDaCodiceIntervento(string aliasComune, int codiceIntervento);

		ListaEndoDaIdInterventoDto WhereInterventoIs(string aslias, int codiceIntervento);

		EndoprocedimentoDto GetById(int codiceEndoprocedimento, AmbitoRicerca ambitoRicerca = AmbitoRicerca.AreaRiservata);

		void AllegaFileAEndoPresente(int idDomanda, int codiceInventario, BinaryFile file, bool verificaFirma);

		void RimuoviAllegatoDaEndo(int idDomanda, int codiceInventario);

		TipiTitoloDto GetTipoTitoloById(int codiceTipoTitolo);

		IEnumerable<EndoprocedimentoIncompatibile> GetEndoprocedimentiIncompatibili(int idDomanda);

        void ImpostaNaturaBaseDomanda(int idDomanda, string naturaBase);

        EndoprocedimentoMappatoDto GetByIdEndoMappato(string idEndoMappato);

    }

	public class EndoprocedimentiService : IEndoprocedimentiService
	{
		private ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		private IEndoprocedimentiRepository _endoprocedimentiRepository;
		private AllegatiDomandaService _allegatiRepository;
		private IAliasResolver _aliasResolver;
		private IEndoprocedimentiIncompatibiliService _endoprocedimentiIncompatibiliService;

		public EndoprocedimentiService(IAliasResolver aliasResolver, ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IEndoprocedimentiRepository endoprocedimentiRepository, AllegatiDomandaService allegatiRepository, IEndoprocedimentiIncompatibiliService endoprocedimentiIncompatibiliService)
		{
			this._aliasResolver = aliasResolver;
			this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			this._endoprocedimentiRepository = endoprocedimentiRepository;
			this._allegatiRepository = allegatiRepository;
			this._endoprocedimentiIncompatibiliService = endoprocedimentiIncompatibiliService;
		}

		#region IEndoprocedimentiService Members

		public void ImpostaEndoSelezionati(int idDomanda, List<int> idNuoviEndoprocedimenti)
		{
			var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Endoprocedimenti.AggiungiESincronizza(idNuoviEndoprocedimenti, new LogicaSincronizzazioneEndo(domanda, this));

			this._salvataggioDomandaStrategy.Salva(domanda);
		}

		public void ImpostaEndoPresenti(int idDomanda, IEnumerable<DatiEndoprocedimentoPresente> listaEndoUtente)
		{
			var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

			foreach (var endo in domanda.ReadInterface.Endoprocedimenti.Endoprocedimenti)
			{
				var endoUtente = listaEndoUtente.Where(x => x.Codice == endo.Codice).FirstOrDefault();

				if (endoUtente == null || !endoUtente.Presente)
				{
					domanda.WriteInterface.Endoprocedimenti.ImpostaNonPresente(endo.Codice);
					continue;
				}

				domanda
					.WriteInterface
					.Endoprocedimenti
					.ImpostaPresente(
						endoUtente.Codice,
						string.IsNullOrEmpty(endoUtente.CodiceTipoTitolo) ? (int?)null : Convert.ToInt32(endoUtente.CodiceTipoTitolo),
						endoUtente.DescrizioneTipoTitolo,
						endoUtente.NumeroAtto,
						string.IsNullOrEmpty(endoUtente.DataAtto) ? (DateTime?)null : DateTime.ParseExact(endoUtente.DataAtto, "dd/MM/yyyy", null),
						endoUtente.RilasciatoDa,
						endoUtente.Note);
			}

			this._salvataggioDomandaStrategy.Salva(domanda);
		}

		public IEnumerable<string> VerificaCorrettezzaListaEndoPresenti(IEnumerable<DatiEndoprocedimentoPresente> listaEndoPresenti)
		{
			foreach (var endoPresente in listaEndoPresenti)
			{
				if (string.IsNullOrEmpty(endoPresente.CodiceTipoTitolo) || endoPresente.CodiceTipoTitolo == "-1")
				{
					var fmtString = "E' obbligatorio specificare il tipo titolo per l'endoprocedimento \"{0}\"";
					yield return string.Format(fmtString, endoPresente.Descrizione);

                    continue;
				}

				var datiEndo = this.GetTipoTitoloById(Convert.ToInt32(endoPresente.CodiceTipoTitolo));

				if (datiEndo != null)
				{
					if (datiEndo.Flags.MostraNumero && string.IsNullOrEmpty(endoPresente.NumeroAtto))
					{
						var fmtString = "E' obbligatorio specificare il numero atto per l'endoprocedimento \"{0}\"";
						yield return string.Format(fmtString, endoPresente.Descrizione);
					}

					if (datiEndo.Flags.MostraData && string.IsNullOrEmpty(endoPresente.DataAtto))
					{
						var fmtString = "E' obbligatorio specificare la data atto per l'endoprocedimento \"{0}\"";
						yield return string.Format(fmtString, endoPresente.Descrizione);
					}

					if (datiEndo.Flags.MostraRilasciatoDa && string.IsNullOrEmpty(endoPresente.RilasciatoDa))
					{
						var fmtString = "E' obbligatorio specificare l'ente di rilascio dell'atto per l'endoprocedimento \"{0}\"";
						yield return string.Format(fmtString, endoPresente.Descrizione);
					}

					if (endoPresente.NumeroAtto.Length > 15)
					{
						var fmtString = "Il numero atto dell'endoprocedimento \"{0}\" supera la lunghezza massima consentita (15 caratteri)";
						yield return string.Format(fmtString, endoPresente.Descrizione);
					}
				}
			}
		}

		public Dictionary<int, List<TipiTitoloDto>> TipiTitoloWhereCodiceInventarioIn(string alias, IEnumerable<int> codiciInventario)
		{
			return this._endoprocedimentiRepository.TipiTitoloWhereCodiceInventarioIn(alias, codiciInventario.ToList());
		}

		public IEnumerable<EndoprocedimentoDto> WhereCodiceEndoIn(string alias, int idIntervento, IEnumerable<int> listaId)
		{
			var e = this._endoprocedimentiRepository.WhereInterventoIs(alias, idIntervento);

			return EstraiEndoDa(e.EndoFacoltativi, listaId)
					.Union(EstraiEndoDa(e.EndoIntervento, listaId));
		}

		private IEnumerable<EndoprocedimentoDto> EstraiEndoDa(FamigliaEndoprocedimentoDto[] famiglie, IEnumerable<int> listaId)
		{
            return famiglie.SelectMany(famiglia => famiglia.TipiEndoprocedimenti)
                            .SelectMany(tipo => tipo.Endoprocedimenti)
                            .Where(endo => listaId.Contains(endo.Codice));
			//foreach (var famiglia in famiglie)
			//{
			//	foreach (var tipo in famiglia.TipiEndoprocedimenti)
			//	{
			//		foreach (var endo in tipo.Endoprocedimenti)
			//		{
			//			if (listaId.Contains(endo.Codice))
			//			{
			//				yield return endo;
			//			}
			//		}
			//	}
			//}
		}

		public EndoprocedimentiDellaDomandaOnline LeggiEndoprocedimentiDaCodiceIntervento(string aliasComune, int codiceIntervento)
		{
			var endoDellIntervento = this._endoprocedimentiRepository.WhereInterventoIs(aliasComune, codiceIntervento);

			return new EndoprocedimentiDellaDomandaOnline(endoDellIntervento.EndoIntervento, endoDellIntervento.EndoFacoltativi);
		}

		public ListaEndoDaIdInterventoDto WhereInterventoIs(string idComune, int codIntervento)
		{
			return this._endoprocedimentiRepository.WhereInterventoIs(idComune, codIntervento);
		}

		public EndoprocedimentoDto GetById(int codiceEndoprocedimento, AmbitoRicerca ambitoRicerca = AmbitoRicerca.AreaRiservata)
		{
			return this._endoprocedimentiRepository.GetById(this._aliasResolver.AliasComune, codiceEndoprocedimento, ambitoRicerca);
		}

		#endregion

		public void AllegaFileAEndoPresente(int idDomanda, int codiceInventario, BinaryFile file, bool richiedeFirmaDigitale)
		{
			var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

			var esitoUpload = this._allegatiRepository.AllegaADomandaSenzaSalvare(domanda, file, richiedeFirmaDigitale);

			domanda.WriteInterface.Endoprocedimenti.AllegaAdEndoPresente(codiceInventario, esitoUpload.IdAllegato);

			this._salvataggioDomandaStrategy.Salva(domanda);
		}

		public void RimuoviAllegatoDaEndo(int idDomanda, int codiceInventario)
		{
			var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Endoprocedimenti.RimuoviAllegatoDaEndoPresente(codiceInventario);

			this._salvataggioDomandaStrategy.Salva(domanda);
		}

		public TipiTitoloDto GetTipoTitoloById(int codiceTipoTitolo)
		{
            if (codiceTipoTitolo == -1)
            {
                return null;
            }

			return this._endoprocedimentiRepository.GetTipoTitoloById(this._aliasResolver.AliasComune, codiceTipoTitolo);
		}

		public IEnumerable<EndoprocedimentoIncompatibile> GetEndoprocedimentiIncompatibili(int idDomanda)
		{
			var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

			return domanda.ReadInterface.Endoprocedimenti.GetEndoprocedimentiIncompatibili(this._endoprocedimentiIncompatibiliService);
		}

        public string GetNaturaBaseDaidEndoprocedimento(int codiceInventario)
        {
            return this._endoprocedimentiIncompatibiliService.GetNaturaBaseDaidEndoprocedimento(codiceInventario);
        }

        public void ImpostaNaturaBaseDomanda(int idDomanda, string naturaBase)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaNaturaBase(naturaBase);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public EndoprocedimentoMappatoDto GetByIdEndoMappato(string idEndoMappato)
        {
            return this._endoprocedimentiRepository.GetByIdEndoMappato(idEndoMappato);
        }
    }
}
