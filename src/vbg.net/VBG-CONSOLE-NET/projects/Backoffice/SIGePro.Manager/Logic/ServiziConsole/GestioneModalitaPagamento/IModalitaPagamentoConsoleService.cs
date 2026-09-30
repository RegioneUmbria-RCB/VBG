using System.Collections.Generic;
using Init.SIGePro.Manager.DTO;

namespace Init.SIGePro.Manager.Logic.ServiziConsole.GestioneModalitaPagamento
{
    public interface IModalitaPagamentoConsoleService
    {
        IEnumerable<BaseDto<string, string>> GetModalitaPagamento();
    }
}