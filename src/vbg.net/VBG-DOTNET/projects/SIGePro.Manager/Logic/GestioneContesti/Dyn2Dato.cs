using System;

namespace Init.SIGePro.Manager.Logic.GestioneContesti
{
    public class Dyn2Dato
    {
        public string ContestoCampo { get; set; }
        public int? CodiceIstanza { get; set; }
        public int? IdCampoDinamico { get; set; }
        public string NomeCampoDinamico { get; set; }
        public string Valore { get; set; }
        public string ValoreDecodificato { get; set; }
        public int? Indice { get; set; }
        public int? IndiceMolteplicita { get; set; }

        internal DatoDinamicoNelModello ToDatoDinamicoNelModello()
        {
            if (!this.IndiceMolteplicita.HasValue)
            {
                throw new Exception("Impossibile richiamare ToDatoDinamicoNelModello() senza aver prima popolato IndiceMolteplicita");
            }

            if (string.IsNullOrEmpty(NomeCampoDinamico))
            {
                throw new Exception("Impossibile richiamare ToDatoDinamicoNelModello() senza aver prima popolato il riferimento del campo dinamico");
            }

            return new DatoDinamicoNelModello
            {
                NomeCampo = this.NomeCampoDinamico,
                IndiceMolteplicita = this.IndiceMolteplicita.Value,
                Valore = this.Valore
            };
        }
    }
}
