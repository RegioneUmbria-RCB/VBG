
using EliosWSFascicolazioneSoapClient;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    internal class CercaFascicoloResponse
    {
        public DatiFascicoloElios[] Fascicoli { get; private set; }

        internal static CercaFascicoloResponse FromWReFascicolo(wReFascicoli response)
        {
            if (response == null)
            {
                throw new Exception("La risposta ottenuta dal WS è nulla");
            }

            if (response.Esito != 0)
            {
                throw new Exception($"{response.MessaggioEsito} Codice: {response.Esito}");
            }

            return new CercaFascicoloResponse
            {
                Fascicoli = response
                                .Fascicoli
                                .Select(x => new DatiFascicoloElios
                                {
                                    Id = x.Id,
                                    Fascicolo = new DatiFascType
                                    {
                                        AnnoFascicolo = x.Anno.ToString(),
                                        NumeroFascicolo = x.Numero.ToString(),
                                        OggettoFascicolo = x.Descrizione,
                                        ClassificaFascicolo = $"{x.Categoria} {x.Classe} {x.Sottoclasse}",
                                        DataFascicolo = x.DataApertura
                                    }
                                })
                                .ToArray()
            };
        }

        internal ListaFascicoliResponseType ToListaFascicoli()
        {
            return new ListaFascicoliResponseType
            {
                Fascicolo = this.Fascicoli.Select(x => x.Fascicolo).ToArray()
            };
        }
    }
}