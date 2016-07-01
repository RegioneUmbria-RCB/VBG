using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche
{
	public class RiepiloghiSchedeDinamicheReadInterface : IRiepiloghiSchedeDinamicheReadInterface
	{
		PresentazioneIstanzaDbV2 _database;
		List<RiepilogoSchedaDinamica> _riepiloghi = new List<RiepilogoSchedaDinamica>();

		public RiepiloghiSchedeDinamicheReadInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;

			PreparaRiepiloghi();
		}



		private void PreparaRiepiloghi()
		{
			this._riepiloghi = new List<RiepilogoSchedaDinamica>();

			foreach(var riepilogo in this._database.RiepilogoDatiDinamici.Cast<PresentazioneIstanzaDbV2.RiepilogoDatiDinamiciRow>())
			{
				var rigaAllegato = riepilogo.IsIdAllegatoNull() ? null : this._database.Allegati.FindById( riepilogo.IdAllegato );
				
				this._riepiloghi.Add( RiepilogoSchedaDinamica.FromRiepilogoDomandaRow( riepilogo, rigaAllegato ));
			}
		}

		#region IRiepiloghiSchedeDinamicheReadInterface Members

		public IEnumerable<RiepilogoSchedaDinamica> GetByCodiceEndo(int codiceEndo)
		{
			var listaIdModello = this._database.ModelliInterventiEndo
											  .Where( x => x.TipoRecord == RiepiloghiSchedeDinamicheConstants.TipoRiepilogo.Endo &&
														   x.Codice == codiceEndo)
											  .Select( x => x.CodiceModello );

			return this._riepiloghi.Where( x => listaIdModello.Contains( x.IdModello ));
		}

		public IEnumerable<RiepilogoSchedaDinamica> GetRiepiloghiInterventoConAllegatoUtente()
		{
			var listaIdModello = this._database.ModelliInterventiEndo
								  .Where(x => x.TipoRecord == RiepiloghiSchedeDinamicheConstants.TipoRiepilogo.Intervento)
								  .Select(x => x.CodiceModello);

			return this._riepiloghi.Where(x => x.AllegatoDellUtente != null && listaIdModello.Contains(x.IdModello));
		}

		public IEnumerable<RiepilogoSchedaDinamica> GetRigheRiepilogoCittadinoExtracomunitario()
		{
			var listaIdModello = this._database.ModelliInterventiEndo
								  .Where(x => x.TipoRecord == RiepiloghiSchedeDinamicheConstants.TipoRiepilogo.CittadinoExtracomunitario )
								  .Select(x => x.CodiceModello);

			return this._riepiloghi.Where(x => listaIdModello.Contains(x.IdModello));
		}

		public RiepilogoSchedaDinamica GetByIdModelloIndiceMolteplicita(int idModello, int indiceMolteplicita)
		{
			return this._riepiloghi.Where(x => x.IdModello == idModello && x.IndiceMolteplicita == indiceMolteplicita).FirstOrDefault();
		}

		public int Count
		{
			get { return this._riepiloghi.Count; }
		}

		public IEnumerable<RiepilogoSchedaDinamica> Riepiloghi 
		{ 
			get 
			{ 
				return this._riepiloghi; 
			} 
		}

		#endregion
	}
}
