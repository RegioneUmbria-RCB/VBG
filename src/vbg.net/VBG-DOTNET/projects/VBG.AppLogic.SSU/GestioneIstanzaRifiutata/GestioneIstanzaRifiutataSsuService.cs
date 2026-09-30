using Init.Sigepro.FrontEnd.AppLogic.DataAccess;
using VBG.AppLogic.SSU.DataAccess;

namespace VBG.AppLogic.SSU.GestioneIstanzaRifiutata
{
    public class GestioneIstanzaRifiutataSsuService : IGestioneIstanzaRifiutataSsuService
    {
        private readonly FoDomandeRepository _domandeRepository;
        private readonly IDomandeSsuRepository _domandeSsuRepository;

        public GestioneIstanzaRifiutataSsuService(FoDomandeRepository domandeRepository, IDomandeSsuRepository domandeSsuRepository)
        {
            this._domandeRepository = domandeRepository;
            this._domandeSsuRepository = domandeSsuRepository;
        }

        public void RifiutaIstanzaDaIdentificativoDomanda(string identificativoDomanda)
        {
            var idDomanda = this._domandeRepository.GetIdDomandaByIdentificativoDomanda(identificativoDomanda);

            if (idDomanda is null)
            {
                throw new ArgumentException($"Nessuna domanda trovata per l'identificativo {identificativoDomanda}", nameof(identificativoDomanda));

            }
            else
            {
                this._domandeSsuRepository.AggiornaStatoByIdDomanda(idDomanda.Value, StatiDomandaSsuEnum.Rifiutata);
            }
        }
    }
}
