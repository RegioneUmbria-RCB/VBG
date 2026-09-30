
namespace VBG.Backend.Protocollo.AppLogic.Shared.Logs
{
    public class ProtocolloWarnings
    {
        public string WarningMessage
        { 
            get { return String.Join("\r\n", _warnings); }
        }

        List<string> _warnings;

        public ProtocolloWarnings()
        {
            _warnings = new List<string>();
        }

        public void Add(string item)
        {
            _warnings.Add(item);
        }
    }
}
