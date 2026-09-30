namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
    public class SubEndoprocedimentoSelezionato
	{
		public readonly int Id;
		public readonly int? IdPadre;

		public SubEndoprocedimentoSelezionato(int id)
		{
			this.Id = id;
			this.IdPadre = null;
		}

		public SubEndoprocedimentoSelezionato(int id, int? idPadre)
		{
			this.Id = id;
			this.IdPadre = idPadre;
		}

        public override bool Equals(object obj)
        {
            return obj is SubEndoprocedimentoSelezionato selezionato &&
                   Id == selezionato.Id &&
                   IdPadre == selezionato.IdPadre;
        }

        public override int GetHashCode()
        {
            int hashCode = -2077961232;
            hashCode = hashCode * -1521134295 + Id.GetHashCode();
            hashCode = hashCode * -1521134295 + IdPadre.GetHashCode();
            return hashCode;
        }
    }
}
