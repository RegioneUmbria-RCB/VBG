using log4net;
using System;
using System.Text.Json;

namespace VBG.Backend.SIT.AppLogic.Ravenna2
{
    public class Ravenna3Result : Data.Sit
    {
        public bool ElementoTrovato { get; }
        public bool PiuDiUnElementoTrovato { get; protected set; }

        private readonly ILog _log;

        public Ravenna3Result(bool elementoTrovato, ILog log)
        {
            this.ElementoTrovato = elementoTrovato;
            this.PiuDiUnElementoTrovato = false;
            this._log = log;
        }

        protected string GetVal(JsonElement jsonElement, string fieldName, string type)
        {
            string val = null;
            try
            {
                if (type.Equals("string"))
                {
                    val = jsonElement.GetProperty(fieldName).GetString();
                }
                else if (type.Equals("number"))
                {
                    val = jsonElement.GetProperty(fieldName).GetInt32().ToString();
                }
                else
                {
                    val = jsonElement.GetProperty(fieldName).GetRawText();
                }

                if (val == null)
                {
                    val = "";
                }
            }
            catch (Exception e)
            {
                this._log.Debug(e);
            }
            return val;
        }
    }
}
