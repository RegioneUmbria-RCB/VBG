
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;

namespace Init.SIGePro.Data
{
	public partial class IstanzeDyn2Dati : IIstanzeDyn2Dati
    {
        Dyn2Campi m_campodinamico = new Dyn2Campi();
        [ForeignKey("Idcomune,FkD2cId", "Idcomune,Id")]
        public Dyn2Campi CampoDinamico
        {
            get { return m_campodinamico; }
            set { m_campodinamico = value; }
        }
    }
}
