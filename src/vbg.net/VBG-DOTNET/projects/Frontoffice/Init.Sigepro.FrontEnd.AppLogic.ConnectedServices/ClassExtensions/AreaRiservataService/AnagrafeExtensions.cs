namespace VBG.Frontend.AppLogic.WsAnagraficheService
{
    public partial class Anagrafe
    {
        public override string ToString()
        {
            string n = this.NOMINATIVO;

            if (!string.IsNullOrEmpty(this.NOME))
                n += " " + this.NOME;

            return n;
        }
    }
}