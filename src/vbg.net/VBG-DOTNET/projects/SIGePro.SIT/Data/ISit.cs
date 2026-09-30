using System;
namespace Init.SIGePro.Sit.Data
{
	public interface ISit
	{
		string CAP { get; }
		string Circoscrizione { get; }
		string Civico { get; }
		string CodCivico { get; }
		string CodiceComune { get; }
		string CodVia { get; }
		string Colore { get; }
		string DescrizioneVia { get; }
		string Esponente { get; }
		string EsponenteInterno { get; }
		string Fabbricato { get; }
		string Foglio { get; }
		string Frazione { get; }
		string IdComune { get; }
		string Interno { get; }
		string Km { get; }
		string Latitudine { get; }
		string Longitudine { get; }
		string OggettoTerritoriale { get; }
		string Particella { get; }
		string Piano { get; }
		string Quartiere { get; }
		string Scala { get; }
		string Sezione { get; }
		string Sub { get; }
		string TipoCatasto { get; }
		string UI { get; }
		string Zona { get; }

		void ExtendWith(ISit src);
	}
}
