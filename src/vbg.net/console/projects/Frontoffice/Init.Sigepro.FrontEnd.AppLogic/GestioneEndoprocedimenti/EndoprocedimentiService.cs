﻿namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
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

		IEnumerable<EndoprocedimentoDto> WhereCodiceEndoIn(int idIntervento, string codiceComune, IEnumerable<int> listaId);

		EndoprocedimentiDellaDomandaOnline LeggiEndoprocedimentiDaCodiceIntervento(int codiceIntervento, string codiceComune);

		ListaEndoDaIdInterventoDto WhereInterventoIs(int codiceIntervento, string codiceComune);

		EndoprocedimentoDto GetById(string alias, int codiceEndoprocedimento, AmbitoRicerca ambitoRicerca);

		void AllegaFileAEndoPresente(int idDomanda, int codiceInventario, BinaryFile file, bool verificaFirma);

		void RimuoviAllegatoDaEndo(int idDomanda, int codiceInventario);

		TipiTitoloDto GetTipoTitoloById(int codiceTipoTitolo);

		IEnumerable<EndoprocedimentoIncompatibile> GetEndoprocedimentiIncompatibili(int idDomanda);

        void ImpostaNaturaBaseDomanda(int idDomanda, string naturaBase);
        EndoprocedimentiConsole GetEndoprocedimentiConsoleDaIdIntervento(int idIntervento, string codiceComune);
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

		public IEnumerable<EndoprocedimentoDto> WhereCodiceEndoIn(int idIntervento, string codiceComune, IEnumerable<int> listaId)
		{
			var e = this._endoprocedimentiRepository.GetEndoprocedimentiConsoleDaIdIntervento(idIntervento, codiceComune);
            var endoIntervento = e.Principali.ToList();

            if (e.Richiesti != null)
            {
                endoIntervento.AddRange(e.Richiesti);
            }

            if (e.Ricorrenti != null)
            {
                endoIntervento.AddRange(e.Ricorrenti);
            }

			return EstraiEndoDa(e.Altri, listaId)
					.Union(EstraiEndoDa(endoIntervento, listaId));
		}

		private IEnumerable<EndoprocedimentoDto> EstraiEndoDa(IEnumerable<FamigliaEndoprocedimentoDto> famiglie, IEnumerable<int> listaId)
		{
            if (famiglie == null)
            {
                yield break;
            }

			foreach (var famiglia in famiglie)
			{
				foreach (var tipo in famiglia.TipiEndoprocedimenti)
				{
					foreach (var endo in tipo.Endoprocedimenti)
					{
						if (listaId.Contains(endo.Codice))
						{
							yield return endo;
						}

                        if (endo.SubEndo != null)
                        {
                            var listaSubEndo = endo.SubEndo.SelectMany(x => x.TipiEndoprocedimenti.SelectMany(y => y.Endoprocedimenti));

                            foreach (var subEndo in listaSubEndo)
                            {
                                if (listaId.Contains(subEndo.Codice))
                                {
                                    yield return subEndo;
                                }
                            }
                        }
					}
				}
			}
		}

		public EndoprocedimentiDellaDomandaOnline LeggiEndoprocedimentiDaCodiceIntervento(int codiceIntervento, string codiceComune)
		{
            var endoDellIntervento = this._endoprocedimentiRepository.GetEndoprocedimentiConsoleDaIdIntervento(codiceIntervento, codiceComune);

			return new EndoprocedimentiDellaDomandaOnline(endoDellIntervento);
		}

		public ListaEndoDaIdInterventoDto WhereInterventoIs(int codiceIntervento, string codiceComune)
		{
            var e = this._endoprocedimentiRepository.GetEndoprocedimentiConsoleDaIdIntervento(codiceIntervento, codiceComune);
            var endoIntervento = e.Principali.ToList();

            if (e.Richiesti != null)
            {
                endoIntervento.AddRange(e.Richiesti);
            }

            if (e.Ricorrenti != null)
            {
                endoIntervento.AddRange(e.Ricorrenti);
            }


            return new ListaEndoDaIdInterventoDto
            {
                EndoIntervento = endoIntervento.ToArray(),
                EndoFacoltativi = e.Altri
            };
		}

		public EndoprocedimentoDto GetById(string alias, int codiceEndoprocedimento, AmbitoRicerca ambitoRicerca)
		{
			return this._endoprocedimentiRepository.GetById(alias, codiceEndoprocedimento, ambitoRicerca);
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

        public EndoprocedimentiConsole GetEndoprocedimentiConsoleDaIdIntervento(int idIntervento, string codiceComune)
        {
            return this._endoprocedimentiRepository.GetEndoprocedimentiConsoleDaIdIntervento(idIntervento, codiceComune);
        }
    }
}