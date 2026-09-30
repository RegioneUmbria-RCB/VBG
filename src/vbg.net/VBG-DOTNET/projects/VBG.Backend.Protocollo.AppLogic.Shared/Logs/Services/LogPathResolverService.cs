using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Logs.Services
{
    public class LogPathResolverService : ILogPathResolverService
    {
        private static class Constants
        {
            public const string HttpItemKeyName = "LogPathFromHttpContextResolverService";
        }

        private readonly ResolveDatiProtocollazioneService _datiProtocollazione;
        private readonly IRequestContextStorage _contextStorage;
        private readonly ITemporaryPathProvider _temporaryPathProvider;

        public LogPathResolverService(
            ResolveDatiProtocollazioneService datiProtocollazione,
            IRequestContextStorage contextStorage,
            ITemporaryPathProvider temporaryPathProvider)
        {
            _datiProtocollazione = datiProtocollazione;
            _contextStorage = contextStorage;
            _temporaryPathProvider = temporaryPathProvider;
        }

        #region ILogPathResolverService Members

        public string LogPath
        {
            get
            {
                if (_contextStorage.TryGetValue<string>(Constants.HttpItemKeyName, out var existingPath)
                    && !string.IsNullOrWhiteSpace(existingPath))
                {
                    return existingPath;
                }

                var newPath = GeneraNuovoPath();

                _contextStorage.SetValue(Constants.HttpItemKeyName, newPath);

                return newPath;
            }
        }

        public void EliminaLogPath()
        {
            if (!_contextStorage.TryGetValue<string>(Constants.HttpItemKeyName, out var path)
                || string.IsNullOrWhiteSpace(path))
            {
                return;
            }

            try
            {
                if (Directory.Exists(path))
                {
                    Directory.Delete(path, recursive: true);
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
                _contextStorage.Remove(Constants.HttpItemKeyName);
            }
        }

        private string GeneraFolder()
        {
            string idComune = _datiProtocollazione.IdComune;
            string codiceIstanza = String.IsNullOrEmpty(_datiProtocollazione.CodiceIstanza) ? "0" : _datiProtocollazione.CodiceIstanza;
            string codiceMovimento = String.IsNullOrEmpty(_datiProtocollazione.CodiceMovimento) ? "0" : _datiProtocollazione.CodiceMovimento;
            string codiceOperatore = !_datiProtocollazione.CodiceResponsabileUtenteLoggato.HasValue ? "0" : _datiProtocollazione.CodiceResponsabileUtenteLoggato.Value.ToString();
            string software = String.IsNullOrEmpty(_datiProtocollazione.Software) ? "XX" : _datiProtocollazione.Software;

            return string.Join(".",
            [
                idComune,
                codiceIstanza,
                codiceMovimento,
                codiceOperatore,
                software,
                DateTime.Now.ToString("ddMMyyyy.HHmmss")
            ]);
        }

        private string GeneraNuovoPath()
        {
            var rootPath = _temporaryPathProvider.GetTemporaryRootPath();

            if (string.IsNullOrWhiteSpace(rootPath))
            {
                throw new InvalidOperationException(
                    "Temporary root path non configurato.");
            }

            var folder = GeneraFolder();

            var fullPath = Path.Combine(rootPath, folder);

            if (!Directory.Exists(fullPath))
                Directory.CreateDirectory(fullPath);

            return fullPath;
        }

        #endregion
    }
}
