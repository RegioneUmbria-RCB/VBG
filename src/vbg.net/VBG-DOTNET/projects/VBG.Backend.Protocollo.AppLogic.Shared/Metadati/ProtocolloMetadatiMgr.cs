using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Metadati
{
    public class ProtocolloMetadatiMgr : BaseManager
    {
        private DataBase _db;

        public ProtocolloMetadatiMgr(DataBase db):base(db)
        {
            this._db = db;
        }

        public void Insert(string idComune, string idProtocollo, List<ProtocolloMetadati> metadati)
        {

            if (metadati?.Count > 0)
            {

                bool closeCnn = false;

                string sqlDelete = "delete from protocollo_metadati where idcomune = {0} and fkidprotocollo = {1} and metadato = {2}";
                string sqlInsert = "insert into protocollo_metadati (idcomune,fkidprotocollo,metadato,valore) values ({0},{1},{2},{3})";

                foreach (var metadato in metadati)
                {

                    try
                    {
                        

                        if (_db.Connection.State == ConnectionState.Closed)
                        {
                            closeCnn = true;
                            _db.Connection.Open();
                        }

                        sqlDelete = PreparaQueryParametrica(sqlDelete, "idcomune", "fkidprotocollo", "metadato");

                        using (IDbCommand cmd = _db.CreateCommand(sqlDelete))
                        {
                            cmd.Parameters.Add(_db.CreateParameter("idcomune", idComune));
                            cmd.Parameters.Add(_db.CreateParameter("fkidprotocollo", idProtocollo));
                            cmd.Parameters.Add(_db.CreateParameter("metadato", metadato.Metadato));
                            cmd.ExecuteNonQuery();
                        }

                        sqlInsert = PreparaQueryParametrica(sqlInsert, "idcomune", "fkidprotocollo", "metadato","valore");
                        using (IDbCommand cmd = _db.CreateCommand(sqlInsert))
                        {
                            cmd.Parameters.Add(_db.CreateParameter("idcomune", idComune));
                            cmd.Parameters.Add(_db.CreateParameter("fkidprotocollo", idProtocollo));
                            cmd.Parameters.Add(_db.CreateParameter("metadato", metadato.Metadato));
                            cmd.Parameters.Add(_db.CreateParameter("valore", metadato.Valore));
                            cmd.ExecuteNonQuery();
                        }
                    }
                    catch (Exception)
                    {
                    }
                    finally
                    {
                        if (closeCnn)
                            _db.Connection.Close();
                    }

                }
            }
        }

        public List<ProtocolloMetadati> GetMetadati(string idComune, string idProtocollo)
        {
            List<ProtocolloMetadati> retVal = new List<ProtocolloMetadati>();

            bool closeCnn = false;

            if (_db.Connection.State == ConnectionState.Closed)
            {
                closeCnn = true;
                _db.Connection.Open();
            }

            string sql = "select metadato, valore from protocollo_metadati where idcomune = {0} and fkidprotocollo = {1}";

            try
            {
                sql = PreparaQueryParametrica(sql, "idcomune", "fkidprotocollo");

                using (IDbCommand cmd = _db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(_db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(_db.CreateParameter("fkidprotocollo", idProtocollo));
                    using (var rd = cmd.ExecuteReader()) 
                    {
                        while (rd.Read())
                        {
                            retVal.Add( new ProtocolloMetadati 
                            { 
                                Metadato = rd["metadato"].ToString(),
                                Valore = rd["valore"].ToString(),
                            } );
                        }
                    }
                }

            }
            finally
            {
                if (closeCnn)
                    _db.Connection.Close();
            }

            return retVal;
        }
    }
}