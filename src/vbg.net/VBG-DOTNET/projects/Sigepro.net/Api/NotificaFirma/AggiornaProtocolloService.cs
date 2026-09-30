using log4net;
using PersonalLib2.Data;
using System;
using System.Linq;

namespace Sigepro.net.Api.NotificaFirma
{
    public class AggiornaProtocolloService
    {
        private readonly IDatabase _db;
        private readonly ILog _logger;
        public AggiornaProtocolloService(ILog logger, IDatabase db)
        {
            this._logger = logger;
            this._db = db;
        }

        public void AggiornaRiferimentiProtocollo(string idComune, string software, string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo)
        {
            var codiceMovimento = this.CodiceMovimentoDaIdProtocollo(idComune, software, idProtocollo);
            this._logger.Debug($"Protocollo trovato nel movimento {codiceMovimento}");

            if (codiceMovimento.HasValue)
            {
                this.AggiornaProtocolloMovimento(idComune, codiceMovimento.Value, idProtocollo, numeroProtocollo, dataProtocollo);
            }

            var codiceIstanza = this.CodiceIstanzaDaIdProtocollo(idComune, software, idProtocollo);
            this._logger.Debug($"Protocollo trovato nell'istanza {codiceIstanza}");

            if (codiceIstanza.HasValue)
            {
                this.AggiornaProtocolloIstanza(idComune, codiceIstanza.Value, idProtocollo, numeroProtocollo, dataProtocollo);
                return;
            }

            if (!codiceMovimento.HasValue)
            {
                throw new Exception($"Impossibile trovare il riferimento del protocollo passato ({idProtocollo}) all'interno del backoffice");
            }
        }

        private int? CodiceMovimentoDaIdProtocollo(string idComune, string software, string idProtocollo)
        {
            string sql = $@"select 
							movimenti.codicemovimento
						from 
                            movimenti
                                inner join istanze on 
                                    movimenti.idcomune = istanze.idcomune and
                                    movimenti.codiceistanza = istanze.codiceistanza and
                                    istanze.software = {this._db.Specifics.QueryParameterName("software")}
						where
							movimenti.idcomune = {this._db.Specifics.QueryParameterName("idComune")} and
							movimenti.fkidprotocollo = {this._db.Specifics.QueryParameterName("idProtocollo")} and
							movimenti.numeroprotocollo is null and
                            movimenti.dataprotocollo is null";

            var movimenti = this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("software", software);
                    mp.AddParameter("idComune", idComune);
                    mp.AddParameter("idProtocollo", idProtocollo);
                },
                dr => dr.GetInt("codicemovimento").Value);

            if (movimenti.Count() > 1)
            {
                throw new Exception($"Impossibile identificare univocamente il movimento da aggiornare. Esistono più movimenti legati a fkidprotocollo {idProtocollo}");
            }

            if (!movimenti.Any())
            {
                return (int?)null;
            }

            return movimenti.First();
        }

        private void AggiornaProtocolloMovimento(string idComune, int codiceMovimento, string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo)
        {
            string sql = $@"update 
							    movimenti
						    set 
                                numeroprotocollo = {this._db.Specifics.QueryParameterName("numeroProtocollo")},
                                dataprotocollo = {this._db.Specifics.QueryParameterName("dataProtocollo")}
						    where
							    idcomune = {this._db.Specifics.QueryParameterName("idComune")} and
							    codicemovimento = {this._db.Specifics.QueryParameterName("codiceMovimento")} and 
                                fkidprotocollo = {this._db.Specifics.QueryParameterName("idProtocollo")}";

            this._db.ExecuteNonQuery(sql,
                 mp =>
                 {
                     mp.AddParameter("numeroProtocollo", numeroProtocollo);
                     mp.AddParameter("dataProtocollo", dataProtocollo);
                     mp.AddParameter("idComune", idComune);
                     mp.AddParameter("codiceMovimento", codiceMovimento);
                     mp.AddParameter("idProtocollo", idProtocollo);
                 });
        }

        private int? CodiceIstanzaDaIdProtocollo(string idComune, string software, string idProtocollo)
        {
            string sql = $@"select 
							istanze.codiceistanza
						from 
                            istanze
						where
							istanze.idcomune = {this._db.Specifics.QueryParameterName("idComune")} and
                            istanze.software = {this._db.Specifics.QueryParameterName("software")} and
							istanze.fkidprotocollo = {this._db.Specifics.QueryParameterName("idProtocollo")} and
							istanze.numeroprotocollo is null and
                            istanze.dataprotocollo is null";

            var istanze = this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", idComune);
                    mp.AddParameter("software", software);
                    mp.AddParameter("idProtocollo", idProtocollo);
                },
                dr => dr.GetInt("codiceistanza").Value);

            if (istanze.Count() > 1)
            {
                throw new Exception($"Impossibile identificare univocamente l'istanza da aggiornare. Esistono più istanze legate a fkidprotocollo {idProtocollo}");
            }

            if (!istanze.Any())
            {
                return (int?)null;
            }

            return istanze.First();
        }

        private void AggiornaProtocolloIstanza(string idComune, int codiceIstanza, string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo)
        {
            string sql = $@"update 
							    istanze
						    set 
                                numeroprotocollo = {this._db.Specifics.QueryParameterName("numeroProtocollo")},
                                dataprotocollo = {this._db.Specifics.QueryParameterName("dataProtocollo")}
						    where
							    idcomune = {this._db.Specifics.QueryParameterName("idComune")} and
							    codiceistanza = {this._db.Specifics.QueryParameterName("codiceIstanza")} and 
                                fkidprotocollo = {this._db.Specifics.QueryParameterName("idProtocollo")}";

            this._db.ExecuteNonQuery(sql,
                 mp =>
                 {
                     mp.AddParameter("numeroProtocollo", numeroProtocollo);
                     mp.AddParameter("dataProtocollo", dataProtocollo);
                     mp.AddParameter("idComune", idComune);
                     mp.AddParameter("codiceIstanza", codiceIstanza);
                     mp.AddParameter("idProtocollo", idProtocollo);
                 });
        }
    }
}