using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.Bari.IMU.DTOs
{
	public class ImmobileImuDto
	{
		public IndirizzoImuDto Ubicazione{ get; set;}
		public DatiCatastaliImuDto RiferimentiCatastali{ get; set;}
		// public string DataInizioPossesso{ get; set;}
		public string PercentualePossesso{ get; set;}
		public string IdImmobile{ get; set;}
		public string CategoriaCatastale { get; set; }
        public TipoImmobileEnum TipoImmobile { get; set; }

		[Obsolete("Utilizzare solo per la serializzazione")]
		public ImmobileImuDto()
		{
		}

		public static ImmobileImuDto Vuoto()
		{
            return new ImmobileImuDto(IndirizzoImuDto.Vuoto(), DatiCatastaliImuDto.Vuoto(), String.Empty, /*String.Empty,*/ String.Empty, TipoImmobileEnum.Sconosciuto, String.Empty);
		}

        public ImmobileImuDto(IndirizzoImuDto ubicazione, DatiCatastaliImuDto riferimentiCatastali, /*string dataInizioPossesso,*/ string percentualePossesso, string idImmobile, TipoImmobileEnum tipoImmobile, string categoriaCatastale)
		{
			this.Ubicazione			 = ubicazione;
			this.RiferimentiCatastali= riferimentiCatastali;
			// this.DataInizioPossesso= dataInizioPossesso;
			this.PercentualePossesso = percentualePossesso;
			this.IdImmobile = idImmobile;
			this.TipoImmobile = tipoImmobile;
			this.CategoriaCatastale = categoriaCatastale;
		}

        [XmlIgnore]
        public string TipoImmobileString
        {
            get
            {
                return GetTipiImmobile().Where(x => x.Key == this.TipoImmobile).First().Value;
            }
        }

        static List<KeyValuePair<TipoImmobileEnum, string>> _tipologieImmobili = new List<KeyValuePair<TipoImmobileEnum, string>>();

        public static List<KeyValuePair<TipoImmobileEnum, string>> GetTipiImmobile()
        {
            if (_tipologieImmobili.Count == 0)
            {
                _tipologieImmobili.Add(new KeyValuePair<TipoImmobileEnum, string>(TipoImmobileEnum.Sconosciuto, ""));
                _tipologieImmobili.Add(new KeyValuePair<TipoImmobileEnum, string>(TipoImmobileEnum.Abitazione,  "Abitazione"));
                _tipologieImmobili.Add(new KeyValuePair<TipoImmobileEnum, string>(TipoImmobileEnum.Pertinenza, "Pertinenza"));
            }

            return _tipologieImmobili;
        }
	}
}
