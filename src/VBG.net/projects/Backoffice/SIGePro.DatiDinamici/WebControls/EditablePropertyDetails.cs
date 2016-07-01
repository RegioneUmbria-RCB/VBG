using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	public class EditablePropertyDetails
	{
		private string m_name = String.Empty;

		public string Name
		{
			get { return m_name; }
			set { m_name = value; }
		}
		private string m_description = String.Empty;

		public string Description
		{
			get { return m_description; }
			set { m_description = value; }
		}
		private string m_value = String.Empty;

		public string Value
		{
			get { return m_value; }
			set { m_value = value; }
		}

		TipoControlloEditEnum m_tipoControllo;

		public TipoControlloEditEnum TipoControllo
		{
			get { return m_tipoControllo; }
			set { m_tipoControllo = value; }
		}
		List<KeyValuePair<string, string>> m_valoriLista;

		public List<KeyValuePair<string, string>> ValoriLista
		{
			get { return m_valoriLista; }
			set { m_valoriLista = value; }
		}

		public EditablePropertyDetails(string name, string description, TipoControlloEditEnum tipoControllo, List<KeyValuePair<string, string>> valoriLista, string valoreDefault)
		{
			Name = name;
			Description = description;
			m_tipoControllo = tipoControllo;
			m_valoriLista = valoriLista;
			m_value = valoreDefault;
		}
	}
}
