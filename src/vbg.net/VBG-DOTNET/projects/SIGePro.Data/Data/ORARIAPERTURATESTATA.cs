using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("ORARIAPERTURATESTATA")]
    [Serializable]
    public class OrariAperturaTestata : BaseDataClass
    {

        #region Key Fields

        private string id = null;
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public string ID
        {
            get { return this.id; }
            set { this.id = value; }
        }

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        #endregion

        private string periododa = null;
        [DataField("PERIODODA", Size = 4, Type = DbType.String, CaseSensitive = false)]
        public string PERIODODA
        {
            get { return this.periododa; }
            set { this.periododa = value; }
        }

        private string periodoa = null;
        [DataField("PERIODOA", Size = 4, Type = DbType.String, CaseSensitive = false)]
        public string PERIODOA
        {
            get { return this.periodoa; }
            set { this.periodoa = value; }
        }

        private string codiceistanza = null;
        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        public string CODICEISTANZA
        {
            get { return this.codiceistanza; }
            set { this.codiceistanza = value; }
        }

        private string fktoid = null;
        [isRequired]
        [DataField("FKTOID", Type = DbType.Decimal)]
        public string FKTOID
        {
            get { return this.fktoid; }
            set { this.fktoid = value; }
        }

        #region Arraylist per gli inserimenti nelle tabelle collegate
        public List<OrariApertura> OrariApertura { get; set; } = new List<OrariApertura>();

        #endregion

    }
}