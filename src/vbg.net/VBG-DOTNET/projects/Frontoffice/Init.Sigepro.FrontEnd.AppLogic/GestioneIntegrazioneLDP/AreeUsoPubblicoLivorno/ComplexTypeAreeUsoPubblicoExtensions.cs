using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using Init.Sigepro.FrontEnd.Infrastructure.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno
{
    public static class ComplexTypeAreeUsoPubblicoExtensions
    {
        public static string GetStringaRipetizioni(this ComplexTypeAreeUsoPubblico aup)
        {
            if (String.IsNullOrEmpty(aup.ripetizione))
            {
                return String.Empty;
            }

            return $"{aup.ripetizione} ({aup.giorni_settimana})";
        }

        public static IEnumerable<IntervalloOccupazioneLDP> TointervalliOccupazione(this ComplexTypeAreeUsoPubblico aup)
        {
            var stringaRipetizioni = String.Empty;
            if (aup.a_periodi == null || aup.a_periodi.Length == 0)
            {
                return Enumerable.Empty<IntervalloOccupazioneLDP>();
            }

            return aup.a_periodi.Select(x =>
            {
                // Formato data: "2017-01-23 00:00:00"
                var dataInizio = DateTime.ParseExact(x.inizio, "yyyy-MM-dd HH:mm:ss", null);
                var dataFine = DateTime.ParseExact(x.fine, "yyyy-MM-dd HH:mm:ss", null);
                var descrizioni = x.a_aree == null ? new[]{String.Empty} : x.a_aree.Select(a => $"{a.identificativo} mq.{a.metri_quadrati}");
                var descrizione = String.Join(Environment.NewLine, descrizioni.ToArray());
                return new IntervalloOccupazioneLDP(dataInizio, dataFine, descrizione);
            });
        }
    }
}