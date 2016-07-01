
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
    /// File generato automaticamente dalla tabella TIPIMOVIMENTO il 05/11/2008 11.33.53
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
    [DataTable("TIPIMOVIMENTO")]
    [Serializable]
    public partial class TipiMovimento : BaseDataClass
    {
        #region Membri privati

        private string m_tipomovimento = null;

        private string m_movimento = null;

        private int? m_sistema = null;

        private int? m_codicelettera = null;

        private int? m_flag_richiestaintegrazione = null;

        private int? m_flag_interruzione = null;

        private int? m_tutteleamministrazioni = null;

        private int? m_tipologiaesito = null;

        private int? m_flag_proroga = null;

        private int? m_ggproroga = null;

        private int? m_flag_enmail = null;

        private int? m_flag_enmostra = null;

        private string m_software = null;

        private string m_idcomune = null;

        private int? m_flag_operante = null;

        private int? m_flag_nonoperante = null;

        private int? m_flag_cds = null;

        private int? m_flag_registro = null;

        private int? m_fkidregistro = null;

        private int? m_flag_noamminterna = null;

        private int? m_flag_usadalprotocollo = null;

        private int? m_flag_pubblicamovimento = null;

        private int? m_flag_pubblicaparere = null;

        private int? m_fk_fo_soggettiesterni = null;

        private int? m_flag_stc = null;

        private int? m_flag_camcom = null;

		private int? flag_pubblicaallegati = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("TIPOMOVIMENTO", Type = DbType.String, Size = 8)]
        public string Tipomovimento
        {
            get { return m_tipomovimento; }
            set { m_tipomovimento = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("MOVIMENTO", Type = DbType.String, CaseSensitive = false, Size = 128)]
        public string Movimento
        {
            get { return m_movimento; }
            set { m_movimento = value; }
        }

        [DataField("SISTEMA", Type = DbType.Decimal)]
        public int? Sistema
        {
            get { return m_sistema; }
            set { m_sistema = value; }
        }

        [DataField("CODICELETTERA", Type = DbType.Decimal)]
        public int? Codicelettera
        {
            get { return m_codicelettera; }
            set { m_codicelettera = value; }
        }

        [DataField("FLAG_RICHIESTAINTEGRAZIONE", Type = DbType.Decimal)]
        public int? FlagRichiestaintegrazione
        {
            get { return m_flag_richiestaintegrazione; }
            set { m_flag_richiestaintegrazione = value; }
        }

        [DataField("FLAG_INTERRUZIONE", Type = DbType.Decimal)]
        public int? FlagInterruzione
        {
            get { return m_flag_interruzione; }
            set { m_flag_interruzione = value; }
        }

        [DataField("TUTTELEAMMINISTRAZIONI", Type = DbType.Decimal)]
        public int? Tutteleamministrazioni
        {
            get { return m_tutteleamministrazioni; }
            set { m_tutteleamministrazioni = value; }
        }

        [DataField("TIPOLOGIAESITO", Type = DbType.Decimal)]
        public int? Tipologiaesito
        {
            get { return m_tipologiaesito; }
            set { m_tipologiaesito = value; }
        }

        [DataField("FLAG_PROROGA", Type = DbType.Decimal)]
        public int? FlagProroga
        {
            get { return m_flag_proroga; }
            set { m_flag_proroga = value; }
        }

        [DataField("GGPROROGA", Type = DbType.Decimal)]
        public int? Ggproroga
        {
            get { return m_ggproroga; }
            set { m_ggproroga = value; }
        }

        [DataField("FLAG_ENMAIL", Type = DbType.Decimal)]
        public int? FlagEnmail
        {
            get { return m_flag_enmail; }
            set { m_flag_enmail = value; }
        }

        [DataField("FLAG_ENMOSTRA", Type = DbType.Decimal)]
        public int? FlagEnmostra
        {
            get { return m_flag_enmostra; }
            set { m_flag_enmostra = value; }
        }

        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        [DataField("FLAG_OPERANTE", Type = DbType.Decimal)]
        public int? FlagOperante
        {
            get { return m_flag_operante; }
            set { m_flag_operante = value; }
        }

        [DataField("FLAG_NONOPERANTE", Type = DbType.Decimal)]
        public int? FlagNonoperante
        {
            get { return m_flag_nonoperante; }
            set { m_flag_nonoperante = value; }
        }

        [DataField("FLAG_CDS", Type = DbType.Decimal)]
        public int? FlagCds
        {
            get { return m_flag_cds; }
            set { m_flag_cds = value; }
        }

        [DataField("FLAG_REGISTRO", Type = DbType.Decimal)]
        public int? FlagRegistro
        {
            get { return m_flag_registro; }
            set { m_flag_registro = value; }
        }

        [DataField("FKIDREGISTRO", Type = DbType.Decimal)]
        public int? Fkidregistro
        {
            get { return m_fkidregistro; }
            set { m_fkidregistro = value; }
        }

        [DataField("FLAG_NOAMMINTERNA", Type = DbType.Decimal)]
        public int? FlagNoamminterna
        {
            get { return m_flag_noamminterna; }
            set { m_flag_noamminterna = value; }
        }

        [DataField("FLAG_USADALPROTOCOLLO", Type = DbType.Decimal)]
        public int? FlagUsadalprotocollo
        {
            get { return m_flag_usadalprotocollo; }
            set { m_flag_usadalprotocollo = value; }
        }

        [DataField("FLAG_PUBBLICAMOVIMENTO", Type = DbType.Decimal)]
        public int? FlagPubblicamovimento
        {
            get { return m_flag_pubblicamovimento; }
            set { m_flag_pubblicamovimento = value; }
        }

        [DataField("FLAG_PUBBLICAPARERE", Type = DbType.Decimal)]
        public int? FlagPubblicaparere
        {
            get { return m_flag_pubblicaparere; }
            set { m_flag_pubblicaparere = value; }
        }

        [DataField("FK_FO_SOGGETTIESTERNI", Type = DbType.Decimal)]
        public int? FkFoSoggettiesterni
        {
            get { return m_fk_fo_soggettiesterni; }
            set { m_fk_fo_soggettiesterni = value; }
        }

        [DataField("FLAG_STC", Type = DbType.Decimal)]
        public int? FLAG_STC
        {
            get { return m_flag_stc; }
            set { m_flag_stc = value; }
        }

        [DataField("FLAG_CAMCOM", Type = DbType.Decimal)]
        public int? FLAG_CAMCOM
        {
            get { return m_flag_camcom; }
            set { m_flag_camcom = value; }
        }

		[DataField("FLAG_PUBBLICAALLEGATI", Type = DbType.Decimal)]
		public int? FlagPubblicaAllegati
        {
			get { return flag_pubblicaallegati; }
			set { flag_pubblicaallegati = value; }
        }

		

        #endregion

        #endregion
    }
}
