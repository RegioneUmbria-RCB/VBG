// -----------------------------------------------------------------------
// <copyright file="SessioneModificaVisibilita.cs" company="">
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
	public class SessioneModificaVisibilitaCampi : CampiServiceBase
	{
		List<IdValoreCampo> _campiVisibili = new List<IdValoreCampo>();
		List<IdValoreCampo> _campiNonVisibili = new List<IdValoreCampo>();

		internal SessioneModificaVisibilitaCampi(IEnumerable<CampoDinamicoBase> campi)
			: base(campi)
		{
		}


		public void ImpostaVisibilitaCampi(IEnumerable<int> idCampi, StatoVisibilitaCampoEnum visibilita)
		{
			foreach (var idCampo in idCampi)
			{
				ImpostaVisibilitaCampo(idCampo, visibilita);
			}
		}

		public void ImpostaVisibilitaCampi(IEnumerable<string> nomiCampi, StatoVisibilitaCampoEnum visibilita)
		{
			foreach (var nomeCampo in nomiCampi)
			{
				ImpostaVisibilitaCampo(nomeCampo, visibilita);
			}
		}

		public void ImpostaVisibilitaCampo(int idCampo, StatoVisibilitaCampoEnum visibilita)
		{
			var campo = this.TrovaCampoDaId(idCampo);

			if (campo == null) return;

			ImpostaVisibilitaCampo(campo, visibilita);
		}

		public void ImpostaVisibilitaCampo(string nomeCampo, StatoVisibilitaCampoEnum visibilita)
		{
			var campo = this.TrovaCampo(nomeCampo, true);

			if (campo == null) return;

			ImpostaVisibilitaCampo(campo, visibilita);
		}

		// Accesso per indice molteplicita
		public void ImpostaVisibilitaCampi(IEnumerable<int> idCampi, int indiceMolteplicita, StatoVisibilitaCampoEnum visibilita)
		{
			foreach (var idCampo in idCampi)
			{
				ImpostaVisibilitaCampo(idCampo, indiceMolteplicita, visibilita);
			}
		}

		public void ImpostaVisibilitaCampi(IEnumerable<string> nomiCampi, int indiceMolteplicita, StatoVisibilitaCampoEnum visibilita)
		{
			foreach (var nomeCampo in nomiCampi)
			{
				ImpostaVisibilitaCampo(nomeCampo, indiceMolteplicita, visibilita);
			}
		}

		public void ImpostaVisibilitaCampo(int idCampo, int indiceMolteplicita, StatoVisibilitaCampoEnum visibilita)
		{
			var campo = this.TrovaCampoDaId(idCampo);

			if (campo == null) return;

			ImpostaVisibilitaCampo(campo, indiceMolteplicita, visibilita);
		}

		public void ImpostaVisibilitaCampo(string nomeCampo, int indiceMolteplicita, StatoVisibilitaCampoEnum visibilita)
		{
			var campo = this.TrovaCampo(nomeCampo, true);

			if (campo == null) return;

			ImpostaVisibilitaCampo(campo, indiceMolteplicita, visibilita);
		}


		private void ImpostaVisibilitaCampo(CampoDinamicoBase campo, StatoVisibilitaCampoEnum visibilita)
		{
			for (int indiceMolteplicita = 0; indiceMolteplicita < campo.ListaValori.Count; indiceMolteplicita++)
			{
				ImpostaVisibilitaCampo(campo, indiceMolteplicita, visibilita);
			}
		}


		private void ImpostaVisibilitaCampo(CampoDinamicoBase campo, int indiceMolteplicita, StatoVisibilitaCampoEnum visibilita)
		{
            //if (indiceMolteplicita>= campo.ListaValori.Count)
            //{
            //    return; 
            //}

			campo.ListaValori[indiceMolteplicita].Visibile = visibilita == StatoVisibilitaCampoEnum.Visibile;

			var id = new IdValoreCampo(campo.Id, indiceMolteplicita);

			if (visibilita == StatoVisibilitaCampoEnum.Visibile)
			{
				this._campiVisibili.Add(id);

				if (this._campiNonVisibili.Contains(id))
					this._campiNonVisibili.Remove(id);

			}
			else
			{
				this._campiNonVisibili.Add(id);

				if (this._campiVisibili.Contains(id))
					this._campiVisibili.Remove(id);
			}
		}

		public IEnumerable<IdValoreCampo> GetCampiVisibili()
		{
			return this._campiVisibili;
		}

		public IEnumerable<IdValoreCampo> GetCampiNonVisibili()
		{
			return this._campiNonVisibili;
		}
	}
}
