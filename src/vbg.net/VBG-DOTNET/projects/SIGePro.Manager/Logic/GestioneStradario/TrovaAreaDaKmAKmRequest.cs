using System;

namespace Init.SIGePro.Manager.Logic.GestioneStradario
{
    public class TrovaAreaDaKmAKmRequest
    {
        public int CodiceTipoArea { get; }
        public int CodiceStradario { get; }
        public double KmA { get; }
        public double KmDa { get; }


        public TrovaAreaDaKmAKmRequest(int codiceTipoArea, int codiceStradario, double kmDa, int metriDa, double kmA, double metriA)
        {

            if (kmDa < 0.0d)
            {
                throw new ArgumentException("Impossibile passare un valore minore di zero. Parametro: ", nameof(kmDa));
            }

            if (metriDa < 0)
            {
                throw new ArgumentException("Impossibile passare un valore minore di zero. Parametro: ", nameof(metriDa));
            }

            if (kmA < 0.0d)
            {
                throw new ArgumentException("Impossibile passare un valore minore di zero. Parametro: ", nameof(kmA));
            }

            if (metriA < 0)
            {
                throw new ArgumentException("Impossibile passare un valore minore di zero. Parametro: ", nameof(metriA));
            }

            this.CodiceTipoArea = codiceTipoArea;
            this.CodiceStradario = codiceStradario;

            this.KmDa = kmDa;
            if (metriDa > 0)
            {
                this.KmDa += ((double)metriDa / 1000);
            }

            this.KmA = kmA;
            if (metriA > 0)
            {
                this.KmA += ((double)metriA / 1000);
            }

        }
    }
}