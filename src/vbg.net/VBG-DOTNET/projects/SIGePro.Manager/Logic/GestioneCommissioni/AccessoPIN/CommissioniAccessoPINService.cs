using Init.SIGePro.Manager.Logic.GestioneCommissioni.Auditing;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.AccessoPIN
{
    public class CommissioniAccessoPINService
    {
        private readonly ICommissioniAccessoPINDao _dao;
        private readonly ICommissioniAuditingService _commissioniAuditingService;
        private readonly ILog _log = LogManager.GetLogger(typeof(CommissioniAccessoPINService));

        public CommissioniAccessoPINService(ICommissioniAccessoPINDao dao, ICommissioniAuditingService commissioniAuditingService)
        {
            _dao = dao ?? throw new ArgumentNullException(nameof(dao));
            _commissioniAuditingService = commissioniAuditingService ?? throw new ArgumentNullException(nameof(commissioniAuditingService));
        }

        public bool VerificaValiditaPIN(string pin)
        {
            return this._dao.VerificaValiditaPIN(pin);
        }

        public int AssociaUtenteACommissioneByPIN(int codiceAnagrafe, string pin)
        {
            try
            {
                this._log.Debug($"Associazione dell'anagrafica {codiceAnagrafe} con la commissione corrispondente al pin {pin}");

                var riferimentiCommissione = this._dao.AssociaUtenteACommissioneByPIN(codiceAnagrafe, pin);

                this._commissioniAuditingService.UtenteAssociatoACommissione(riferimentiCommissione.IdCommissione, codiceAnagrafe, riferimentiCommissione.IdAmministrazione, pin);

                this._log.Debug($"Associazione dell'anagrafica {codiceAnagrafe} riuscita, " +
                                $"idCommissione={riferimentiCommissione.IdCommissione}, " +
                                $"idAmministrazione={riferimentiCommissione.IdAmministrazione}, " +
                                $"idRigaAppello={riferimentiCommissione.IdAppello}");

                return riferimentiCommissione.IdCommissione;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nell'associazione dell'anagrafica {codiceAnagrafe} con pin {pin}: {ex}");

                throw;
            }
        }
    }
}
