
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella I_ATTIVITADYN2DATI il 26/10/2010 12.24.12
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
    [DataTable("I_ATTIVITADYN2DATI")]
    [Serializable]
    public partial class IAttivitaDyn2Dati : BaseDataClass, IValoreCampo
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_fk_ia_id = null;

        private int? m_fk_d2c_id = null;

        private string m_valore = null;

        private int? m_indice = null;

        private string m_valoredecodificato = null;

        private int? m_indice_molteplicita = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }

        [KeyField("FK_IA_ID", Type = DbType.Decimal)]
        public int? FkIaId
        {
            get { return this.m_fk_ia_id; }
            set { this.m_fk_ia_id = value; }
        }

        [KeyField("FK_D2C_ID", Type = DbType.Decimal)]
        public int? FkD2cId
        {
            get { return this.m_fk_d2c_id; }
            set { this.m_fk_d2c_id = value; }
        }

        [KeyField("INDICE", Type = DbType.Decimal)]
        public int? Indice
        {
            get { return this.m_indice; }
            set { this.m_indice = value; }
        }

        [KeyField("INDICE_MOLTEPLICITA", Type = DbType.Decimal)]
        public int? IndiceMolteplicita
        {
            get { return this.m_indice_molteplicita; }
            set { this.m_indice_molteplicita = value; }
        }


        #endregion

        #region Data fields

        [DataField("VALORE", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        public string Valore
        {
            get { return this.m_valore; }
            set { this.m_valore = value; }
        }

        [DataField("VALOREDECODIFICATO", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        public string Valoredecodificato
        {
            get { return this.m_valoredecodificato; }
            set { this.m_valoredecodificato = value; }
        }

        #endregion

        #endregion
    }
}
