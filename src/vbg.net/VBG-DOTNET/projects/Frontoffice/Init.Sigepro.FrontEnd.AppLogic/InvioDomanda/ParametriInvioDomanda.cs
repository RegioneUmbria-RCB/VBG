namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
    public class ParametriInvioDomanda
    {
        public bool GeneraEAllegaRiepilogoDomanda { get; private set; } = true;
        public string PecDestinatario { get; private set; } = "";
        public SportelloStcDestinatario? SportelloDestinatario { get; private set; }
        public bool GeneraEAllegaCertificatoDiInvio { get; private set; } = true;
        public bool VerificaEsistenzaPraticaNelBackoffice { get; private set; } = true;

        public static ParametriInvioDomanda DomandaOnLine() => new ParametriInvioDomanda
        {
            GeneraEAllegaRiepilogoDomanda = false,
            PecDestinatario = "",
            SportelloDestinatario = null,
            GeneraEAllegaCertificatoDiInvio = false,
            VerificaEsistenzaPraticaNelBackoffice = true
        };

        public static ParametriInvioDomanda AreaRiservata(bool generaEAllegaRiepilogoDomanda) => new ParametriInvioDomanda
        {
            GeneraEAllegaRiepilogoDomanda = generaEAllegaRiepilogoDomanda,
            PecDestinatario = "",
            SportelloDestinatario = null,
            GeneraEAllegaCertificatoDiInvio = true,
            VerificaEsistenzaPraticaNelBackoffice = true
        };

        public static ParametriInvioDomanda AreaRiservataSsu(SportelloStcDestinatario sportelloDestinatario) => new ParametriInvioDomanda
        {
            GeneraEAllegaRiepilogoDomanda = false,
            PecDestinatario = "",
            SportelloDestinatario = sportelloDestinatario,
            GeneraEAllegaCertificatoDiInvio = false,
            VerificaEsistenzaPraticaNelBackoffice = false
        };
    }
}
