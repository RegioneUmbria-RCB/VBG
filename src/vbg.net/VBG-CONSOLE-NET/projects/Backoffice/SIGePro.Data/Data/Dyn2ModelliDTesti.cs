
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.DatiDinamici.Interfaces;


namespace Init.SIGePro.Data
{
    public partial class Dyn2ModelliDTesti
	{
		#region foreign key
		Dyn2BaseTipiTesto m_baseTipoTesto;
		[ForeignKey(/*typeof(Dyn2BaseTipiTesto),*/ "FkD2bttId", "Id")]
		public Dyn2BaseTipiTesto BaseTipoTesto
		{
			get { return m_baseTipoTesto; }
			set { m_baseTipoTesto = value; }
		}

		#endregion


		public override string ToString()
		{
			StringBuilder sb = new StringBuilder();

			if (this.BaseTipoTesto != null)
			{
				sb.Append("[").Append(this.BaseTipoTesto.ToString()).Append("] ");
			}

			sb.Append(this.Testo);

			return sb.ToString() ;
		}
	}
}
				