namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	public class SitCampiSupportati
	{
		public enum Campi
		{
			Nessuno = 0x00,
			Fabbricato = 1 << 1,
			UnitaImmobiliare = 1 << 2,
			CodiceVia = 1 << 3,
			Civico = 1 << 4,
			Colore = 1 << 5,
			EsponenteInterno = 1 << 6,
			Esponente = 1 << 7,
			Foglio = 1 << 8,
			Interno = 1 << 9,
			Km = 1 << 10,
			Particella = 1 << 11,
			Scala = 1 << 12,
			Sezione = 1 << 13,
			Sub = 1 << 14,
			Cap = 1 << 15,
			Frazione = 1 << 16,
			Circoscrizione = 1 << 17,
			Vincoli = 1 << 18,
			Zone = 1 << 19,
			SottoZone = 1 << 20,
			DatiUrbanistici = 1 << 21,
			Piani = 1 << 22,
			Quartieri = 1 << 23,
			TipoCatasto = 1 << 24,
			Piano = 1 << 25,
			Quartiere = 1 << 26,
			CodCivico = 1 << 27,
			Coordinate = 1 << 28,
            UnitaImmob = 1 << 29
		}

		private uint _mask = 0x00;

		public SitCampiSupportati()
		{
		}

		public SitCampiSupportati(string[] listaNomiCampi)
		{
			foreach (var nomeCampo in listaNomiCampi)
			{
				var enumVal = (Campi)Enum.Parse(typeof(Campi), nomeCampo, true);

				this.Attiva(enumVal);
			}
		}

		public void Attiva(Campi campo)
		{
			this._mask |= (uint)campo;
		}

		public bool Supporta(Campi campo)
		{
			return (this._mask & (ulong)campo) != 0;
		}
	}
}
