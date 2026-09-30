using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
    public class LivelloAccessoVisura
    {
        public static class Constants
        {
            public const int DatiGenerali = 1 << 0;
            public const int Schede = 1 << 1;
            public const int Documenti = 1 << 2;
            public const int Endoprocedimenti = 1 << 3;
            public const int Oneri = 1 << 4;
            public const int MovimentiEffettuati = 1 << 5;
            public const int Autorizzazioni = 1 << 6;
        }

        public static LivelloAccessoVisura DaValoreFlag(int valoreFlagAccesso)
        {
            var livello = new LivelloAccessoVisura();

            livello.DatiGenerali = (valoreFlagAccesso & Constants.DatiGenerali) == Constants.DatiGenerali;
            livello.Schede = (valoreFlagAccesso & Constants.Schede) == Constants.Schede;
            livello.Documenti = (valoreFlagAccesso & Constants.Documenti) == Constants.Documenti;
            livello.Endoprocedimenti = (valoreFlagAccesso & Constants.Endoprocedimenti) == Constants.Endoprocedimenti;
            livello.Oneri = (valoreFlagAccesso & Constants.Oneri) == Constants.Oneri;
            livello.MovimentiEffettuati = (valoreFlagAccesso & Constants.MovimentiEffettuati) == Constants.MovimentiEffettuati;
            livello.Autorizzazioni = (valoreFlagAccesso & Constants.Autorizzazioni) == Constants.Autorizzazioni;
            livello.IsAccessoAnonimo = false;

            return livello;
        }

        private static LivelloAccessoVisura _livelloAccessoAnonimo;
        private static readonly object _lockAccessoAnonimo = new object();

        public static LivelloAccessoVisura AccessoAnonimo
        {
            get
            {
                lock (_lockAccessoAnonimo)
                {
                    if (_livelloAccessoAnonimo == null)
                    {
                        _livelloAccessoAnonimo = new LivelloAccessoVisura();
                        _livelloAccessoAnonimo.DatiGenerali = true;
                        _livelloAccessoAnonimo.Autorizzazioni = true;
                        _livelloAccessoAnonimo.IsAccessoAnonimo = true;
                    }
                }

                return _livelloAccessoAnonimo;
            }
        }

        private static LivelloAccessoVisura _livelloAccessoCompleto;
        private static readonly object _lockAccessoCompleto = new object();

        public static LivelloAccessoVisura Completo
        {
            get
            {
                lock (_lockAccessoCompleto)
                {
                    if (_livelloAccessoCompleto == null)
                    {
                        _livelloAccessoCompleto = new LivelloAccessoVisura();

                        _livelloAccessoCompleto.DatiGenerali = true;
                        _livelloAccessoCompleto.IsAccessoAnonimo = false; // vedi nota su DaValoreFlag

                        _livelloAccessoCompleto.Schede = true;
                        _livelloAccessoCompleto.Documenti = true;
                        _livelloAccessoCompleto.Endoprocedimenti = true;
                        _livelloAccessoCompleto.Oneri = true;
                        _livelloAccessoCompleto.MovimentiEffettuati = true;
                        _livelloAccessoCompleto.Autorizzazioni = true;
                        _livelloAccessoCompleto.IsAccessoAnonimo = false;
                    }
                }

                return _livelloAccessoCompleto;
            }
        }

        public static LivelloAccessoVisura FromSerializationCode(string valore)
        {
            if (string.IsNullOrEmpty(valore))
            {
                return new LivelloAccessoVisura();
            }

            var parts = valore.Split('-');
            var ints = Convert.ToInt32(parts[0]);
            var boolPart = Convert.ToBoolean(parts[1]);
            var livello = LivelloAccessoVisura.DaValoreFlag(ints);
            livello.IsAccessoAnonimo = boolPart;

            return livello;
        }

        public override bool Equals(object obj)
        {
            return obj is LivelloAccessoVisura visura &&
                   DatiGenerali == visura.DatiGenerali &&
                   Schede == visura.Schede &&
                   Documenti == visura.Documenti &&
                   Endoprocedimenti == visura.Endoprocedimenti &&
                   Oneri == visura.Oneri &&
                   MovimentiEffettuati == visura.MovimentiEffettuati &&
                   Autorizzazioni == visura.Autorizzazioni &&
                   SogettiPratica == visura.SogettiPratica;
        }

        public string ToSerializationCode()
        {
            int somma = 0;

            somma += this.DatiGenerali ? Constants.DatiGenerali : 0;
            somma += this.Schede ? Constants.Schede : 0;
            somma += this.Documenti ? Constants.Documenti : 0;
            somma += this.Endoprocedimenti ? Constants.Endoprocedimenti : 0;
            somma += this.Oneri ? Constants.Oneri : 0;
            somma += this.MovimentiEffettuati ? Constants.MovimentiEffettuati : 0;
            somma += this.Autorizzazioni ? Constants.Autorizzazioni : 0;

            return $"{somma}-{this.IsAccessoAnonimo}";
        }

        public override int GetHashCode()
        {
            int hashCode = 496721707;
            hashCode = hashCode * -1521134295 + DatiGenerali.GetHashCode();
            hashCode = hashCode * -1521134295 + Schede.GetHashCode();
            hashCode = hashCode * -1521134295 + Documenti.GetHashCode();
            hashCode = hashCode * -1521134295 + Endoprocedimenti.GetHashCode();
            hashCode = hashCode * -1521134295 + Oneri.GetHashCode();
            hashCode = hashCode * -1521134295 + MovimentiEffettuati.GetHashCode();
            hashCode = hashCode * -1521134295 + Autorizzazioni.GetHashCode();
            hashCode = hashCode * -1521134295 + SogettiPratica.GetHashCode();
            return hashCode;
        }

        public static bool operator ==(LivelloAccessoVisura v1, LivelloAccessoVisura v2)
        {
            return (v1 is null && v2 is null) || v1.Equals(v2);
        }

        public static bool operator !=(LivelloAccessoVisura v1, LivelloAccessoVisura v2)
        {
            var areEqual = v1 == v2;
            return !areEqual;
        }

        public bool DatiGenerali { get; private set; } = false;
        public bool Schede { get; private set; } = false;
        public bool Documenti { get; private set; } = false;
        public bool Endoprocedimenti { get; private set; } = false;
        public bool Oneri { get; private set; } = false;
        public bool MovimentiEffettuati { get; private set; } = false;
        public bool Autorizzazioni { get; private set; } = false;
        public bool SogettiPratica => !this.IsAccessoAnonimo;

        public bool IsAccessoCompleto => this == LivelloAccessoVisura.Completo;
        public bool IsAccessoAnonimo { get; private set; } = false;

        public LivelloAccessoVisura()
        {

        }
    }
}
