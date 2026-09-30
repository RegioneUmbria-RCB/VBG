using System;

namespace IntegrazioneCUnicoWS
{
    public class DatiOccupazione
    {
        public int? Matricola { get; set; }
        public bool AreaOmbrelloni { get; set; }
        public bool AreaRiqualificata { get; set; }
        public bool Cancellato { get; set; }
        public bool CalcolaAutomaticamenteIGiorni { get; set; }
        public int? Civico { get; set; }
        public int? TipoOccupazione { get; set; }
        public int CodiceVia { get; set; }
        public DateTime? DataCessazione { get; set; }
        public DateTime? DataFine { get; set; }
        public DateTime? DataInizio { get; set; }
        public bool Esente { get; set; }
        public bool EventiLocali { get; set; }
        public int? GGDurataOccupazione { get; set; }
        public int? MeseInizio { get; set; }
        public int? MeseFine { get; set; }
        public int? MesiOccupazione
        {
            get
            {
                if (!this.MeseInizio.HasValue)
                {
                    return (int?)null;
                }
                if (!this.MeseFine.HasValue)
                {
                    return (int?)null;
                }

                return this.MeseFine.Value - this.MeseInizio.Value + 1;
            }
        }
        public int? OraInizioOccupazione { get; set; }
        public int? OraFineOccupazione { get; set; }
        public double? Quantita { get; set; } = 0.0d;
        public bool ScontoSuperficie { get; set; }
        public bool StrutturaFissa { get; set; }
        public string UnitaMisura { get; set; }
        public double? Litri { get; set; }
        public double? FasceOrarie { get; set; }
        public DayOfWeek? GiornoOccupazione { get; set; }
        public double? Larghezza { get; set; }
        public double? LarghezzaVarco { get; set; }
        public double? Lunghezza { get; set; }
        public double? LarghezzaConcessa { get; set; }
        public double? LarghezzaMarciapiede { get; set; }
        public double? LarghezzaMassima { get; set; }
        public double? Riduzione { get; set; }
        public string ClassificazioneSanzione { get; set; }
    }
}