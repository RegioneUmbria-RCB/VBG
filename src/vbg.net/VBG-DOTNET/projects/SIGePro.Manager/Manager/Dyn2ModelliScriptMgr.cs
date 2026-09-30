
using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class Dyn2ModelliScriptMgr
    {

        public List<Dyn2ModelliScript> GetList(string idComune, int idModello)
        {
            FormattableString sql = $@"SELECT * FROM dyn2_modelli_script WHERE idcomune={idComune} AND FK_D2MT_ID={idModello}";

            return this.db.GetClassList<Dyn2ModelliScript>(sql);
        }

        public IDyn2ScriptModello? GetScriptById(string idComune, int idModello, TipoScriptEnum contesto)
        {
            var script = this.GetById(idComune, idModello, contesto.ToString());

            if (script == null)
            {
                return null;
            }
            if (String.IsNullOrEmpty(script.Checksum))
            {
                script.Checksum = this.CalcolaHash(script.GetTestoScript());
                this.Update(script);
            }

            return script;
        }

        public IEnumerable<ModelloDinamicoScriptDto> GetScriptsModello(string idComune, int idModello)
        {
            var scripts = this.GetList(idComune, idModello);

            var rVal = new List<ModelloDinamicoScriptDto>();

            // Faccio uun foreach per vedere se esistono script senza checksum, in tal caso lo creo/aggiorno
            // Una volta che sono sicuro della presenza di tutti i checksum posso fare semplicemente una select
            foreach (var script in scripts)
            {
                if (String.IsNullOrEmpty(script.Checksum))
                {
                    script.Checksum = this.CalcolaHash(script.GetTestoScript());
                    this.Update(script);
                }

                rVal.Add(new ModelloDinamicoScriptDto
                {
                    Evento = script.Evento,
                    FkD2mtId = script.FkD2mtId,
                    Script = script.Script,
                    // Checksum = x.Checksum
                });
            }

            return rVal;
        }

        public string GetTestoScript(string idComune, int idModello, TipoScriptEnum contesto)
        {
            var script = this.GetById(idComune, idModello, contesto.ToString());

            return script?.GetTestoScript() ?? String.Empty;
        }

        //public void AggiornaChecksum(string idComune, int idModello, TipoScriptEnum tipoScript)
        //{
        //    var script = this.GetById(idComune, idModello, tipoScript.ToString());
        //    if (script == null)
        //        return;
        //    script.Checksum = this.CalcolaHash(script.GetTestoScript());
        //    this.Update(script);
        //}

        public void SalvaScript(string idComune, int idModello, TipoScriptEnum tipoScript, string corpoScript, string corpoUsing, string corpoServiziIniettati)
        {
            base.db.BeginTransaction();
            try
            {
                this.AggiornaScript(idComune, idModello, tipoScript, corpoScript);
                this.AggiornaScript(idComune, idModello, TipoScriptEnum.Using, corpoUsing);
                this.AggiornaScript(idComune, idModello, TipoScriptEnum.Inject, corpoServiziIniettati);
                base.db.CommitTransaction();
            }
            catch (Exception)
            {
                base.db.RollbackTransaction();
                throw;
            }
        }

        private void AggiornaScript(string idComune, int idModello, TipoScriptEnum tipoScript, string corpoScript)
        {
            var script = this.GetById(idComune, idModello, tipoScript.ToString());
            var insert = false;

            if (script != null && String.IsNullOrEmpty(corpoScript))
            {
                this.Delete(script);
                return;
            }

            if (script == null)
            {
                script = new Dyn2ModelliScript();
                script.IdComune = idComune;
                script.FkD2mtId = idModello;
                script.Evento = tipoScript.ToString();

                insert = true;
            }

            script.SetTestoScript(corpoScript);
            script.Checksum = String.IsNullOrEmpty(corpoScript) ? String.Empty : this.CalcolaHash(corpoScript);

            if (insert)
                this.Insert(script);
            else
                this.Update(script);
        }


        /// <summary>
        /// Calcola l'hash md5 del testo dello script
        /// </summary>
        /// <param name="corpoScript">Corpo dello script di cui calcolare l'hash</param>
        /// <returns>Hash md5 del testo dello script</returns>
        /// <exception cref="NotImplementedException"></exception>
        private string CalcolaHash(string corpoScript)
        {
            using (var md5 = System.Security.Cryptography.MD5.Create())
            {
                return string.Join("", md5.ComputeHash(Encoding.UTF8.GetBytes(corpoScript)).Select(x => x.ToString("x2")));
            }
        }
    }
}
