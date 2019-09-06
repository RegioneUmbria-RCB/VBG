using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class CCClassiSuperfici
    {
        #region Key Fields

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        #endregion

        #region Data Fields

        [isRequired]
        [DataField("DA", Type = DbType.Decimal)]
        public int? Da{get;set;}

		[isRequired]
		[DataField("A", Type = DbType.Decimal)]
		public int? A { get; set; }

        #endregion


		[DataField("ALIQUOTA_CALCOLO_CC", Type = DbType.Decimal)]
		public double? AliquotaCalcoloCostoCostruzione{get;set;}

		public override string ToString()
		{
			return this.Classe;
		}
    }
}
