using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.EndoAcquisiti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.SIGePro.Manager.DTO.Common;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
    public class EndoprocedimentiService : IEndoprocedimentiService, IEndoAcquisitiService
    {
        public static class Constants
        {
            public const string StrutturaSubEndoKey = "EndoprocedimentiService.StrutturaSubEndo";
        }

        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IEndoprocedimentiRepository _endoprocedimentiRepository;
        private readonly AllegatiDomandaService _allegatiRepository;
        private readonly IEndoprocedimentiIncompatibiliService _endoprocedimentiIncompatibiliService;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        public EndoprocedimentiService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IEndoprocedimentiRepository endoprocedimentiRepository, AllegatiDomandaService allegatiRepository, IEndoprocedimentiIncompatibiliService endoprocedimentiIncompatibiliService, IAuthenticationDataResolver authenticationDataResolver)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._endoprocedimentiRepository = endoprocedimentiRepository;
            this._allegatiRepository = allegatiRepository;
            this._endoprocedimentiIncompatibiliService = endoprocedimentiIncompatibiliService;
            this._authenticationDataResolver = authenticationDataResolver;
        }

        #region IEndoprocedimentiService Members

        public void SalvaEndoSelezionati(int idDomanda, IEnumerable<SubEndoprocedimentoSelezionato> subEndo)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Endoprocedimenti.AggiungiESincronizza(subEndo, new LogicaSincronizzazioneSubEndo(domanda, this, this));

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void SalvaEndoAcquisiti(int idDomanda, IEnumerable<DatiEndoprocedimentoPresente> listaEndoUtente)
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

        public async Task SalvaEndoAcquisitiAsync(int idDomanda, IEnumerable<DatiEndoprocedimentoPresente> listaEndoUtente)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

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

            await this._salvataggioDomandaStrategy.SalvaAsync(domanda);
        }

        public IEnumerable<string> VerificaCorrettezzaListaEndoAcquisiti(IEnumerable<DatiEndoprocedimentoPresente> listaEndoPresenti)
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

        public bool AlmenoUnEndoPresente(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return domanda.ReadInterface.Endoprocedimenti.Endoprocedimenti.Any();
        }

        public Dictionary<int, IEnumerable<TipiTitoloDto>> TipiTitoloWhereCodiceInventarioIn(IEnumerable<int> codiciInventario)
        {
            return this._endoprocedimentiRepository.TipiTitoloWhereCodiceInventarioIn(codiciInventario.ToList());
        }

        public EndoprocedimentiAreaRiservataDto GetListaEndoDaIdIntervento(string codiceComune, int codIntervento)
        {
            var utenteTester = this._authenticationDataResolver.IsAuthenticated ?
                                    this._authenticationDataResolver.DatiAutenticazione?.DatiUtente.UtenteTester :
                                    false;

            return this._endoprocedimentiRepository.GetListaEndoDaIdIntervento(codiceComune, codIntervento, utenteTester.GetValueOrDefault(false));
        }

        public EndoprocedimentoDto GetById(int codiceEndoprocedimento, AmbitoRicerca ambitoRicerca = AmbitoRicerca.AreaRiservata)
        {
            return this._endoprocedimentiRepository.GetById(codiceEndoprocedimento, ambitoRicerca);
        }

        #endregion

        public void AllegaFileAEndoAcquisito(int idDomanda, int codiceInventario, BinaryFile file, bool richiedeFirmaDigitale)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var esitoUpload = this._allegatiRepository.AllegaADomandaSenzaSalvare(domanda, file, richiedeFirmaDigitale);

            domanda.WriteInterface.Endoprocedimenti.AllegaAdEndoPresente(codiceInventario, esitoUpload.IdAllegato);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void AllegaFileAEndoAcquisito(int idDomanda, int codiceInventario, int? codiceOggetto)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            if (codiceOggetto.HasValue)
            {
                var esitoUpload = this._allegatiRepository.AllegaADomandaSenzaSalvare(domanda, codiceOggetto.Value);

                domanda.WriteInterface.Endoprocedimenti.AllegaAdEndoPresente(codiceInventario, esitoUpload.IdAllegato);
            }
            else
            {
                domanda.WriteInterface.Endoprocedimenti.RimuoviAllegatoDaEndoPresente(codiceInventario);
            }

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public async Task AllegaFileAEndoAcquisitoAsync(int idDomanda, int codiceInventario, int? codiceOggetto)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            if (codiceOggetto.HasValue)
            {
                var esitoUpload = this._allegatiRepository.AllegaADomandaSenzaSalvare(domanda, codiceOggetto.Value);

                domanda.WriteInterface.Endoprocedimenti.AllegaAdEndoPresente(codiceInventario, esitoUpload.IdAllegato);
            }
            else
            {
                domanda.WriteInterface.Endoprocedimenti.RimuoviAllegatoDaEndoPresente(codiceInventario);
            }

            await this._salvataggioDomandaStrategy.SalvaAsync(domanda);
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

            return this._endoprocedimentiRepository.GetTipoTitoloById(codiceTipoTitolo);
        }

        public IEnumerable<EndoprocedimentoIncompatibile> GetEndoprocedimentiIncompatibili(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return domanda.ReadInterface.Endoprocedimenti.GetEndoprocedimentiIncompatibili(this._endoprocedimentiIncompatibiliService);
        }

        /*
        public string GetNaturaBaseDaidEndoprocedimento(int codiceInventario)
        {
            return this._endoprocedimentiIncompatibiliService.GetNaturaBaseDaidEndoprocedimento(codiceInventario);
        }
		*/

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

        public StrutturaSubEndo GetSubEndoSelezionati(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var struttura = domanda.ReadInterface.DatiExtra.Get<StrutturaSubEndo>(Constants.StrutturaSubEndoKey);

            return struttura ?? new StrutturaSubEndo();
        }
    }
}
