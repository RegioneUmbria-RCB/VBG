using Init.Sigepro.FrontEnd.WebServices.Nla;

namespace AreaRiservataCore.wcf.nla
{
    public static class InserimentoAttivitaResult
    {
        public static InserimentoAttivitaNLAResponse1 Success(string identificativoDomanda, string idAttivita)
        {
            return new InserimentoAttivitaNLAResponse1
            {
                InserimentoAttivitaNLAResponse = new InserimentoAttivitaNLAResponse
                {
                    Items = [new RiferimentiAttivitaType {
                            idPratica = identificativoDomanda,
                            idAttivita = idAttivita
                        }]
                }
            };
        }
    }
}
