using IntegrazioneCUnicoWS.CUnicoWS;
using System;

namespace IntegrazioneCUnicoWS
{
    public class PreventivoRequest
    {
        public DatiOccupazione DatiOccupazione { get; set; }

        public int TipoTributo { get; set; }

        internal preventivoRichiesta TopreventivoRichiesta(CUnicoConfigurazione configurazione)
        {
            if (configurazione is null)
            {
                throw new ArgumentNullException(nameof(configurazione));
            }

            int? dataInizioOccupazione = DateUtils.DateToCunicoWSDate(this.DatiOccupazione.DataInizio);
            int? dataFineOccupazione = DateUtils.DateToCunicoWSDate(this.DatiOccupazione.DataFine);

            return new preventivoRichiesta
            {
                codBel = configurazione.CodBel,
                codUte = configurazione.CodUte,
                OGG_CALC_AUTO = 1,
                OGG_CALC_AUTOSpecified = true,
                OGG_CIV = this.DatiOccupazione.Civico.HasValue ? this.DatiOccupazione.Civico.Value : int.MinValue,
                OGG_CIVSpecified = this.DatiOccupazione.Civico.HasValue,
                OGG_COD_TIP = this.DatiOccupazione.TipoOccupazione.HasValue ? this.DatiOccupazione.TipoOccupazione.Value : int.MinValue,
                OGG_COD_TIPSpecified = this.DatiOccupazione.TipoOccupazione.HasValue,
                OGG_COD_VIA = this.DatiOccupazione.CodiceVia,
                OGG_COD_VIASpecified = true,
                OGG_DATA_FINE = dataFineOccupazione.HasValue ? dataFineOccupazione.Value : int.MinValue,
                OGG_DATA_FINESpecified = dataFineOccupazione.HasValue,
                OGG_DATA_INIZIO = dataInizioOccupazione.HasValue ? dataInizioOccupazione.Value : int.MinValue,
                OGG_DATA_INIZIOSpecified = dataInizioOccupazione.HasValue,
                OGG_GG = this.DatiOccupazione.GGDurataOccupazione ?? int.MinValue,
                OGG_GGSpecified = this.DatiOccupazione.GGDurataOccupazione.HasValue,
                OGG_QTA = this.DatiOccupazione.Quantita ?? double.MinValue,
                OGG_QTASpecified = this.DatiOccupazione.Quantita.HasValue,
                OGG_UM = this.DatiOccupazione.UnitaMisura,
                TIP_TRI = TipoTributo,
                TIP_TRISpecified = true
            };
        }
    }
}