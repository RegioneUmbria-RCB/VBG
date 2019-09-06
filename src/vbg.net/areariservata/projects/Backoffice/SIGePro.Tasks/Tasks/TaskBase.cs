using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using System.Collections.Specialized;
using System.Globalization;

namespace SIGePro.Tasks
{
    /// <summary>
    /// Rappresenta un task del sistema SIGePro
    /// </summary>
    public class TaskBase
    {
        private string m_idComune = String.Empty;
        private int? m_idjob = null;
        private NameValueCollection m_parametri = null;

        /// <summary>
        /// Hashtable di coppie chiave / valore di tutti i parametri del task
        /// <code>string mioValore = Task.Parametri["NOMEPARAMETRO"]</code>
        /// </summary>
        [XmlIgnore]
        public NameValueCollection Parametri
        {
            get { return m_parametri; }
        }

        /// <summary>
        /// IdComune del task
        /// </summary>
        public string IdComune
        {
            get { return m_idComune; }
            set { m_idComune = value; }
        }

        /// <summary>
        /// Id del task
        /// </summary>
        public int? IdJob
        {
            get { return m_idjob; }
            set { m_idjob = value; }
        }

        protected TaskBase() { }

        		/// <summary>
		/// Istanzia un nuovo task. Utilizzando i parametri passati
		/// </summary>
        public TaskBase(DataBase db, string idComune, int idjob)
		{
            IdComune = idComune;
            IdJob = idjob;

			Read( db );
		}

        /// <summary>
        /// Ottiene un valore DATE da un parametro. Se il parametro è null ritorna null
        /// </summary>
        /// <param name="paramName">Nome del parametro</param>
        /// <returns>Valore del parametro</returns>
        public DateTime? GetDate(string paramName)
        {
            if (Parametri == null) return null;
            if (Parametri[paramName] == null) return null;

            return DateTime.ParseExact(Parametri[paramName], "dd/MM/yyyy", null);
        }

        /// <summary>
        /// Imposta un valore DateTime in un parametro.
        /// </summary>
        /// <param name="paramName">Nome del parametro</param>
        /// <param name="value">Valore del parametro</param>
        public void SetDate(string paramName, DateTime value)
        {
            if (Parametri == null) return;
            Parametri[paramName] = value.ToString("dd/MM/yyyy");
        }

        /// <summary>
        /// Ottiene un valore string da un parametro. Se il parametro è null ritorna String.Empty
        /// </summary>
        /// <param name="paramName">Nome del parametro</param>
        /// <returns>Valore del parametro</returns>
        public string GetString(string paramName)
        {
            if (Parametri == null) return String.Empty;
            if (Parametri[paramName] == null) return String.Empty;
            return Parametri[paramName];
        }

        /// <summary>
        /// Imposta un valore string in un parametro.
        /// </summary>
        /// <param name="paramName">Nome del parametro</param>
        /// <param name="value">Valore del parametro</param>
        public void SetString(string paramName, string value)
        {
            if (Parametri == null) return;
            Parametri[paramName] = value;
        }

        /// <summary>
        /// Ottiene un valore Int32 da un parametro. Se il parametro è null ritorna int.MinValue
        /// </summary>
        /// <param name="paramName">Nome del parametro</param>
        /// <returns>Valore del parametro</returns>
        public Int32? GetInt(string paramName)
        {
            if (Parametri == null) return null;
            if (Parametri[paramName] == null) return null;
            return Convert.ToInt32( Parametri[paramName] );
        }

        /// <summary>
        /// Imposta un valore Int32 in un parametro.
        /// </summary>
        /// <param name="paramName">Nome del parametro</param>
        /// <param name="value">Valore del parametro</param>
        public void SetInt(string paramName, int value)
        {
            if (Parametri == null) return;
            Parametri[paramName] = value.ToString();
        }

        /// <summary>
        /// Ottiene un valore double da un parametro. Se il parametro è null ritorna null
        /// </summary>
        /// <param name="paramName">Nome del parametro</param>
        /// <returns>Valore del parametro</returns>
        public double? GetDouble(string paramName)
        {
            if (Parametri == null) return null;
            if (Parametri[paramName] == null) return null;
            return Convert.ToDouble(Parametri[paramName]);
        }

        /// <summary>
        /// Imposta un valore double in un parametro.
        /// </summary>
        /// <param name="paramName">Nome del parametro</param>
        /// <param name="value">Valore del parametro</param>
        public void SetDouble(string paramName, double value)
        {
            if (Parametri == null) return;
            Parametri[paramName] = value.ToString();
        }

        /// <summary>
        /// Legge i parametri del task
        /// </summary>
        protected void Read(DataBase db)
        {
            TaskScheduler task = new TaskScheduler();
            task.Idcomune = IdComune;
            task.Idjob = IdJob;

            task = (TaskScheduler)db.GetClass(task);

            if (task == null)
            {
                m_parametri = null;
                return;
            }

            m_parametri = new NameValueCollection();

            FillParametri(db, task.Idjob.GetValueOrDefault(int.MinValue));
        }

        private void FillParametri(DataBase db, int fkidjob)
        {
            TaskSchedulerParametri filtriParametri = new TaskSchedulerParametri();
            filtriParametri.Idcomune = IdComune;
            filtriParametri.Fkidjob = fkidjob;

            List<TaskSchedulerParametri> listaParametri = db.GetClassList(filtriParametri).ToList<TaskSchedulerParametri>();

            foreach (TaskSchedulerParametri parametro in listaParametri)
            {
                string chiave = parametro.Parametro;
                string valore = parametro.Valore;

                m_parametri[chiave] = valore;
            }
        }
    }
}
