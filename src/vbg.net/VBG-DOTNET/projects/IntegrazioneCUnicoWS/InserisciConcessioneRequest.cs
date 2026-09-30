using IntegrazioneCUnicoWS.CUnicoWS;
using System;
using System.Collections.Generic;
using System.Linq;

namespace IntegrazioneCUnicoWS
{
    public class InserisciConcessioneRequest
    {
        public Concessione Concessione { get; set; }
        public string DescrizioneDichiarazione { get; set; }
        public double ImportoCauzione { get; set; } = 0.0d;
        public List<DatiOccupazione> Occupazioni { get; set; }
        public int TipoTributo { get; set; }
        public DateTime? DataDichiarazioneRinnovo { get; set; }

        public List<DateTime> Scadenze { get; set; }

        internal inserisciConcessioneRichiesta ToinserisciConcessioneRichiesta(CUnicoConfigurazione configurazione)
        {
            if (configurazione is null)
            {
                throw new ArgumentNullException(nameof(configurazione));
            }

            int? dataCessazione = DateUtils.DateToCunicoWSDate(this.Concessione.DataCessazione);

            return new inserisciConcessioneRichiesta
            {
                codBel = configurazione.CodBel,
                codUte = configurazione.CodUte,
                importoCauzione = ImportoCauzione,
                richiestaConcessione = new tConcessioneWS
                {
                    DIC_COD_FIS = this.Concessione.CodiceFiscaleTitolare,
                    DIC_DAT = DateUtils.DateToCunicoWSDate(this.Occupazioni.Where(x => x.DataInizio.HasValue).Min(x => x.DataInizio.Value)).Value,
                    DIC_DATSpecified = true,
                    DIC_DAT_AUT = DateUtils.DateToCunicoWSDate(this.Concessione.DataRilascio).Value,
                    DIC_DAT_AUTSpecified = true,
                    DIC_DAT_CES = dataCessazione ?? int.MinValue,
                    DIC_DAT_CESSpecified = dataCessazione.HasValue,
                    DIC_DAT_RIC_RIN = this.DataDichiarazioneRinnovo.HasValue ? DateUtils.DateToCunicoWSDate(this.DataDichiarazioneRinnovo).Value : int.MinValue,
                    DIC_DAT_RIC_RINSpecified = this.DataDichiarazioneRinnovo.HasValue,
                    DIC_DES = DescrizioneDichiarazione,
                    DIC_NUM_AUT = this.Concessione.Numero,
                    DIC_PERIOD = this.Concessione.PeriodicitaOccupazione,
                    DIC_RIN = this.Concessione.Rinnovabile ? "S" : "N",
                    DIC_TIP_OCC = this.Concessione.TipoOccupazione,
                    DIC_TIP_TRI = TipoTributo,
                    DIC_TIP_TRISpecified = true,
                    DIC_UNI_SOL = this.CalcolaDicUniSol(),
                    scadenze = this.Scadenze?.Select(s => (int?)Convert.ToInt32(s.ToString("yyyyMMdd"))).ToArray() ?? Array.Empty<int?>(),
                    oggetti = this.Occupazioni.Select(x => new tConcessioneOggWS
                    {
                        cancellatoSpecified = false,
                        OGG_AREA_OMB = x.AreaOmbrelloni ? "S" : "N",
                        OGG_AREA_RIQ = x.AreaRiqualificata ? "S" : "N",
                        OGG_CALC_AUTO = x.CalcolaAutomaticamenteIGiorni ? 1 : 0,
                        OGG_CALC_AUTOSpecified = true,
                        OGG_CIV = x.Civico ?? int.MinValue,
                        OGG_CIVSpecified = x.Civico.HasValue,
                        OGG_COD_TIP = x.TipoOccupazione ?? int.MinValue,
                        OGG_COD_TIPSpecified = x.TipoOccupazione.HasValue,
                        OGG_COD_VIA = x.CodiceVia,
                        OGG_COD_VIASpecified = true,
                        OGG_DAT_CES = x.DataCessazione.HasValue ? DateUtils.DateToCunicoWSDate(x.DataCessazione).Value : int.MinValue,
                        OGG_DAT_CESSpecified = x.DataCessazione.HasValue,
                        OGG_DATA_FINE = x.DataFine.HasValue ? DateUtils.DateToCunicoWSDate(x.DataFine).Value : int.MinValue,
                        OGG_DATA_FINESpecified = x.DataFine.HasValue,
                        OGG_DATA_INIZIO = x.DataInizio.HasValue ? DateUtils.DateToCunicoWSDate(x.DataInizio).Value : int.MinValue,
                        OGG_DATA_INIZIOSpecified = x.DataInizio.HasValue,
                        OGG_ESENTE = x.Esente ? "S" : "N",
                        OGG_EVT_LOCALI = x.EventiLocali ? "S" : "N",
                        OGG_FLG_SCONTO_SUP = x.ScontoSuperficie ? "S" : "N",
                        OGG_GG = x.GGDurataOccupazione ?? int.MinValue,
                        OGG_GGSpecified = x.GGDurataOccupazione.HasValue,
                        OGG_LARG = x.Larghezza ?? double.MinValue,
                        OGG_LARGSpecified = x.Larghezza.HasValue,
                        OGG_LARG_CONCESSA = x.LarghezzaConcessa ?? double.MinValue,
                        OGG_LARG_CONCESSASpecified = x.LarghezzaConcessa.HasValue,
                        OGG_LARG_MARCIAPIEDE = x.LarghezzaMarciapiede ?? double.MinValue,
                        OGG_LARG_MARCIAPIEDESpecified = x.LarghezzaMarciapiede.HasValue,
                        OGG_LARG_MASSIMA = x.LarghezzaMassima ?? double.MinValue,
                        OGG_LARG_MASSIMASpecified = x.LarghezzaMassima.HasValue,
                        OGG_LARG_VARCO = x.LarghezzaVarco ?? double.MinValue,
                        OGG_LARG_VARCOSpecified = x.LarghezzaVarco.HasValue,
                        OGG_LITRI = x.Litri ?? double.MinValue,
                        OGG_LITRISpecified = x.Litri.HasValue,
                        OGG_LUN = x.GiornoOccupazione == DayOfWeek.Monday ? 1 : 0,
                        OGG_LUNSpecified = x.GiornoOccupazione.HasValue,
                        OGG_LUNG = x.Lunghezza ?? double.MinValue,
                        OGG_LUNGSpecified = x.Lunghezza.HasValue,
                        OGG_MAR = x.GiornoOccupazione == DayOfWeek.Tuesday ? 1 : 0,
                        OGG_MARSpecified = x.GiornoOccupazione.HasValue,
                        OGG_MER = x.GiornoOccupazione == DayOfWeek.Wednesday ? 1 : 0,
                        OGG_MERSpecified = x.GiornoOccupazione.HasValue,
                        OGG_GIO = x.GiornoOccupazione == DayOfWeek.Thursday ? 1 : 0,
                        OGG_GIOSpecified = x.GiornoOccupazione.HasValue,
                        OGG_VEN = x.GiornoOccupazione == DayOfWeek.Friday ? 1 : 0,
                        OGG_VENSpecified = x.GiornoOccupazione.HasValue,
                        OGG_SAB = x.GiornoOccupazione == DayOfWeek.Saturday ? 1 : 0,
                        OGG_SABSpecified = x.GiornoOccupazione.HasValue,
                        OGG_DOM = x.GiornoOccupazione == DayOfWeek.Sunday ? 1 : 0,
                        OGG_DOMSpecified = x.GiornoOccupazione.HasValue,
                        OGG_MESE_DA = x.MeseInizio ?? int.MinValue,
                        OGG_MESE_DASpecified = x.MeseInizio.HasValue,
                        OGG_MESE_A = x.MeseFine ?? int.MinValue,
                        OGG_MESE_ASpecified = x.MeseFine.HasValue,
                        OGG_MM = x.MesiOccupazione ?? int.MinValue,
                        OGG_MMSpecified = x.MesiOccupazione.HasValue,
                        OGG_ORA_INIZIO = x.OraInizioOccupazione ?? int.MinValue,
                        OGG_ORA_INIZIOSpecified = x.OraInizioOccupazione.HasValue,
                        OGG_ORA_FINE = x.OraFineOccupazione ?? int.MinValue,
                        OGG_ORA_FINESpecified = x.OraFineOccupazione.HasValue,
                        OGG_QTA = x.Quantita ?? double.MinValue,
                        OGG_QTASpecified = x.Quantita.HasValue,
                        OGG_SC = x.FasceOrarie ?? double.MinValue,
                        OGG_SCSpecified = x.FasceOrarie.HasValue,
                        OGG_STR_FISSA = x.StrutturaFissa ? "S" : "N",
                        OGG_UM = x.UnitaMisura
                    }).ToArray()
                }
            };
        }

        private string CalcolaDicUniSol()
        {
            //1. Se indicato pagamento anticipato allora assume valore S
            if (this.Concessione.PagamentoAnticipato)
            {
                return "S";
            }

            //2. Se occupazione con data fine - data inizio maggiore di 360gg allora torna S
            var dicUniSolX = this
                .Occupazioni
                .Exists(x => (x.DataFine.Value - x.DataInizio.Value).TotalDays <= 360);

            if (dicUniSolX)
            {
                return "X";
            }

            //3. Ritorna N
            return "N";
        }
    }
}