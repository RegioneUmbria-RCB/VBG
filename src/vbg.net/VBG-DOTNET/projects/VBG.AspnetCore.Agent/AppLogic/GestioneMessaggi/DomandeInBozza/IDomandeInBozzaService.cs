using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi.DomandeInBozza
{
    public interface IDomandeInBozzaService
    {
        Task EliminaDomandaAsync(string alias, int IdDomanda);
    }
}
