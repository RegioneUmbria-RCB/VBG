namespace Init.SIGePro.Manager.DTO.Interventi
{
    public class NodoAlberoInterventiDto : ClassTree<InterventoDto>
    {
        public NodoAlberoInterventiDto()
        {
            this.Elemento = new InterventoDto();
        }

    }
}
