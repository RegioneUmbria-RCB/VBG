using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Pagamenti.Legacy.ESED
{
    public interface IGetStatoPagamento
    {
        string GetDatiPagamento(string numeroOperazione);
    }
}
