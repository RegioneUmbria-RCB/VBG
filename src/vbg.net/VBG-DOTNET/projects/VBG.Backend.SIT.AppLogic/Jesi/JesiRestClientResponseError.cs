using System;
using System.Collections.Generic;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.Jesi
{
    public class JesiRestClientResponseError<T>
    {
        [JsonPropertyName("hx_err")]
        [JsonConverter(typeof(SingleOrArrayConverter<JesiRestResponseError>))]
        public List<JesiRestResponseError> ErrorResponse { get; set; }
    }

    public class SingleOrArrayConverter<T> : JsonConverter<List<T>>
    {
        public override List<T> Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            if (reader.TokenType == JsonTokenType.StartArray)
            {
                // Se è un array, deserializza normalmente
                return JsonSerializer.Deserialize<List<T>>(ref reader, options);
            }
            else
            {
                // Se è un singolo oggetto, lo avvolge in una lista
                var singleItem = JsonSerializer.Deserialize<T>(ref reader, options);
                return new List<T> { singleItem };
            }
        }

        public override void Write(Utf8JsonWriter writer, List<T> value, JsonSerializerOptions options)
        {
            // Scrittura della lista
            JsonSerializer.Serialize(writer, value, options);
        }
    }
}
