// -----------------------------------------------------------------------
// <copyright file="RigaSingolaRenderer.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.Web.UI.HtmlControls;
	using System.Web.UI.WebControls;
	using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
	using Init.SIGePro.DatiDinamici.WebControls.MaschereSolaLettura;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class RigaSingolaRenderer : RigaRendererBase
	{
		IMascheraSolaLettura _campiInSolaLettura;
		ICampiNonVisibili _campiNonVisilbili;
        

        public RigaSingolaRenderer(IMascheraSolaLettura campiInSolaLettura, ICampiNonVisibili campiNonVisilbili, Action<string> callbackErroreCreazioneControllo, WebControl contenitoreCampiNascosti)
			: base(callbackErroreCreazioneControllo, contenitoreCampiNascosti)
		{
			this._campiInSolaLettura = campiInSolaLettura;
			this._campiNonVisilbili = campiNonVisilbili;
		}

		internal IRigaRenderizzata Render(RigaModelloDinamico rigaScheda, int indiceMolteplicitaValore)
		{
			var row = new RigaSingola();
			var numeroRiga = rigaScheda.NumeroRiga;

			for (int indiceColonna = 0; indiceColonna < rigaScheda.NumeroColonne; indiceColonna++)
			{
				var campoScheda = rigaScheda[indiceColonna];

				// Se nella cella non sono presenti campi creo una cella vuota
				if (campoScheda == null)
				{
					row.AggiungiCellaVuota();

					continue;
				}

                // Genero l'id del controllo di input
                var idDelControlloDiInput = new IdControlloInputRigaSingola(campoScheda.Id, indiceMolteplicitaValore, numeroRiga, indiceColonna);

                // Se il campo è un campo nascosto allora non lo renderizzo
                if (campoScheda.CampoNascosto)
                {
                    var campoNascosto = new CampoDinamicoRenderizzato(idDelControlloDiInput, campoScheda);
                    var readOnly = this._campiInSolaLettura.ContieneCampo(campoScheda);

                    IDatiDinamiciControl dynControlNascosto = campoNascosto.CreaControllo(readOnly, (errMsg) => {
                        NotificaErroreCreazioneControllo(errMsg);
                    });

                    this.ContenitoreCampiNascosti.Controls.Add(dynControlNascosto as WebControl);
                    continue;
                }

                // Verifico la visibilità del campo
                var campoVisibile = this._campiNonVisilbili.ValoreVisibile(campoScheda.Id, indiceMolteplicitaValore);

				if (!campoVisibile)
				{
					row.AggiungiCellaVuota();	// Campo etichetta
					row.AggiungiCellaVuota();	// campo dinamico

					continue;
				}

				var classeCssCampo = GetClasseCssCella(campoScheda, indiceMolteplicitaValore);

				// Creazione della cella che contiene l'etichetta del controllo
				if (campoScheda.RichiedeEtichetta && !campoScheda.EtichettaADestra)
				{
					var etichetta = new EtichettaSinistra(campoScheda.Etichetta);
					row.AggiungiCellaDiIntestazione(etichetta, classeCssCampo);
				}

				// creazione della cella che contiene il controllo di edit
				var solaLettura = this._campiInSolaLettura.ContieneCampo(campoScheda);

				var campo = new CampoDinamicoRenderizzato(idDelControlloDiInput, campoScheda);

				IDatiDinamiciControl dynControl = campo.CreaControllo(solaLettura, (errMsg) => {
					NotificaErroreCreazioneControllo(errMsg);
				});

				row.AggiungiCampoDinamico(dynControl, classeCssCampo);

				// Creazione della cella che contiene l'etichetta del controllo
				if (campoScheda.RichiedeEtichetta && campoScheda.EtichettaADestra)
				{
					var etichetta = new EtichettaDestra(campoScheda.Etichetta);
					row.AggiungiCellaDiIntestazione(etichetta, classeCssCampo);
				}
			}

			return row;
		}

		private string EstraiValoreCampo(CampoDinamicoBase campoScheda, int indiceMolteplicitaValore)
		{
			if (campoScheda.TipoCampo == TipoControlloEnum.Label || campoScheda.TipoCampo == TipoControlloEnum.Titolo)
				return campoScheda.ListaValori[0].Valore;

			var valoreDecodificato = campoScheda.ListaValori[indiceMolteplicitaValore].ValoreDecodificato;

			if (this._campiInSolaLettura.ContieneCampo(campoScheda) && !String.IsNullOrEmpty(valoreDecodificato) && campoScheda.TipoCampo != TipoControlloEnum.Checkbox)
				return valoreDecodificato;

			return campoScheda.ListaValori[indiceMolteplicitaValore].Valore;
		}
	}
}
