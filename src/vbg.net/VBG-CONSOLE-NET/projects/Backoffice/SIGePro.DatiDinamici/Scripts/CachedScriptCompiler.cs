using Init.SIGePro.DatiDinamici.Contesti;
using log4net;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Security.Cryptography;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Scripts
{
    internal class CachedScriptCompiler
    {
        ILog _log = LogManager.GetLogger(typeof(CachedScriptCompiler));

        private static class Constants
        {
            public const string KeyFormatString = "{0}:{1}:{2}";
        }

        static ConcurrentDictionary<string, Assembly> _cache = new ConcurrentDictionary<string, Assembly>();
        ScriptCompiler _scriptcompiler;



        public CachedScriptCompiler(ReferencedAssembliesProvider assemblyProvider)
        {
            this._scriptcompiler = new ScriptCompiler(assemblyProvider);
        }

        internal Assembly Compila(bool flagFrontoffice, ContestoModelloEnum contesto, string script)
        {
            var key = String.Format(Constants.KeyFormatString, CalcolaMd5(script), flagFrontoffice, contesto.ToString());

            if (_cache.ContainsKey(key))
            {
                _log.DebugFormat("Assembly con hash {0} trovato nella cache", key);

                return _cache[key];
            }

            try
            {
                _log.DebugFormat("Assembly con hash {0} NON trovato nella cache, inizio compilazione", key);

                var assembly = this._scriptcompiler.Compila(flagFrontoffice, contesto, script);

                _log.DebugFormat("Assembly con hash {0} compilato con successo", key);

                _cache[key] = assembly;

                _log.DebugFormat("Assembly con hash {0} aggiunto alla cache", key);

                return assembly;
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("Compilazione dell'assembly con hash {0} fallita: {1}", key, ex.ToString());

                throw;
            }
        }

        private string CalcolaMd5(string script)
        {
            using (MD5 md5Hash = MD5.Create())
            {
                // Convert the input string to a byte array and compute the hash.
                byte[] data = md5Hash.ComputeHash(Encoding.UTF8.GetBytes(script));

                // Create a new Stringbuilder to collect the bytes
                // and create a string.
                StringBuilder sBuilder = new StringBuilder();

                // Loop through each byte of the hashed data 
                // and format each one as a hexadecimal string.
                for (int i = 0; i < data.Length; i++)
                {
                    sBuilder.Append(data[i].ToString("x2"));
                }

                // Return the hexadecimal string.
                return sBuilder.ToString();
            }
        }
    }
}
