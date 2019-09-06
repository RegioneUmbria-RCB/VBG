
using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella ISTANZECALCOLOCANONI_T il 11/11/2008 9.19.34
    ///
    ///												ATTENZIONE!!!
    ///	- Specificare manualmente in quali colonne vanno applicate eventuali sequenze		
    /// - Verificare l'applicazione di eventuali attributi di tipo "[isRequired]". In caso contrario applicarli manualmente
    ///	- Verificare che il tipo di dati assegnato alle proprietà sia corretto
    ///
    ///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
    ///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
    /// -
    /// -
    /// -
    /// - 
    ///
    ///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
    ///
    [DataTable("ISTANZECALCOLOCANONI_T")]
    [Serializable]
    public partial class IstanzeCalcoloCanoniT : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_id = null;

        private int? m_codiceistanza = null;

        private string m_descrizione = null;

        private double? m_percaddizregionale = null;

        private double? m_percaddizcomunale = null;

        private int? m_anno = null;

        private int? m_idconfigarea = null;

        private string m_tipocalcolo = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("ANNO", Type = DbType.Decimal)]
        public int? Anno
        {
            get { return m_anno; }
            set { m_anno = value; }
        }

        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        public int? Codiceistanza
        {
            get { return m_codiceistanza; }
            set { m_codiceistanza = value; }
        }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 400)]
        public string Descrizione
        {
            get { return m_descrizione; }
            set { m_descrizione = value; }
        }

        [DataField("PERCADDIZREGIONALE", Type = DbType.Decimal)]
        public double? Percaddizregionale
        {
            get { return m_percaddizregionale; }
            set { m_percaddizregionale = value; }
        }

        [DataField("PERCADDIZCOMUNALE", Type = DbType.Decimal)]
        public double? Percaddizcomunale
        {
            get { return m_percaddizcomunale; }
            set { m_percaddizcomunale = value; }
        }

        [isRequired]
        [DataField("IDCONFIGAREA", Type = DbType.Decimal)]
        public int? Idconfigarea
        {
            get { return m_idconfigarea; }
            set { m_idconfigarea = value; }
        }

        [isRequired]
        [DataField("TIPOCALCOLO", Type = DbType.String)]
        public string Tipocalcolo
        {
            get { return m_tipocalcolo; }
            set { m_tipocalcolo = value; }
        }

        #endregion

        #endregion
    }
}
