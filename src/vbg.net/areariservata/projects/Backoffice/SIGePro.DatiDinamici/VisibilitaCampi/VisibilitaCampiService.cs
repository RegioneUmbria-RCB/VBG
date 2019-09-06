// -----------------------------------------------------------------------
// <copyright file="VisibilitaCampiService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.VisibilitaCampi
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class VisibilitaCampiService : CampiServiceBase
	{
		SessioneModificaVisibilitaCampi _ultimaSessioneModificaVisibilita;

		public VisibilitaCampiService(IEnumerable<CampoDinamicoBase> campi):base(campi)
		{
		}

		internal SessioneModificaVisibilitaCampi IniziaModificaVisibilita()
		{
			this._ultimaSessioneModificaVisibilita = new SessioneModificaVisibilitaCampi(this.Campi);

			return this._ultimaSessioneModificaVisibilita;
		}

		public IEnumerable<IdValoreCampo> GetIdCampiByStatoVisibilita(StatoVisibilitaCampoEnum visibilita)
		{
			var trovaVisibili = StatoVisibilitaCampoEnum.Visibile == visibilita;

			foreach (var c in this.Campi)
			{
				for (int i = 0; i < c.ListaValori.Count; i++)
				{
					if (c.ListaValori[i].Visibile == trovaVisibili)
						yield return new IdValoreCampo(c.Id, i);
				}
			}
		}
	}
}
