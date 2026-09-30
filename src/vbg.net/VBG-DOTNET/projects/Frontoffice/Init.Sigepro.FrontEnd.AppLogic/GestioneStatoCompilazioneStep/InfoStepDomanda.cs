using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneStatoCompilazioneStep
{
    public class InfoStepDomanda : IEquatable<InfoStepDomanda>
    {
        public InfoStepDomanda()
        {
        }

        public InfoStepDomanda(int id, string descrizione)
        {
            this.Id = id;
            this.Descrizione = descrizione;
        }

        public int Id { get; set; } = 0;
        public string Descrizione { get; set; } = "";

        public override bool Equals(object obj)
        {
            return this.Equals(obj as InfoStepDomanda);
        }

        public bool Equals(InfoStepDomanda other)
        {
            return !(other is null) &&
                   this.Id == other.Id &&
                   this.Descrizione == other.Descrizione;
        }

        public override int GetHashCode()
        {
            int hashCode = 23038600;
            hashCode = hashCode * -1521134295 + this.Id.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(this.Descrizione);
            return hashCode;
        }

        public static bool operator ==(InfoStepDomanda left, InfoStepDomanda right)
        {
            return EqualityComparer<InfoStepDomanda>.Default.Equals(left, right);
        }

        public static bool operator !=(InfoStepDomanda left, InfoStepDomanda right)
        {
            return !(left == right);
        }
    }
}
