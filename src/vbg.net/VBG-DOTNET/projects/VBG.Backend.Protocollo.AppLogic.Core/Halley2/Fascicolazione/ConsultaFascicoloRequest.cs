namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Fascicolazione
{
    public class ConsultaFascicoloRequest
    {
        /// <summary>
        /// Serial del fascicolo restituito in fase di inserimento mediante web service
        /// </summary>
        public int? IdFascicolo { get; set; }

        /// <summary>
        /// Numero progressivo del fascicolo
        /// </summary>
        public int? Progressivo { get; set; }

        /// <summary>
        /// Anno del fascicolo
        /// </summary>
        public string Anno { get; set; }

        /// <summary>
        /// La categoria con la quale è stato classificato il fascicolo da ricercare
        /// </summary>
        public int? Categoria { get; set; }

        /// <summary>
        /// La classe con la quale è stato classificato il fascicolo da ricercare
        /// </summary>
        public int? Classe { get; set; }

        /// <summary>
        /// La sottoclasse con la quale è stato classificato il fascicolo da ricercare
        /// </summary>
        public int? SottoClasse { get; set; }
    }
}
