using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("AMMINISTRAZIONI")]
    [Serializable]
    public partial class Amministrazioni : BaseDataClass
    {

        #region Key Fields

        private string codiceamministrazione = null;
        [useSequence]
        [KeyField("CODICEAMMINISTRAZIONE", Type = DbType.Decimal)]
        public string CODICEAMMINISTRAZIONE
        {
            get { return this.codiceamministrazione; }
            set { this.codiceamministrazione = value; }
        }

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        #endregion

        private string amministrazione = null;
        [DataField("AMMINISTRAZIONE", Size = 80, Type = DbType.String, CaseSensitive = false)]
        public string AMMINISTRAZIONE
        {
            get { return this.amministrazione; }
            set { this.amministrazione = value; }
        }

        private string ufficio = null;
        [DataField("UFFICIO", Size = 50, Type = DbType.String, CaseSensitive = false)]
        public string UFFICIO
        {
            get { return this.ufficio; }
            set { this.ufficio = value; }
        }

        private string referente = null;
        [DataField("REFERENTE", Size = 50, Type = DbType.String, CaseSensitive = false)]
        public string REFERENTE
        {
            get { return this.referente; }
            set { this.referente = value; }
        }

        private string indirizzo = null;
        [DataField("INDIRIZZO", Size = 50, Type = DbType.String, CaseSensitive = false)]
        public string INDIRIZZO
        {
            get { return this.indirizzo; }
            set { this.indirizzo = value; }
        }

        private string citta = null;
        [DataField("CITTA", Size = 50, Type = DbType.String, CaseSensitive = false)]
        public string CITTA
        {
            get { return this.citta; }
            set { this.citta = value; }
        }

        private string cap = null;
        [DataField("CAP", Size = 5, Type = DbType.String, CaseSensitive = false)]
        public string CAP
        {
            get { return this.cap; }
            set { this.cap = value; }
        }

        private string provincia = null;
        [DataField("PROVINCIA", Size = 2, Type = DbType.String, CaseSensitive = false)]
        public string PROVINCIA
        {
            get { return this.provincia; }
            set { this.provincia = value; }
        }

        private string partitaiva = null;
        [DataField("PARTITAIVA", Size = 16, Type = DbType.String, CaseSensitive = false)]
        public string PARTITAIVA
        {
            get { return this.partitaiva; }
            set { this.partitaiva = value; }
        }

        private string telefono1 = null;
        [DataField("TELEFONO1", Size = 15, Type = DbType.String, CaseSensitive = false)]
        public string TELEFONO1
        {
            get { return this.telefono1; }
            set { this.telefono1 = value; }
        }

        private string telefono2 = null;
        [DataField("TELEFONO2", Size = 15, Type = DbType.String, CaseSensitive = false)]
        public string TELEFONO2
        {
            get { return this.telefono2; }
            set { this.telefono2 = value; }
        }

        private string fax = null;
        [DataField("FAX", Size = 15, Type = DbType.String, CaseSensitive = false)]
        public string FAX
        {
            get { return this.fax; }
            set { this.fax = value; }
        }

        private string email = null;
        [DataField("EMAIL", Size = 50, Type = DbType.String, CaseSensitive = false)]
        public string EMAIL
        {
            get { return this.email; }
            set { this.email = value; }
        }

        private string password = null;
        [DataField("PASSWORD", Size = 6, Type = DbType.String, CaseSensitive = false)]
        public string PASSWORD
        {
            get { return this.password; }
            set { this.password = value; }
        }

        private string web = null;
        [DataField("WEB", Size = 150, Type = DbType.String, CaseSensitive = false)]
        public string WEB
        {
            get { return this.web; }
            set { this.web = value; }
        }

        private string flag_silenziodiniego = null;
        [DataField("FLAG_SILENZIODINIEGO", Type = DbType.Decimal)]
        public string FLAG_SILENZIODINIEGO
        {
            get { return this.flag_silenziodiniego; }
            set { this.flag_silenziodiniego = value; }
        }

        private string codiceancitel = null;
        [DataField("CODICEANCITEL", Size = 100, Type = DbType.String, CaseSensitive = false)]
        public string CODICEANCITEL
        {
            get { return this.codiceancitel; }
            set { this.codiceancitel = value; }
        }

        private string progressivoexport = null;
        [DataField("PROGRESSIVOEXPORT", Size = 4, Type = DbType.String, CaseSensitive = false)]
        public string PROGRESSIVOEXPORT
        {
            get { return this.progressivoexport; }
            set { this.progressivoexport = value; }
        }

        private string flag_amministrazioneinterna = null;
        [DataField("FLAG_AMMINISTRAZIONEINTERNA", Type = DbType.Decimal)]
        public string FLAG_AMMINISTRAZIONEINTERNA
        {
            get { return this.flag_amministrazioneinterna; }
            set { this.flag_amministrazioneinterna = value; }
        }

        private string stc_idente = null;
        [DataField("STC_IDENTE", Size = 4, Type = DbType.String, CaseSensitive = false)]
        public string STC_IDENTE
        {
            get { return this.stc_idente; }
            set { this.stc_idente = value; }
        }

        private string stc_idsportello = null;
        [DataField("STC_IDSPORTELLO", Size = 10, Type = DbType.String, CaseSensitive = false)]
        public string STC_IDSPORTELLO
        {
            get { return this.stc_idsportello; }
            set { this.stc_idsportello = value; }
        }

        private string pec = null;
        [DataField("PEC", Size = 10, Type = DbType.String, CaseSensitive = false)]
        public string PEC
        {
            get { return this.pec; }
            set { this.pec = value; }
        }

        private string codicecomune = null;
        [DataField("CODICECOMUNE", Size = 5, Type = DbType.String, CaseSensitive = false)]
        public string CodiceComune
        {
            get { return this.codicecomune; }
            set { this.codicecomune = value; }
        }

        private string codiceIPA = null;
        [DataField("CODICEIPA", Size = 30, Type = DbType.String, CaseSensitive = false)]
        public string CodiceIPA
        {
            get { return this.codiceIPA; }
            set { this.codiceIPA = value; }
        }



    }
}