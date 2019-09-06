using System.Collections.Generic;
using Init.SIGePro.Manager.DTO;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneModalitaPagamento
{
    public interface IModalitaPagamentoConsoleService
    {
        IEnumerable<BaseDto<string, string>> GetModalitaPagamento();
    }
}