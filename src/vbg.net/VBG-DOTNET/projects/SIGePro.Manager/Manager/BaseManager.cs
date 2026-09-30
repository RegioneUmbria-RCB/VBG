using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager
{
    public static class BaseManagerExtensions
    {
        public static int LegacyRecordCount(this DataBase db, string tabella, string campo, IEnumerable<KeyValuePair<string, string>> conditionList)
        {
            var internalOpen = false;
            var sql = String.Format("select count({0}) as conta from {1} ", campo, tabella);
            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    internalOpen = true;
                }

                if (conditionList != null)
                {
                    sql = String.Concat(sql, "where ");

                    var condizioniWhere = conditionList.Select(x => x.Key + " = " + db.Specifics.QueryParameterName(x.Key));

                    sql += condizioniWhere.Count() == 0 ? "1=1" : String.Join(" and ", condizioniWhere.ToArray());
                }

                using (var cmd = db.CreateCommand(sql))
                {
                    conditionList.ToList().ForEach(x => cmd.Parameters.Add(db.CreateParameter(x.Key, x.Value)));

                    var obj = cmd.ExecuteScalar();
                    return (obj == DBNull.Value) ? 0 : Convert.ToInt32(obj.ToString());
                }

            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO NEL METODO MethodBase.GetCurrentMethod(), SQL = {0}", sql), ex);
            }
            finally
            {
                if (internalOpen) db.Connection.Close();
            }
        }
    }

    /// <summary>
    /// Descrizione di riepilogo per BaseManager.
    /// </summary>
    public class BaseManager : IManager
    {
        public BaseManager(DataBase dataBase)
        {
            this.db = dataBase;

            this.RegisterHandlers();

        }

        public virtual void RegisterHandlers() { }

        protected DataBase db { get; private set; }

        #region IManager

        public bool RequiredFieldValidate(DataClass pClass, AmbitoValidazione ambitoValidazione)
        {
            var retVal = false;

            var pValidator = new ClassValidator(pClass);

            retVal = pValidator.RequiredFieldValidator(this.db, ambitoValidazione);

            return retVal;
        }

        #endregion

        #region funzioni pubbliche

        public int recordCount(string tabella, string campo, IEnumerable<KeyValuePair<string, string>> conditionList)
        {
            return this.db.LegacyRecordCount(tabella, campo, conditionList);
        }
        #endregion

        #region utilità

        protected int FindMax(string nomeCampo, string nomeTabella, string idComune, List<KeyValuePair<string, object>> condizioneWhere)
        {
            return this.FindMax(nomeCampo, nomeTabella, idComune, 1, condizioneWhere);
        }

        protected int FindMax(string nomeCampo, string nomeTabella, string idComune, int incremento, List<KeyValuePair<string, object>> condizioneWhere)
        {
            var provider = this.db.Specifics;
            var sql = $"select {provider.MaxFunction(nomeCampo)} from {nomeTabella}";
            var where = new StringBuilder();

            if (!string.IsNullOrEmpty(idComune))
                where.Append($" where IDCOMUNE='{idComune}'");

            condizioneWhere ??= new List<KeyValuePair<string, object>>();

            foreach (var filtro in condizioneWhere)
            {
                if (where.Length > 0)
                {
                    where.Append(" and ");
                }
                else
                {
                    where.Append(" where ");
                }

                where.Append($"{filtro.Key} = {provider.QueryParameterName(filtro.Key)}");
            }

            sql += where;

            var closeCnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                closeCnn = true;
                this.db.Connection.Open();
            }
            try
            {
                using (var cmd = this.db.CreateCommand())
                {
                    cmd.CommandText = sql;

                    foreach (var filtro in condizioneWhere)
                    {
                        var par = cmd.CreateParameter();
                        par.ParameterName = filtro.Key;
                        par.Value = filtro.Value;

                        cmd.Parameters.Add(par);
                    }

                    var val = cmd.ExecuteScalar();

                    if (val == null || val == DBNull.Value)
                        return incremento;

                    return Convert.ToInt32(val) + incremento;
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        #endregion

        /// <summary>
        /// Prepara una query parametrica utilizzando i nomi dei parametri passati
        /// </summary>
        /// <example>
        /// string s = PreparaParametriQuery("Select * from t where a={0} and b={1}","primo","secondo");
        /// </example>
        /// <param name="sql">Query con segnaposto di sostituzione in cui inserire i parametri</param>
        /// <param name="nomiParametri">Lista di nomi di parametri da riportare nella query in base alle specifiche del db</param>
        /// <returns>Espressione sql con i nomi dei paramtri al posto dei segnaposto</returns>
        protected string PreparaQueryParametrica(string sql, params string[] nomiParametri)
        {
            for (var i = 0; i < nomiParametri.Length; i++)
                nomiParametri[i] = this.db.Specifics.QueryParameterName(nomiParametri[i]);

            return String.Format(sql, nomiParametri);
        }

        private bool CheckConnectionState()
        {
            if (this.db.Connection.State == ConnectionState.Closed)
            {
                this.db.Connection.Open();
                return true;
            }

            return false;
        }

        protected T ExecuteInConnection<T>(Func<T> function)
        {
            var shouldClose = this.CheckConnectionState();

            try
            {
                return function();
            }
            finally
            {
                if (shouldClose)
                {
                    this.db.Connection.Close();
                }
            }
        }
    }
}
