using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.PagamentiESED
{
    interface INotificaPagamentiService
    {
        void Inserisci(string messaggioXml, string numeroOperazione, string idDomanda, string esito, string data, string idOrdine, string idTransazione, string tipoPagamento);
        DatiNotificaPagamenti GetNotificaPagamentiByKey(string numeroOperazione);
    }
}
