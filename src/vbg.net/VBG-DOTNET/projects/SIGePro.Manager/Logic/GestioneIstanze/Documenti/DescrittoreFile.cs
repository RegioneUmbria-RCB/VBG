using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;
using System;

namespace Init.SIGePro.Manager.Logic.GestioneIstanze.Documenti
{
    public class DescrittoreFile
    {
        public string Descrizione { get; set; }
        public string Note { get; set; }
        public DateTime Data { get; set; } = DateTime.Now;
        public int? Verifica { get; set; } = null;
        public bool Necessario { get; set; } = false;
        public string Nomefile { get; set; }
        public string Mime { get; set; }

        public string ToJsonString()
        {
            var serializerSettings = new JsonSerializerSettings();
            serializerSettings.ContractResolver = new CamelCasePropertyNamesContractResolver();
            return JsonConvert.SerializeObject(this, serializerSettings);
        }
    }
}
