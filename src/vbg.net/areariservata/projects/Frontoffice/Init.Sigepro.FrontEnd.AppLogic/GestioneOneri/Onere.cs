// -----------------------------------------------------------------------
// <copyright file="Onere.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class Onere
	{
		public enum TipoOnereEnum
		{
			Intervento,
			Endoprocedimento
		}

		public int Codice { get; protected set; }
		public string Descrizione { get; protected set; }
		protected float Importo { get; set; }
		public TipoOnereEnum TipoOnere { get; protected set; }
		public int CodiceInterventoOEndoOrigine { get; protected set; }
		public string InterventoOEndoOrigine { get; protected set; }
		public string Note { get; protected set; }
        public int? CampoDinamico { get; protected set; }

        internal Onere(OnereDto onereDto)
		{
			this.Codice = onereDto.Codice;
			this.Descrizione = onereDto.Descrizione;
			this.Importo = onereDto.Importo;
			this.TipoOnere = onereDto.OrigineOnere == "I" ? TipoOnereEnum.Intervento : TipoOnereEnum.Endoprocedimento;
			this.CodiceInterventoOEndoOrigine = onereDto.CodiceInterventoOEndoOrigine;
			this.InterventoOEndoOrigine = onereDto.InterventoOEndoOrigine;
			this.Note = onereDto.Note;
            this.CampoDinamico = onereDto.CampoDinamico;
		}

        public float GetImporto(IDatiDinamiciReadInterface readInterface)
        {
            if (!this.CampoDinamico.HasValue)
            {
                return this.Importo;
            }

            var valoreCampo = readInterface.DatiDinamici.Where(x => x.IdCampo == this.CampoDinamico.Value).FirstOrDefault();

            if (valoreCampo != null)
            {
                return Convert.ToSingle(valoreCampo.Valore);
            }

            return this.Importo;
        }
	}
}
