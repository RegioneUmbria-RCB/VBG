using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	public enum RuoloTipoSoggettoDomandaEnum
	{
		Richiedente,
		Azienda,
		Tecnico,
		Altro
	}

	public class TipoSoggettoDomanda
	{
		public int? Id { get; set; }
		public string Descrizione { get; set; }
		public string DescrizioneEstesa { get; set; }
		public bool RichiedeAnagraficaCollegata { get; set; }
		public RuoloTipoSoggettoDomandaEnum Ruolo { get; set; }

		public static TipoSoggettoDomanda NonDefinito
		{
			get
			{
				return new TipoSoggettoDomanda
				{
					Id = -1,
					Descrizione = "Non definito",
					DescrizioneEstesa = "",
					RichiedeAnagraficaCollegata = false,
					Ruolo = RuoloTipoSoggettoDomandaEnum.Altro
				};
			}
		}

		public static RuoloTipoSoggettoDomandaEnum RuoloDaCodiceBackoffice(string codice)
		{
			if (codice == "R")
				return RuoloTipoSoggettoDomandaEnum.Richiedente;

			if (codice == "A")
				return RuoloTipoSoggettoDomandaEnum.Azienda;

			if (codice == "T")
				return RuoloTipoSoggettoDomandaEnum.Tecnico;

			return RuoloTipoSoggettoDomandaEnum.Altro;
		}

		public string RuoloAsCodiceBackoffice()
		{
			switch (this.Ruolo)
			{
				case RuoloTipoSoggettoDomandaEnum.Richiedente:
					return "R";
				case RuoloTipoSoggettoDomandaEnum.Azienda:
					return "A";
				case RuoloTipoSoggettoDomandaEnum.Tecnico:
					return "T";
			}

			return String.Empty;
		}

		public override string ToString()
		{
			var sb = new StringBuilder();

			sb.Append(this.Descrizione);

			if (!String.IsNullOrEmpty(this.DescrizioneEstesa))
				sb.Append(": ").Append(this.DescrizioneEstesa);

			return sb.ToString();
		}

        public override bool Equals(object obj)
        {
            if (obj == null) return false;
            if (this.GetType() != obj.GetType()) return false;

            var typedObj = obj as TipoSoggettoDomanda;

            return this.Id == typedObj.Id;
        }


        public override int GetHashCode()
        {
            return this.Id.GetHashCode() ^ this.Descrizione.GetHashCode();
        }

        public static Boolean operator ==(TipoSoggettoDomanda v1, TipoSoggettoDomanda v2)
        {

            if ((object)v1 == null)
                if ((object)v2 == null)
                    return true;
                else
                    return false;

            return (v1.Equals(v2));
        }

        public static Boolean operator !=(TipoSoggettoDomanda v1, TipoSoggettoDomanda v2)
        {
            return !(v1 == v2);
        }
	}


	public static class TipoSoggettoExtensions
	{
		public static TipoSoggettoDomanda ToTipoSoggettoDomanda(this TipoSoggetto tipoSoggetto)
		{
			return new TipoSoggettoDomanda
			{
				Id = tipoSoggetto.Codice,
				Descrizione = tipoSoggetto.Descrizione,
				RichiedeAnagraficaCollegata = tipoSoggetto.RichiedeAnagraficaCollegata,
				Ruolo = TipoSoggettoDomanda.RuoloDaCodiceBackoffice(tipoSoggetto.FlagTipoDato)
			};
		}
	}
}
