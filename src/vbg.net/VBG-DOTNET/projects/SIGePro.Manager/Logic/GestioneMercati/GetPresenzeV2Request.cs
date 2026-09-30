using Init.SIGePro.Manager.WsMercatiService;
using System;

namespace Init.SIGePro.Manager.Logic.GestioneMercati
{
    public class GetPresenzeV2Request
    {
        public int CodiceManifestazione { get; set; }
        public int? CodiceUso { get; set; }
        public DateTime Autorizdata { get; set; }
        public string Autoriznumero { get; set; }
        public string Autorizcomune { get; set; }
        public int Fkidregistro { get; set; }
        public string CatMerc { get; set; }
        public int? Codiceistanza { get; set; }
        public bool InserisciAutSeNonTrovata { get; set; }
        public int? PresenzeDaAggiungere { get; set; }

        internal PresenzeManifestazioneV2Request ToPresenzeManifestazioneV2Request(string token)
        {
            return new PresenzeManifestazioneV2Request
            {
                catMerc = this.CatMerc,
                codiceIstanza = this.Codiceistanza ?? int.MinValue,
                codiceIstanzaSpecified = this.Codiceistanza.HasValue,
                codiceManifestazione = this.CodiceManifestazione,
                codiceUso = this.CodiceUso ?? int.MinValue,
                codiceUsoSpecified = this.CodiceUso.HasValue,
                estremiAut = new EstremiAut
                {
                    autorizdata = this.Autorizdata.ToString("dd/MM/yyyy", null),
                    autoriznumero = this.Autoriznumero,
                    codiceAutorizcomune = this.Autorizcomune,
                    codiceAutorizregistro = this.Fkidregistro
                },
                inserisciAutSeNonTrovata = this.InserisciAutSeNonTrovata,
                presenzeDaAggiungere = this.PresenzeDaAggiungere ?? int.MinValue,
                presenzeDaAggiungereSpecified = this.PresenzeDaAggiungere.HasValue,
                token = token
            };
        }
    }
}
