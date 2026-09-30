using EliosWSFascicolazioneSoapClient;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class FascicolaResponse
    {
        public int Id { get; private set; }
        public int Numero { get; private set; }
        public int Anno { get; private set; }
        public string Categoria { get; private set; }
        public string Classe { get; private set; }
        public string DataApertura { get; private set; }
        public string Descrizione { get; private set; }
        public int IdPadre { get; private set; }
        public int Livello { get; private set; }
        public string Sottoclasse { get; private set; }
        public string Stato { get; private set; }

        internal static FascicolaResponse FromWReFascicolo(wReFascicolo response)
        {
            if (response == null)
            {
                throw new Exception("La risposta ottenuta dal WS è nulla");
            }

            if (response.Esito != 0)
            {
                throw new Exception($"{response.MessaggioEsito} Codice: {response.Esito}");
            }

            return new FascicolaResponse
            {
                Id = response.Id,
                Numero = response.Numero,
                Anno = response.Anno,
                Categoria = response.Categoria,
                Classe = response.Classe,
                DataApertura = response.DataApertura,
                Descrizione = response.Descrizione,
                IdPadre = response.IdPadre,
                Livello = response.Livello,
                Sottoclasse = response.Sottoclasse,
                Stato = response.Stato
            };
        }
    }
}