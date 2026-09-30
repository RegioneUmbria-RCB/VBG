using Init.Sigepro.FrontEnd.Infrastructure.Server;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriPhantomjsBuilder : IConfigurazioneBuilder<ParametriPhantomjs>
    {
        private readonly IPathMapper _pathMapper;
        private readonly IAppConfigurationReader _appConfigurationReader;

        private static class Constants
        {
            public const string PhantomWebConfigKey = "phantomjsPath";
            public const string GhostscriptWebConfigKey = "ghostscriptPath";
            public const string DefaultPath = "~/phantomjs/";
        }

        public ParametriPhantomjsBuilder(IPathMapper pathMapper, IAppConfigurationReader appConfigurationReader)
        {
            this._pathMapper = pathMapper;
            this._appConfigurationReader = appConfigurationReader;
        }

        public ParametriPhantomjs Build()
        {
            var phantomPath = this._appConfigurationReader.GetSetting(Constants.PhantomWebConfigKey);

            if (String.IsNullOrEmpty(phantomPath))
            {
                phantomPath = Constants.DefaultPath;
            }

            if (phantomPath.StartsWith("~"))
            {
                phantomPath = this._pathMapper.MapPath(phantomPath);
            }

            return new ParametriPhantomjs(phantomPath);
        }
    }
}
