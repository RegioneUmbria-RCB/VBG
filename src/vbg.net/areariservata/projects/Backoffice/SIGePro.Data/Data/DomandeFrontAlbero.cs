
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class DomandeFrontAlbero
    {
		List<DomandeFrontAlbero> m_sottoAree = new List<DomandeFrontAlbero>();
		[ForeignKey("Idcomune, Id", "Idcomune, Idpadre")]
		public List<DomandeFrontAlbero> SottoAree
		{
			get { return m_sottoAree; }
			set { m_sottoAree = value; }
		}
	}
}
				