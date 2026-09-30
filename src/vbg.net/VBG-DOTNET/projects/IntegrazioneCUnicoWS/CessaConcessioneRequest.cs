using IntegrazioneCUnicoWS.CUnicoWS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace IntegrazioneCUnicoWS
{
    public class CessaConcessioneRequest
    {
        //public Concessione Concessione { get; set; }

        //public DatiOccupazione DatiOccupazione { get; set; }

        //public double ImportoCauzione { get; set; } = 0.0d;

        //public DateTime DataPresentazioneDichiarazione { get; set; }


        public DateTime DataCessazione { get; set; } 
        public int MatricolaRichiesta { get; set; }

        //public int MatricolaOggetto { get; set; }

        //public int TipoTributo { get; set; }

        internal modificaConcessioneRichiesta TomodificaConcessioneRichiesta(CUnicoConfigurazione configurazione)
        {
            if (configurazione is null)
            {
                throw new ArgumentNullException(nameof(configurazione));
            }

            //int? dataCessazione = DateUtils.DateToCunicoWSDate(this.Concessione.DataCessazione);
            //int? dataInizioOccupazione = DateUtils.DateToCunicoWSDate(this.DatiOccupazione.DataInizio);
            //int? dataFineOccupazione = DateUtils.DateToCunicoWSDate(this.DatiOccupazione.DataFine);

            return new modificaConcessioneRichiesta
            {
                codBel = configurazione.CodBel,
                codUte = configurazione.CodUte,
                //importoCauzione = this.ImportoCauzione,
                richiestaConcessione = new tConcessioneWS
                {
                    //DIC_COD_FIS = this.Concessione.CodiceFiscaleTitolare,
                    //DIC_DAT = DateUtils.DateToCunicoWSDate(this.DataPresentazioneDichiarazione).Value,
                    //DIC_DATSpecified = true,
                    //DIC_DAT_AUT = DateUtils.DateToCunicoWSDate(this.Concessione.DataRilascio).Value,
                    //DIC_DAT_AUTSpecified = true,
                    DIC_DAT_CES = DateUtils.DateToCunicoWSDate(this.DataCessazione).Value,
                    DIC_DAT_CESSpecified = true,
                    DIC_MTR = this.MatricolaRichiesta,
                    DIC_MTRSpecified = true,
                    //DIC_NUM_AUT = this.Concessione.Numero,
                    //DIC_PERIOD = this.Concessione.PeriodicitaOccupazione,
                    //DIC_RIN = this.Concessione.Rinnovabile ? "S" : "N",
                    //DIC_TIP_OCC = this.Concessione.TipoOccupazione,
                    //DIC_TIP_TRI = this.TipoTributo,
                    //DIC_TIP_TRISpecified = true,
                    //oggetti = new tConcessioneOggWS[]
                    //{
                    //new tConcessioneOggWS
                    //{
                    //OGG_CALC_AUTO = 0,
                    //OGG_CALC_AUTOSpecified = true,
                    //OGG_CIV = this.DatiOccupazione.Civico.HasValue ? this.DatiOccupazione.Civico.Value : int.MinValue,
                    //OGG_CIVSpecified = this.DatiOccupazione.Civico.HasValue,
                    //OGG_COD_TIP = this.DatiOccupazione.TipoOccupazione,
                    //OGG_COD_TIPSpecified = true,
                    //OGG_COD_VIA = this.DatiOccupazione.CodiceVia,
                    //OGG_COD_VIASpecified = true,
                    //OGG_DATA_FINE = dataFineOccupazione.HasValue ? dataFineOccupazione.Value : int.MinValue,
                    //OGG_DATA_FINESpecified = dataFineOccupazione.HasValue,
                    //OGG_DATA_INIZIO = dataInizioOccupazione.HasValue ? dataInizioOccupazione.Value : int.MinValue,
                    //OGG_DATA_INIZIOSpecified = dataInizioOccupazione.HasValue,
                    //OGG_GG = this.DatiOccupazione.GGDurataOccupazione,
                    //OGG_GGSpecified = true,
                    //OGG_MTR = this.MatricolaOggetto,
                    //OGG_MTRSpecified = true,
                    //OGG_QTA = this.DatiOccupazione.Quantita,
                    //OGG_QTASpecified = true,
                    //OGG_UM = this.DatiOccupazione.UnitaMisura
                    //}
                    //}
                }
            };
        }

    }
}
