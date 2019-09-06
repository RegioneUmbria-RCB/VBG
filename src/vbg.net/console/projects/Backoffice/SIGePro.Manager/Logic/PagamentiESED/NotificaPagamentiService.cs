using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.PagamentiESED
{
    public class NotificaPagamentiService : INotificaPagamentiService
    {
        string _idComune;
        DataBase _db;

        public NotificaPagamentiService(string idComune, DataBase db)
        {
            _idComune = idComune;
            _db = db;
        }

        public void Inserisci(string messaggioXml, string numeroOperazione, string idDomanda, string esito, string data, string idOrdine, string idTransazione, string tipoPagamento)
        {
            var notificaPagamenti = new NotificaPagamenti
            {
                Data = data,
                Esito = esito,
                IdComune = _idComune,
                IdDomanda = idDomanda,
                Messaggio = messaggioXml,
                NumeroOperazione = numeroOperazione,
                IdOrdine = idOrdine,
                IdTransazione = idTransazione,
                TipoPagamento = tipoPagamento
            };

            _db.Insert(notificaPagamenti);
        }

        public DatiNotificaPagamenti GetNotificaPagamentiByKey(string numeroOperazione)
        {
            var sql = String.Format("SELECT IDDOMANDA, MESSAGGIO, ESITO, DATA, IDCOMUNE, NUMEROOPERAZIONE, IDORDINE, IDTRANSAZIONE, TIPOPAGAMENTO FROM NOTIFICA_PAGAMENTI WHERE IDCOMUNE = {0} AND NUMEROOPERAZIONE = {1}", this._db.Specifics.QueryParameterName("idComune"), this._db.Specifics.QueryParameterName("numeroOperazione"));

            using (var cmd = this._db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this._db.CreateParameter("idComune", this._idComune));
                cmd.Parameters.Add(this._db.CreateParameter("numeroOperazione", numeroOperazione));

                var notificaPagamenti = _db.GetClass<NotificaPagamenti>(cmd);

                if (notificaPagamenti == null)
                {
                    return null;
                }

                return new DatiNotificaPagamenti(notificaPagamenti.IdDomanda, notificaPagamenti.Messaggio, notificaPagamenti.Esito, notificaPagamenti.Data, notificaPagamenti.IdComune, notificaPagamenti.NumeroOperazione, notificaPagamenti.IdOrdine, notificaPagamenti.IdTransazione, notificaPagamenti.TipoPagamento);
            }
        }
    }
}
