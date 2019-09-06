using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	public class EditablePropertyDetails
	{

        public string Name { get; set; } = String.Empty;
        public string Description { get; set; } = String.Empty;
        public string Value { get; set; } = String.Empty;
        public TipoControlloEditEnum TipoControllo { get; set; }
        public IEnumerable<KeyValuePair<string, string>> ValoriLista { get; set; }

        public EditablePropertyDetails(string name, string description, TipoControlloEditEnum tipoControllo, IEnumerable<KeyValuePair<string, string>> valoriLista, string valoreDefault)
		{
			Name = name;
			Description = description;
			TipoControllo = tipoControllo;
			ValoriLista = valoriLista;
			Value = valoreDefault;
		}
	}
}
