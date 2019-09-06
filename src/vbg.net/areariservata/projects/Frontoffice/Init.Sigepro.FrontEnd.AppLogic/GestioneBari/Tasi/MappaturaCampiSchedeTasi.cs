// -----------------------------------------------------------------------
// <copyright file="MappaturaCampiSchedeTasi.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tasi
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class MappaturaCampiSchedeTasi
	{
		public class CampoDinamicoMappato
		{
			public readonly int IdCampo;
			public readonly string Valore;
			public readonly string ValoreDecodificato;

			public CampoDinamicoMappato(int idCampo, string valore, string valoreDecodificato)
			{
				this.IdCampo = idCampo;
				this.Valore = valore;
				this.ValoreDecodificato = valoreDecodificato;
			}

			public CampoDinamicoMappato(int idCampo, string valore)
			{
				this.IdCampo = idCampo;
				this.Valore = valore;
				this.ValoreDecodificato = valore;
			}
		}

		public int	 _idContribuente ;
		public int	 _codContribuente;

		public int[] _idImmobile ;
		public int[] _via		 ;
		public int[] _idVia;
		public int[] _civico	 ;
		public int[] _esponente	 ;
		public int[] _palazzo	 ;
		public int[] _scala		 ;
		public int[] _piano		 ;
		public int[] _interno	 ;
		public int[] _sezione	 ;
		public int[] _foglio	 ;
		public int[] _particella ;
		public int[] _sub		 ;
		public int[] _categoria	 ;
		public int[] _percentuale;


		public void SetIdContribuente	(string value) { this._idContribuente = Convert.ToInt32(value); }
		public void SetCodContribuente(string value) { this._codContribuente = Convert.ToInt32(value); }
		//public void SetInizioUtenza		(int value) { this._inizioUtenza = value; }
		//public void SetFineUtenza		(int value) { this._fineUtenza = value; }
		public void SetIdImmobile		(string value) { this._idImmobile = SplitToIntArray(value); }
		public void SetIdVia			(string value) { this._idVia = SplitToIntArray(value); }
		public void SetVia				(string value) { this._via = SplitToIntArray(value); }
		public void SetCivico			(string value) { this._civico = SplitToIntArray(value); }
		public void SetEsponente		(string value) { this._esponente = SplitToIntArray(value); }
		public void SetPalazzo			(string value) { this._palazzo = SplitToIntArray(value); }
		public void SetScala			(string value) { this._scala = SplitToIntArray(value); }
		public void SetPiano			(string value) { this._piano = SplitToIntArray(value); }
		public void SetInterno			(string value) { this._interno = SplitToIntArray(value); }
		public void SetSezione			(string value) { this._sezione = SplitToIntArray(value); }
		public void SetFoglio			(string value) { this._foglio = SplitToIntArray(value); }
		public void SetParticella		(string value) { this._particella = SplitToIntArray(value); }
		public void SetSub				(string value) { this._sub = SplitToIntArray(value); }
		public void SetCategoria		(string value) { this._categoria = SplitToIntArray(value); }
		public void SetPercentuale		(string value) { this._percentuale = SplitToIntArray(value); }

		private int[] SplitToIntArray(string value)
		{
			return value.Split(',').Select(x => Convert.ToInt32(x)).ToArray();
		}

		internal IEnumerable<CampoDinamicoMappato> Mappa(DatiContribuenteTasiDto datiContribuenteDto)
		{
			var abitazione = datiContribuenteDto.ListaImmobili.Where( x => x.TipoImmobile.StartsWith("A")).First();
			var pertinenze = datiContribuenteDto.ListaImmobili.Where(x => x != abitazione);

			var immobili = new List<ImmobileTasiDto>();

			immobili.Add(abitazione);
			immobili.AddRange(pertinenze);

			var result = new List<CampoDinamicoMappato>();


			if(_idContribuente	!= -1)
			{
				result.Add(new CampoDinamicoMappato(_idContribuente, datiContribuenteDto.IdContribuente));
			}

			if(_codContribuente	!= -1)
			{
				result.Add(new CampoDinamicoMappato(_codContribuente, datiContribuenteDto.CodiceContribuente));
			}

			var utenzaVuota = ImmobileTasiDto.Vuoto();

			result.AddRange(MappaInternal(0, utenzaVuota));
			result.AddRange(MappaInternal(1, utenzaVuota));
			result.AddRange(MappaInternal(2, utenzaVuota));
			result.AddRange(MappaInternal(3, utenzaVuota));

			for (int i = 0; i < immobili.Count; i++)
			{
				result.AddRange(MappaInternal(i, immobili[i]));
			}

			return result;
		}

		private IEnumerable<CampoDinamicoMappato> MappaInternal(int idx, ImmobileTasiDto datiUtenza)
		{
			yield return new CampoDinamicoMappato(_idImmobile 	[idx], datiUtenza.IdImmobile );
			yield return new CampoDinamicoMappato(_via		 	[idx], datiUtenza.Ubicazione.Via );
			yield return new CampoDinamicoMappato(_idVia		[idx], datiUtenza.Ubicazione.CodiceVia);
			yield return new CampoDinamicoMappato(_civico	 	[idx], datiUtenza.Ubicazione.Civico );
			yield return new CampoDinamicoMappato(_esponente	[idx], datiUtenza.Ubicazione.Esponente );
			yield return new CampoDinamicoMappato(_palazzo	 	[idx], datiUtenza.Ubicazione.Palazzina );
			yield return new CampoDinamicoMappato(_scala		[idx], datiUtenza.Ubicazione.Scala );
			yield return new CampoDinamicoMappato(_piano		[idx], datiUtenza.Ubicazione.Piano );
			yield return new CampoDinamicoMappato(_interno	 	[idx], datiUtenza.Ubicazione.Interno );
			yield return new CampoDinamicoMappato(_sezione	 	[idx], datiUtenza.RiferimentiCatastali.Sezione );
			yield return new CampoDinamicoMappato(_foglio	 	[idx], datiUtenza.RiferimentiCatastali.Foglio );
			yield return new CampoDinamicoMappato(_particella 	[idx], datiUtenza.RiferimentiCatastali.Particella );
			yield return new CampoDinamicoMappato(_sub		 	[idx], datiUtenza.RiferimentiCatastali.Subalterno );
			yield return new CampoDinamicoMappato(_categoria	[idx], datiUtenza.RiferimentiCatastali.CategoriaCatastale );
			yield return new CampoDinamicoMappato(_percentuale	[idx], datiUtenza.PercentualePossesso );
		}
	}
}
