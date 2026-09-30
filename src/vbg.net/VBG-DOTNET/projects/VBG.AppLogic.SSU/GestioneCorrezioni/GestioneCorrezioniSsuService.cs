using Init.Sigepro.FrontEnd.AppLogic.DataAccess;
using VBG.AppLogic.SSU.DataAccess;

namespace VBG.AppLogic.SSU.GestioneCorrezioni
{

    public class GestioneCorrezioniSsuService : IGestioneCorrezioniSsuService
    {
        private readonly FoDomandeCorrezioniRepository _domandeCorrezioniRepository;
        private readonly FoDomandeRepository _domandeRepository;

        public GestioneCorrezioniSsuService(FoDomandeCorrezioniRepository foDomandeCorrezioniRepository, FoDomandeRepository foDomandeRepository)
        {
            this._domandeCorrezioniRepository = foDomandeCorrezioniRepository;
            this._domandeRepository = foDomandeRepository;
        }

        public bool DomandaDaCorreggere(int idDomanda) => this._domandeCorrezioniRepository.CountCorrezioniByIdDomanda(idDomanda) > 0;

        public IEnumerable<CorrezioneDomandaSsu> GetListaCorrezioniDaFare(int idDomanda)
        {
            return this._domandeCorrezioniRepository.GetCorrezioniDaFareByIdDomanda(idDomanda);
        }

        private void InserisciCorrezioni(int idDomanda, IEnumerable<SsuProcedimentoCorrezione> correzioni)
        {
            this._domandeCorrezioniRepository.InserisciCorrezioni(idDomanda, correzioni);
        }

        public void InserisciCorrezioniDaIdentificativoDomanda(string identificativoDomanda, IEnumerable<SsuProcedimentoCorrezione> correzioniSsu)
        {
            var idDomanda = this._domandeRepository.GetIdDomandaByIdentificativoDomanda(identificativoDomanda);

            if (idDomanda is null)
            {
                throw new ArgumentException($"Nessuna domanda trovata per l'identificativo {identificativoDomanda}", nameof(identificativoDomanda));

            }
            else
            {
                this.InserisciCorrezioni(idDomanda.Value, correzioniSsu);
            }
        }
    }
}