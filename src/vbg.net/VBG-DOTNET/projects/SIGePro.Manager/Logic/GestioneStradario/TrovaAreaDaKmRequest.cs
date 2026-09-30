using System;

namespace Init.SIGePro.Manager.Logic.GestioneStradario
{
    public class TrovaAreaDaKmRequest
    {
        public int CodiceTipoArea { get; }

        public int CodiceStradario { get; }
        public double Km { get; }


        public TrovaAreaDaKmRequest( int codiceTipoArea, int codiceStradario, double km ): this(codiceTipoArea, codiceStradario, km, 0 )
        {
        }

        public TrovaAreaDaKmRequest(int codiceTipoArea, int codiceStradario, double km, int metri)
        {

            if (km < 0.0d)
            {
                throw new ArgumentException("Impossibile passare un valore minore di zero. Parametro: ", nameof(km));
            }

            if (metri < 0) {
                throw new ArgumentException( "Impossibile passare un valore minore di zero. Parametro: ", nameof(metri));
            }

            this.CodiceTipoArea = codiceTipoArea;
            this.CodiceStradario = codiceStradario;

            this.Km = km;
            if (metri > 0) 
            {
                this.Km += ( (double)metri / 1000);
            }

        }
    }
}