using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using log4net;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.AccessoPIN
{
    public class AccessoPINService : IAccessoPINService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(AccessoPINService));
        private readonly IAuthenticationDataResolver _authDataResolver;
        private readonly IAccessoPINDao _dao;

        public AccessoPINService(IAuthenticationDataResolver authDataResolver, IAccessoPINDao dao)
        {
            this._authDataResolver = authDataResolver ?? throw new ArgumentNullException(nameof(authDataResolver));
            this._dao = dao ?? throw new ArgumentNullException(nameof(dao));
        }

        public int AssociaUtenteCorrenteACommissioneByPIN(string pin)
        {
            var datiUtente = this._authDataResolver.DatiAutenticazione.DatiUtente;
            var codiceAnagrafe = datiUtente.Codiceanagrafe.Value;
            var nomeUtente = $"{datiUtente.Nominativo} {datiUtente.Nome} ({codiceAnagrafe})";

            try
            {
                this._log.Debug($"Inizio associazione dell utente {nomeUtente} all'amministrazione con PIN {pin}");

                var idCommissione = this._dao.AssociaUtenteACommissioneByPIN(codiceAnagrafe, pin);

                this._log.Debug($"Associazione dell'utente {nomeUtente} riuscita. Id commissione: {idCommissione}");

                return idCommissione;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante l'associazione dell'utente {nomeUtente} tramite pin {pin}: {ex}");

                throw;
            }
        }

        public bool VerificaValiditaPIN(string pin)
        {
            try
            {
                this._log.Debug($"Inizio verifica della validità del pin {pin}");

                var valido = this._dao.VerificaValiditaPIN(pin);

                this._log.Debug($"Pin {pin} verificato, esito {valido}");

                return valido;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante la verifica del pin {pin}: {ex}");

                return false;
            }
        }
    }
}
