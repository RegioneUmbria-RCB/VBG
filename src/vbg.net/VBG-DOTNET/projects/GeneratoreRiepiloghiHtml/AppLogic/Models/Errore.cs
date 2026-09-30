using Microsoft.AspNetCore.Mvc;
using System.Runtime.Serialization;
using System.Text;
using System.Text.Json;

namespace GeneratoreRiepiloghiHtml.AppLogic.Models
{
    /// <summary>
    /// Dettagli dell&#x27;errore, conforme a RFC 9457 (https://www.rfc-editor.org/rfc/rfc9457.html)
    /// </summary>
    [DataContract]
    public partial class Errore : IEquatable<Errore>
    {
        /// <summary>
        /// stringa che indica lo status code HTTP generato dal server per questa occorrenza del problema
        /// </summary>
        /// <value>stringa che indica lo status code HTTP generato dal server per questa occorrenza del problema</value>

        [DataMember(Name = "status")]
        public string Status { get; set; }

        /// <summary>
        /// JSON string che contiene una URI reference che identifica il tipo di problema
        /// </summary>
        /// <value>JSON string che contiene una URI reference che identifica il tipo di problema</value>

        [DataMember(Name = "type")]
        public string Type { get; set; }

        /// <summary>
        /// Breve descrizione dell'errore
        /// </summary>

        [DataMember(Name = "title")]
        public string Title { get; set; }

        /// <summary>
        /// [opzionale] descrizione di uno o più errori che si sono verificati durante l&#x27;elaborazione della richiesta
        /// </summary>
        /// <value>[opzionale] descrizione di uno o più errori che si sono verificati durante l&#x27;elaborazione della richiesta</value>

        [DataMember(Name = "errors")]
        public List<string> Errors { get; set; }

        /// <summary>
        /// Returns the string presentation of the object
        /// </summary>
        /// <returns>String presentation of the object</returns>
        public override string ToString()
        {
            var sb = new StringBuilder();
            sb.Append("class Errore {\n");
            sb.Append("  Status: ").Append(this.Status).Append("\n");
            sb.Append("  Type: ").Append(this.Type).Append("\n");
            sb.Append("  Title: ").Append(this.Title).Append("\n");
            sb.Append("  Errors: ").Append(this.Errors).Append("\n");
            sb.Append("}\n");
            return sb.ToString();
        }

        /// <summary>
        /// Returns the JSON string presentation of the object
        /// </summary>
        /// <returns>JSON string presentation of the object</returns>
        public string ToJson() => JsonSerializer.Serialize(this);

        /// <summary>
        /// Returns true if objects are equal
        /// </summary>
        /// <param name="obj">Object to be compared</param>
        /// <returns>Boolean</returns>
        public override bool Equals(object? obj)
        {
            if (obj is null) return false;
            if (ReferenceEquals(this, obj)) return true;
            return obj.GetType() == this.GetType() && this.Equals((Errore)obj);
        }

        /// <summary>
        /// Returns true if Errore instances are equal
        /// </summary>
        /// <param name="other">Instance of Errore to be compared</param>
        /// <returns>Boolean</returns>
        public bool Equals(Errore? other)
        {
            if (other is null) return false;
            if (ReferenceEquals(this, other)) return true;

            return
                (
                    this.Status == other.Status ||
                    this.Status != null &&
                    this.Status.Equals(other.Status)
                ) &&
                (
                    this.Type == other.Type ||
                    this.Type != null &&
                    this.Type.Equals(other.Type)
                ) &&
                (
                    this.Title == other.Title ||
                    this.Title != null &&
                    this.Title.Equals(other.Title)
                ) &&
                (
                    this.Errors == other.Errors ||
                    this.Errors != null &&
                    this.Errors.SequenceEqual(other.Errors)
                );
        }

        /// <summary>
        /// Gets the hash code
        /// </summary>
        /// <returns>Hash code</returns>
        public override int GetHashCode()
        {
            unchecked // Overflow is fine, just wrap
            {
                var hashCode = 41;
                // Suitable nullity checks etc, of course :)
                if (this.Status != null)
                    hashCode = hashCode * 59 + this.Status.GetHashCode();
                if (this.Type != null)
                    hashCode = hashCode * 59 + this.Type.GetHashCode();
                if (this.Title != null)
                    hashCode = hashCode * 59 + this.Title.GetHashCode();
                if (this.Errors != null)
                    hashCode = hashCode * 59 + this.Errors.GetHashCode();
                return hashCode;
            }
        }

        #region Operators
#pragma warning disable 1591

        public static bool operator ==(Errore left, Errore right)
        {
            return Equals(left, right);
        }

        public static bool operator !=(Errore left, Errore right)
        {
            return !Equals(left, right);
        }

#pragma warning restore 1591
        #endregion Operators

        public static JsonResult ToUnauthorizedObjectResult()
        {
            var error = new Errore
            {
                Status = StatusCodes.Status401Unauthorized.ToString(),
                Title = "Utente non autorizzato",
                Type = "ap-api:errors:unauthorized"
            };

            return new JsonResult(error)
            {
                StatusCode = StatusCodes.Status401Unauthorized,
            };
        }
    }
}
