using IntegrazioneCUnicoWS.CUnicoWS;
using System;

namespace IntegrazioneCUnicoWS
{
    /* 
     
     TODO: Valorizzare il domicilio solo se presente indirizzo domicilio
     
     */
     
    public class InserisciSoggettoRequest 
    {
        public string CodiceFiscale { get; set; }
        public string TipoAnagrafe { get; set; }
        public IndirizzoType Residenza { get; set; }
        public IndirizzoType Corrispondenza { get; set; }
        public DateTime? DataNascita { get; set; }
        public string Nominativo { get; set; }
        public string ComuneNascita { get; set; }
        public string Email { get; set; }
        public string Nome { get; set; }
        public string Sesso { get; set; }
        public string PartitaIVA { get; set; }
        public string PEC { get; set; }

        internal inserisciSoggettoRichiesta ToinserisciSoggettoRichiesta(CUnicoConfigurazione configurazione)
        {
            if (configurazione is null)
            {
                throw new ArgumentNullException(nameof(configurazione));
            }

            return new inserisciSoggettoRichiesta 
            { 
                codBel = configurazione.CodBel,
                codUte = configurazione.CodUte,
                soggetto= new tSoggettoWS 
                {
                    SOG_CAP_DOM = this.Corrispondenza.CAP,
                    SOG_CAP_RES = this.Residenza.CAP,
                    SOG_COD_FIS = this.CodiceFiscale ?? this.PartitaIVA, // nel codice fiscale vogliono la partita iva se non presente codice fiscale impresa
                    SOG_COM_DOM = this.Corrispondenza.Comune,
                    SOG_COM_RES = this.Residenza.Comune,
                    SOG_DAT_NAS = this.DataNascita.HasValue ? DateUtils.DateToCunicoWSDate(this.DataNascita).Value : 0,
                    SOG_DAT_NASSpecified = this.DataNascita.HasValue,
                    SOG_DEN = this.Nominativo,
                    SOG_DES_COM_NAS = this.ComuneNascita,
                    SOG_EMAIL = this.Email,
                    SOG_EMAIL_PEC = this.PEC,
                    SOG_IND_DOM = this.Corrispondenza.Indirizzo,
                    SOG_IND_RES = this.Residenza.Indirizzo,
                    SOG_NOM = this.Nome,
                    SOG_PAR_IVA = this.PartitaIVA,
                    SOG_PRO_COM_DOM = this.Corrispondenza.Provincia,
                    SOG_PRO_COM_RES = this.Residenza.Provincia,
                    SOG_SEX = this.Sesso,
                    SOG_TIP = this.TipoAnagrafe
                }
            };
        }
    }
}