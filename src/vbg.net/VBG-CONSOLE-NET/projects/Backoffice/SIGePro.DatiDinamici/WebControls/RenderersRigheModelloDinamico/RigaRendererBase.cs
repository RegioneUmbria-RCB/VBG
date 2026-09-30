// -----------------------------------------------------------------------
// <copyright file="RigaRendererBase.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
	using Init.SIGePro.DatiDinamici.WebControls.CreazioneControlli;
	using System.Diagnostics;
    using System.Web.UI.WebControls;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class RigaRendererBase
	{
		protected Action<string> _callbackErroreCreazioneControllo;
        protected WebControl ContenitoreCampiNascosti { get; private set; }

        public RigaRendererBase(Action<string> callbackErroreCreazioneControllo, WebControl contenitoreCampiNascosti)
		{
			this._callbackErroreCreazioneControllo = callbackErroreCreazioneControllo;
            this.ContenitoreCampiNascosti = contenitoreCampiNascosti;
		}

		protected void NotificaErroreCreazioneControllo(string messaggio)
		{
			if (this._callbackErroreCreazioneControllo != null)
				this._callbackErroreCreazioneControllo(messaggio);
		}

		protected string GetClasseCssCella(CampoDinamicoBase campoScheda, int indiceMolteplicitaValore)
		{
			return String.Format("c{0}_{1}", campoScheda.Id, indiceMolteplicitaValore);
		}
	}
}
