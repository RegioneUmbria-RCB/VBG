using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneDecodifiche;
using System;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.Cosap
{
    public class Soglia
    {
        private static class SogliaConstants
        {
            public const string ChiaveTabellaValoriSoglie = "VALORISOGLIE";
            public const string TabellaGiorniSoglie = "GIORNISOGLIE";
        }

        public string Codice { get; }
        public string Descrizione { get; }
        public bool Ciascuna { get; }
        public double Da { get; }
        public double A { get; }
        public int? DalGiorno { get; }
        public int? AlGiorno { get; }

        public Coefficiente Coefficiente;

        public Importo Importo;

        public static Soglia FromImportoECoefficiente(AuthenticationInfo authInfo, string codice, string descrizione, double importo, double? coefficiente)
        {
            return new Soglia(authInfo, codice, descrizione, coefficiente, importo);
        }

        public static Soglia FromImporto(AuthenticationInfo authInfo, string codice, string descrizione, double importo)
        {
            return new Soglia(authInfo, codice, descrizione, null, importo);
        }

        public static Soglia FromCoefficiente(AuthenticationInfo authInfo, string codice, string descrizione, double coefficiente)
        {
            return new Soglia(authInfo, codice, descrizione, coefficiente, null);
        }

        private Soglia(AuthenticationInfo authInfo, string codice, string descrizione, double? coefficiente, double? importo)
        {
            this.Codice = codice;
            this.Descrizione = descrizione;
            this.Coefficiente = coefficiente.HasValue ? new Coefficiente(coefficiente.Value) : null;
            this.Importo = importo.HasValue ? new Importo(importo.Value) : null;

            using (var db = authInfo.CreateDatabase())
            {
                DecodificheService service = new DecodificheService(db, authInfo.IdComune);

                var decodifica = service.GetDecodificheAttive(SogliaConstants.ChiaveTabellaValoriSoglie)
                    .FirstOrDefault(x => x.Raggruppamento.Contains(this.Codice));

                this.Ciascuna = decodifica.Valore.IndexOf("-") == 0;

                if (!this.Ciascuna)
                {
                    string first = decodifica.Valore.Split(Convert.ToChar("-")).First();
                    string last = decodifica.Valore.Split(Convert.ToChar("-")).Last();

                    this.Da = Convert.ToDouble(first);
                    this.A = String.IsNullOrEmpty(last) ? double.MaxValue : Convert.ToDouble(last);
                }

                var giorni = service
                                .GetDecodificheAttive(SogliaConstants.TabellaGiorniSoglie)
                                .FirstOrDefault(x => x.Raggruppamento.Contains(this.Codice));

                if (giorni != null)
                {
                    string first = giorni.Valore.Split(Convert.ToChar("-")).First();
                    string last = giorni.Valore.Split(Convert.ToChar("-")).Last();

                    this.DalGiorno = Convert.ToInt32(first);
                    this.AlGiorno = String.IsNullOrEmpty(last) ? int.MaxValue : Convert.ToInt32(last);

                }
            }

        }

    }
}
