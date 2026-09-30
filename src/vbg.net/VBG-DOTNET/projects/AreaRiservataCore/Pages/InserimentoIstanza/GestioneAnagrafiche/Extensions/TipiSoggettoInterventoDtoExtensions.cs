using Init.SIGePro.Manager.DTO.TipiSoggetto;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche.Extensions
{
    public static class TipiSoggettoInterventoDtoExtensions
    {
        public static IEnumerable<TipoSoggettoDto> GetSoggettiObbligatori(this TipiSoggettoInterventoDto? soggetti)
        {
            if (soggetti is null)
            {
                return Enumerable.Empty<TipoSoggettoDto>();
            }

            var soggettiObbligatoriPF = soggetti.PersoneFisiche?.Where(x => x.Richiesto) ?? Enumerable.Empty<TipoSoggettoDto>();
            var soggettiObbligatoriPG = soggetti.PersoneGiuridiche?.Where(x => x.Richiesto) ?? Enumerable.Empty<TipoSoggettoDto>();

            var soggettiObbligatori = soggettiObbligatoriPF.Union(soggettiObbligatoriPG).Distinct();

            return soggettiObbligatori;
        }

        public static TipoSoggettoDto? GetById(this TipiSoggettoInterventoDto? soggetti, int id)
        {
            if (soggetti is null)
            {
                return null;
            }

            var soggetto = soggetti.PersoneFisiche.FirstOrDefault(x => x.Id.GetValueOrDefault(-1) == id);

            if (soggetto != null)
            {
                return soggetto;
            }

            soggetto = soggetti.PersoneGiuridiche.FirstOrDefault(x => x.Id.GetValueOrDefault(-1) == id);

            return soggetto;
        }
    }
}
