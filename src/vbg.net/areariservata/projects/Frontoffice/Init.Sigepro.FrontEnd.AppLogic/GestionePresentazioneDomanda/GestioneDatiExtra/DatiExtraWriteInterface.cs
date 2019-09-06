// -----------------------------------------------------------------------
// <copyright file="DatiExtraWriteInterface.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiExtra
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
    using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class DatiExtraWriteInterface : IDatiExtraWriteinterface
	{
		private PresentazioneIstanzaDbV2 _db;

		public DatiExtraWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._db = database;
		}

        public void Set<T>(string chiave, T valore) where T : class
        {
            SetValoreDato(chiave, valore.ToXmlString());
        }

        public void SetValoreDato(string chiave, string valore)
		{
			var chiaveEsistente = this._db.DatiExtra.FindByChiave(chiave);

			if (chiaveEsistente != null)
			{
				chiaveEsistente.Delete();
				this._db.DatiExtra.AcceptChanges();
			}

			this._db.DatiExtra.AddDatiExtraRow(chiave, valore);

			this._db.DatiExtra.AcceptChanges();
		}


	}
}
