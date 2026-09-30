using Init.SIGePro.Data;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.Export
{
    public class SchedaDinamicaEsportata
    {
        private static readonly JsonSerializerOptions JsonOptions = new JsonSerializerOptions
        {
            WriteIndented = true,
            Converters = { new System.Text.Json.Serialization.JsonStringEnumConverter() }
        };

        public Dyn2ModelliT Modello { get; set; }
        public List<Dyn2ModelliScript> ScriptsModello { get; set; }
        public List<Dyn2ModelliD> Struttura { get; set; }
        public List<Dyn2Campi> CampiDinamici { get; set; }
        public List<Dyn2ModelliDTesti> Testi { get; set; }
        public List<Dyn2CampiProprieta> ProprietaCampiDinamici { get; set; }


        public static SchedaDinamicaEsportata FromJson(Stream inputStream)
        {
            if (inputStream.Position != 0)
            {
                inputStream.Seek(0, SeekOrigin.Begin);
            }
            return JsonSerializer.Deserialize<SchedaDinamicaEsportata>(inputStream, JsonOptions);
        }

        public string ToJson()
        {
            return JsonSerializer.Serialize(this, JsonOptions);
        }
    }
}
