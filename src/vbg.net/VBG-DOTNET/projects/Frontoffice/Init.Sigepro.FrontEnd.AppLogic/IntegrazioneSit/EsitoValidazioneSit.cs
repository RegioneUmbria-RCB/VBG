namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.SigeproSitWebService;

	public class EsitoValidazioneSit
	{
		public string IdComune { get; set; }
		public string CodVia { get; set; }
		public string Civico { get; set; }
		public string Km { get; set; }
		public string Esponente { get; set; }
		public string Colore { get; set; }
		public string Scala { get; set; }
		public string Interno { get; set; }
		public string EsponenteInterno { get; set; }
		public string CodCivico { get; set; }
		public string TipoCatasto { get; set;}
		public string Sezione { get; set; }
		public string Foglio { get; set; }
		public string Particella { get; set; }
		public string Sub { get; set; }
		public string Ui { get; set; }
		public string Fabbricato { get; set; }
		public string OggettoTerritoriale { get; set; }
		public string DescrizioneVia { get; set; }
		public string CAP { get; set; }
		public string Circoscrizione { get; set; }
		public string Frazione { get; set; }
		public string Zona { get; set; }
		public string Piano { get; set; }
		public string Quartiere { get; set; }
        public string AccessoTipo { get; set; }
        public string AccessoNumero { get; set; }
        public string AccessoDescrizione { get; set; }


        public static EsitoValidazioneSit FromSitClass(Sit cls)
		{
			if (cls == null)
			{
				return null;
			}

			return new EsitoValidazioneSit
			{
				IdComune = cls.IdComune,
				CodVia = cls.CodVia,
				Civico = cls.Civico,
				Km = cls.Km,
				Esponente = cls.Esponente,
				Colore = cls.Colore,
				Scala = cls.Scala,
				Interno = cls.Interno,
				EsponenteInterno = cls.EsponenteInterno,
				CodCivico = cls.CodCivico,
				TipoCatasto = cls.TipoCatasto,
				Sezione = cls.Sezione,
				Foglio = cls.Foglio,
				Particella = cls.Particella,
				Sub = cls.Sub,
				Ui = cls.UI,
				Fabbricato = cls.Fabbricato,
				OggettoTerritoriale = cls.OggettoTerritoriale,
				DescrizioneVia = cls.DescrizioneVia,
				CAP = cls.CAP,
				Circoscrizione = cls.Circoscrizione,
				Frazione = cls.Frazione,
				Zona = cls.Zona,
				Piano = cls.Piano,
				Quartiere = cls.Quartiere,
                AccessoTipo = cls.AccessoTipo,
                AccessoNumero = cls.AccessoNumero,
                AccessoDescrizione = cls.AccessoDescrizione
			};
		}
	}
}
