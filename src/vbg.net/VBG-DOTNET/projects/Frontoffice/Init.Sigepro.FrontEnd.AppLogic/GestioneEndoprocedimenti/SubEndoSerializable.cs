using Newtonsoft.Json;

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

        public SubEndoprocedimentoSelezionato ToSubEndoprocedimentoSelezionato()
        {
            return new SubEndoprocedimentoSelezionato(this.Id, this.IdPadre);
        }
    }
}