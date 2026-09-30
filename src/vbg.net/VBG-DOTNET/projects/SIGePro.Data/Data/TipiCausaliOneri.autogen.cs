
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella TIPICAUSALIONERI il 23/01/2009 16.03.12
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
    [DataTable("TIPICAUSALIONERI")]
    [Serializable]
    [DataContract]
    public partial class TipiCausaliOneri : BaseDataClass
    {
        #region Membri privati

        private int? m_co_id = null;

        private string m_co_descrizione = null;

        private string m_co_serichiedeendo = null;

        private string m_idcomune = null;

        private string m_software = null;

        private int? m_fk_rco_id = null;

        private int? m_co_disabilitato = null;

        private int? m_co_ordinamento = null;

        private int? m_pagamentiregulus = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("CO_ID", Type = DbType.Decimal)]
        [useSequence]
        [DataMember]
        [XmlElement(Order = 1)]
        public int? CoId
        {
            get { return this.m_co_id; }
            set { this.m_co_id = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("CO_DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string CoDescrizione
        {
            get { return this.m_co_descrizione; }
            set { this.m_co_descrizione = value; }
        }

        [DataField("CO_SERICHIEDEENDO", Type = DbType.String, CaseSensitive = false, Size = 1)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string CoSerichiedeendo
        {
            get { return this.m_co_serichiedeendo; }
            set { this.m_co_serichiedeendo = value; }
        }

        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        [DataMember]
        [XmlElement(Order = 5)]
        public string Software
        {
            get { return this.m_software; }
            set { this.m_software = value; }
        }

        [DataField("FK_RCO_ID", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 6)]
        public int? FkRcoId
        {
            get { return this.m_fk_rco_id; }
            set { this.m_fk_rco_id = value; }
        }

        [DataField("CO_DISABILITATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 7)]
        public int? CoDisabilitato
        {
            get { return this.m_co_disabilitato; }
            set { this.m_co_disabilitato = value; }
        }

        [DataField("CO_ORDINAMENTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 8)]
        public int? CoOrdinamento
        {
            get { return this.m_co_ordinamento; }
            set { this.m_co_ordinamento = value; }
        }

        [DataField("MAPPATURANODOPAG", Type = DbType.String, CaseSensitive = false, Size = 50)]
        [DataMember]
        [XmlElement(Order = 9)]
        public string MappaturaNodoPagamenti { get; set; }

        [DataField("PAGAMENTIREGULUS", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 10)]
        public int? PagamentiRegulus
        {
            get { return this.m_pagamentiregulus; }
            set { this.m_pagamentiregulus = value; }
        }

        [DataField("CODICETASSONOMIA", Type = DbType.String, CaseSensitive = false, Size = 12)]
        [DataMember]
        [XmlElement(Order = 11)]
        public string CodiceTassonomia { get; set; }

        #endregion

        #endregion
    }
}
