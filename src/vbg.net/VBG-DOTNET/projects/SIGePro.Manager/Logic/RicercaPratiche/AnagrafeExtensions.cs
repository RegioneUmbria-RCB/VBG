using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;

namespace Init.SIGePro.Manager.Logic.RicercaPratiche
{
    public static class AnagrafeExtensions
    {
        internal static AnagraficaIstanzaTrovata ToAnagraficaIstanzaTrovata(this Anagrafe anagrafe, bool accessoCompleto, LogicaEstrazioneUtenti.TipoSoggettoAnagrafica tipoSoggetto)
        {
            if (anagrafe == null)
            {
                return null;
            }

            var campiSemprePresenti = new[]
            {
                nameof(Anagrafe.TIPOANAGRAFE),
                nameof(Anagrafe.NOME),
                nameof(Anagrafe.NOMINATIVO),
                nameof(Anagrafe.CODICEFISCALE),
                nameof(Anagrafe.PARTITAIVA),
                nameof(Anagrafe.DATANASCITA),
                nameof(Anagrafe.CODCOMNASCITA),
                nameof(Anagrafe.SESSO)
            };

            var r = new AnagraficaIstanzaTrovata
            {
                TipoSoggetto = new AnagraficaIstanzaTrovata.TipoSoggettoAnagraficaTrovata
                {
                    Codice = tipoSoggetto?.Codice ?? -1,
                    Descrizione = tipoSoggetto?.Descrizione ?? "Non definito"
                }
            };

            if (!accessoCompleto)
            {
                r.Proprieta = campiSemprePresenti.Select(x => anagrafe.GetValoreProperty(x)).Where(x => x != null).ToList();
            }
            else
            {
                r.Proprieta = anagrafe.GetValoriProperties().ToList();
            }

            return r;
        }

        private static AnagraficaIstanzaTrovata.ValoreDatoAnagrafica GetValoreProperty(this Anagrafe anagrafe, string nomeProperty)
        {
            var pi = anagrafe.GetType().GetProperty(nomeProperty);

            if (pi == null)
            {
                return null;
            }

            return pi.EstraiValoreProperty(anagrafe);
        }

        private static AnagraficaIstanzaTrovata.ValoreDatoAnagrafica EstraiValoreProperty(this PropertyInfo pi, Anagrafe anagrafe)
        {
            bool isDateTime = pi.PropertyType == typeof(Nullable<DateTime>);

            var valore = pi.GetValue(anagrafe, null);

            if (valore == null)
            {
                return null;
            }

            return new AnagraficaIstanzaTrovata.ValoreDatoAnagrafica
            {
                Chiave = pi.Name,
                Valore = isDateTime ? ((DateTime)valore).ToString("yyyyMMdd") : valore.ToString(),
                IsDateTime = isDateTime
            };
        }

        private static IEnumerable<AnagraficaIstanzaTrovata.ValoreDatoAnagrafica> GetValoriProperties(this Anagrafe anagrafe)
        {
            var pi = anagrafe.GetType().GetProperties(BindingFlags.Public | BindingFlags.Instance);

            if (pi == null || pi.Length == 0)
            {
                return Enumerable.Empty<AnagraficaIstanzaTrovata.ValoreDatoAnagrafica>();
            }

            pi = pi.Where(x => x.GetCustomAttribute<DataFieldAttribute>() != null || x.GetCustomAttribute<KeyFieldAttribute>() != null).ToArray();

            return pi.Select(x => x.EstraiValoreProperty(anagrafe)).Where(x => x != null).ToArray();
        }
    }
}
