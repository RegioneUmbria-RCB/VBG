using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Utils;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Configurazione
{
    internal class ConfigurazioneRabbitMQBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ConfigurazioneRabbitMQ>
    {
        private static class Constants
        {
            public const string HostName = "RABBIT_HOSTNAME";
            public const string HostPort = "RABBIT_PORT";
            public const string UserName = "RABBIT_USERNAME";
            public const string Password = "RABBIT_PASSWORD";
            public const string ExchangeName = "RABBIT_EXCHANGE_NAME";
        }

        private readonly CacheParametriSigeproSecurity _cacheParametriSigeproSecurity;

        public ConfigurazioneRabbitMQBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservata, CacheParametriSigeproSecurity cacheParametriSigeproSecurity)
            : base(aliasSoftwareResolver, configurazioneAreaRiservata)
        {
            this._cacheParametriSigeproSecurity = cacheParametriSigeproSecurity;
        }

        public ConfigurazioneRabbitMQ Build()
        {
            var cfg = this.GetConfig();

            var attivo = cfg.RabbitMQ.Attivo;
            var url = cfg.RabbitMQ.Attivo ? this._cacheParametriSigeproSecurity.GetValoreCache(Constants.HostName) : "";
            var port = cfg.RabbitMQ.Attivo ? this._cacheParametriSigeproSecurity.GetValoreCache(Constants.HostPort) : "";
            var username = cfg.RabbitMQ.Attivo ? this._cacheParametriSigeproSecurity.GetValoreCache(Constants.UserName) : "";
            var password = cfg.RabbitMQ.Attivo ? this._cacheParametriSigeproSecurity.GetValoreCache(Constants.Password) : "";
            var exchangeName = cfg.RabbitMQ.Attivo ? this._cacheParametriSigeproSecurity.GetValoreCache(Constants.ExchangeName) : "";
            var portInt = 0;

            if (!String.IsNullOrEmpty(port) && !int.TryParse(port, out portInt))
            {
                throw new Exception($"Valore porta di rabbitMQ non valido: {port}");
            }

            return new ConfigurazioneRabbitMQ(attivo, url, portInt, username, password, exchangeName);
        }
    }
}
