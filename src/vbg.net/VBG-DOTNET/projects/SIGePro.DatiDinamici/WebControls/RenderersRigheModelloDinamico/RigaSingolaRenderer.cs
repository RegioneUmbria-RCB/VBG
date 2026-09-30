// -----------------------------------------------------------------------
// <copyright file="RigaSingolaRenderer.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------
using System;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces.WebControls;
using VBG.DatiDinamici.Web;
using VBG.DatiDinamici.WebControls;
using VBG.DatiDinamici.WebControls.MaschereCampiNonVisibili;
using VBG.DatiDinamici.WebControls.MaschereSolaLettura;


namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{


    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class RigaSingolaRenderer : RigaRendererBase
    {
        private readonly IMascheraSolaLettura _campiInSolaLettura;
        private readonly ICampiNonVisibili _campiNonVisilbili;


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
            var accumulatoreNote = AccumulatoreNoteModello.GetContextInstance();

            for (int indiceColonna = 0; indiceColonna < rigaScheda.NumeroColonne; indiceColonna++)
            {
                var campoScheda = rigaScheda[indiceColonna];

                // Se nella cella non sono presenti campi creo una cella vuota
                if (campoScheda == null)
                {
                    row.AggiungiCellaVuota();

                    continue;
                }

                if (accumulatoreNote != null)
                {
                    accumulatoreNote.ValutaNote(campoScheda);
                }

                // Genero l'id del controllo di input
                var idDelControlloDiInput = new IdControlloInputRigaSingola(campoScheda.Id, indiceMolteplicitaValore, numeroRiga, indiceColonna);

                // Se il campo è un campo nascosto allora non lo renderizzo
                if (campoScheda.CampoNascosto)
                {
                    var campoNascosto = new CampoDinamicoRenderizzato(idDelControlloDiInput, campoScheda);
                    var readOnly = this._campiInSolaLettura.ContieneCampo(campoScheda);

                    IDatiDinamiciControl dynControlNascosto = campoNascosto.CreaControllo(readOnly, (errMsg) =>
                    {
                        this.NotificaErroreCreazioneControllo(errMsg);
                    });

                    this.ContenitoreCampiNascosti.Controls.Add(dynControlNascosto as WebControl);
                    continue;
                }

                // Verifico la visibilità del campo
                var campoVisibile = this._campiNonVisilbili.ValoreVisibile(campoScheda.Id, indiceMolteplicitaValore);

                if (!campoVisibile)
                {
                    row.AggiungiCellaVuota();   // Campo etichetta
                    row.AggiungiCellaVuota();   // campo dinamico

                    continue;
                }

                var classeCssCampo = this.GetClasseCssCella(campoScheda, indiceMolteplicitaValore);


                var solaLettura = this._campiInSolaLettura.ContieneCampo(campoScheda);
                var campo = new CampoDinamicoRenderizzato(idDelControlloDiInput, campoScheda);

                IDatiDinamiciControl dynControl = campo.CreaControllo(solaLettura, (errMsg) =>
                {
                    this.NotificaErroreCreazioneControllo(errMsg);
                });

                // Creazione della cella che contiene l'etichetta del controllo
                if (campoScheda.RichiedeEtichetta && !campoScheda.EtichettaADestra)
                {
                    var etichetta = new EtichettaSinistra(campoScheda.Etichetta, campoScheda.IdRiferimentoNote);
                    //var cssEtichetta = $"{classeCssCampo} checkbox-label";
                    row.AggiungiCellaDiIntestazione(etichetta, classeCssCampo);
                }

                // creazione della cella che contiene il controllo di edit

                row.AggiungiCampoDinamico(dynControl, classeCssCampo);

                // Creazione della cella che contiene l'etichetta del controllo
                if (campoScheda.RichiedeEtichetta && campoScheda.EtichettaADestra)
                {
                    var etichetta = new EtichettaDestra(campoScheda.Etichetta, campoScheda.IdRiferimentoNote);
                    var cssEtichetta = $"{classeCssCampo} checkbox-label";
                    row.AggiungiCellaDiIntestazione(etichetta, cssEtichetta);
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
