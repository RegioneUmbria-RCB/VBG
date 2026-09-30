using Init.Sigepro.FrontEnd.AppLogic.DataAccess;

namespace VBG.AppLogic.SSU.GestioneRichiesteIntegrazioni
{
    public class GestioneRichiestaIntegrazioniService : IGestioneRichiestaIntegrazioniService
    {
        private readonly FoDomandeRepository _domandeRepository;
        private readonly FoDomandeIntegrazioniRepository _foDomandeIntegrazioniRepository;

        public GestioneRichiestaIntegrazioniService(FoDomandeRepository foDomandeRepository, FoDomandeIntegrazioniRepository foDomandeIntegrazioniRepository)
        {
            this._domandeRepository = foDomandeRepository;
            this._foDomandeIntegrazioniRepository = foDomandeIntegrazioniRepository;
        }

        private void InserisciIntegrazioni(int idDomanda, IEnumerable<SsuRichiestaIntegrazione> integrazioni)
        {
            this._foDomandeIntegrazioniRepository.InserisciRichiestaIntegrazioni(idDomanda, integrazioni);
        }

        public void InserisciRichiestaIntegrazioneDaIdentificativoDomanda(string identificativoDomanda, IEnumerable<SsuRichiestaIntegrazione> integrazioniSsu)
        {
            var idDomanda = this._domandeRepository.GetIdDomandaByIdentificativoDomanda(identificativoDomanda);

            if (idDomanda is null)
            {
                throw new ArgumentException($"Nessuna domanda trovata per l'identificativo {identificativoDomanda}", nameof(identificativoDomanda));

            }
            else
            {
                this.InserisciIntegrazioni(idDomanda.Value, integrazioniSsu);
            }
        }

        public SsuRichiestaIntegrazione GetListaIntegrazioniDaFare(int idDomanda)
        {
            return this._foDomandeIntegrazioniRepository.GetIntegrazioniDaFareByIdDomanda(idDomanda);
        }

        public bool DomandaDaIntegrare(int idDomanda) => this._foDomandeIntegrazioniRepository.CountIntegrazioniByIdDomanda(idDomanda) > 0;

        public void InserisciRichiestaIntegrazione(int idDomanda, SsuRichiestaIntegrazione richiesta)
        {
            throw new NotImplementedException();
        }

        public void MarcaDomandaComeIntegrata(int idDomanda)
        {
            this._foDomandeIntegrazioniRepository.MarcaDomandaComeIntegrata(idDomanda);
        }
    }
}
