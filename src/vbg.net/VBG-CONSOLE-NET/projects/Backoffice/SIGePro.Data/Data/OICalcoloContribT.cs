using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
	public partial class OICalcoloContribT
	{
		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}


		public bool PrimoInserimento
		{
			get
			{
                return (
                    this.FkAreeCodiceareaZto.GetValueOrDefault(int.MinValue) == int.MinValue &&
                    this.FkAreeCodiceareaPrg.GetValueOrDefault(int.MinValue) == int.MinValue &&
                    this.FkOitId.GetValueOrDefault(int.MinValue) == int.MinValue &&
                    this.FkOinId.GetValueOrDefault(int.MinValue) == int.MinValue &&
                    this.FkOclaId.GetValueOrDefault(int.MinValue) == int.MinValue);
			}
		}
	}
}
