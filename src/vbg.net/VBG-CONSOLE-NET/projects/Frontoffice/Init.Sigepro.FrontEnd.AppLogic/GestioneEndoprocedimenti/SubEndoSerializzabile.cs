using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
    public class SubEndoSerializable
    {
        public static SubEndoSerializable FromSubEndoprocedimentoSelezionato(SubEndoprocedimentoSelezionato subEndo)
        {
            return new SubEndoSerializable
            {
                Id = subEndo.Id,
                IdPadre = subEndo.IdPadre
            };
        }

        [JsonProperty("id")]
        public int Id { get; set; }

        [JsonProperty("idPadre")]
        public int? IdPadre { get; set; }
    }
}
