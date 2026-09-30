using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.SIGePro.Manager.DTO.Commissioni;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni
{
    public class CommissioniService : ICommissioniService
    {
        private readonly IAuthenticationDataResolver _authDataResolver;
        private readonly ICommissioniDao _dao;

        public CommissioniService(IAuthenticationDataResolver authDataResolver, ICommissioniDao dao)
        {
            this._authDataResolver = authDataResolver;
            this._dao = dao;
        }

        public bool ApprovaAllegatoPerUtenteCorrente(int idCommissione, int idAllegato)
        {
            int codiceAnagrafe = this._authDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value;
            return this._dao.ApprovaAllegato(idCommissione, idAllegato, codiceAnagrafe);
        }



        public IEnumerable<CommissioneTestataDto> GetCommissioniApertePerUtenteCorrente()
        {
            int codiceAnagrafe = this._authDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value;
            return this._dao.GetCommissioniAperteByCodiceAnagrafe(codiceAnagrafe);
        }

        public DettaglioCommissioneDto GetDettaglioCommissionePerUtenteCorrente(int idCommissione)
        {
            int codiceAnagrafe = this._authDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value;
            return this._dao.GetDettaglioCommissione(idCommissione, codiceAnagrafe);
        }

        public PraticaCommissioneEstesaDto GetDettaglioPraticaPerUtenteCorrente(int idCommissione, string uuidIstanza)
        {
            int codiceAnagrafe = this._authDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value;
            return this._dao.GetDettaglioPratica(idCommissione, codiceAnagrafe, uuidIstanza);
        }

        public bool VerificaAccessoAFilePerUtenteCorrente(int idCommissione, string uuidIstanza, int codiceOggetto)
        {
            int codiceAnagrafe = this._authDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value;
            return this._dao.VerificaAccessoAFile(idCommissione, codiceAnagrafe, uuidIstanza, codiceOggetto);
        }

    }
}
