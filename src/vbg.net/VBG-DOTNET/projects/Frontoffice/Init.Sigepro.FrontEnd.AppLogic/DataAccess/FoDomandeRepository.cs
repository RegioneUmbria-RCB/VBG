using System;

namespace Init.Sigepro.FrontEnd.AppLogic.DataAccess
{
    public class FoDomandeRepository
    {
        private readonly DbConnectionFactory _connectionFactory;

        public FoDomandeRepository(DbConnectionFactory connectionFactory)
        {
            this._connectionFactory = connectionFactory;
        }

        public int? GetIdDomandaByIdentificativoDomanda(string identificativoDomanda)
        {
            using var db = this._connectionFactory.CreateDatabase();

            FormattableString sql = $@"
                SELECT ID
                FROM FO_DOMANDE
                WHERE 
                    IDCOMUNE={this._connectionFactory.IdComune} AND
                    IDENTIFICATIVODOMANDA = {identificativoDomanda}";

            var id = db.ExecuteScalar(sql, -1);

            return id == -1 ? (int?)null : id;
        }
    }
}
