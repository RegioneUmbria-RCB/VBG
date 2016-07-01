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
    /// File generato automaticamente dalla tabella MERCATIPRESENZE_T il 29/10/2008 10.40.25
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
    [DataTable("MERCATIPRESENZE_T")]
    [Serializable]
    public partial class MercatiPresenzeT : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private string m_software = null;

        private DateTime? m_dataregistrazione = null;

        private string m_descrizione = null;

        private int? m_codiceresponsabile = null;

        private int? m_giornimercato = null;

        private int? m_fkcodicemercato = null;

        private int? m_fkidmercatiuso = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }
        #endregion

        #region Data fields

        [isRequired]
        [DataField("DATAREGISTRAZIONE", Type = DbType.DateTime)]
        public DateTime? Dataregistrazione
        {
            get { return m_dataregistrazione; }
            set { m_dataregistrazione = VerificaDataLocale(value); }
        }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = true, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }
        
        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 1000)]
        public string Descrizione
        {
            get { return m_descrizione; }
            set { m_descrizione = value; }
        }

        [isRequired]
        [DataField("CODICERESPONSABILE", Type = DbType.Decimal)]
        public int? Codiceresponsabile
        {
            get { return m_codiceresponsabile; }
            set { m_codiceresponsabile = value; }
        }

        [DataField("GIORNIMERCATO", Type = DbType.String, CaseSensitive = false, Size = 10)]
        public int? Giornimercato
        {
            get { return m_giornimercato; }
            set { m_giornimercato = value; }
        }

        [DataField("FKCODICEMERCATO", Type = DbType.Decimal)]
        public int? Fkcodicemercato
        {
            get { return m_fkcodicemercato; }
            set { m_fkcodicemercato = value; }
        }

        [DataField("FKIDMERCATIUSO", Type = DbType.Decimal)]
        public int? Fkidmercatiuso
        {
            get { return m_fkidmercatiuso; }
            set { m_fkidmercatiuso = value; }
        }
        #endregion

        #endregion
    }
}
