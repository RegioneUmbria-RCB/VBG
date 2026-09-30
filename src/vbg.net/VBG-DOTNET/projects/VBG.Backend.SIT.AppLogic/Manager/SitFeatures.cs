using System;
// <copyright file="SitFeatures.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace VBG.Backend.SIT.AppLogic.Manager
{
    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class SitFeatures
    {
        public enum TipoVisualizzazione
        {
            PuntoDaIndirizzo,
            PuntoDaMappale
        }

        public BaseDto<TipoVisualizzazione, string>[] VisualizzazioniFrontoffice { get; set; } = Array.Empty<BaseDto<TipoVisualizzazione, string>>();
        public BaseDto<TipoVisualizzazione, string>[] VisualizzazioniBackoffice { get; set; } = Array.Empty<BaseDto<TipoVisualizzazione, string>>();
        public string[] CampiGestiti { get; set; } = Array.Empty<string>();
    }
}
