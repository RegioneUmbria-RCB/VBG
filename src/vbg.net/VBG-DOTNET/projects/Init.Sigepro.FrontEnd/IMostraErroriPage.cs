using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd
{
    public interface IMostraErroriPage
    {
        //void MostraErrori(IEnumerable<string> errori);
        //void MostraMessaggiInformativi(IEnumerable<string> errori);
        //void MostraMessaggiSuccesso(IEnumerable<string> errori);
        void MostraMessaggi(IEnumerable<string> errori, IEnumerable<string> messaggiInformativi, IEnumerable<string> messaggiSuccesso);
    }
}
