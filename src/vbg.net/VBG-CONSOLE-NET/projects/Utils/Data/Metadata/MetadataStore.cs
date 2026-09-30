using log4net;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;

namespace PersonalLib2.Data.Metadata
{
    internal class MetadataStore
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(MetadataStore));
        private readonly ConcurrentDictionary<Type, DataClassMetadata> _metadata = new();

        public static MetadataStore Instance { get; } = new MetadataStore();

        private MetadataStore()
        {
        }

        public void AddMetadata(Type type, DataClassMetadata metadata)
        {
            if (type == null)
            {
                throw new ArgumentNullException(nameof(type));
            }

            if (metadata == null)
            {
                this._log.Error($"Tentativo di aggiungere metadati nulli per il tipo {type.FullName}");

                return;
            }

            this._metadata[type] = metadata;
        }

        internal bool TryGetMetadata(Type objType, out DataClassMetadata metadata)
        {
            if (objType == null)
            {
                metadata = null;

                return false;
            }

            return this._metadata.TryGetValue(objType, out metadata);
        }

        internal void DumpToDebugConsole()
        {
            foreach (var md in this._metadata.Values)
            {
                if (md == null)
                {
                    this._log.Error("DumpToDebugConsole: trovati metadati nulli");

                    continue;
                }

                md.DumpToDebugConsole();
            }
        }

        internal DataClassMetadata GetMetadata(Type objType)
        {
            if (objType == null)
            {
                throw new ArgumentNullException(nameof(objType));
            }

            if (this._metadata.TryGetValue(objType, out var retVal))
            {
                return retVal;
            }

            var knownTypes = this._metadata.Keys
                .Select(t => t.FullName)
                .OrderBy(t => t, StringComparer.Ordinal)
                .ToArray();

            throw new KeyNotFoundException(
                $"Il tipo {objType.FullName} non è stato analizzato. Tipi disponibili: {string.Join(", ", knownTypes)}");
        }
    }
}