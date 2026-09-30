using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using RestSharp;
using System.Net;
using System.Text.Json;
using System.Text.Json.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services
{
    public class BaseService
    {
        protected string Url { get; private set; }
        protected ProtocolloLogs Logs { get; private set; }
        protected string ContentType { get; private set; }
        protected AuthenticationRestClient AuthClient { get; private set; }
        public BaseService(ParametriRegoleInfo par, ProtocolloLogs logs, Protocollazione.Enum.ContentType contentType)
        {
            this.Url = par.UrlWS;
            this.Logs = logs;
            this.ContentType = EnumConverter.ConvertToString(contentType);
            this.AuthClient = par.AuthenticationRestClient;
        }

        private void CleanEmptyStringsAndStatoParziale(object obj)
        {
            if (obj == null)
                return;

            var properties = obj.GetType().GetProperties();

            foreach (var prop in properties)
            {
                var propType = prop.PropertyType;

                if (typeof(System.Collections.IEnumerable).IsAssignableFrom(propType) && propType != typeof(string))
                {
                    var collection = (System.Collections.IEnumerable)prop.GetValue(obj);
                    if (collection != null)
                    {
                        foreach (var item in collection)
                        {
                            CleanEmptyStringsAndStatoParziale(item); // Recursively clean each item in the collection
                        }
                    }
                }
                else if (propType == typeof(string))
                {
                    string value = (string)prop.GetValue(obj);
                    if (string.IsNullOrEmpty(value))
                    {
                        prop.SetValue(obj, null); // Set empty string to null
                    }
                }
                else if (propType == typeof(bool?) && prop.Name.Equals("StatoParziale", StringComparison.CurrentCultureIgnoreCase))
                {
                    bool? value = (bool?)prop.GetValue(obj);
                    if (value.HasValue && !value.Value)
                        value = null; // Set false to null
                }
                else if (propType.IsClass && prop.GetValue(obj) != null)
                {
                    CleanEmptyStringsAndStatoParziale(prop.GetValue(obj)); // Recursively clean nested objects
                }
            }
        }

        private RestRequest InitJsonRequest(RestRequest request, object data)
        {
            request.AddHeader("Authorization", $"Bearer {this.AuthClient.GetAuthToken()}");
            request.AddHeader("Content-Type", this.ContentType);

            CleanEmptyStringsAndStatoParziale(data); // imposta a null i valori con stringa vuota

            var options = new JsonSerializerOptions
            {
                DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull, // Ignora i valori null
                WriteIndented = false, // Rimuovi l'indentazione
                Converters = { new UtcDateTimeConverter() } // Add custom converter
            };

            request.AddJsonBody(JsonSerializer.Serialize(data, options));

            return request;
        }

        private RestRequest InitMultipartRequest(RestRequest request, object data)
        {
            request.AddHeader("Authorization", $"Bearer {this.AuthClient.GetAuthToken()}");
            request.AddHeader("Content-Type", this.ContentType);

            var properties = data.GetType().GetProperties();

            try
            {
                // Scorri tutte le proprietà
                foreach (var property in properties)
                {
                    var propertyName = property.Name;
                    propertyName = char.ToLower(propertyName[0]) + propertyName.Substring(1);

                    var propertyValue = property.GetValue(data);

                    // Se la proprietà è un file (byte array)
                    if (property.PropertyType == typeof(byte[]))
                    {
                        var byteArray = (byte[])propertyValue;
                        if (byteArray != null)
                        {
                            // Aggiungi il file come parte del body multipart
                            request.AddFile(propertyName, byteArray, Guid.NewGuid().ToString());
                        }
                    }
                    else if (property.PropertyType == typeof(string))
                    {
                        // Aggiungi le stringhe come contenuto della form
                        if (propertyValue != null)
                        {
                            request.AddParameter(propertyName, propertyValue.ToString());
                        }
                    }

                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore per formato multipart/form-data", ex);
            }

            return request;
        }

        protected RestRequest InitRequest(string resource, object data, Method method = Method.Post)
        {
            var request = new RestRequest(resource);
            request.Method = method;

            switch (this.ContentType)
            {
                case "multipart/form-data":
                    return InitMultipartRequest(request, data);
                case "application/json":
                    return InitJsonRequest(request, data);
                default: throw new Exception($"ContentType ({this.ContentType}) non gestito dalla Request");
            }
        }

        protected RestResponse ExecuteRequest(RestRequest request, bool loggaRisposta = true)
        {
            if (String.IsNullOrEmpty(this.Url))
                throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE URL_WS[COLLAUDO/PRODUZIONE] NON È STATO VALORIZZATO.");

            Logs.Debug($"parametri della request: {Utility.NameValueCollectionToString(request.Parameters.ToList())}");

            var client = new RestClient($"{this.Url}");

            RestResponse response = client.Execute(request);

            // Verifica se il token è scaduto, in tal caso lo rinnova e riprova a eseguire la chiamata
            if (response.StatusCode == HttpStatusCode.Unauthorized)
            {
                this.AuthClient.RenewToken();
                request.AddOrUpdateParameter("Authorization", $"Bearer {this.AuthClient.GetAuthToken()}", ParameterType.HttpHeader);
                response = client.Execute(request);
            }

            if (loggaRisposta)
            {
                Logs.Debug($"parametri della response: {response.Content}");
            }

            return response;
        }
    }

    public class BasicBadRestResponse
    {
        public string title { get; set; }
        public string status { get; set; }
        public string detail { get; set; }
        public string instance { get; set; }
    }
}
