using VBG.AppLogic.SSU.DataAccess;

namespace VBG.AppLogic.SSU.OperazioniPostInvio
{
    public class SsuOperazioniPostInvioService
    {
        private readonly IDomandeSsuRepository _domandeSsuRepository;
        private readonly FoDomandeCorrezioniRepository _foDomandeCorrezioniRepository;

        public SsuOperazioniPostInvioService(IDomandeSsuRepository domandeSsuRepository, FoDomandeCorrezioniRepository foDomandeCorrezioniRepository)
        {
            this._domandeSsuRepository = domandeSsuRepository;
            this._foDomandeCorrezioniRepository = foDomandeCorrezioniRepository;
        }

        public void MarcaDomandaComePresentata(int idDomanda, string codiceDomandaSsu, string numeroDomandaSsu, string istatEnte)
        {
            this._domandeSsuRepository.MarcaDomandaComePresentata(idDomanda, codiceDomandaSsu, numeroDomandaSsu, istatEnte);
        }

        public void MarcaDomandaComeCorretta(int idDomanda)
        {
            this._foDomandeCorrezioniRepository.EliminaCorrezioniByIdDomanda(idDomanda);
        }
    }
}
