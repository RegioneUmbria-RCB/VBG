using IntegrazioneCUnicoWS.CUnicoWS;
using System;
using System.Collections.Generic;
using System.Linq;

namespace IntegrazioneCUnicoWS
{
    public class InserimentoVerbaleRequest
    {
        public Concessione Concessione { get; set; }
        public string DescrizioneDichiarazione { get; set; }
        public double ImportoCauzione { get; set; } = 0.0d;
        public List<DatiOccupazione> Occupazioni { get; set; }
        public int TipoTributo { get; set; }

        internal inserisciVerbaleRichiesta ToinserisciVerbaleRichiesta(CUnicoConfigurazione configurazione)
        {
            if (configurazione is null)
            {
                throw new ArgumentNullException(nameof(configurazione));
            }

            int? dataCessazione = DateUtils.DateToCunicoWSDate(this.Concessione.DataCessazione);

            return new inserisciVerbaleRichiesta
            {
                codBel = configurazione.CodBel,
                codUte = configurazione.CodUte,
                //importoCauzione = ImportoCauzione,
                richiestaVerbale = new tVerbaleWS
                {
                    VRB_COD_FIS = this.Concessione.CodiceFiscaleTitolare,
                    VRB_DAT = DateUtils.DateToCunicoWSDate(this.Occupazioni.Where(x => x.DataInizio.HasValue).Min(x => x.DataInizio.Value)).Value,
                    VRB_DATSpecified = true,
                    //VRB_DAT_AUT = DateUtils.DateToCunicoWSDate(this.Concessione.DataRilascio).Value,
                    //VRB_DAT_AUTSpecified = true,
                    VRB_DAT_CES = dataCessazione ?? int.MinValue,
                    VRB_DAT_CESSpecified = dataCessazione.HasValue,
                    //VRB_DAT_RIC_RIN = this.DataDichiarazioneRinnovo.HasValue ? DateUtils.DateToCunicoWSDate(this.DataDichiarazioneRinnovo).Value : int.MinValue,
                    //VRB_DAT_RIC_RINSpecified = this.DataDichiarazioneRinnovo.HasValue,
                    VRB_DES = DescrizioneDichiarazione,
                    VRB_NUM = this.Concessione.Numero,
                    VRB_PERIOD = this.Concessione.PeriodicitaOccupazione,
                    VRB_RIN = this.Concessione.Rinnovabile ? "S" : "N",
                    VRB_TIP_OCC = this.Concessione.TipoOccupazione,
                    VRB_TIP_TRI = TipoTributo,
                    VRB_TIP_TRISpecified = true,
                    VRB_UNI_SOL = this.CalcolaDicUniSol(),
                    oggetti = this.Occupazioni.Select(x => new tVerbaleOggWS
                    {
                        VRBO_AREA_OMB = x.AreaOmbrelloni ? "S" : "N",
                        VRBO_AREA_RIQ = x.AreaRiqualificata ? "S" : "N",
                        VRBO_CALC_AUTO = x.CalcolaAutomaticamenteIGiorni ? 1 : 0,
                        VRBO_CALC_AUTOSpecified = true,
                        VRBO_CIV = x.Civico ?? int.MinValue,
                        VRBO_CIVSpecified = x.Civico.HasValue,
                        VRBO_COD_TIP = x.TipoOccupazione ?? int.MinValue,
                        VRBO_COD_TIPSpecified = x.TipoOccupazione.HasValue,
                        VRBO_COD_VIA = x.CodiceVia,
                        VRBO_COD_VIASpecified = true,
                        VRBO_DATA_FINE = x.DataFine.HasValue ? DateUtils.DateToCunicoWSDate(x.DataFine).Value : int.MinValue,
                        VRBO_DATA_FINESpecified = x.DataFine.HasValue,
                        VRBO_DATA_INIZIO = x.DataInizio.HasValue ? DateUtils.DateToCunicoWSDate(x.DataInizio).Value : int.MinValue,
                        VRBO_DATA_INIZIOSpecified = x.DataInizio.HasValue,
                        VRBO_ESENTE = x.Esente ? "S" : "N",
                        VRBO_EVT_LOCALI = x.EventiLocali ? "S" : "N",
                        VRBO_FLG_SCONTO_SUP = x.ScontoSuperficie ? "S" : "N",
                        VRBO_GG = x.GGDurataOccupazione ?? int.MinValue,
                        VRBO_GGSpecified = x.GGDurataOccupazione.HasValue,
                        VRBO_LARG = x.Larghezza ?? double.MinValue,
                        VRBO_LARGSpecified = x.Larghezza.HasValue,
                        VRBO_LITRI = x.Litri ?? double.MinValue,
                        VRBO_LITRISpecified = x.Litri.HasValue,
                        VRBO_LUN = x.GiornoOccupazione == DayOfWeek.Monday ? 1 : 0,
                        VRBO_LUNSpecified = x.GiornoOccupazione.HasValue,
                        VRBO_LUNG = x.Lunghezza ?? double.MinValue,
                        VRBO_LUNGSpecified = x.Lunghezza.HasValue,
                        VRBO_MAR = x.GiornoOccupazione == DayOfWeek.Tuesday ? 1 : 0,
                        VRBO_MARSpecified = x.GiornoOccupazione.HasValue,
                        VRBO_MER = x.GiornoOccupazione == DayOfWeek.Wednesday ? 1 : 0,
                        VRBO_MERSpecified = x.GiornoOccupazione.HasValue,
                        VRBO_GIO = x.GiornoOccupazione == DayOfWeek.Thursday ? 1 : 0,
                        VRBO_GIOSpecified = x.GiornoOccupazione.HasValue,
                        VRBO_VEN = x.GiornoOccupazione == DayOfWeek.Friday ? 1 : 0,
                        VRBO_VENSpecified = x.GiornoOccupazione.HasValue,
                        VRBO_SAB = x.GiornoOccupazione == DayOfWeek.Saturday ? 1 : 0,
                        VRBO_SABSpecified = x.GiornoOccupazione.HasValue,
                        VRBO_DOM = x.GiornoOccupazione == DayOfWeek.Sunday ? 1 : 0,
                        VRBO_DOMSpecified = x.GiornoOccupazione.HasValue,
                        VRBO_MM = x.MesiOccupazione ?? int.MinValue,
                        VRBO_MMSpecified = x.MesiOccupazione.HasValue,
                        VRBO_ORA_INIZIO = x.OraInizioOccupazione ?? int.MinValue,
                        VRBO_ORA_INIZIOSpecified = x.OraInizioOccupazione.HasValue,
                        VRBO_ORA_FINE = x.OraFineOccupazione ?? int.MinValue,
                        VRBO_ORA_FINESpecified = x.OraFineOccupazione.HasValue,
                        VRBO_QTA = x.Quantita ?? double.MinValue,
                        VRBO_QTASpecified = x.Quantita.HasValue,
                        VRBO_RID = x.Riduzione ?? double.MinValue,
                        VRBO_RIDSpecified = x.Riduzione.HasValue,
                        VRBO_SC = x.FasceOrarie ?? double.MinValue,
                        VRBO_SCSpecified = x.FasceOrarie.HasValue,
                        VRBO_STR_FISSA = x.StrutturaFissa ? "S" : "N",
                        VRBO_TIP_ABU = x.ClassificazioneSanzione,
                        VRBO_UM = x.UnitaMisura
                    }).ToArray()
                }
            };
        }

        private string CalcolaDicUniSol()
        {
            var dicUniSolX = this
                .Occupazioni
                .Any(x => (x.DataFine.Value - x.DataInizio.Value).TotalDays <= 360);

            if (dicUniSolX)
            {
                return "X";
            }

            return this.Concessione.PagamentoAnticipato ? "S" : "N";
        }
    }
}