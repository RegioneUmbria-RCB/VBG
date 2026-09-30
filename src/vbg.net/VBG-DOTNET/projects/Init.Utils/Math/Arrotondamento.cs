namespace Init.Utils.Math
{
    public static class Arrotondamento
    {
        public static double PerEccesso(double valore, int numeroDecimali)
        {
            var val = (double)valore;

            var fattore = (int)System.Math.Pow((double)10, (double)numeroDecimali);
            var valTrunc = System.Math.Truncate((val * fattore) + 0.999f);
            /*float valNTrunc = (val * (float)fattore);

			float delta = valNTrunc - valTrunc;

			if (delta > ( 0 + float.Epsilon * fattore ) )
				++valTrunc;*/

            return valTrunc / fattore;
        }


        /// <summary>
        /// Arrotonda per eccesso un numero decimale, con un numero di decimali specificato
        /// </summary>
        /// <param name="valore"></param>
        /// <param name="numeroDecimali"></param>
        /// <returns></returns>
        public static decimal PerEccesso(decimal valore, int numeroDecimali)
        {
            // Sarebbe meglio decimal.Round(valore, numeroDecimali, MidpointRounding.ToPositiveInfinity); ma non è supportato in .NET 4.8

            var fattore = 1.0m;

            for (var i = 0; i < numeroDecimali; i++)
            {
                fattore *= 10.0m;
            }
            // var fattore = (decimal)System.Math.Pow(10, numeroDecimali);
            // var valTrunc = System.Math.Truncate((valore * fattore) + 0.999m);
            var valTrunc = System.Math.Truncate(valore * fattore);
            return valTrunc / fattore;
        }
    }
}
