
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.DatiDinamici.Interfaces;


namespace Init.SIGePro.Data
{
    public partial class Dyn2ModelliD : IDyn2DettagliModello
    {
		#region foreign keys
		Dyn2ModelliDTesti m_testo;
		[ForeignKey(/*typeof(Dyn2ModelliDTesti),*/ "Idcomune, FkD2mdtId", "Idcomune, Id")]
		public Dyn2ModelliDTesti CampoTestuale
		{
			get { return m_testo; }
			set { m_testo = value; }
		}

		Dyn2Campi m_campo;
		[ForeignKey(/*typeof(Dyn2Campi),*/ "Idcomune, FkD2cId", "Idcomune, Id")]
		public Dyn2Campi CampoDinamico
		{
			get { return m_campo; }
			set { m_campo = value; }
		}


		#endregion
	}
}
				