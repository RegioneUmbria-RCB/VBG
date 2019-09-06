using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Manager
{
    public class BaseManager2
    {
        public class CommandParameter
        {
            public readonly string Name;
            public readonly object Value;

            public CommandParameter(string name, object value)
            {
                this.Name = name;
                this.Value = value;
            }
        }


        protected readonly DataBase Database;
        protected readonly string  IdComune;

        protected BaseManager2(DataBase db, string idComune)
        {
            this.Database = db;
            this.IdComune = idComune;
        }

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
            for (int i = 0; i < nomiParametri.Length; i++)
                nomiParametri[i] = this.Database.Specifics.QueryParameterName(nomiParametri[i]);

            return String.Format(sql, nomiParametri);
        }

        public bool CheckConnectionState()
        {
            if (Database.Connection.State == ConnectionState.Closed)
            {
                Database.Connection.Open();
                return true;
            }

            return false;
        }

        protected IDbCommand CreateCommand(string sql)
        {
            return this.Database.CreateCommand(sql);
        }

        protected IDbCommand CreateCommand(string sql, IEnumerable<CommandParameter> parameters)
        {
            var cmd = this.Database.CreateCommand(sql);

            foreach(var p in parameters)
            {
                CreateAndAddParameter(cmd, p.Name, p.Value);
            }

            return cmd;
        }

        protected IDbDataParameter CreateAndAddParameter(IDbCommand cmd, string nome, object valore)
        {
            var par = this.Database.CreateParameter(nome, valore);

            cmd.Parameters.Add(par);

            return par;
        }

        protected void CloseIfNeeded(bool closeCnn)
        {
            if (!closeCnn)
            {
                return;
            }

            this.Database.Connection.Close();
        }

        protected T ExecuteInConnection<T>(Func<T> function)
        {
            var shouldClose = CheckConnectionState();

            try
            {
                return function();
            }
            finally
            {
                if (shouldClose)
                {
                    Database.Connection.Close();
                }
            }
        }

    }
}
